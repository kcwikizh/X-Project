/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.xtraffic.websocket;

import kcwiki.management.xtraffic.crypto.rsa.RSAUtil;
import kcwiki.management.xtraffic.protobuf.ProtobufUtils;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.type.websocket.WebsocketMessageType;
import org.iharu.websocket.client.BaseWebsocketClient;
import org.iharu.websocket.client.WebsocketClientCallBack;
import org.slf4j.Logger;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import protobuf.proto.XTrafficProto;

/**
 *
 * @author iHaru
 */
public abstract class ModuleWebsocketClientCallBack extends WebsocketClientCallBack {
    
    @Override
    protected abstract ModuleWebsocketClient getWebsocketClient();
    
    @Override
    protected void callback(TextMessage paramTextMessage) {
        getImplLogger().info("websocket client {} received: {}", getWebsocketClient().getName(), paramTextMessage.getPayload());
    }

    @Override
    protected void callback(BinaryMessage paramBinaryMessage) {
        byte[] payload = paramBinaryMessage.getPayload().array();
        if(payload.length == 0)
            return;
        byte[] data = RSAUtil.DecryptWithPrivateKey(payload, getWebsocketClient().getPrivateKey());
        if(data == null)
            return;
        WebsocketProto proto = ProtobufUtils.TransforAndConvert(data);
        if(proto.getProto_type() == WebsocketMessageType.SYSTEM){
            getImplLogger().info("websocket client {} received system msg: {}", getWebsocketClient().getName(), proto.getProto_payload());
            return;
        }
        moduleCallback(proto);
    }
    
    protected abstract void moduleCallback(String paramTextMessage);
    
    protected abstract void moduleCallback(WebsocketProto proto);
    
}
