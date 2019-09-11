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
import kcwiki.x.enshuhelper.message.websocket.types.ModuleType;
import org.iharu.type.ResultType;
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
    
    @PostConstruct
    public void initMethod() throws NoSuchAlgorithmException {
        xMessagePublisher.connect(xModuleCallBack);
    }
    
    public void publish(EnshuHelperProto proto, ResultType resultType){
        xMessagePublisher.publishNonSystemMsg(resultType, proto);
    }
    
    public void publish(String payload, ModuleType enshuDataType){
        publish(new EnshuHelperProto(enshuDataType, payload));
    }
    
    public void publish(EnshuHelperProto enshuHelperProto){
        publish(enshuHelperProto, ResultType.SUCCESS);
    }
    
}
