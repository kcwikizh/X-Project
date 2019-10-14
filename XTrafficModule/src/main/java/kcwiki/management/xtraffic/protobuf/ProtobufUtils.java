/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.xtraffic.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import javax.validation.constraints.NotNull;
import org.iharu.auth2.utils.AuthenticationUtils;
import protobuf.proto.XTrafficProto;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.exception.BaseException;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.proto.websocket.system.WebsocketSystemProto;
import org.iharu.type.ResultType;
import org.iharu.type.error.ErrorType;
import org.iharu.type.websocket.WebsocketMessageType;
import org.iharu.type.websocket.WebsocketSystemMessageType;
import org.iharu.util.JsonUtils;
import org.iharu.util.StringUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 * https://stackoverflow.com/questions/32820728/simple-protobuf-compilation-with-gradle
 * http://thoreauz.com/2018/03/24/language/java/spring-boot/spring-boot-protobuf/
 * https://blog.csdn.net/qazwsxpcm/article/details/81069833
 */
public class ProtobufUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ProtobufUtils.class);
    
    public static WebsocketProto TransforAndConvert(byte[] bytes){
        if(bytes == null)
            return null;
        try {
            XTrafficProto websocketWapper = XTrafficProto.parseFrom(bytes);
            if(websocketWapper == null)
                return null;
            if(websocketWapper.getProtoType() == XTrafficProto.ProtoType.SYSTEM) {
                return new WebsocketProto(convertProtoCode(websocketWapper.getProtoCode()), 
                        StringUtils.ByteArrayToString(websocketWapper.getProtoPayload().toByteArray()),
                        websocketWapper.getTimestamp(),
                        websocketWapper.getSign());
            } else {
                byte[] data = websocketWapper.getProtoPayload().toByteArray();
                return new WebsocketProto(convertProtoCode(websocketWapper.getProtoCode()), 
                        websocketWapper.getProtoModule(), 
                        websocketWapper.getProtoSender(), 
                        websocketWapper.getProtoRecipient(), 
                        StringUtils.ByteArrayToString(data),
                        websocketWapper.getTimestamp()==0?AuthenticationUtils.GetTimestamp():websocketWapper.getTimestamp(),
                        DigestUtils.sha512Hex(data));
            }
        } catch (InvalidProtocolBufferException ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }
    
    public static WebsocketProto TransforAndConvert(ByteBuffer buffer){
        return TransforAndConvert(buffer.array());
    }
    
    public static XTrafficProto Transfor(byte[] bytes){
        try {
            return XTrafficProto.parseFrom(bytes);
        } catch (InvalidProtocolBufferException ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }
    
    public static XTrafficProto Transfor(ByteBuffer buffer){
        return Transfor(buffer.array());
    }
    
    public static byte[] TransforAndConvert(WebsocketProto proto) {
        byte[] data = StringUtils.StringToByteArray(proto.getProto_payload());
        XTrafficProto websocketWapper = 
                XTrafficProto.newBuilder()
                    .setProtoCode(convertResultType(proto.getProto_code()))
                    .setProtoType(convertWebsocketMessageType(proto.getProto_type()))
                    .setProtoModule(proto.getProto_module()==null?"":proto.getProto_module())
                    .setProtoSender(proto.getProto_sender()==null?"":proto.getProto_sender())
                    .setProtoRecipient(proto.getProto_recipient()==null?"":proto.getProto_recipient())
                    .setProtoPayload(ByteString.copyFrom(data))
                    .setTimestamp(proto.getTimestamp()==0?AuthenticationUtils.GetTimestamp():proto.getTimestamp())
                    .setSign(DigestUtils.sha512Hex(data))
                    .build();
        return websocketWapper.toByteArray();
    }
    
    public static byte[] Transfor(ResultType resultType, WebsocketSystemMessageType systemMessageType, String payload){
        if(StringUtils.isNullOrWhiteSpace(payload))
            throw new BaseException(ErrorType.PARAMETER_ERROR, "payload 不能为空");
        byte[] data = JsonUtils.object2bytes(new WebsocketSystemProto(systemMessageType, payload));
        XTrafficProto websocketWapper = 
                XTrafficProto.newBuilder()
                    .setProtoCode(convertResultType(resultType))
                    .setProtoType(XTrafficProto.ProtoType.SYSTEM)
                    .setProtoPayload(ByteString.copyFrom(data))
                    .setTimestamp(AuthenticationUtils.GetTimestamp())
                    .setSign(DigestUtils.sha512Hex(data))
                    .build();
        return websocketWapper.toByteArray();
    }
    
    public static byte[] Transfor(ResultType proto_code, @NotNull String module, String sender, String recipient, String payload){
        if(StringUtils.isNullOrWhiteSpace(payload))
            throw new BaseException(ErrorType.PARAMETER_ERROR, "payload 不能为空");
        if(StringUtils.isNullOrWhiteSpace(module))
            throw new BaseException(ErrorType.PARAMETER_ERROR, "module 不能为空");
        byte[] data = StringUtils.StringToByteArray(payload);
        XTrafficProto websocketWapper = 
                XTrafficProto.newBuilder()
                    .setProtoCode(convertResultType(proto_code))
                    .setProtoType(XTrafficProto.ProtoType.NON_SYSTEM)
                    .setProtoModule(module)
                    .setProtoSender(sender==null?"":sender)
                    .setProtoRecipient(recipient==null?"":recipient)
                    .setProtoPayload(ByteString.copyFrom(data))
                    .setTimestamp(AuthenticationUtils.GetTimestamp())
                    .setSign(DigestUtils.sha512Hex(data))
                    .build();
        return websocketWapper.toByteArray();
    }
    
    public static <T> byte[] Transfor(@NotNull String module, String sender, String recipient, String payload){
        return Transfor(ResultType.SUCCESS, module, sender, recipient, payload);
    }
    
    public static XTrafficProto.ProtoCode convertResultType(ResultType resultType){
        switch(resultType){
            case SUCCESS:
                return XTrafficProto.ProtoCode.SUCCESS;
            case FAILURE:
                return XTrafficProto.ProtoCode.FAILURE;
            case ERROR:
                return XTrafficProto.ProtoCode.ERROR;
        }
        return XTrafficProto.ProtoCode.UNRECOGNIZED;
    }
    
    public static ResultType convertProtoCode(XTrafficProto.ProtoCode protoCode){
        switch(protoCode){
            case SUCCESS:
                return ResultType.SUCCESS;
            case FAILURE:
                return ResultType.FAILURE;
            default:
                return ResultType.ERROR;
        }
    }
    
    public static XTrafficProto.ProtoType convertWebsocketMessageType(WebsocketMessageType messageType){
        switch(messageType){
            case SYSTEM:
                return XTrafficProto.ProtoType.SYSTEM;
            case NON_SYSTEM:
                return XTrafficProto.ProtoType.NON_SYSTEM;
        }
        return XTrafficProto.ProtoType.UNRECOGNIZED;
    }
    
    public static WebsocketMessageType convertProtoType(XTrafficProto.ProtoType protoType){
        switch(protoType){
            case SYSTEM:
                return WebsocketMessageType.SYSTEM;
            case NON_SYSTEM:
                return WebsocketMessageType.NON_SYSTEM;
        }
        return WebsocketMessageType.NON_SYSTEM;
    }
    
}
