package kcwiki.msgtransfer.websocket.handler;

import kcwiki.msgtransfer.core.TransferController;
import kcwiki.msgtransfer.protobuf.ProtobufController;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.proto.websocket.WebsocketProto;
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
    @Autowired
    TransferController transferController;
    @Autowired
    ProtobufController protobufController;
  
        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message)
        {
            String payload = message.getPayload();
            LOG.info("incoming msg: {}", payload);
//            try{
//                WebsocketProto websocketProto = proto2object(session, payload);
//                if (websocketProto == null) {
//                    return;
//                }
//                transferController.ReTransfer(websocketProto);
//            } catch (Exception ex) {
//                LOG.error(ExceptionUtils.getStackTrace(ex));
//            }
        }
        
        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            String userId = getUserId(session);
            getImplLogger().debug("user: {} connected", userId);
            if (userId != null) {
                USERS.put(userId, session);
                sendMessageToUser(userId, protobufController.Transfor(ResultType.SUCCESS, WebsocketSystemMessageType.SYSTEM_INFO, "连接服务器成功"));
            }
        }

        @Override
        protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message)
        {
            LOG.info("incoming msg: {}", message.getPayload().array());
        }
  
    @Override
    protected Logger getImplLogger()
    {
      return LOG;
    }
}
