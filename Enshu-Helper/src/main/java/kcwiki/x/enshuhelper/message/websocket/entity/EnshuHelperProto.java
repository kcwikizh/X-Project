/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.message.websocket.entity;

import kcwiki.management.xtraffic.base.BaseModuleProto;
import kcwiki.x.enshuhelper.message.websocket.types.ModuleType;
import org.iharu.type.ResultType;

/**
 *
 * @author iHaru
 */
public class EnshuHelperProto extends BaseModuleProto {

    private ModuleType moduleType;
    
    public EnshuHelperProto(){}
    
    public EnshuHelperProto(ModuleType moduleType, String modulePayload){
        super(modulePayload);
        this.moduleType = moduleType;
    }
    
    public EnshuHelperProto(ModuleType moduleType, ResultType moduleCode, String modulePayload){
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
