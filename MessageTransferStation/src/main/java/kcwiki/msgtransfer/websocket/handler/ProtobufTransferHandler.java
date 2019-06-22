package kcwiki.msgtransfer.websocket.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kcwiki.msgtransfer.core.TransferController;
import kcwiki.msgtransfer.protobuf.ProtobufController;
import org.iharu.type.ResultType;
import org.iharu.type.websocket.WebsocketSystemMessageType;
import org.iharu.websocket.handler.DefaultWebsocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class ProtobufTransferHandler
  extends DefaultWebsocketHandler
{
    private static final Logger LOG = LoggerFactory.getLogger(ProtobufTransferHandler.class);
    
    private static final Map<String, WebSocketSession> USERS = new ConcurrentHashMap();
    
    @Autowired
    TransferController transferController;
    @Autowired
    ProtobufController protobufController;
        
    
        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            String userId = getUserId(session);
            LOG.debug("user: {} connected", userId);
            if (userId != null) {
                registerUser(userId, session);
                sendMessageToUser(userId, protobufController.Transfor(ResultType.SUCCESS, WebsocketSystemMessageType.SYSTEM_INFO, "连接服务器成功"));
            } else {
                LOG.error("session: {} could not find userid", session);
            }
        }

        @Override
        protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message)
        {
            byte[] bytes = message.getPayload().array();
            LOG.info("incoming msg: {}", bytes);
            transferController.ReTransfer2S(protobufController.Transfor(bytes));
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

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        
    }
}
