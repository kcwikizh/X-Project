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
import kcwiki.management.xcontrolled.exception.XControlledModuleConnectFailException;
import kcwiki.management.xcontrolled.websocket.XModuleReconnectCallBack;
import kcwiki.management.xtraffic.crypto.rsa.RSAUtils;
import kcwiki.management.xtraffic.utils.AuthenticationUtils;
import kcwiki.management.xcontrolled.websocket.XModuleWebsocketClient;
import kcwiki.management.xcontrolled.websocket.XModuleWebsocketClientCallBack;
import kcwiki.management.xtraffic.crypto.aes.AesUtils;
import kcwiki.management.xtraffic.utils.RandomUtils;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.type.BaseHttpStatus;
import org.iharu.type.error.ErrorType;
import org.iharu.util.Base64Utils;
import org.iharu.util.HttpUtils;
import org.iharu.util.JsonUtils;
import org.iharu.util.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    
    
    public void connect(XModuleWebsocketClientCallBack callbackImpl, XModuleReconnectCallBack reconnectCallBack) throws XControlledModuleConnectFailException {
        this.identity = xModuleConfig.getXtraffic_identity();
        String publickey = null;
        try {
            publickey = HttpUtils.GetBody(xModuleConfig.getXtraffic_url_publickey());
        } catch (IOException ex) {
            LOG.error("fetch public key error", ex);
            handleConnectFailed();
            return;
        }
        if(StringUtils.isNullOrWhiteSpace(publickey)){
            LOG.error("public key is blank");
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
            handleConnectFailed();
            return;
        }
        String body;
        try {
            body = HttpUtils.GetBody(xModuleConfig.getXtraffic_url_auth(), reqBody);
        } catch (IOException ex) {
            LOG.error("fetch voucher error", ex);
            handleConnectFailed();
            return;
        }
        if(StringUtils.isNullOrWhiteSpace(body)){
            LOG.error("voucher is blank");
            handleConnectFailed();
            return;
        }
        WebResponseProto resp = JsonUtils.json2objectWithoutThrowException(body, new TypeReference<WebResponseProto>(){});
        if(resp == null){
            LOG.error("decode resp failed");
            handleConnectFailed();
            return;
        }
        if(resp.getStatus() != BaseHttpStatus.SUCCESS) {
            LOG.warn("authentication failed with: {}", resp.getMsg());
            handleConnectFailed();
            return;
        }
        String voucher = StringUtils.ByteArrayToString(AesUtils.DecryptWithoutException(Base64Utils.DecryptBase64((String) resp.getData()), symmetricKey));
        if(voucher == null) {
            LOG.warn("decrypt voucher failed");
            handleConnectFailed();
            return;
        }
        HashMap<String, String> headers = new HashMap();
        headers.put("x-access-token", voucher);
        websocketClient = new XModuleWebsocketClient(getIdentity(), headers, xModuleConfig.getXtraffic_url_subscribe(), symmetricKey, callbackImpl, reconnectCallBack);
        if(websocketClient.connect()){
            callbackImpl.setWebsocketClient(websocketClient);
            AppDataCache.isAppInit = true;
            return;
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
    
    private void handleConnectFailed() throws XControlledModuleConnectFailException{
        LOG.warn("XControlledModule connect failed");
        if(websocketClient != null)
            websocketClient.close();
        if(xModuleConfig.isXtraffic_force())
            throw new XControlledModuleConnectFailException(ErrorType.KERNEL_ERROR, "XControlledModule connect failed");
    }
    
    /**
     * @return the identity
     */
    public String getIdentity() {
        return identity;
    }
    
}
