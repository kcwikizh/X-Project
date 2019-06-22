package kcwiki.msgtransfer.cache.inmem;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.iharu.websocket.client.BaseWebsocketClient;

public class AppDataCache
{
    public static boolean isAppInit = false;
    public static final Map<String, BaseWebsocketClient> websocketClients = new ConcurrentHashMap();
    public static final Map<String, String> dataHashCache = new ConcurrentHashMap();
    public static ExecutorService userScannerExecutor = Executors.newFixedThreadPool(25);
    public static final Set<String> existTables = new HashSet();
    public static final Map<Integer, String> responseCache = new ConcurrentHashMap();
}
