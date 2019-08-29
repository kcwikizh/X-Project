/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.xtraffic.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Base64;
import java.util.Calendar;
import kcwiki.management.xtraffic.authentication.entity.AuthenticationEntity;
import kcwiki.management.xtraffic.configuration.ModuleConfig;
import kcwiki.management.xtraffic.crypto.rsa.RSAUtil;
import org.iharu.proto.websocket.system.WebsocketSystemProto;
import org.iharu.util.CalendarUtils;
import org.iharu.util.JsonUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author iHaru
 */
public class AuthenticationUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AuthenticationUtils.class);
    
    private final static String TIMEZONE = "JST";
    
    public static AuthenticationEntity GetAuthenticationEntity(String key, String body){
        byte[] data = RSAUtil.DecryptWithPrivateKey(body, key);
        return JsonUtils.json2objectWithoutThrowException(data, new TypeReference<AuthenticationEntity>(){});
    }
    
    public static long GetTimestamp(){
        return CalendarUtils.getTimezoneCalendar(TIMEZONE).getTimeInMillis();
    }
    
    public static boolean isAuthRequestTimeout(long timestamp){
        return GetTimestamp() - timestamp > 30000;
    }
    
    public static String GenVoucher(){
        return RandomUtils.GenRandomString(20);
    }
    
}
