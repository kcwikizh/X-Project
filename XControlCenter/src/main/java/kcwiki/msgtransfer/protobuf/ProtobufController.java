/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.msgtransfer.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import javax.validation.constraints.NotNull;
import protobuf.proto.Websocket;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.exception.BaseException;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.proto.websocket.system.WebsocketSystemProto;
import org.iharu.type.ResultType;
import org.iharu.type.error.ErrorType;
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
    
    public WebsocketProto Transfor(byte[] bytes){
        try {
            Websocket websocketWapper = Websocket.parseFrom(bytes);
            if(websocketWapper == null)
                return null;
            if(websocketWapper.getProtoType() == Websocket.ProtoType.SYSTEM) {
                return new WebsocketProto(convertProtoCode(websocketWapper.getProtoCode()), bytes2string(websocketWapper.getProtoPayload().toByteArray()));
            } else {
                return new WebsocketProto(convertProtoCode(websocketWapper.getProtoCode()), websocketWapper.getProtoModule(), bytes2string(websocketWapper.getProtoPayload().toByteArray()));
            }
        } catch (InvalidProtocolBufferException ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }
    
    public WebsocketProto Transfor(ByteBuffer buffer){
        return Transfor(buffer.array());
    }
    
    public byte[] Transfor(WebsocketProto proto) throws IOException {
        byte[] data = JsonUtils.json2bytes(proto.getProto_payload());
        Websocket websocketWapper = 
                Websocket.newBuilder()
                    .setProtoCode(convertResultType(proto.getProto_code()))
                    .setProtoType(convertWebsocketMessageType(proto.getProto_type()))
                    .setTimestamp(System.currentTimeMillis())
                    .setSign(DigestUtils.sha256Hex(data))
                    .setProtoPayload(ByteString.copyFrom(data))
                    .build();
        return websocketWapper.toByteArray();
    }
    
    public byte[] Transfor(ResultType resultType, WebsocketSystemMessageType systemMessageType, String payload){
        if(StringUtils.isBlank(payload))
            throw new BaseException(ErrorType.PARAMETER_ERROR, "payload 不能为空");
        byte[] data = JsonUtils.object2bytes(new WebsocketSystemProto(systemMessageType, payload));
        Websocket websocketWapper = 
                Websocket.newBuilder()
                    .setProtoCode(convertResultType(resultType))
                    .setProtoType(Websocket.ProtoType.SYSTEM)
                    .setTimestamp(System.currentTimeMillis())
                    .setSign(DigestUtils.sha256Hex(data))
                    .setProtoPayload(ByteString.copyFrom(data))
                    .build();
        return websocketWapper.toByteArray();
    }
    
    public byte[] Transfor(ResultType proto_code, @NotNull String module, String payload){
        if(StringUtils.isBlank(payload))
            throw new BaseException(ErrorType.PARAMETER_ERROR, "payload 不能为空");
        if(StringUtils.isBlank(module))
            throw new BaseException(ErrorType.PARAMETER_ERROR, "module 不能为空");
        byte[] data = string2bytes(payload);
        Websocket websocketWapper = 
                Websocket.newBuilder()
                    .setProtoCode(convertResultType(proto_code))
                    .setProtoType(Websocket.ProtoType.NON_SYSTEM)
                    .setProtoModule(module)
                    .setTimestamp(System.currentTimeMillis())
                    .setSign(DigestUtils.sha256Hex(data))
                    .setProtoPayload(ByteString.copyFrom(data))
                    .build();
        return websocketWapper.toByteArray();
    }
    
    public <T> byte[] Transfor(@NotNull String module, String payload){
        return Transfor(ResultType.SUCCESS, module, payload);
    }
    
    public Websocket.ProtoCode convertResultType(ResultType resultType){
        switch(resultType){
            case SUCCESS:
                return Websocket.ProtoCode.SUCCESS;
            case FAILURE:
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
                return ResultType.FAILURE;
            default:
                return ResultType.ERROR;
        }
    }
    
    public Websocket.ProtoType convertWebsocketMessageType(WebsocketMessageType messageType){
        switch(messageType){
            case SYSTEM:
                return Websocket.ProtoType.SYSTEM;
            case NON_SYSTEM:
                return Websocket.ProtoType.NON_SYSTEM;
        }
        return Websocket.ProtoType.UNRECOGNIZED;
    }
    
    public WebsocketMessageType convertProtoType(Websocket.ProtoType protoType){
        switch(protoType){
            case SYSTEM:
                return WebsocketMessageType.SYSTEM;
            case NON_SYSTEM:
                return WebsocketMessageType.NON_SYSTEM;
        }
        return WebsocketMessageType.NON_SYSTEM;
    }
    
    public byte[] string2bytes(String str){
        return str.getBytes(StandardCharsets.UTF_8);
    }
    
    public String bytes2string(byte[] bytes){
        return new String(bytes, StandardCharsets.UTF_8);
    }
    
}
