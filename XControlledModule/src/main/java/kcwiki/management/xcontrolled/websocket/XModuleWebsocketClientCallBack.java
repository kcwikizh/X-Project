/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.xcontrolled.websocket;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import kcwiki.management.xcontrolled.core.XModuleController;
import kcwiki.management.xcontrolled.exception.XControlledModuleConnectFailException;
import org.iharu.crypto.aes.AesUtils;
import org.iharu.crypto.rsa.RSAUtils;
import kcwiki.management.xtraffic.protobuf.ProtobufUtils;
import org.iharu.exception.BaseException;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.proto.websocket.system.WebsocketSystemProto;
import org.iharu.type.ResultType;
import org.iharu.type.error.ErrorType;
import org.iharu.type.websocket.WebsocketMessageType;
import org.iharu.util.JsonUtils;
import org.iharu.util.StackTraceUtils;
import org.iharu.websocket.client.WebsocketClientCallBack;
import org.iharu.websocket.util.WebsocketUtils;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;

/**
 *
 * @author iHaru
 */
public abstract class XModuleWebsocketClientCallBack extends WebsocketClientCallBack {
    
    private byte[] symmetricKey;
    
    private XModuleWebsocketClient websocketClient;
    
    @Override
    protected XModuleWebsocketClient getWebsocketClient(){
        return websocketClient;
    }
    
    @Override
    protected void callback(TextMessage paramTextMessage) {
        getImplLogger().info("websocket client {} received: {}", getWebsocketClient().getName(), paramTextMessage.getPayload());
        throw new XControlledModuleConnectFailException(ErrorType.OPERATION_ERROR, paramTextMessage.getPayload());
    }

    @Override
    protected void callback(BinaryMessage paramBinaryMessage) {
        byte[] payload = paramBinaryMessage.getPayload().array();
        if(payload.length == 0)
            return;
        try {
            byte[] data = AesUtils.Decrypt(payload, symmetricKey);
            if(data == null)
                return;
            WebsocketProto proto = ProtobufUtils.TransforAndConvert(data);
            if(proto.getProto_type() == WebsocketMessageType.SYSTEM){
                try {
                    WebsocketSystemProto systemproto = WebsocketUtils.SystemMessageDecoder(proto.getProto_payload());
                    switch(systemproto.getMsg_type()) {
                        case PING: {
                            websocketClient.send(WebsocketUtils.PONGMessage());
                            break;
                        }
                        case PONG: {
                            break;
                        }
                        default: {
                            getImplLogger().info("websocket client {} received system msg: {} {}", websocketClient.getName(), systemproto.getMsg_type(), systemproto.getData());
                        }
                    }
                } catch (IOException ex) {
                    getImplLogger().error("decode system payload fdiled", ex);
                }
                return;
            }
            getImplLogger().debug("proto: {}", JsonUtils.object2json(proto));
            moduleCallback(proto);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException | InvalidKeySpecException ex) {
            getImplLogger().warn("decrypt failed", ex);
        }
    }
    
    protected abstract void moduleCallback(String paramTextMessage);
    
    protected abstract void moduleCallback(WebsocketProto proto);

    /**
     * @param symmetricKey the symmetricKey to set
     */
    public final void setPrivKey(byte[] symmetricKey) {
        if(!StackTraceUtils.IsCallerLegal(XModuleController.class.getName(), "connect"))
            throw new BaseException(ErrorType.OPERATION_ERROR, "Insufficient permissions with class: " + StackTraceUtils.GetUpperCaller().getClassName());
        this.symmetricKey = symmetricKey;
    }
    
    public final void setWebsocketClient(XModuleWebsocketClient websocketClient){
        if(!StackTraceUtils.IsCallerLegal(XModuleController.class.getName(), "connect"))
            throw new BaseException(ErrorType.OPERATION_ERROR, "Insufficient permissions with class: " + StackTraceUtils.GetUpperCaller().getClassName());
        this.websocketClient = websocketClient;
    }
    
}
