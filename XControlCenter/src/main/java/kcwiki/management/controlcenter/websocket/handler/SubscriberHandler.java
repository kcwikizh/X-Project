/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.controlcenter.websocket.handler;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import kcwiki.management.controlcenter.cache.inmem.AppDataCache;
import kcwiki.management.controlcenter.database.entity.ModuleAuthorization;
import kcwiki.management.controlcenter.database.service.ModuleUtilsService;
import kcwiki.management.controlcenter.initializer.AppConfig;
import kcwiki.management.controlcenter.web.controller.entity.AuthVoucher;
import org.iharu.crypto.aes.AesUtils;
import kcwiki.management.controlcenter.websocket.entity.ByteArrayContainer;
import kcwiki.management.controlcenter.websocket.entity.ModuleNotification;
import kcwiki.management.xtraffic.keepalive.KeepAlive;
import kcwiki.management.xtraffic.protobuf.ProtobufUtils;
import org.iharu.auth2.utils.AuthenticationUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.proto.websocket.system.WebsocketSystemProto;
import org.iharu.type.ResultType;
import org.iharu.type.websocket.WebsocketMessageType;
import org.iharu.type.websocket.WebsocketSystemMessageType;
import org.iharu.util.Base64Utils;
import org.iharu.util.JsonUtils;
import org.iharu.util.StringUtils;
import org.iharu.web.session.entity.SessionEntity;
import org.iharu.websocket.handler.DefaultWebsocketHandler;
import org.iharu.websocket.util.WebsocketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author iHaru
 */
@Service
public class SubscriberHandler extends DefaultWebsocketHandler {
    private static final Logger LOG = LoggerFactory.getLogger(SubscriberHandler.class);
    
    private static final Map<String, WebSocketSession> USERS = new ConcurrentHashMap();
    private static final Map<String, List<String>> IDENTITYS = new ConcurrentHashMap();
    private static final String IDENTITY = "IDENTITY";
    private static final String ENCRYPT_KEY = "ENCRYPT_KEY";
    private static final String AUTHORIZATIONS_INTERACT = "AUTHORIZATIONS_INTERACT";
    private String notifierUserID = null;
    
    @Autowired
    ModuleUtilsService moduleUtilsService;
    @Autowired
    AppConfig appConfig;
    
    @PostConstruct
    public void initMethod()
    {
        if (appConfig.isXtraffic_keepalive_enable()) {
            Runnable keepAliveTask = () -> {
                byte[] data = ProtobufUtils.TransforAndConvert(WebsocketUtils.PINGMessage());
                List<WebSocketSession> waitforclean = new ArrayList();
                USERS.values().forEach(session -> {
                    try {
                        if(!sendMessageToUser(session, 
                                AesUtils.Encrypt(data, 
                                        ((ByteArrayContainer) session.getAttributes().get(ENCRYPT_KEY)).getArray())))
                        {
                            waitforclean.add(session);
                        }
                    } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeySpecException ex) {
                        LOG.warn("send proto failed in: {}", (String) session.getAttributes().get(IDENTITY), ex);
                    }
                });
                waitforclean.forEach(session -> handleClose(session));
                waitforclean.clear();
            };
            KeepAlive.newTask(keepAliveTask, appConfig.getXtraffic_keepalive_period());
            LOG.info("subscriber handler keepalive task set. period: {}", appConfig.getXtraffic_keepalive_period());
        }
    }
    
    @Override
    protected Logger GetImplLogger() {
        return LOG;
    }

    @Override
    protected Map GetUsers() {
        return USERS;
    }
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        try {
            String userId = getUserId(session);
            if (userId == null) {
                GetImplLogger().error("session: {} could not find userid", session);
                return;
            }
            registerUser(userId, session);
            SessionEntity sessionEntity = (SessionEntity) session.getAttributes().get(SESSION_DATA);
            if(sessionEntity == null) {
                sendMessageToUser(userId, "authentication failed");
                handleClose(session);
                return;
            }
            String voucher = sessionEntity.getVoucher();
            if(!AppDataCache.Vouchers.containsKey(voucher)) {
                sendMessageToUser(userId, "authentication not found");
                handleClose(session);
                return;
            }
            
            AuthVoucher authVoucher = AppDataCache.Vouchers.get(voucher);
            if(AuthenticationUtils.isAuthRequestTimeout(authVoucher.getTimestamp())){
                sendMessageToUser(userId, "authentication timeout");
                handleClose(session);
                return;
            }
            String identity = authVoucher.getIdentity();
            if(appConfig.getXtraffic_notifier_identity().equals(identity))
                notifierUserID = userId;
            if(!IDENTITYS.containsKey(identity))
                IDENTITYS.put(identity, new ArrayList());
            IDENTITYS.get(identity).add(userId);
            List<ModuleAuthorization> authorizations = moduleUtilsService.getAuthorizations(authVoucher.getToken());
            HashSet interact = new HashSet();
            authorizations.forEach(item -> {
                if(item.getTransmit()== 1) {
                    interact.add(item.getIdentity_name());
                }
                if(item.getReceive()== 1) {
                    if(!AppDataCache.Subscribers.containsKey(item.getIdentity_name()))
                        AppDataCache.Subscribers.put(item.getIdentity_name(), new HashSet());
                    AppDataCache.Subscribers.get(item.getIdentity_name()).add(userId);
                }
            });
            ByteArrayContainer bytesContainer = new ByteArrayContainer(Base64Utils.DecryptBase64(authVoucher.getKey()));
            session.getAttributes().put(IDENTITY, identity);
            session.getAttributes().put(ENCRYPT_KEY, bytesContainer);
            session.getAttributes().put(AUTHORIZATIONS_INTERACT, interact);
//            AppDataCache.Vouchers.remove(voucher);
            sendConnectedMsg(userId);
            LOG.info("identity {} connected, id {}", identity, userId);
        } catch (Exception ex) {
            GetImplLogger().error("init connection error", ex);
            handleClose(session);
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        WebsocketProto proto = null;
        byte[] key = null;
        String identity = (String) session.getAttributes().get(IDENTITY);
        if(StringUtils.isNullOrWhiteSpace(identity)){
            handleClose(session);
            return;
        }
        if(!message.getPayload().hasArray()){
            LOG.warn("Payload array is empty");
            return;
        }
        try {
            if(!session.getAttributes().containsKey(ENCRYPT_KEY)){
                LOG.error("{} ByteArrayContainer property not exist", identity);
                handleClose(session);
                return;
            }else if((key = ((ByteArrayContainer) session.getAttributes().get(ENCRYPT_KEY)).getArray()) == null){
                LOG.error("{} ENCRYPT_KEY bytes is empty", identity);
                return;
            }
            proto = ProtobufUtils.TransforAndConvert(AesUtils.Decrypt(message.getPayload().array(), key));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException | InvalidKeySpecException ex) {
            LOG.warn("websocket client {} decrypt failed", identity, ex);
            return;
        }
        if(proto == null){
            return;
        }
        
        proto.setProto_sender(getUserId(session));
        if(proto.getProto_type() == WebsocketMessageType.SYSTEM){
            try {
                WebsocketSystemProto systemproto = WebsocketUtils.SystemMessageDecoder(proto.getProto_payload());
                switch(systemproto.getMsg_type()) {
                    case PING: {
                        try {
                            sendMessageToUser(session, 
                                    AesUtils.Encrypt(ProtobufUtils.TransforAndConvert(WebsocketUtils.PONGMessage()), ((ByteArrayContainer) session.getAttributes().get(ENCRYPT_KEY)).getArray()));
                        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeySpecException ex) {
                            LOG.warn("send proto failed in: {}", identity, ex);
                        }
                        break;
                    }
                    case PONG: {
                        break;
                    }
                    default: {
                        LOG.info("websocket client {} send system msg: {} {}", identity, systemproto.getMsg_type(), systemproto.getData());
                    }
                }
            } catch (IOException ex) {
                LOG.error("decode system payload fdiled {}", proto.getProto_payload(), ex);
            }
            return;
        } else {
            LOG.info("receive from {} non_system msg: {}", identity, proto.getProto_payload());
        }
        
        if (!StringUtils.isNullOrWhiteSpace(proto.getProto_module())) {
            if(!IDENTITYS.containsKey(proto.getProto_module()))
                return;
            if(!((HashSet)session.getAttributes().get(AUTHORIZATIONS_INTERACT)).contains(proto.getProto_module())) {
                LOG.info("PermissionsError from: {} to: {}", identity, proto.getProto_module());
                handlePermissionsError(getUserId(session), proto.getProto_module());
                return;
            }
            
            String recipient;
            if(!StringUtils.isNullOrWhiteSpace(proto.getProto_recipient())) {
                recipient = proto.getProto_recipient();
            } else {
                recipient = IDENTITYS.get(proto.getProto_module()).get(0);
            }
            proto.setProto_module(identity);
            try {
                sendMessageToUser(recipient, 
                        AesUtils.Encrypt(ProtobufUtils.TransforAndConvert(proto), ((ByteArrayContainer) USERS.get(recipient).getAttributes().get(ENCRYPT_KEY)).getArray()));
            } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeySpecException ex) {
               LOG.warn("send proto failed in: {}", identity, ex);
            }
        } else if(!StringUtils.isNullOrWhiteSpace(proto.getProto_recipient())) {
            if(!USERS.containsKey(proto.getProto_recipient()))
                return;
            if(!((HashSet)USERS.get(proto.getProto_recipient()).getAttributes().get(AUTHORIZATIONS_INTERACT)).contains(identity)){
                handlePermissionsError(getUserId(session), proto.getProto_recipient());
                return;
            }
            proto.setProto_module(identity);
            try {
                sendMessageToUser(proto.getProto_recipient(), 
                        AesUtils.Encrypt(ProtobufUtils.TransforAndConvert(proto), ((ByteArrayContainer) USERS.get(proto.getProto_recipient()).getAttributes().get(ENCRYPT_KEY)).getArray()));
            } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeySpecException ex) {
                LOG.warn("send proto failed in: {}", identity, ex);
            }
        } else {
            if(!AppDataCache.Subscribers.containsKey(identity)){
                LOG.warn("unknown destination data: {}", identity, JsonUtils.object2json(proto));
                return;
            }
                
            proto.setProto_module(identity);
            byte[] protoPayload = ProtobufUtils.TransforAndConvert(proto);
            for(String userid:AppDataCache.Subscribers.get(identity)) {
                if(!USERS.containsKey(userid))
                    continue;
                try {
                    sendMessageToUser(userid, 
                            AesUtils.Encrypt(protoPayload, ((ByteArrayContainer) USERS.get(userid).getAttributes().get(ENCRYPT_KEY)).getArray()));
                } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeySpecException ex) {
                    LOG.warn("send proto failed in: {}", identity, ex);
                }
            }
        }
    }
    
    public void sendNotifierMsg(String identity, String clientID, String status, String msg){
        if(notifierUserID == null)
            return;
        WebsocketProto proto = new WebsocketProto(ResultType.SUCCESS, 
                new WebsocketSystemProto(WebsocketSystemMessageType.MODULE_STATUS_NOTIFY,
                        JsonUtils.object2json(new ModuleNotification(identity, clientID, status, msg))));
        try {
            sendMessageToUser(notifierUserID,
                    AesUtils.Encrypt(ProtobufUtils.TransforAndConvert(proto), ((ByteArrayContainer) USERS.get(notifierUserID).getAttributes().get(ENCRYPT_KEY)).getArray()));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException ex) {
            LOG.warn("send notification failed", ex);
        }
    }
    
    @Override
    protected void sendConnectedMsg(String userId){
        try {
            sendMessageToUser(userId, 
                    AesUtils.Encrypt(
                            ProtobufUtils.TransforAndConvert(WebsocketUtils.SystemMessageEncoder(ResultType.SUCCESS, 
                                    WebsocketSystemMessageType.SYSTEM_INFO, 
                                    "连接服务器成功")), 
                            ((ByteArrayContainer) USERS.get(userId).getAttributes().get(ENCRYPT_KEY)).getArray()));
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeySpecException ex) {
            LOG.warn("send proto failed in: {}", (String) USERS.get(userId).getAttributes().get(IDENTITY), ex);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        
    }
    
    private void handlePermissionsError(String userId, String identity){
        try {
            sendMessageToUser(userId, 
                    AesUtils.Encrypt(
                            ProtobufUtils.TransforAndConvert(
                                    WebsocketUtils.SystemMessageEncoder(ResultType.FAILURE, 
                                    WebsocketSystemMessageType.PERMISSION_DENIED, 
                                    "identity: "+ identity)
                            ), 
                            ((ByteArrayContainer) USERS.get(userId).getAttributes().get(ENCRYPT_KEY)).getArray()));
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeySpecException ex) {
            LOG.warn("send proto failed in: {}", (String) USERS.get(userId).getAttributes().get(IDENTITY), ex);
        }
    }

    @Override
    protected void handleClose(WebSocketSession session) {
        String userId = getUserId(session);
        if(userId == null)
            return;
        if(userId.equals(notifierUserID))
            notifierUserID = null;
        unRegisterUser(userId);
        String identity = (String) session.getAttributes().get(IDENTITY);
        if(identity == null)
            return;
        if(IDENTITYS.containsKey(identity)) {
            IDENTITYS.get(identity).remove(userId);
            if(IDENTITYS.get(identity).isEmpty())
                IDENTITYS.remove(identity);
        }
        try {
            if(session.isOpen())
                session.close();
        } catch (IOException ex) {
            GetImplLogger().error("{} handleClose failed", identity, ex);
        }
        GetImplLogger().info("{} disconnected", identity);
    }
    
}
