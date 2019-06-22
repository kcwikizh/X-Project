/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.message.websocket;

import com.fasterxml.jackson.core.type.TypeReference;
import kcwiki.x.enshuhelper.message.websocket.entity.ExchangeProto;
import kcwiki.x.enshuhelper.message.websocket.entity.UserData;
import kcwiki.x.enshuhelper.message.websocket.types.EnshuDataType;
import static org.iharu.type.ResultType.FAIL;
import static org.iharu.type.ResultType.SUCCESS;
import org.iharu.util.JsonUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
public class MessageInterceptor {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MessageInterceptor.class);
    
    @Autowired
    MessageProcessor messageProcessor;
    
    public ExchangeProto filter(ExchangeProto proto) {
        ExchangeProto rsproto = new ExchangeProto();
        rsproto.setProto_code(FAIL);
        String payload = proto.getProto_payload();
        UserData userData = JsonUtils.json2objectWithoutThrowException(payload, new TypeReference<UserData>(){});
        if(userData == null){
            rsproto.setModule_type(EnshuDataType.PayloadError);
            rsproto.setProto_payload(rsproto.getProto_payload());
            return rsproto;
        }
        UserData rsdata = new UserData();
        rsdata.setQq(userData.getQq());
        rsdata.setQqgroup(userData.getQqgroup());
        rsproto.setModule_type(EnshuDataType.SystemInfo);
        switch(proto.getModule_type()) {
            default:
                rsproto.setModule_type(EnshuDataType.PayloadError);
                rsproto.setProto_payload(rsproto.getProto_payload());
                break;
            case EnshuHelperRegister:
                int rs = messageProcessor.enshuHelperRegister(userData);
                if(rs == -1) {
                    rsdata.setComments("出现未知错误，无法新增用户信息。");
                    rsproto.setProto_payload(JsonUtils.object2json(rsdata));
                    return rsproto;
                } else if(rs == 0) {
                    rsdata.setComments(userData.getMemberid() + "该用户已存在。");
                    rsproto.setProto_payload(JsonUtils.object2json(rsdata));
                    return rsproto;
                }
                rsdata.setComments(userData.getMemberid() + "用户注册成功。");
                break;
            case EnshuHelperUnregister:
                rs = messageProcessor.enshuHelperUnregister(userData);
                switch (rs) {
                    case -1:
                        rsdata.setComments(userData.getMemberid() + "用户不存在。");
                        rsproto.setProto_payload(JsonUtils.object2json(rsdata));
                        return rsproto;
                    case -2:
                        rsdata.setComments(userData.getMemberid() + "请在用户注册群进行删除操作。");
                        rsproto.setProto_payload(JsonUtils.object2json(rsdata));
                        return rsproto;
                    case 0:
                        rsdata.setComments(userData.getMemberid() + "用户删除失败。");
                        rsproto.setProto_payload(JsonUtils.object2json(rsdata));
                        return rsproto;
                    default:
                        break;
                }
                rsdata.setComments(userData.getMemberid() + "用户删除成功。");
                break;
        }
        rsproto.setProto_code(SUCCESS);
        rsproto.setProto_payload(JsonUtils.object2json(rsdata));
        return rsproto;
    }
}
