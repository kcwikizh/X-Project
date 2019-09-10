package kcwiki.management.controlcenter.initializer;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.EOFException;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import kcwiki.management.controlcenter.cache.inmem.RuntimeValue;
import static kcwiki.management.controlcenter.constant.ConstantValue.TEMP_FOLDER;
import kcwiki.management.controlcenter.database.service.UtilsService;
import protobuf.proto.Websocket;
import org.apache.commons.lang3.exception.ExceptionUtils;
import static org.iharu.constant.ConstantValue.LINESEPARATOR;
import org.iharu.initializer.InitializerInterface;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.proto.websocket.system.WebsocketSystemProto;
import org.iharu.type.websocket.WebsocketMessageType;
import org.iharu.util.JsonUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements InitializerInterface 
{
    private static final Logger LOG = LoggerFactory.getLogger(AppInitializer.class);
    @Autowired
    AppConfig appConfig;
    @Autowired
    UtilsService utilsService;
    @Autowired
    RuntimeValue RUNTIME;
    boolean isInit = false;

    @PostConstruct
    @Override
    public void initMethod()
    {
      if (appConfig == null) {
        LOG.error("找不到程序主配置文件 程序初始化失败。");
      }
    }

    @PreDestroy
    @Override
    public void destroyMethod() {}

    @Override
    public void init()
      throws IOException
    {
      LOG.info("X-Project MessageTransferStation: initialization started");
      isInit = true;
      long startTime = System.currentTimeMillis();
      checkDatabase();
//      startTestClient();
//      startProtobufTestClient();
      long endTime = System.currentTimeMillis();
      LOG.info("AppRoot folder: {}", RUNTIME.APPROOT);
      LOG.info("Temp folder: {}", TEMP_FOLDER);
      LOG.info("WebRoot folder: {}", RUNTIME.WEBROOT_FOLDER);
      if (isInit)
      {
            kcwiki.management.controlcenter.cache.inmem.AppDataCache.isAppInit = true;
        LOG.info("X-Project MessageTransferStation: initialization completed in {} ms{}", endTime - startTime, LINESEPARATOR);
      }
      else
      {
        LOG.error("X-Project MessageTransferStation: initialization failed in {} ms{}", endTime - startTime, LINESEPARATOR);
        System.exit(0);
      }
    }
    
    private void checkDatabase() {
        utilsService.createModuleAuthorizationTable();
        utilsService.createModuleIdentityTable();
        utilsService.createModuleTokenTable();
        utilsService.createUserAuthenticationTable();
        utilsService.createAuthorizationLogTable();
        utilsService.createSystemLogTable();
    }

    public void startTestClient()
    {
        Map<String, String> httpHeaders = new HashMap();
        httpHeaders.put("x-access-token", Base64.getEncoder().withoutPadding().encodeToString(appConfig.getWebsocket_token().getBytes()));
        WebSocketClient wsClient = new WebSocketClient(URI.create("ws://localhost:58080/websocket/transfer"), httpHeaders)
        {
            @Override
            public void onOpen(ServerHandshake sh)
            {
              AppInitializer.LOG.info("TestClient - onOpen HttpStatus:{}, HttpStatusMessage:{}", sh.getHttpStatus(), sh.getHttpStatusMessage());
            }

            @Override
            public void onMessage(String string)
            {
                WebsocketProto websocketProto = JsonUtils.json2objectWithoutThrowException(string, new TypeReference<WebsocketProto>(){});
                if(websocketProto == null)
                    return;
                if(websocketProto.getProto_type()== WebsocketMessageType.SYSTEM){
                    try {
                        WebsocketSystemProto websocketSystemProto = JsonUtils.json2object(websocketProto.getProto_payload(), new TypeReference<WebsocketSystemProto>(){});
                        if(websocketSystemProto == null)
                            return;
                        LOG.info("StringData from system Received: {}", websocketSystemProto.getData());
                    } catch (IOException ex) {
                        LOG.error(ExceptionUtils.getStackTrace(ex));
                    }
                } else {
                     LOG.info("StringData from nonsystem: {} Received: {}", websocketProto.getProto_module(), websocketProto.getProto_payload());
                }
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
        WebSocketClient wsClient = new WebSocketClient(URI.create("ws://localhost:58080/websocket/protobuftransfer"), httpHeaders)
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
                        WebsocketSystemProto websocketSystemProto = JsonUtils.json2object(websocketWapper.getProtoPayload().toByteArray(), new TypeReference<WebsocketSystemProto>(){});
                        if(websocketSystemProto == null)
                            return;
                        LOG.info("ByteData from system Received: {}", websocketSystemProto.getData());
                    } else {
                        LOG.info("ByteData from nonsystem: {} Received: {}", websocketWapper.getProtoModule(), new String(websocketWapper.getProtoPayload().toByteArray(), StandardCharsets.UTF_8));
                    }
                } catch (IOException ex) {
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
