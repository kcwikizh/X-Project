/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.controlcenter.web.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import kcwiki.management.controlcenter.cache.inmem.AppDataCache;
import kcwiki.management.controlcenter.database.service.ModuleUtilsService;
import kcwiki.management.controlcenter.web.controller.entity.AuthVoucher;
import kcwiki.management.controlcenter.web.controller.type.VoucherType;
import org.iharu.crypto.aes.AesUtils;
import org.iharu.auth2.utils.AuthenticationUtils;
import org.iharu.auth2.authentication.entity.TokenAuthEntity;
import org.iharu.crypto.rsa.RSAKeyPairGenerator;
import org.iharu.crypto.rsa.RSAUtils;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.BaseHttpStatus;
import org.iharu.util.Base64Utils;
import org.iharu.util.StringUtils;
import org.iharu.web.BaseController;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author iHaru
 */
@RestController
@RequestMapping(value = "/authentication", produces = "application/json")
public class Authentication extends BaseController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Authentication.class);
    
    @Autowired
    ModuleUtilsService moduleUtilsService;
    
    private static String publicKey = null;
    private static String privateKey = null;
    
    @GetMapping("/key")
    public String getPublishKey() throws NoSuchAlgorithmException {
        if(publicKey == null || privateKey == null) {
            RSAKeyPairGenerator keyPairGenerator = new RSAKeyPairGenerator();
            publicKey =  RSAUtils.GetBase64Key(keyPairGenerator.getPublicKey());
            privateKey =  RSAUtils.GetBase64Key(keyPairGenerator.getPrivateKey());
            AppDataCache.setPrivateKey(privateKey);
        }
        return publicKey;
    }
    
    @PostMapping("/module")
    public WebResponseProto module(@RequestBody String body) {
        if(StringUtils.isNullOrWhiteSpace(body))
            return GenResponse(BaseHttpStatus.FAILURE, "body is blank", null);
        TokenAuthEntity authentication = AuthenticationUtils.GetAuthenticationEntity(privateKey, body);
        if(authentication == null || StringUtils.isNullOrWhiteSpace(authentication.getToken()))
            return GenResponse(BaseHttpStatus.FAILURE, "body decode failed", null);
        if(AuthenticationUtils.isAuthRequestTimeout(authentication.getTimestamp()))
            return GenResponse(BaseHttpStatus.FAILURE, "request timeout", null);
        String identity = moduleUtilsService.getIdentification(authentication.getToken());
        if(identity == null)
            return GenResponse(BaseHttpStatus.FAILURE, "authentication failed", null);
        byte[] key = Base64Utils.DecryptBase64(authentication.getKey());
        if(key == null)
            return GenResponse(BaseHttpStatus.FAILURE, "decode key failed", null);
        String voucher = AuthenticationUtils.GenVoucher();
        AuthVoucher authVoucher = new AuthVoucher();
        authVoucher.setIdentity(identity);
        authVoucher.setKey(Base64Utils.EncryptBase64ToString(key));
        authVoucher.setToken(authentication.getToken());
        authVoucher.setType(VoucherType.MODULE_WEBSOCKET);
        authVoucher.setTimestamp(AuthenticationUtils.GetTimestamp());
        AppDataCache.Vouchers.put(voucher, authVoucher);
        byte[] _voucher = AesUtils.EncryptWithoutException(voucher, key);
        if(_voucher == null)
            return GenResponse(BaseHttpStatus.FAILURE, "encrypt voucher failed", null);
        return GenResponse(BaseHttpStatus.SUCCESS, "authentication success", Base64Utils.EncryptBase64ToString(_voucher));
    }
    
    
}
