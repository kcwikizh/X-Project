/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.msgtransfer.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import java.nio.ByteBuffer;
import java.util.UUID;
import protobuf.proto.WebsocketNonSystem;
import protobuf.proto.WebsocketSystem;
import protobuf.proto.Websocket;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.proto.websocket.nonsystem.WebsocketNonSystemProto;
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
            Websocket websocketWapper = Websocket.parseFrom(buffer.array());
            if(websocketWapper == null)
                return null;
            if(websocketWapper.getProtoType() == Websocket.ProtoType.SYSTEM) {
                WebsocketSystem websocketSystem = WebsocketSystem.parseFrom(websocketWapper.getProtoPayload());
                return new WebsocketProto(WebsocketMessageType.SYSTEM, convertProtoCode(websocketWapper.getProtoCode()), 
                        new WebsocketSystemProto(convertWebsocketSystemMessageType(websocketSystem.getMsgType()), websocketSystem.getData()));
            } else {
                WebsocketNonSystem websocketNonSystem = WebsocketNonSystem.parseFrom(websocketWapper.getProtoPayload());
                return new WebsocketProto(WebsocketMessageType.NON_SYSTEM, convertProtoCode(websocketWapper.getProtoCode()), 
                        new WebsocketNonSystemProto(websocketNonSystem.getModule(), websocketNonSystem.getData()));
            }
        } catch (InvalidProtocolBufferException ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }
    
    public byte[] Transfor(WebsocketProto proto){
        if(proto.getProto_type() == WebsocketMessageType.SYSTEM){
            WebsocketSystemProto systemProto = (WebsocketSystemProto) proto.getProto_payload();
            Websocket websocketWapper = 
                Websocket.newBuilder()
                    .setProtoCode(convertResultType(proto.getProto_code()))
                    .setProtoType(Websocket.ProtoType.SYSTEM)
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
        Websocket websocketWapper = 
                Websocket.newBuilder()
                    .setProtoCode(convertResultType(resultType))
                    .setProtoType(Websocket.ProtoType.SYSTEM)
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
        Websocket websocketWapper = 
                Websocket.newBuilder()
                    .setProtoCode(Websocket.ProtoCode.SUCCESS)
                    .setProtoType(Websocket.ProtoType.NON_SYSTEM)
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
    
    public Websocket.ProtoCode convertResultType(ResultType resultType){
        switch(resultType){
            case SUCCESS:
                return Websocket.ProtoCode.SUCCESS;
            case FAIL:
                return Websocket.ProtoCode.FAIL;
            case ERROR:
                return Websocket.ProtoCode.ERROR;
        }
        return Websocket.ProtoCode.UNRECOGNIZED;
    }
    
    public ResultType convertProtoCode(Websocket.ProtoCode protoCode){
        switch(protoCode){
            case SUCCESS:
                return ResultType.SUCCESS;
            case FAIL:
                return ResultType.FAIL;
            default:
                return ResultType.ERROR;
        }
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
    
    public WebsocketSystemMessageType convertWebsocketSystemMessageType(WebsocketSystem.SystemMessageType systemMessageType){
        switch(systemMessageType){
            case DEBUG_MSG:
                return WebsocketSystemMessageType.DEBUG_MSG;
            case PAYLOAD_ERROR:
                return WebsocketSystemMessageType.PAYLOAD_ERROR;
            case CLIENT_REBOOT:
                return WebsocketSystemMessageType.CLIENT_REBOOT;
            case CLIENT_SHUTDOWN:
                return WebsocketSystemMessageType.CLIENT_SHUTDOWN;
            case SERVER_REBOOT:
                return WebsocketSystemMessageType.SERVER_REBOOT;
            case SERVER_SHUTDOWN:
                return WebsocketSystemMessageType.SERVER_SHUTDOWN;
            case AUTHORIZATION_REQUIRED:
                return WebsocketSystemMessageType.AUTHORIZATION_REQUIRED;
            case AUTHORIZATION_FAIL:
                return WebsocketSystemMessageType.AUTHORIZATION_FAIL;
            case AUTHORIZATION_SUCCESS:
                return WebsocketSystemMessageType.AUTHORIZATION_SUCCESS;
            case PING:
                return WebsocketSystemMessageType.PING;
            default:
                return WebsocketSystemMessageType.SYSTEM_INFO;
        }
    }
    
}
