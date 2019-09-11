/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.message.websocket;

import com.fasterxml.jackson.core.type.TypeReference;
import kcwiki.management.xcontrolled.websocket.XModuleWebsocketClientCallBack;
import kcwiki.x.enshuhelper.message.websocket.entity.EnshuHelperProto;
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
public class XModuleCallBack extends XModuleWebsocketClientCallBack {
    private static final Logger LOG = LoggerFactory.getLogger(XModuleCallBack.class);
    
    @Autowired
    MessageInterceptor messageInterceptor;
    
    @Override
    protected void moduleCallback(String paramTextMessage) {
        LOG.info("received string data: {}", paramTextMessage);
    }

    @Override
    protected void moduleCallback(WebsocketProto proto) {
        LOG.info("received proto data: {}", JsonUtils.object2json(proto));
        EnshuHelperProto _proto = JsonUtils.json2objectWithoutThrowException(proto.getProto_payload(), new TypeReference<EnshuHelperProto>(){});
        if(_proto == null)
            return;
        _proto = messageInterceptor.filter(_proto);
        getWebsocketClient().send(new WebsocketProto(ResultType.SUCCESS, null, null, proto.getProto_sender(), JsonUtils.object2json(_proto)));
    }

    @Override
    protected Logger getImplLogger() {
        return LOG;
    }
    
}
