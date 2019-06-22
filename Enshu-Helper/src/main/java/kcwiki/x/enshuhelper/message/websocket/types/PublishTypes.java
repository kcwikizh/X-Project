/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.message.websocket.types;

/**
 *
 * @author iHaru
 */
public enum PublishTypes{
    Admin(0),
    Guest(1),
    All(2)
    ;
    
    private int code;
    
    PublishTypes(int code) {
        this.code = code;
    }

}
