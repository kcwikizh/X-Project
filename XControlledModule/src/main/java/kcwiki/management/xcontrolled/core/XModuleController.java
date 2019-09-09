/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.xcontrolled.core;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.HashMap;
import javax.annotation.PreDestroy;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import kcwiki.management.xcontrolled.cache.inmem.AppDataCache;
import kcwiki.management.xtraffic.entity.AuthenticationEntity;
import kcwiki.management.xcontrolled.configuration.XModuleConfig;
import kcwiki.management.xcontrolled.exception.XControlledModuleConnectFail;
import kcwiki.management.xtraffic.crypto.rsa.RSAUtils;
import kcwiki.management.xtraffic.utils.AuthenticationUtils;
import kcwiki.management.xtraffic.utils.HttpClientUtils;
import kcwiki.management.xcontrolled.websocket.XModuleWebsocketClient;
import kcwiki.management.xcontrolled.websocket.XModuleWebsocketClientCallBack;
import kcwiki.management.xtraffic.crypto.aes.AesUtils;
import kcwiki.management.xtraffic.utils.RandomUtils;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.type.BaseHttpStatus;
import org.iharu.type.error.ErrorType;
import org.iharu.util.Base64Utils;
import org.iharu.util.JsonUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 *
 * @author iHaru
 */
@Component
public class XModuleController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(XModuleController.class);
    
    @Autowired
    XModuleConfig xModuleConfig;
    
    private String identity;
    private XModuleWebsocketClient websocketClient;
    
    
    public void connect(XModuleWebsocketClientCallBack callbackImpl) throws XControlledModuleConnectFail {
        identity = xModuleConfig.getXtraffic_identity();
        String publickey = HttpClientUtils.GetBody(xModuleConfig.getXtraffic_url_publickey());
        if(StringUtils.isEmpty(publickey)){
            handleConnectFailed();
            return;
        }
        byte[] symmetricKey = null;
        
        try {
            symmetricKey = AesUtils.GenKey(RandomUtils.GenRandomString(16), RandomUtils.GenRandomString(16));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | UnsupportedEncodingException ex) {
            LOG.error("generate encrypt key failed", ex);
            handleConnectFailed();
            return;
        }
        
        callbackImpl.setPrivKey(symmetricKey);
        
        AuthenticationEntity authentication = new AuthenticationEntity();
        authentication.setToken(xModuleConfig.getXtraffic_token());
        authentication.setKey(Base64Utils.EncryptBase64ToString(symmetricKey));
        authentication.setTimestamp(AuthenticationUtils.GetTimestamp());
        String reqBody = null;
        try {
            reqBody = Base64.getEncoder().encodeToString(RSAUtils.Encrypt(JsonUtils.object2bytes(authentication), RSAUtils.GetPublicKey(publickey)));
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            LOG.error("XControlledModule: {} Encrypt data failed", getIdentity(), ex);
        }
        String body = HttpClientUtils.GetBody(xModuleConfig.getXtraffic_url_auth(), reqBody);
        if(StringUtils.isEmpty(body)){
            handleConnectFailed();
            return;
        }
        WebResponseProto resp = JsonUtils.json2objectWithoutThrowException(body, new TypeReference<WebResponseProto>(){});
        if(resp == null){
            handleConnectFailed();
            return;
        }
        if(resp.getStatus() != BaseHttpStatus.SUCCESS) {
            LOG.warn("authentication failed with: {}", resp.getMsg());
            handleConnectFailed();
            return;
        }
        String voucher = (String) resp.getData();
        HashMap<String, String> headers = new HashMap();
        headers.put("x-access-token", voucher);
        websocketClient = new XModuleWebsocketClient(getIdentity(), headers, xModuleConfig.getXtraffic_url_subscribe(), symmetricKey, callbackImpl);
        if(websocketClient.connect()){
            callbackImpl.setWebsocketClient(websocketClient);
            AppDataCache.isAppInit = true;
            return;
        }
        try {
            websocketClient.close();
        } catch (IOException ex) {
            LOG.error("", ex);
        }
        handleConnectFailed();
    }
    
    public void send(String payload) {
        LOG.info("string: {}", payload);
        try {
            websocketClient.send(payload);
        } catch (IOException ex) {
            LOG.error("XControlledModule: {} Encrypt data failed", getIdentity(), ex);
        }
    }
    
    public void send(WebsocketProto proto) {
        LOG.info("proto: {}", JsonUtils.object2json(proto));
        websocketClient.send(proto);
    }
    
    private void handleConnectFailed() throws XControlledModuleConnectFail{
        LOG.warn("XControlledModule connect failed");
        if(xModuleConfig.isXtraffic_force())
            throw new XControlledModuleConnectFail(ErrorType.KERNEL_ERROR, "XControlledModule connect failed");
    }
    
    @PreDestroy
    public void destroyMethod() {
        try {
            websocketClient.close();
        } catch (IOException ex) {
            LOG.error("XControlledModule: {} shutdown failed", getIdentity(), ex);
        }
    }
    
    /**
     * @return the identity
     */
    public String getIdentity() {
        return identity;
    }
    
}
