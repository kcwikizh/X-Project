package kcwiki.management.controlcenter.websocket.interceptor;

import io.micrometer.core.instrument.util.StringUtils;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import javax.servlet.http.HttpSession;
import kcwiki.management.controlcenter.cache.inmem.AppDataCache;
import kcwiki.management.controlcenter.initializer.AppConfig;
import org.iharu.web.session.entity.SessionEntity;
import org.iharu.websocket.interceptor.DefaultWebsocketInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class SubscriberInterceptor
  extends DefaultWebsocketInterceptor
{
    private static final Logger LOG = LoggerFactory.getLogger(SubscriberInterceptor.class);

    @Autowired
    private AppConfig appConfig;

    @Override
    protected SessionEntity valid(ServletServerHttpRequest servletRequest, HttpSession session)
    {
        List<String> tokenlist = servletRequest.getHeaders().get("x-access-token");
        if ((tokenlist == null) || (tokenlist.isEmpty())) {
            return null;
        }
        String access_token = (String)tokenlist.get(0);
        if (StringUtils.isBlank(access_token)) {
            return null;
        }
        LOG.debug("x-access-token: {}", access_token);
        LOG.debug("{}", servletRequest.getHeaders());
        if (!AppDataCache.Vouchers.containsKey(access_token)) {
            return null;
        }
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setUid(session.getId());
        sessionEntity.setVoucher(access_token);
        sessionEntity.setValid_timestamp(System.currentTimeMillis());
        return sessionEntity;
    }
}
