package kcwiki.retweet.websocket.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.websocket.handler.DefaultWebsocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class RetweetHandler
  extends DefaultWebsocketHandler
{
    private static final Logger LOG = LoggerFactory.getLogger(RetweetHandler.class);
    
    private static final Map<String, WebSocketSession> USERS = new ConcurrentHashMap();
  
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
    {
        String payload = message.getPayload();
        LOG.info("incoming msg: {}", payload);
    }
  
    @Override
    protected Logger GetImplLogger()
    {
        return LOG;
    }
    
    @Override
    protected Map GetUsers() {
        return USERS;
    }
}
