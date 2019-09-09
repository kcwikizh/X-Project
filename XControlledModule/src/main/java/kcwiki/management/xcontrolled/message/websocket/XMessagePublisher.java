/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.xcontrolled.message.websocket;

import java.security.NoSuchAlgorithmException;
import kcwiki.management.xcontrolled.configuration.XModuleConfig;
import kcwiki.management.xcontrolled.core.XModuleController;
import kcwiki.management.xcontrolled.websocket.XModuleWebsocketClientCallBack;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.type.ResultType;
import org.iharu.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
public class XMessagePublisher <T> {
    private static final Logger LOG = LoggerFactory.getLogger(XMessagePublisher.class);
    
    @Autowired
    private XModuleController xModuleController;

    private String identity;
    
    public void connect(XModuleWebsocketClientCallBack callback) {
        xModuleController.connect(callback);
        identity = xModuleController.getIdentity();
    }
    
    public void publishNonSystemMsg(ResultType result, T payload){
        if(payload instanceof String)
            xModuleController.send(new WebsocketProto(result, identity, (String) payload));
        else
            xModuleController.send(new WebsocketProto(result, identity, JsonUtils.object2json(payload)));
    }
    
    public void publishNonSystemMsgWithTrack(ResultType result, String sender, String recipient, T payload){
        if(payload instanceof String)
            xModuleController.send(new WebsocketProto(result, identity, sender, recipient, (String) payload));
        else
            xModuleController.send(new WebsocketProto(result, identity, sender, recipient, JsonUtils.object2json(payload)));
    }
    
    public void publishSystemMsg(ResultType result, T payload){
        if(payload instanceof String)
            xModuleController.send(new WebsocketProto(result, (String) payload));
        else
            xModuleController.send(new WebsocketProto(result, JsonUtils.object2json(payload)));
    }
    
}
