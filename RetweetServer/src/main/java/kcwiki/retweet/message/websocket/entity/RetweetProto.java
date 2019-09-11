/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.retweet.message.websocket.entity;

import kcwiki.management.xtraffic.base.BaseModuleProto;
import kcwiki.retweet.message.websocket.types.ModuleType;
import org.iharu.type.ResultType;

/**
 *
 * @author iHaru
 */
public class RetweetProto extends BaseModuleProto {

    private ModuleType moduleType;
    
    public RetweetProto(){}
    
    public RetweetProto(ModuleType moduleType, String modulePayload){
        super(modulePayload);
        this.moduleType = moduleType;
    }
    
    public RetweetProto(ModuleType moduleType, ResultType moduleCode, String modulePayload){
        super(moduleCode, modulePayload);
        this.moduleType = moduleType;
    }

    /**
     * @return the moduleType
     */
    public ModuleType getModuleType() {
        return moduleType;
    }

    /**
     * @param moduleType the moduleType to set
     */
    public void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
    }

}
