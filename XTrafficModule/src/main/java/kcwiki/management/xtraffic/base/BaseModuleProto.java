/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.xtraffic.base;

import org.iharu.type.MsgType;
import org.iharu.type.ResultType;

/**
 *
 * @author iHaru
 */
public class BaseModuleProto <T> {
    private ResultType moduleCode;
    private String modulePayload;
    private MsgType msgType = MsgType.INFO;
    
    public BaseModuleProto(){}
    
    public BaseModuleProto(String modulePayload){
        this.modulePayload = modulePayload;
        this.moduleCode = ResultType.SUCCESS;
    }
    
    public BaseModuleProto(ResultType moduleCode, String modulePayload){
        this.modulePayload = modulePayload;
        this.moduleCode = moduleCode;
    }

    /**
     * @return the moduleCode
     */
    public ResultType getModuleCode() {
        return moduleCode;
    }

    /**
     * @param moduleCode the moduleCode to set
     */
    public void setModuleCode(ResultType moduleCode) {
        this.moduleCode = moduleCode;
    }

    /**
     * @return the modulePayload
     */
    public String getModulePayload() {
        return modulePayload;
    }

    /**
     * @param modulePayload the modulePayload to set
     */
    public void setModulePayload(String modulePayload) {
        this.modulePayload = modulePayload;
    }

    /**
     * @return the msgType
     */
    public MsgType getMsgType() {
        return msgType;
    }

    /**
     * @param msgType the msgType to set
     */
    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }
    
}
