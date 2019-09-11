/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.retweet.message.websocket;

import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;
import kcwiki.management.xcontrolled.message.websocket.XMessagePublisher;
import kcwiki.retweet.core.entity.retwitter.Rertweet;
import kcwiki.retweet.message.websocket.entity.RetweetProto;
import kcwiki.retweet.message.websocket.types.ModuleType;
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
    private XModuleCallBack xModuleCallBack;
    @Autowired
    private XMessagePublisher xMessagePublisher;
    
    @PostConstruct
    public void initMethod() throws NoSuchAlgorithmException {
        xMessagePublisher.connect(xModuleCallBack);
    }
    
    public void publish(Rertweet rertweet, ResultType resultType){
        xMessagePublisher.publishNonSystemMsg(resultType, new RetweetProto(ModuleType.Retweet, JsonUtils.object2json(rertweet)));
    }
    
    public void publish(Rertweet rertweet){
        publish(rertweet, ResultType.SUCCESS);
    }
    
}
