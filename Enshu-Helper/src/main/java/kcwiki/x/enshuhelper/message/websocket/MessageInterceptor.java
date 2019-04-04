/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.message.websocket;

import kcwiki.x.enshuhelper.message.websocket.entity.ExchangeMessageEntity;
import kcwiki.x.enshuhelper.message.websocket.types.ExchangeMessageTypes;
import kcwiki.x.enshuhelper.web.controller.types.HttpRepStatus;
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
    
    public ExchangeMessageEntity filter(ExchangeMessageEntity _exchangeMessageEntity) {
        LOG.trace("exchangeMessageEntity - {}", _exchangeMessageEntity);
        String payload = _exchangeMessageEntity.getPayload();
        ExchangeMessageEntity exchangeMessageEntity = new ExchangeMessageEntity();
        exchangeMessageEntity.setPayload(payload);
        switch(_exchangeMessageEntity.getExchangeMessageTypes()) {
            default:
                exchangeMessageEntity.setStatus(HttpRepStatus.FAILURE);
                exchangeMessageEntity.setCode(HttpRepStatus.FAILURE.getCode());
                exchangeMessageEntity.setInfo("未知信息。");
                exchangeMessageEntity.setExchangeMessageTypes(ExchangeMessageTypes.BaseProtocol);
                break;
            case EnshuHelperRegister:
                int rs = messageProcessor.enshuHelperRegister(payload);
                exchangeMessageEntity.setExchangeMessageTypes(ExchangeMessageTypes.EnshuHelperRegister);
                exchangeMessageEntity.setStatus(HttpRepStatus.FAILURE);
                exchangeMessageEntity.setCode(HttpRepStatus.FAILURE.getCode());
                if(rs == -1) {
                    exchangeMessageEntity.setInfo("出现未知错误，无法新增用户信息。");
                    return exchangeMessageEntity;
                } else if(rs == 0) {
                    exchangeMessageEntity.setInfo("该用户已存在。");
                    return exchangeMessageEntity;
                }
                break;
            case EnshuHelperUnregister:
                rs = messageProcessor.enshuHelperUnregister(payload);
                exchangeMessageEntity.setExchangeMessageTypes(ExchangeMessageTypes.EnshuHelperUnregister);
                exchangeMessageEntity.setStatus(HttpRepStatus.FAILURE);
                exchangeMessageEntity.setCode(HttpRepStatus.FAILURE.getCode());
                switch (rs) {
                    case -1:
                        exchangeMessageEntity.setInfo("用户不存在。");
                        return exchangeMessageEntity;
                    case -2:
                        exchangeMessageEntity.setInfo("请在用户注册群进行删除操作。");
                        return exchangeMessageEntity;
                    case 0:
                        exchangeMessageEntity.setInfo("用户删除失败。");
                        return exchangeMessageEntity;
                    default:
                        break;
                }
                break;
        }
        exchangeMessageEntity.setStatus(HttpRepStatus.SUCCESS);
        exchangeMessageEntity.setCode(HttpRepStatus.SUCCESS.getCode());
        exchangeMessageEntity.setInfo("信息处理完成。");
        return exchangeMessageEntity;
    }
}
