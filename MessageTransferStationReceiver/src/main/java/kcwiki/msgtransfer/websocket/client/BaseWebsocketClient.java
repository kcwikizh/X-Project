package kcwiki.msgtransfer.websocket.client;

import java.io.EOFException;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class BaseWebsocketClient
{
    private static final Logger LOG = LoggerFactory.getLogger(BaseWebsocketClient.class);
    protected String name;
    protected String description;
    protected URI url;
    protected BaseClientCallBack callbackImpl;
    protected WebSocketClient webSocketClient;
    protected WebSocketSession webSocketSession;
    protected AtomicInteger retrycount = new AtomicInteger(0);

    private BaseWebsocketClient() {}

    public BaseWebsocketClient(String name, String url, BaseClientCallBack callback)
    {
        this(name, null, url, callback);
    }

    public BaseWebsocketClient(String name, String description, String url, BaseClientCallBack callback)
    {
        this.name = name;
        this.description = description;
        this.url = URI.create(url);
        callbackImpl = callback;
    }

    public boolean connect()
    {
        try
        {
            if (webSocketClient == null) {
              webSocketClient = new StandardWebSocketClient();
            }
            if ((webSocketSession == null) || (!webSocketSession.isOpen())) {
                webSocketSession = ((WebSocketSession)webSocketClient.doHandshake(new TextWebSocketHandler()
                {
                    @Override
                    public void handleTextMessage(WebSocketSession session, TextMessage message)
                    {
                        callbackImpl.callback(message);
                    }

                    @Override
                    public void afterConnectionEstablished(WebSocketSession session)
                    {
                        BaseWebsocketClient.LOG.info("websocket: {} established connection", name);
                        retrycount = new AtomicInteger(0);
                    }

                    @Override
                    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
                      throws Exception
                    {
                        BaseWebsocketClient.LOG.info("websocket: {} connection closed. code: {}, reason:{}", name, status.getCode(), status.getReason());
                        ReconnectContorller.reconnect(name, retrycount.getAndIncrement());
                    }

                    @Override
                    public void handleTransportError(WebSocketSession session, Throwable ex)
                      throws Exception
                    {
                        if (session.isOpen()) {
                          session.close();
                        }
                        if(ex instanceof EOFException)
                            return;
                        LOG.error("websocket: {} TransportError:{}", name, ExceptionUtils.getStackTrace(ex));
                    }
                }, new WebSocketHttpHeaders(), url).get());
            }
            return webSocketSession.isOpen();
        }
        catch (InterruptedException|ExecutionException e)
        {
          LOG.error("websocket: {} Exception while accessing websockets", name, e);
        }
        return false;
    }

    public void close()
      throws IOException
    {
        if ((webSocketSession != null) && (webSocketSession.isOpen())) {
            webSocketSession.close();
        }
    }
}
