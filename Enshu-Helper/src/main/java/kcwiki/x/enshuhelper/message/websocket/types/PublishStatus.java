/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.message.websocket.types;

import kcwiki.x.enshuhelper.types.EnumBase;

/**
 *
 * @author iHaru
 */
public enum PublishStatus implements EnumBase{
    SUCCESS(0),
    NORMAL(1),
    ERROR(2)
    ;
    
    private int code;
    
    PublishStatus(int code) {
        this.code = code;
    }
    
    @Override
    public String getName() {
        switch(code) {
            default:
                return "未知";
            case 0:
                return "成功";
            case 1:
                return "普通";
            case 2:
                return "错误";
        }
    }

    @Override
    public int getCode() {
        return code;
    }
}
