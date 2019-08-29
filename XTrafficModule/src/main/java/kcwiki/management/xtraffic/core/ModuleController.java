/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.xtraffic.core;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import kcwiki.management.xtraffic.authentication.entity.AuthenticationEntity;
import kcwiki.management.xtraffic.configuration.ModuleConfig;
import kcwiki.management.xtraffic.crypto.rsa.RSAKeyPairGenerator;
import kcwiki.management.xtraffic.crypto.rsa.RSAUtil;
import kcwiki.management.xtraffic.utils.AuthenticationUtils;
import kcwiki.management.xtraffic.utils.HttpClientUtils;
import kcwiki.management.xtraffic.websocket.ModuleWebsocketClient;
import kcwiki.management.xtraffic.websocket.ModuleWebsocketClientCallBack;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.BaseHttpStatus;
import org.iharu.util.JsonUtils;
import org.iharu.websocket.client.BaseWebsocketClient;
import org.iharu.websocket.client.WebsocketClientCallBack;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 *
 * @author iHaru
 */
@Component
public class ModuleController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ModuleController.class);
    
    @Autowired
    ModuleConfig moduleConfig;
    
    private ModuleWebsocketClient websocketClient;
    
    public boolean connect(String identity, String token, ModuleWebsocketClientCallBack callbackImpl) throws NoSuchAlgorithmException{
        String publickey = HttpClientUtils.GetBody(moduleConfig.getXtraffic_url_publickey());
        if(StringUtils.isEmpty(publickey)){
            return false;
        }
        RSAKeyPairGenerator keyPairGenerator = new RSAKeyPairGenerator();
        AuthenticationEntity authentication = new AuthenticationEntity();
        authentication.setToken(token);
        authentication.setKey(RSAUtil.GetBase64Key(keyPairGenerator.getPublicKey()));
        authentication.setTimestamp(AuthenticationUtils.GetTimestamp());
        String body = HttpClientUtils.GetBody(moduleConfig.getXtraffic_url_auth(), 
                Base64.getEncoder().encodeToString(RSAUtil.EncryptWithPublicKey(JsonUtils.object2json(authentication), publickey)));
        if(StringUtils.isEmpty(body)){
            return false;
        }
        WebResponseProto resp = JsonUtils.json2objectWithoutThrowException(body, new TypeReference<WebResponseProto>(){});
        if(resp == null){
            return false;
        }
        if(resp.getStatus() != BaseHttpStatus.SUCCESS) {
            LOG.warn("authentication failed with: {}", resp.getMsg());
            return false;
        }
        String voucher = (String) resp.getData();
        HashMap<String, String> headers = new HashMap();
        headers.put("x-access-token", voucher);
        websocketClient = new ModuleWebsocketClient(RSAUtil.GetBase64Key(keyPairGenerator.getPrivateKey()), identity, headers, moduleConfig.getXtraffic_url_subscribe(), callbackImpl);
        if(getWebsocketClient().connect()){
            return true;
        }
        try {
            getWebsocketClient().close();
        } catch (IOException ex) {
            LOG.error("", ex);
        }
        return false;
    }
    
    @PreDestroy
    
    public void destroyMethod() {
        try {
            getWebsocketClient().close();
        } catch (IOException ex) {
            LOG.error("", ex);
        }
    }

    /**
     * @return the websocketClient
     */
    public BaseWebsocketClient getWebsocketClient() {
        return websocketClient;
    }
    
}
