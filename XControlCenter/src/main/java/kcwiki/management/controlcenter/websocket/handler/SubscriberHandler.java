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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import kcwiki.management.controlcenter.cache.inmem.AppDataCache;
import kcwiki.management.controlcenter.database.entity.ModuleAuthorization;
import kcwiki.management.controlcenter.database.service.ModuleUtilsService;
import kcwiki.management.controlcenter.web.controller.entity.AuthVoucher;
import kcwiki.management.xtraffic.crypto.aes.AesUtils;
import kcwiki.management.xtraffic.entity.ByteArrayContainer;
import kcwiki.management.xtraffic.protobuf.ProtobufUtils;
import kcwiki.management.xtraffic.utils.AuthenticationUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.type.ResultType;
import org.iharu.type.websocket.WebsocketMessageType;
import org.iharu.type.websocket.WebsocketSystemMessageType;
import org.iharu.util.Base64Utils;
import org.iharu.util.JsonUtils;
import org.iharu.web.session.entity.SessionEntity;
import org.iharu.websocket.handler.DefaultWebsocketHandler;
import org.iharu.websocket.util.WebsocketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
    
    @Autowired
    ModuleUtilsService moduleUtilsService;
    
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
            if(!IDENTITYS.containsKey(identity))
                IDENTITYS.put(identity, new ArrayList());
            IDENTITYS.get(identity).add(userId);
            List<ModuleAuthorization> authorizations = moduleUtilsService.getAuthorizations(authVoucher.getToken());
            HashSet interact = new HashSet();
            authorizations.forEach(item -> {
                if(!AppDataCache.Subscribers.containsKey(item.getIdentity_name()))
                    AppDataCache.Subscribers.put(item.getIdentity_name(), new HashSet());
                AppDataCache.Subscribers.get(item.getIdentity_name()).add(userId);
                if(item.getInteract() == 1) {
                    interact.add(item.getIdentity_name());
                }
            });
            ByteArrayContainer bytesContainer = new ByteArrayContainer(Base64Utils.DecryptBase64(authVoucher.getKey()));
            session.getAttributes().put(IDENTITY, identity);
            session.getAttributes().put(ENCRYPT_KEY, bytesContainer);
            session.getAttributes().put(AUTHORIZATIONS_INTERACT, interact);
//            AppDataCache.Vouchers.remove(voucher);
            sendConnectedMsg(userId);
        } catch (Exception ex) {
            GetImplLogger().error("init connection error", ex);
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        String identity = (String) session.getAttributes().get(IDENTITY);
        if(!AppDataCache.Subscribers.containsKey(identity)){
            return;
        }
        
        byte[] payload = message.getPayload().array();
        if(payload.length == 0)
            return;
        byte[] data = null;
        try {
            data = AesUtils.Decrypt(payload, ((ByteArrayContainer) session.getAttributes().get(ENCRYPT_KEY)).getArray());
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException | InvalidKeySpecException ex) {
            LOG.warn("decrypt failed", ex);
        }
        if(data == null)
            return;
        WebsocketProto proto = ProtobufUtils.TransforAndConvert(data);
        if(proto.getProto_type() == WebsocketMessageType.SYSTEM){
            LOG.info("websocket client {} send system msg: {}", identity, proto.getProto_payload());
            return;
        } else {
            LOG.info("websocket client {} send non_system msg: {}", identity, JsonUtils.object2json(proto));
        }
        if(!StringUtils.isEmpty(proto.getProto_recipient())) {
            if(!USERS.containsKey(proto.getProto_recipient()))
                return;
            if(!((HashSet)USERS.get(proto.getProto_recipient()).getAttributes().get(AUTHORIZATIONS_INTERACT)).contains(identity))
                return;
            try {
                sendMessageToUser(proto.getProto_recipient(), 
                        AesUtils.Encrypt(data, ((ByteArrayContainer) USERS.get(proto.getProto_recipient()).getAttributes().get(ENCRYPT_KEY)).getArray()));
            } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeySpecException ex) {
                LOG.warn("send proto failed in: {}", identity, ex);
            }
        } else if (!StringUtils.isEmpty(proto.getProto_module())) {
            if(!IDENTITYS.containsKey(proto.getProto_module()))
                return;
            if(!((HashSet)session.getAttributes().get(AUTHORIZATIONS_INTERACT)).contains(proto.getProto_module()))
                return;
            String recipient = IDENTITYS.get(proto.getProto_module()).get(0);
            try {
                proto.setProto_module(identity);
                proto.setProto_sender(getUserId(session));
                sendMessageToUser(recipient, 
                        AesUtils.Encrypt(data, ((ByteArrayContainer) USERS.get(recipient).getAttributes().get(ENCRYPT_KEY)).getArray()));
            } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeySpecException ex) {
               LOG.warn("send proto failed in: {}", identity, ex);
            }
        } else {
            for(String userid:AppDataCache.Subscribers.get(identity)) {
                if(!USERS.containsKey(userid))
                    continue;
                try {
                    sendMessageToUser(userid, 
                            AesUtils.Encrypt(data, ((ByteArrayContainer) USERS.get(userid).getAttributes().get(ENCRYPT_KEY)).getArray()));
                } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeySpecException ex) {
                    LOG.warn("send proto failed in: {}", identity, ex);
                }
            }
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

    @Override
    protected void handleClose(WebSocketSession session) {
        String userId = getUserId(session);
        if(userId == null)
            return;
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
            GetImplLogger().error("handleClose: {}", ExceptionUtils.getStackTrace(ex));
        }
    }
    
}
