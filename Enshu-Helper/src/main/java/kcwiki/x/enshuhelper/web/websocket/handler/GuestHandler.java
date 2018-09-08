/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.web.websocket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kcwiki.x.enshuhelper.message.websocket.MessageInterceptor;
import kcwiki.x.enshuhelper.message.websocket.entity.ExchangeMessageEntity;
import kcwiki.x.enshuhelper.message.websocket.types.ExchangeMessageTypes;
import kcwiki.x.enshuhelper.web.controller.types.HttpRepStatus;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author x5171
 */
@Component
public class GuestHandler extends TextWebSocketHandler {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(GuestHandler.class);
    
    @Autowired
    MessageInterceptor messageInterceptor;
    
    //在线用户列表
    private static final Map<String, WebSocketSession> users = new ConcurrentHashMap<>();
    //用户标识
    private static final String SESSION_USER = "user";
    
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 连接已关闭，移除在Map集合中的记录
     * @param session
     * @param status
     * @throws java.lang.Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        users.remove(getUserId(session));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        LOG.error("连接出错");
        users.remove(getUserId(session));
    }

    /**
     * 连接建立成功之后，记录用户的连接标识，便于后面发信息
     * @param session
     * @throws java.lang.Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOG.info("成功建立连接");
        String userId = getUserId(session);
        LOG.info("{}", userId);
        if (userId != null) {
            users.put(userId, session);
            ExchangeMessageEntity exchangeMessageEntity = new ExchangeMessageEntity();
            exchangeMessageEntity.setStatus(HttpRepStatus.SUCCESS);
            exchangeMessageEntity.setCode(HttpRepStatus.SUCCESS.getCode());
            exchangeMessageEntity.setExchangeMessageTypes(ExchangeMessageTypes.BaseProtocol);
            exchangeMessageEntity.setInfo("连接成功");
            session.sendMessage(new TextMessage(Object2Json(exchangeMessageEntity)));
            LOG.info("{}", userId);
            LOG.info("{}", session);
        }
    }

    /**
     * 处理收到的websocket信息
     * @param session
     * @param message
     * @throws java.lang.Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
//        LOG.trace("payload{}", payload);
        ExchangeMessageEntity exchangeMessageEntity;
        try {
            exchangeMessageEntity = objectMapper.readValue(payload,
                new TypeReference<ExchangeMessageEntity>(){});
        } catch (IOException ex) {
            LOG.info("转换数据时发生错误 payload： {}", payload);
            LOG.info(ExceptionUtils.getStackTrace(ex));
            sendMessageToUser(session.getId(), payloadError());
            return;
        }
        LOG.trace("messageInterceptor{}", messageInterceptor);
//        MessageInterceptor _messageInterceptor = (MessageInterceptor) AppDataCache.ctx.getBean("messageInterceptor");
//        LOG.trace("_messageInterceptor{}", _messageInterceptor);
        sendMessageToUser(session.getId(), Object2Json(messageInterceptor.filter(exchangeMessageEntity)));
    }

     /**
     * 发送信息给指定用户
     * @param clientId
     * @param message
     * @return
     */
    public boolean sendMessageToUser(String clientId, String message) {
        if (users.get(clientId) == null) {
            return false;
        }

        WebSocketSession session = users.get(clientId);
        LOG.info("sendMessage:{}", session);

        if (!session.isOpen()) {
            return false;
        }
        try {
            TextMessage textMessage = new TextMessage(message);
            session.sendMessage(textMessage);
            return true;
        } catch (IOException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
            return false;
        }
    }

    /**
     * 广播信息
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsers(String message) {
        boolean allSendSuccess = true;
        Set<String> clientIds = users.keySet();
        WebSocketSession session;
//        LOG.trace(message);
        TextMessage textMessage = new TextMessage(message);
        for (String clientId : clientIds) {
            try {
                session = users.get(clientId);
                if (session.isOpen()) {
                    session.sendMessage(textMessage);
                }
            } catch (IOException e) {
                LOG.error(ExceptionUtils.getStackTrace(e));
                allSendSuccess = false;
            }
        }
        return  allSendSuccess;
    }

     /**
     * 获取用户标识
     * @param session
     * @return
     */
    private String getUserId(WebSocketSession session) {
        try {
//            UserBean userBean = (UserBean) session.getAttributes().get(SESSION_USER);
//            return userBean.getId();
            return session.getId();
        } catch (Exception e) {
            return null;
        }
    }
    
    private String payloadError() {
        ExchangeMessageEntity exchangeMessageEntity = new ExchangeMessageEntity();
        exchangeMessageEntity.setStatus(HttpRepStatus.ERROR);
        exchangeMessageEntity.setCode(HttpRepStatus.ERROR.getCode());
        exchangeMessageEntity.setInfo("上传数据格式有误，解析出错。");
        exchangeMessageEntity.setExchangeMessageTypes(ExchangeMessageTypes.PayloadError);
        return Object2Json(exchangeMessageEntity);
    }
    
    private String Object2Json(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            LOG.error("转换数据时出错：{}, 原始数据为：{}", ExceptionUtils.getStackTrace(ex), obj);
        }
        return "null";
    }
}
