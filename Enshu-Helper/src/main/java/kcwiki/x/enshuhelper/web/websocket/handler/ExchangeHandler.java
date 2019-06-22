/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.web.websocket.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kcwiki.x.enshuhelper.message.websocket.MessageInterceptor;
import kcwiki.x.enshuhelper.message.websocket.entity.ExchangeProto;
import kcwiki.x.enshuhelper.message.websocket.types.EnshuDataType;
import org.iharu.websocket.handler.DefaultWebsocketHandler;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.type.ResultType;
import org.iharu.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author iHaru
 */
@Service
public class ExchangeHandler extends DefaultWebsocketHandler {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ExchangeHandler.class);
    
    private static final Map<String, WebSocketSession> USERS = new ConcurrentHashMap();
    
    @Autowired
    MessageInterceptor messageInterceptor;

    /**
     * 处理收到的websocket信息
     * @param session
     * @param message
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        try {
            ExchangeProto exchangeProto = JsonUtils.json2object(payload, new TypeReference<ExchangeProto>(){});
            if(exchangeProto == null)
                return;
            sendMessageToUser(getUserId(session), messageInterceptor.filter(exchangeProto));
        } catch (IOException ex) {
            LOG.info("转换数据时发生错误 payload： {}", payload);
            LOG.info(ExceptionUtils.getStackTrace(ex));
            sendMessageToUser(getUserId(session), payloadError());
        }
    }
    
    private ExchangeProto payloadError() {
        return new ExchangeProto(ResultType.ERROR, EnshuDataType.PayloadError, "上传数据格式有误，解析出错。");
    }

    @Override
    protected Logger GetImplLogger() {
        return LOG;
    }

    @Override
    protected Map GetUsers() {
        return USERS;
    }
}
