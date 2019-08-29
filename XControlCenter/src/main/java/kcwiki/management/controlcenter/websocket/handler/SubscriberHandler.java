/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.controlcenter.websocket.handler;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import kcwiki.management.controlcenter.cache.inmem.AppDataCache;
import kcwiki.management.controlcenter.database.service.ModuleService;
import kcwiki.management.controlcenter.web.controller.entity.AuthVoucher;
import kcwiki.management.xtraffic.crypto.rsa.RSAUtil;
import kcwiki.management.xtraffic.protobuf.ProtobufUtils;
import kcwiki.management.xtraffic.utils.AuthenticationUtils;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.type.websocket.WebsocketMessageType;
import org.iharu.web.session.entity.SessionEntity;
import org.iharu.websocket.handler.DefaultWebsocketHandler;
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
    private static final Map<String, String> Uid2Identity = new ConcurrentHashMap();
    private static final Map<String, String> Uid2Key = new ConcurrentHashMap();
    
    @Autowired
    ModuleService moduleService;
    
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
            if (userId != null) {
                registerUser(userId, session);
                sendConnectedMsg(userId);
            } else {
                GetImplLogger().error("session: {} could not find userid", session);
            }
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
            Uid2Identity.put(userId, authVoucher.getIdentity());
            Uid2Key.put(userId, authVoucher.getKey());
            moduleService.GetAuthorizations(authVoucher.getToken()).forEach(item -> {
                if(!AppDataCache.Subscribers.containsKey(item))
                    AppDataCache.Subscribers.put(item, new HashSet());
                AppDataCache.Subscribers.get(item).add(userId);
            });
            AppDataCache.Vouchers.remove(voucher);
        } catch (Exception ex) {
            GetImplLogger().error("init connection error", ex);
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        byte[] payload = message.getPayload().array();
        if(payload.length == 0)
            return;
        byte[] data = null;
        try {
            data = RSAUtil.Decrypt(payload, RSAUtil.GetPrivateKey(AppDataCache.getPrivateKey()));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException ex) {
            java.util.logging.Logger.getLogger(SubscriberHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(data == null)
            return;
        String uid = getUserId(session);
        if(!AppDataCache.Subscribers.containsKey(Uid2Identity.get(uid))){
            return;
        }
        WebsocketProto proto = ProtobufUtils.TransforAndConvert(data);
        if(proto.getProto_type() == WebsocketMessageType.SYSTEM){
            LOG.info("websocket client {} send system msg: {}", Uid2Identity.get(uid), proto.getProto_payload());
            return;
        }
        byte[] _data = data;
        AppDataCache.Subscribers.get(Uid2Identity.get(uid)).forEach(_uid -> {
            if(!USERS.containsKey(uid))
                return;
            try {
                sendMessageToUser(uid, RSAUtil.Encrypt(_data, RSAUtil.GetPublicKey(Uid2Key.get(uid))));
            } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException ex) {
                LOG.warn("send proto failed in: {}", Uid2Identity.get(uid), ex);
            }
        });
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        
    }
    
    @Override
    protected void handleClose(WebSocketSession session) {
        String uid = getUserId(session);
        unRegisterUser(uid);
        Uid2Identity.remove(uid);
        Uid2Key.remove(uid);
        try {
            if(session.isOpen())
                session.close();
        } catch (IOException ex) {
            GetImplLogger().error("handleClose error", ex);
        }
    }
}
