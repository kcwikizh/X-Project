/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.message.websocket.entity;

import kcwiki.x.enshuhelper.message.websocket.types.EnshuDataType;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.type.ResultType;
import org.iharu.type.websocket.WebsocketMessageType;

/**
 *
 * @author iHaru
 */
public class ExchangeProto extends WebsocketProto {

    private EnshuDataType module_type;
    
    public ExchangeProto(){}
    
    public ExchangeProto(EnshuDataType module_type, String proto_payload){
        this.proto_type = WebsocketMessageType.NON_SYSTEM;
        this.module_type = module_type;
        this.proto_payload = proto_payload;
        this.proto_code = ResultType.SUCCESS;
    }
    
    public ExchangeProto(ResultType proto_code, EnshuDataType module_type, String proto_payload){
        this.proto_type = WebsocketMessageType.NON_SYSTEM;
        this.module_type = module_type;
        this.proto_payload = proto_payload;
        this.proto_code = proto_code;
    }
    
    /**
     * @return the module_type
     */
    public EnshuDataType getModule_type() {
        return module_type;
    }

    /**
     * @param module_type the module_type to set
     */
    public void setModule_type(EnshuDataType module_type) {
        this.module_type = module_type;
    }
}
