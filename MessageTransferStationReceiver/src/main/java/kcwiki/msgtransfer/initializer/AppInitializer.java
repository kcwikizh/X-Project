package kcwiki.msgtransfer.initializer;

import java.io.EOFException;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import kcwiki.msgtransfer.cache.inmem.RuntimeValue;
import protobuf.proto.WebsocketNonSystem;
import protobuf.proto.WebsocketSystem;
import protobuf.proto.Websocket;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.constant.ConstantValue;
import org.iharu.util.BaseConstantValue;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer
{
    private static final Logger LOG = LoggerFactory.getLogger(AppInitializer.class);
    @Autowired
    AppConfig appConfig;
    @Autowired
    RuntimeValue RUNTIME;
    boolean isInit = false;

    @PostConstruct
    public void initMethod()
    {
      if (appConfig == null) {
        LOG.error("找不到程序主配置文件 程序初始化失败。");
      }
    }

    @PreDestroy
    public void destroyMethod() {}

    public void init()
      throws IOException
    {
      LOG.info("X-Project MessageTransferStation: initialization started");
      isInit = true;
      long startTime = System.currentTimeMillis();
      startTestClient();
      startProtobufTestClient();
      long endTime = System.currentTimeMillis();
      LOG.info("AppRoot folder: {}", RUNTIME.APPROOT);
      LOG.info("Temp folder: {}", ConstantValue.TEMP_FOLDER);
      LOG.info("WebRoot folder: {}", RUNTIME.WEBROOT_FOLDER);
      if (isInit)
      {
        kcwiki.msgtransfer.cache.inmem.AppDataCache.isAppInit = true;
        LOG.info("X-Project MessageTransferStation: initialization completed in {} ms{}", endTime - startTime, BaseConstantValue.LINESEPARATOR);
      }
      else
      {
        LOG.error("X-Project MessageTransferStation: initialization failed in {} ms{}", endTime - startTime, BaseConstantValue.LINESEPARATOR);
        System.exit(0);
      }
    }

    public void startTestClient()
    {
        Map<String, String> httpHeaders = new HashMap();
        httpHeaders.put("x-access-token", Base64.getEncoder().withoutPadding().encodeToString(appConfig.getWebsocket_token().getBytes()));
        WebSocketClient wsClient = new WebSocketClient(URI.create("ws://localhost:48081/websocket/transfer"), httpHeaders)
        {
            @Override
            public void onOpen(ServerHandshake sh)
            {
              AppInitializer.LOG.info("TestClient - onOpen HttpStatus:{}, HttpStatusMessage:{}", sh.getHttpStatus(), sh.getHttpStatusMessage());
            }

            @Override
            public void onMessage(String string)
            {
              AppInitializer.LOG.info("TestClient - onMessage: {}", string);
            }

            @Override
            public void onClose(int i, String string, boolean bln)
            {
              AppInitializer.LOG.info("TestClient - onClose i:{}, string:{}, bln:{}", i, string, Boolean.valueOf(bln));
            }

            @Override
            public void onError(Exception excptn)
            {
                if(excptn instanceof EOFException)
                    return;
                AppInitializer.LOG.info("TestClient - onError excptn:{}", ExceptionUtils.getStackTrace(excptn));
            }
        };
        try
        {
            wsClient.connect();
            if (wsClient.isOpen())
            {
              wsClient.send("connected");
              LOG.info("TestClient - connected.");
            }
        }
        catch (Exception ex)
        {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }
    }
    
    public void startProtobufTestClient()
    {
        Map<String, String> httpHeaders = new HashMap();
        httpHeaders.put("x-access-token", Base64.getEncoder().withoutPadding().encodeToString(appConfig.getWebsocket_token().getBytes()));
        WebSocketClient wsClient = new WebSocketClient(URI.create("ws://localhost:48081/websocket/protobuftransfer"), httpHeaders)
        {
            @Override
            public void onOpen(ServerHandshake sh)
            {
              AppInitializer.LOG.info("ProtobufTestClient - onOpen HttpStatus:{}, HttpStatusMessage:{}", sh.getHttpStatus(), sh.getHttpStatusMessage());
            }

            @Override
            public void onMessage(String string)
            {
              AppInitializer.LOG.info("ProtobufTestClient - onMessage: {}", string);
            }
            
            @Override
            public void onMessage(ByteBuffer bb)
            {
                try{
                    Websocket websocketWapper = Websocket.parseFrom(bb);
                    if(websocketWapper == null)
                        return;
                    if(websocketWapper.getProtoType() == Websocket.ProtoType.SYSTEM){
                        WebsocketSystem websocketSystem = WebsocketSystem.parseFrom(websocketWapper.getProtoPayload());
                        if(websocketSystem == null)
                            return;
                        LOG.info("ByteData from system Received: {}", websocketSystem.getData());
                    } else {
                        WebsocketNonSystem websocketNonSystem = WebsocketNonSystem.parseFrom(websocketWapper.getProtoPayload());
                        if(websocketNonSystem == null)
                            return;
                        LOG.info("ByteData from nonsystem: {} Received: {}", websocketNonSystem.getModule(), websocketNonSystem.getData());
                    }
                } catch (Exception ex) {
                    LOG.error(ExceptionUtils.getStackTrace(ex));
                }
            }

            @Override
            public void onClose(int i, String string, boolean bln)
            {
              AppInitializer.LOG.info("ProtobufTestClient - onClose i:{}, string:{}, bln:{}", i, string, Boolean.valueOf(bln));
            }

            @Override
            public void onError(Exception excptn)
            {
                if(excptn instanceof EOFException)
                    return;
                AppInitializer.LOG.info("ProtobufTestClient - onError excptn:{}", ExceptionUtils.getStackTrace(excptn));
            }
        };
        try
        {
            wsClient.connect();
            if (wsClient.isOpen())
            {
              wsClient.send("connected");
              LOG.info("ProtobufTestClient - connected.");
            }
        }
        catch (Exception ex)
        {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }
    }
}
