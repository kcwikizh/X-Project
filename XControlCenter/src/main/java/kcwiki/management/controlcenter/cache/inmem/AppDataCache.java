package kcwiki.management.controlcenter.cache.inmem;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kcwiki.management.controlcenter.web.controller.Authentication;
import kcwiki.management.controlcenter.web.controller.entity.AuthVoucher;
import kcwiki.management.controlcenter.websocket.handler.SubscriberHandler;
import org.iharu.exception.BaseException;
import org.iharu.type.error.ErrorType;
import org.iharu.util.StackTraceUtils;
import org.iharu.websocket.client.BaseWebsocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppDataCache
{
    private static final Logger LOG = LoggerFactory.getLogger(AppDataCache.class);
    
    private static String PrivateKey = null;
    
    public static boolean isAppInit = false;
    
    public static final Map<String, AuthVoucher> Vouchers = new ConcurrentHashMap();
    
    public static final Map<String, BaseWebsocketClient> WebsocketClients = new ConcurrentHashMap();
    public static final Map<String, HashSet<String>> Subscribers = new ConcurrentHashMap();
    
    /**
     * @return the PrivateKey
     */
    public static String getPrivateKey() {
        if(!StackTraceUtils.IsCallerLegal(SubscriberHandler.class.getName())){
            LOG.warn(StackTraceUtils.GetUpperCaller().getClassName());
            return null;
        }
        return PrivateKey;
    }

    /**
     * @param aPrivateKey the PrivateKey to set
     */
    public static void setPrivateKey(String aPrivateKey) {
        if(!StackTraceUtils.IsCallerLegal(Authentication.class.getName(), "getPublishKey"))
            throw new BaseException(ErrorType.OPERATION_ERROR, "Insufficient permissions with class: " + StackTraceUtils.GetUpperCaller().getClassName());
        PrivateKey = aPrivateKey;
    }
    
//    public static final Map<String, String> dataHashCache = new ConcurrentHashMap();
//    public static ExecutorService userScannerExecutor = Executors.newFixedThreadPool(25);
//    public static final Set<String> existTables = new HashSet();
//    public static final Map<Integer, String> responseCache = new ConcurrentHashMap();
}
