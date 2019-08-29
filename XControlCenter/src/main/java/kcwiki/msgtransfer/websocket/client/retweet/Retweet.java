package kcwiki.msgtransfer.websocket.client.retweet;

import javax.annotation.PostConstruct;
import kcwiki.management.controlcenter.cache.inmem.AppDataCache;
import kcwiki.msgtransfer.core.TransferController;
import kcwiki.management.controlcenter.initializer.AppConfig;
import org.iharu.websocket.client.WebsocketClientCallBack;
import org.iharu.websocket.client.BaseWebsocketClient;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;

@Service
public class Retweet
  extends WebsocketClientCallBack
{
  private static final Logger LOG = LoggerFactory.getLogger(Retweet.class);
  
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private TransferController transferController;
  
    private final String name = "xproject-retweet";
    private String url;
    private BaseWebsocketClient websocketClient;
  
    
    @PostConstruct
    public void initMethod()
    {
        url = appConfig.getWebsocket_url_retweet();
        websocketClient = new BaseWebsocketClient(name, url, this);
        if (websocketClient.connect()) {
            AppDataCache.WebsocketClients.put(name, websocketClient);
        }
    }
  
    @Override
    public void callback(TextMessage message)
    {
        if (message == null || message.getPayloadLength() == 0) {
            return;
        }
        try
        {
            procress(message.getPayload());
        }
        catch (Exception ex)
        {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }
    }
  
    private void procress(String payload)
    {
        LOG.info("msg coming: {}", payload);
        transferController.ReTransfer2C(name, payload);
        transferController.TransformAndReTransfer2C(name, payload);
    }
  
    @Override
    protected BaseWebsocketClient getWebsocketClient()
    {
        return websocketClient;
    }

    @Override
    protected Logger getImplLogger()
    {
        return LOG;
    }

    @Override
    protected void callback(BinaryMessage paramTextMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
