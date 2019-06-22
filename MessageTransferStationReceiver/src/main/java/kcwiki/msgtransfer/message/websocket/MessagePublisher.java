package kcwiki.msgtransfer.message.websocket;

import kcwiki.msgtransfer.websocket.handler.ProtobufTransferHandler;
import kcwiki.msgtransfer.websocket.handler.TransferHandler;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.proto.websocket.system.WebsocketSystemProto;
import org.iharu.type.ResultType;
import org.iharu.type.websocket.WebsocketMessageType;
import org.iharu.type.websocket.WebsocketSystemMessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher<T>
{
  private static final Logger LOG = LoggerFactory.getLogger(MessagePublisher.class);
  
    @Autowired
    private TransferHandler transferHandler;
    @Autowired
    private ProtobufTransferHandler protobufTransferHandler;
  
    public void publish(WebsocketProto payload, WebsocketMessageType messageType)
    {
        if(payload == null)
            return;
        switch (messageType)
        {
            case SYSTEM: 
                transferHandler.sendMessageToAllUsers(payload);
                break;
            case NON_SYSTEM: 
                transferHandler.sendMessageToAllUsers(payload);
                break;
        }
    }
    
    public void publish(byte[] payload, WebsocketMessageType messageType)
    {
        if(payload == null)
            return;
        switch (messageType)
        {
            case SYSTEM: 
                protobufTransferHandler.sendMessageToAllUsers(payload);
                break;
            case NON_SYSTEM: 
                protobufTransferHandler.sendMessageToAllUsers(payload);
                break;
        }
    }
  
    public void publish(WebsocketProto payload)
    {
        publish(payload, WebsocketMessageType.NON_SYSTEM);
    }
    
    public void publish(byte[] payload)
    {
        publish(payload, WebsocketMessageType.NON_SYSTEM);
    }

    public <T> void publish(T payload, ResultType resultType, WebsocketSystemMessageType systemMessageType)
    {
        publish(new WebsocketProto(WebsocketMessageType.SYSTEM, resultType, new WebsocketSystemProto(systemMessageType, payload)), WebsocketMessageType.SYSTEM);
    }
}
