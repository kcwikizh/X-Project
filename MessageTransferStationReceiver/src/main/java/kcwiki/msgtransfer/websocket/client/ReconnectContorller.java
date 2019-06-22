package kcwiki.msgtransfer.websocket.client;

import java.util.concurrent.Executors;
import kcwiki.msgtransfer.cache.inmem.AppDataCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReconnectContorller {
  private static final Logger LOG = LoggerFactory.getLogger(ReconnectContorller.class);
  
  public static void reconnect(String clientName, int retry) {
    Executors.newSingleThreadExecutor().submit(() -> {
          try {
            Thread.sleep(calcReconnectDelay(retry));
            BaseWebsocketClient wsClient = (BaseWebsocketClient)AppDataCache.websocketClients.get(clientName);
            if (wsClient == null) {
              LOG.warn("websocket client name:{} not exist.", clientName);
              return;
            } 
            if (wsClient.connect()) {
              LOG.info("websocket client name:{} reconnected.", clientName);
            } else {
              LOG.info("websocket client name:{} reconnect failed. retrying...", clientName);
              reconnect(clientName, retry);
            } 
          } catch (InterruptedException e) {
            LOG.error("Exception while sending a message", e);
          } 
        });
  }
  
  public static long calcReconnectDelay(int retry) {
    if (retry == 0)
      return 2000L; 
    long delay = Math.round(Math.pow(2.0D, retry) * 1000.0D);
    if (delay > 1800000L)
      delay = 1800000L; 
    return delay;
  }
}
