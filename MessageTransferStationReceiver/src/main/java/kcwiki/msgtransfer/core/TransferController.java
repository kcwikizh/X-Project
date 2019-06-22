package kcwiki.msgtransfer.core;

import java.nio.ByteBuffer;
import kcwiki.msgtransfer.message.websocket.MessagePublisher;
import kcwiki.msgtransfer.protobuf.ProtobufController;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.type.ResultType;
import org.iharu.type.websocket.WebsocketMessageType;
import org.iharu.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferController<T>
{
    private static final Logger LOG = LoggerFactory.getLogger(TransferController.class);

    @Autowired
    private MessagePublisher messagePublisher;
    @Autowired
    private ProtobufController protobufController;
    

    public void ReTransfer(WebsocketProto websocketProto) {
        messagePublisher.publish(websocketProto);
    }

    public void ReTransfer(ByteBuffer buffer) {
        messagePublisher.publish(buffer.array());
    }
    
    public <T>  void TransformAndReTransfer(String module, T payload) {
        messagePublisher.publish(protobufController.Transfor(module, JsonUtils.object2json(payload)));
    }
  
}
