/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.message.websocket.types;

import kcwiki.x.enshuhelper.types.EnumBase;

/**
 *
 * @author x5171
 */
public enum ExchangeMessageTypes implements EnumBase{
    PayloadError(-1),
    BaseProtocol(0),
    EnshuHelperRegister(1),
    EnshuHelperUnregister(2),
    EnshuHelperInform(3)
    ; 
    
    private int code;
    
    ExchangeMessageTypes(int code) {
        this.code = code;
    }
    
    @Override
    public String getName() {
        switch(code) {
            default:
                return "未知";
            case 0:
                return "演习助手";
        }
    }

    @Override
    public int getCode() {
        return code;
    }
}
