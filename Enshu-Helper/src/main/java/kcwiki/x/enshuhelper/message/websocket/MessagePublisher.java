/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.message.websocket;

import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;
import kcwiki.management.xcontrolled.message.websocket.XMessagePublisher;
import kcwiki.x.enshuhelper.message.websocket.entity.EnshuHelperProto;
import kcwiki.x.enshuhelper.message.websocket.entity.ExchangeProto;
import kcwiki.x.enshuhelper.message.websocket.types.EnshuDataType;
import kcwiki.x.enshuhelper.message.websocket.types.PublishTypes;
import static kcwiki.x.enshuhelper.message.websocket.types.PublishTypes.All;
import static kcwiki.x.enshuhelper.message.websocket.types.PublishTypes.Guest;
import kcwiki.x.enshuhelper.web.websocket.handler.AdministratorHandler;
import kcwiki.x.enshuhelper.web.websocket.handler.ExchangeHandler;
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
//@Scope("prototype")
public class MessagePublisher {
    private static final Logger LOG = LoggerFactory.getLogger(MessagePublisher.class);
    
    @Autowired
    XModuleCallBack xModuleCallBack;
    @Autowired
    XMessagePublisher xMessagePublisher;
    @Autowired
    ExchangeHandler exchangeHandler;
    
    @PostConstruct
    public void initMethod() throws NoSuchAlgorithmException {
        xMessagePublisher.connect(xModuleCallBack);
    }
    
    public void publish(ExchangeProto msg, PublishTypes publishTypes, ResultType resultType){
        LOG.info(JsonUtils.object2json(msg));
        exchangeHandler.sendMessageToAllUsers(msg);
        xMessagePublisher.publishNonSystemMsg(resultType, new EnshuHelperProto(resultType, msg.getModule_type(), msg.getProto_payload()));
    }
    
    public void publish(String payload, EnshuDataType enshuDataType){
        publish(new ExchangeProto(enshuDataType, payload));
    }
    
    public void publish(ExchangeProto exchangeProto){
        publish(exchangeProto, PublishTypes.All, ResultType.SUCCESS);
    }
    
}
