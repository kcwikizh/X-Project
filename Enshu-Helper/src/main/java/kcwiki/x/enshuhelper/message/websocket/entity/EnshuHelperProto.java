/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.message.websocket.entity;

import kcwiki.x.enshuhelper.message.websocket.types.EnshuDataType;
import org.iharu.type.ResultType;

/**
 *
 * @author iHaru
 */
public class EnshuHelperProto {
    private ResultType proto_code;
    private String proto_payload;
    private EnshuDataType proto_type;
    
    public EnshuHelperProto(){}
    
    public EnshuHelperProto(EnshuDataType proto_type, String proto_payload){
        this.proto_type = proto_type;
        this.proto_payload = proto_payload;
        this.proto_code = ResultType.SUCCESS;
    }
    
    public EnshuHelperProto(ResultType proto_code, EnshuDataType proto_type, String proto_payload){
        this.proto_type = proto_type;
        this.proto_payload = proto_payload;
        this.proto_code = proto_code;
    }

    /**
     * @return the proto_code
     */
    public ResultType getProto_code() {
        return proto_code;
    }

    /**
     * @param proto_code the proto_code to set
     */
    public void setProto_code(ResultType proto_code) {
        this.proto_code = proto_code;
    }

    /**
     * @return the proto_payload
     */
    public String getProto_payload() {
        return proto_payload;
    }

    /**
     * @param proto_payload the proto_payload to set
     */
    public void setProto_payload(String proto_payload) {
        this.proto_payload = proto_payload;
    }

    /**
     * @return the proto_type
     */
    public EnshuDataType getProto_type() {
        return proto_type;
    }

    /**
     * @param proto_type the proto_type to set
     */
    public void setProto_type(EnshuDataType proto_type) {
        this.proto_type = proto_type;
    }

    
}
