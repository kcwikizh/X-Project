/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.msgtransfer.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import kcwiki.msgtransfer.core.TransferController;
import kcwiki.msgtransfer.protobuf.proto.WebsocketNonSystem;
import kcwiki.msgtransfer.protobuf.proto.WebsocketSystem;
import kcwiki.msgtransfer.protobuf.proto.WebsocketWapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.proto.websocket.system.WebsocketSystemProto;
import org.iharu.type.ResultType;
import org.iharu.type.websocket.WebsocketMessageType;
import org.iharu.type.websocket.WebsocketSystemMessageType;
import org.iharu.util.JsonUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 * https://stackoverflow.com/questions/32820728/simple-protobuf-compilation-with-gradle
 * http://thoreauz.com/2018/03/24/language/java/spring-boot/spring-boot-protobuf/
 * https://blog.csdn.net/qazwsxpcm/article/details/81069833
 */
@Component
public class ProtobufController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ProtobufController.class);
    
    public WebsocketProto Transfor(ByteBuffer buffer){
        try {
            WebsocketWapper websocketWapper = WebsocketWapper.parseFrom(buffer.array());
//            websocketWapper.getProtoType() == WebsocketWapper.ProtoType.SYSTEM;
        } catch (InvalidProtocolBufferException ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }
    
    public byte[] Transfor(WebsocketProto proto){
        if(proto.getProto_type() == WebsocketMessageType.SYSTEM){
            WebsocketSystemProto systemProto = (WebsocketSystemProto) proto.getProto_payload();
            WebsocketWapper websocketWapper = 
                WebsocketWapper.newBuilder()
                    .setProtoCode(convertResultType(proto.getProto_code()))
                    .setProtoType(WebsocketWapper.ProtoType.SYSTEM)
                    .setTimestamp(System.currentTimeMillis())
                    .setSign(DigestUtils.sha256Hex(UUID.randomUUID().toString()))
                    .setProtoPayload(
                        ByteString.copyFrom(
                            WebsocketSystem.newBuilder()
                                .setMsgType(convertSystemMessageType(systemProto.getMsg_type()))
                                .setData(JsonUtils.object2json(systemProto.getData()))
                                .build().toByteArray()
                        )
                    )
                    .build();
            return websocketWapper.toByteArray();
        }
        return null;
    }
    
    public byte[] Transfor(ResultType resultType, WebsocketSystemMessageType systemMessageType, String payload){
        WebsocketWapper websocketWapper = 
                WebsocketWapper.newBuilder()
                    .setProtoCode(convertResultType(resultType))
                    .setProtoType(WebsocketWapper.ProtoType.SYSTEM)
                    .setTimestamp(System.currentTimeMillis())
                    .setSign(DigestUtils.sha256Hex(UUID.randomUUID().toString()))
                    .setProtoPayload(
                        ByteString.copyFrom(
                            WebsocketSystem.newBuilder()
                                .setMsgType(convertSystemMessageType(systemMessageType))
                                .setData(payload)
                                .build().toByteArray()
                        )
                    )
                    .build();
        return websocketWapper.toByteArray();
    }
    
    public byte[] Transfor(ResultType proto_code, String module, String payload){
        WebsocketWapper websocketWapper = 
                WebsocketWapper.newBuilder()
                    .setProtoCode(WebsocketWapper.ProtoCode.SUCCESS)
                    .setProtoType(WebsocketWapper.ProtoType.NON_SYSTEM)
                    .setTimestamp(System.currentTimeMillis())
                    .setSign(DigestUtils.sha256Hex(UUID.randomUUID().toString()))
                    .setProtoPayload(
                        ByteString.copyFrom(
                            WebsocketNonSystem.newBuilder()
                                .setModule(module)
                                .setData(payload)
                                .build().toByteArray()
                        )
                    )
                    .build();
        return websocketWapper.toByteArray();
    }
    
    public byte[] Transfor(String module, String payload){
        return Transfor(ResultType.SUCCESS, module, payload);
    }
    
    public WebsocketWapper.ProtoCode convertResultType(ResultType resultType){
        switch(resultType){
            case SUCCESS:
                return WebsocketWapper.ProtoCode.SUCCESS;
            case FAIL:
                return WebsocketWapper.ProtoCode.FAIL;
            case ERROR:
                return WebsocketWapper.ProtoCode.ERROR;
        }
        return WebsocketWapper.ProtoCode.UNRECOGNIZED;
    }
    
    public WebsocketSystem.SystemMessageType convertSystemMessageType(WebsocketSystemMessageType systemMessageType){
        switch(systemMessageType){
            case SYSTEM_INFO:
                return WebsocketSystem.SystemMessageType.SYSTEM_INFO;
            case DEBUG_MSG:
                return WebsocketSystem.SystemMessageType.DEBUG_MSG;
            case PAYLOAD_ERROR:
                return WebsocketSystem.SystemMessageType.PAYLOAD_ERROR;
            case CLIENT_REBOOT:
                return WebsocketSystem.SystemMessageType.CLIENT_REBOOT;
            case CLIENT_SHUTDOWN:
                return WebsocketSystem.SystemMessageType.CLIENT_SHUTDOWN;
            case SERVER_REBOOT:
                return WebsocketSystem.SystemMessageType.SERVER_REBOOT;
            case SERVER_SHUTDOWN:
                return WebsocketSystem.SystemMessageType.SERVER_SHUTDOWN;
            case AUTHORIZATION_REQUIRED:
                return WebsocketSystem.SystemMessageType.AUTHORIZATION_REQUIRED;
            case AUTHORIZATION_FAIL:
                return WebsocketSystem.SystemMessageType.AUTHORIZATION_FAIL;
            case AUTHORIZATION_SUCCESS:
                return WebsocketSystem.SystemMessageType.AUTHORIZATION_SUCCESS;
            case PING:
                return WebsocketSystem.SystemMessageType.PING;
        }
        return WebsocketSystem.SystemMessageType.UNRECOGNIZED;
    }
    
}
