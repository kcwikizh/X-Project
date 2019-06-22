package kcwiki.msgtransfer.websocket.client.enshuhelper;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import javax.annotation.PostConstruct;
import kcwiki.msgtransfer.cache.inmem.AppDataCache;
import kcwiki.msgtransfer.core.TransferController;
import kcwiki.msgtransfer.initializer.AppConfig;
import kcwiki.msgtransfer.websocket.client.BaseClientCallBack;
import kcwiki.msgtransfer.websocket.client.BaseWebsocketClient;
import kcwiki.msgtransfer.websocket.client.enshuhelper.entity.ExchangeMessageEntity;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.type.ResultType;
import org.iharu.type.websocket.WebsocketMessageType;
import org.iharu.util.JsonUtils;
import org.iharu.websocket.util.WebsocketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

@Service
public class EnshuHelper
  extends BaseClientCallBack
{
  private static final Logger LOG = LoggerFactory.getLogger(EnshuHelper.class);
  
    @Autowired
    private AppConfig appConfig;
    @Autowired
    TransferController transferController;
    
    private final String name = "xproject-enshuhelper";
    private String url;
    private BaseWebsocketClient websocketClient;
  
    
    @PostConstruct
    public void initMethod()
    {
        url = appConfig.getWebsocket_url_enshuhelper();
        websocketClient = new BaseWebsocketClient(name, url, this);
        if (websocketClient.connect()) {
            AppDataCache.websocketClients.put(name, websocketClient);
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
        try
        {
            ExchangeMessageEntity exchangeMessageEntity = JsonUtils.json2object(payload, new TypeReference<ExchangeMessageEntity>(){});
            if(exchangeMessageEntity == null)
                return;
            transferController.ReTransfer(new WebsocketProto(WebsocketMessageType.NON_SYSTEM, ResultType.SUCCESS, exchangeMessageEntity));
            transferController.TransformAndReTransfer(name, exchangeMessageEntity);
        }
        catch (IOException ex)
        {
          LOG.error(ExceptionUtils.getStackTrace(ex));
        }
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
    
}
