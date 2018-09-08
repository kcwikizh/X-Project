/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.message.websocket;

import kcwiki.x.enshuhelper.message.websocket.types.PublishStatus;
import kcwiki.x.enshuhelper.message.websocket.types.PublishTypes;
import static kcwiki.x.enshuhelper.message.websocket.types.PublishTypes.All;
import static kcwiki.x.enshuhelper.message.websocket.types.PublishTypes.Guest;
import kcwiki.x.enshuhelper.web.websocket.handler.AdministratorHandler;
import kcwiki.x.enshuhelper.web.websocket.handler.GuestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author x5171
 */
@Component
//@Scope("prototype")
public class MessagePublisher {
    static final Logger LOG = LoggerFactory.getLogger(MessagePublisher.class);
    
    @Autowired
    AdministratorHandler administratorHandler;
    @Autowired
    GuestHandler guestHandler;
    
    public void publish(String msg, PublishTypes publishTypes, PublishStatus publishStatus){
        switch (publishTypes) {
            case Admin:
                administratorHandler.sendMessageToAllUsers(msg);
                break;
            case Guest:
                guestHandler.sendMessageToAllUsers(msg);
                break;
            case All:
                administratorHandler.sendMessageToAllUsers(msg);
                guestHandler.sendMessageToAllUsers(msg);
                break;
            default:
                break;
        }
    }
}
