/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.xtraffic.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Date;
import static kcwiki.management.xtraffic.constant.ConstantValue.TIMEZONEID;
import kcwiki.management.xtraffic.entity.authentication.TokenAuthEntity;
import kcwiki.management.xtraffic.crypto.rsa.RSAUtils;
import org.iharu.util.CalendarUtils;
import org.iharu.util.JsonUtils;
import org.iharu.util.RandomUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class AuthenticationUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AuthenticationUtils.class);
    
    public static TokenAuthEntity GetAuthenticationEntity(String key, String body){
        return JsonUtils.json2objectWithoutThrowException(RSAUtils.DecryptWithPrivateKey(body, key), new TypeReference<TokenAuthEntity>(){});
    }
    
    public static long GetTimestamp(){
//        long createdDateUtc = Timestamp.from(CalendarUtils.getZonedDateTime(TIMEZONEID).toInstant()).getTime();
        return CalendarUtils.getZonedDateTime(TIMEZONEID).toInstant().toEpochMilli();
    }
    
    public static boolean isAuthRequestTimeout(long timestamp){
        long ts = GetTimestamp();
        long diff = ts - timestamp;
        LOG.info("{} - {} {}", ts, timestamp, diff);
//        LOG.info("{} - {}", new Date(ts).toString(), new Date(timestamp).toString());
        return  (diff > 0 ? diff : -diff) > 900 * 1000;
    }
    
    public static String GenVoucher(){
        return RandomUtils.GenRandomString(20);
    }
    
    public static String GenToken(){
        return RandomUtils.GenTokenString(20);
    }
    
}
