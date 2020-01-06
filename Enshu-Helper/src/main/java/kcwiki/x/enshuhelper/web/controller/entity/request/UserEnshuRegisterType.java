/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.web.controller.entity.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @author iHaru
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserEnshuRegisterType {
    QQ("qq"),
    DISCORD("discord")
    ;
    
    @JsonValue
    private final String displayName;
    
    UserEnshuRegisterType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
}
