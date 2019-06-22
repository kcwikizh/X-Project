package kcwiki.msgtransfer.websocket.handler;

import kcwiki.msgtransfer.core.TransferController;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.websocket.handler.DefaultWebsocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class TransferHandler
  extends DefaultWebsocketHandler
{
    private static final Logger LOG = LoggerFactory.getLogger(TransferHandler.class);
    @Autowired
    TransferController transferController;
  
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
    {
        String payload = message.getPayload();
            LOG.info("incoming msg: {}", payload);
            try{
                WebsocketProto websocketProto = proto2object(session, payload);
                if (websocketProto == null) {
                    return;
                }
                transferController.ReTransfer(websocketProto);
            } catch (Exception ex) {
                LOG.error(ExceptionUtils.getStackTrace(ex));
            }
    }
  
    @Override
    protected Logger getImplLogger()
    {
        return LOG;
    }
}
