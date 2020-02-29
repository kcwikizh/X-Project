/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.xcontrolled.core;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import kcwiki.management.xcontrolled.cache.inmem.AppDataCache;
import org.iharu.auth2.authentication.entity.TokenAuthEntity;
import kcwiki.management.xcontrolled.configuration.XModuleConfig;
import kcwiki.management.xcontrolled.exception.XControlledModuleConnectFailException;
import kcwiki.management.xcontrolled.websocket.XModuleReconnectCallBack;
import org.iharu.crypto.rsa.RSAUtils;
import org.iharu.auth2.utils.AuthenticationUtils;
import kcwiki.management.xcontrolled.websocket.XModuleWebsocketClient;
import kcwiki.management.xcontrolled.websocket.XModuleWebsocketClientCallBack;
import kcwiki.management.xtraffic.keepalive.KeepAlive;
import org.iharu.crypto.aes.AesUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.type.BaseHttpStatus;
import org.iharu.type.error.ErrorType;
import org.iharu.util.Base64Utils;
import org.iharu.util.HttpUtils;
import org.iharu.util.JsonUtils;
import org.iharu.util.RandomUtils;
import org.iharu.util.StringUtils;
import org.iharu.websocket.util.WebsocketUtils;
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
            LOG.error("fetch public key error");
            handleConnectFailed(ex);
            return;
        }
        if(StringUtils.isNullOrWhiteSpace(publickey)){
            LOG.error("public key is blank");
            handleConnectFailed(null);
            return;
        }
        byte[] symmetricKey = null;
        
        try {
            symmetricKey = AesUtils.GenKey(RandomUtils.GenRandomString(16), RandomUtils.GenRandomString(16));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | UnsupportedEncodingException ex) {
            LOG.error("generate encrypt key failed");
            handleConnectFailed(ex);
            return;
        }
        
        callbackImpl.setPrivKey(symmetricKey);
        long ts = AuthenticationUtils.GetTimestamp();
        LOG.info(new Date(ts).toString());
        TokenAuthEntity authentication = new TokenAuthEntity();
        authentication.setToken(xModuleConfig.getXtraffic_token());
        authentication.setKey(Base64Utils.EncryptBase64ToString(symmetricKey));
        authentication.setTimestamp(ts);
        String reqBody = null;
        try {
            reqBody = Base64.getEncoder().encodeToString(RSAUtils.Encrypt(JsonUtils.object2bytes(authentication), RSAUtils.GetPublicKey(publickey)));
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            LOG.error("XControlledModule: {} Encrypt data failed", getIdentity(), ex);
            handleConnectFailed(ex);
            return;
        }
        String body;
        try {
            body = HttpUtils.GetBody(xModuleConfig.getXtraffic_url_auth(), reqBody);
        } catch (IOException ex) {
            LOG.error("fetch voucher error", ex);
            handleConnectFailed(ex);
            return;
        }
        if(StringUtils.isNullOrWhiteSpace(body)){
            LOG.error("voucher is blank");
            handleConnectFailed(null);
            return;
        }
        WebResponseProto resp = JsonUtils.json2objectWithoutThrowException(body, new TypeReference<WebResponseProto>(){});
        if(resp == null){
            LOG.error("decode resp failed");
            handleConnectFailed(null);
            return;
        }
        if(resp.getStatus() != BaseHttpStatus.SUCCESS) {
            LOG.warn("authentication failed with: {}", resp.getMsg());
            handleConnectFailed(null);
            return;
        }
        String voucher = StringUtils.ByteArrayToString(AesUtils.DecryptWithoutException(Base64Utils.DecryptBase64((String) resp.getData()), symmetricKey));
        if(voucher == null) {
            LOG.warn("decrypt voucher failed");
            handleConnectFailed(null);
            return;
        }
        HashMap<String, String> headers = new HashMap();
        headers.put("x-access-token", voucher);
        if(websocketClient != null)
            websocketClient.shutdown();
        websocketClient = new XModuleWebsocketClient(getIdentity(), headers, xModuleConfig.getXtraffic_url_subscribe(), symmetricKey, callbackImpl, reconnectCallBack);
        callbackImpl.setWebsocketClient(websocketClient);
        if(websocketClient.connect()){
            AppDataCache.isAppInit = true;
            if (xModuleConfig.isXtraffic_keepalive_enable()) {
                websocketClient.keepalive(xModuleConfig.getXtraffic_keepalive_period());
            }
            return;
        }
        handleConnectFailed(null);
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
        proto.setSign(DigestUtils.sha512Hex(StringUtils.StringToByteArray(proto.getProto_payload())));
        proto.setTimestamp(AuthenticationUtils.GetTimestamp());
        LOG.info("proto: {}", JsonUtils.object2json(proto));
        websocketClient.send(proto);
    }
    
    private void handleConnectFailed(Throwable ex) throws XControlledModuleConnectFailException{
        if(ex != null){
            if(ex instanceof IOException){
                LOG.error(ex.getMessage());
            } else {
                LOG.error("", ex);
            }
        }
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
