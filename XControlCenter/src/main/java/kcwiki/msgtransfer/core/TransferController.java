package kcwiki.msgtransfer.core;

import com.fasterxml.jackson.core.type.TypeReference;
import javax.validation.constraints.NotNull;
import io.micrometer.core.instrument.util.StringUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import kcwiki.management.controlcenter.cache.inmem.AppDataCache;
import kcwiki.management.controlcenter.message.websocket.MessagePublisher;
import kcwiki.msgtransfer.protobuf.ProtobufController;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.exception.BaseException;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.proto.websocket.system.WebsocketSystemProto;
import org.iharu.type.ResultType;
import org.iharu.type.error.ErrorType;
import org.iharu.type.websocket.WebsocketMessageType;
import org.iharu.util.JsonUtils;
import org.iharu.websocket.client.BaseWebsocketClient;
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
    
    public void ReTransfer2S(@NotNull WebsocketProto websocketProto) {
        if(websocketProto.getProto_code() != ResultType.SUCCESS){
            LOG.warn(JsonUtils.object2json(websocketProto));
            return;
        }
        if(websocketProto.getProto_type() == WebsocketMessageType.SYSTEM) {
            WebsocketSystemProto systemProto = JsonUtils.json2objectWithoutThrowException(websocketProto.getProto_payload(), new TypeReference<WebsocketSystemProto>(){});
            LOG.info("receive system data, type:{} payload:{}", systemProto.getMsg_type(), systemProto.getData());
        } else {
            String module = websocketProto.getProto_module();
            if(StringUtils.isBlank(module))
                throw new BaseException(ErrorType.PARAMETER_ERROR, "module 不能为空");
            if(!AppDataCache.WebsocketClients.containsKey(module)){
                LOG.warn("找不到 {} 模块", module);
                return;
            }
            BaseWebsocketClient wsClient = AppDataCache.WebsocketClients.get(module);
            try {
                wsClient.send(websocketProto.getProto_payload());
            } catch (IOException ex) {
                LOG.error(ExceptionUtils.getStackTrace(ex));
            }
        }
            
    }
    
//    public void ReTransfer2S(byte[] bytes) {
//        
//    }
    
    public void ReTransfer2S(ByteBuffer buffer) {
        messagePublisher.publish(buffer.array());
    }
    
    public void ReTransfer2C(WebsocketProto websocketProto) {
        messagePublisher.publish(websocketProto);
    }
    
    public void ReTransfer2C(byte[] bytes) {
        messagePublisher.publish(bytes);
    }
    
    public void ReTransfer2C(@NotNull String module, String payload) {
        ReTransfer2C(new WebsocketProto(module, payload));
    }

    public void ReTransfer2C(ByteBuffer buffer) {
        ReTransfer2C(buffer.array());
    }
    
    public <T>  void TransformAndReTransfer2C(@NotNull String module, String payload) {
        ReTransfer2C(protobufController.Transfor(module, payload));
    }
  
}
