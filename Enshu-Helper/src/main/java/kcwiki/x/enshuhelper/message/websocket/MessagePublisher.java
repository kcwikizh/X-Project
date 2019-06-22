/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.message.websocket;

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
    static final Logger LOG = LoggerFactory.getLogger(MessagePublisher.class);
    
    @Autowired
    ExchangeHandler exchangeHandler;
    
    public void publish(ExchangeProto msg, PublishTypes publishTypes, ResultType resultType){
        LOG.info(JsonUtils.object2json(msg));
        exchangeHandler.sendMessageToAllUsers(msg);
    }
    
    public void publish(String payload, EnshuDataType enshuDataType){
        publish(new ExchangeProto(enshuDataType, payload));
    }
    
    public void publish(ExchangeProto exchangeProto){
        publish(exchangeProto, PublishTypes.All, ResultType.SUCCESS);
    }
    
}
