package kcwiki.management.controlcenter.websocket.config;

import kcwiki.management.controlcenter.websocket.handler.SubscriberHandler;
import kcwiki.management.controlcenter.websocket.interceptor.SubscriberInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig
  implements WebSocketConfigurer
{
    @Autowired
    private SubscriberHandler subscriberHandler;
    @Autowired
    private SubscriberInterceptor subscriberInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
    {
        
        registry
            .addHandler(subscriberHandler, "/websocket/subscriber")
            .setAllowedOrigins("https://api.x.kcwiki.org", "http://localhost:48080", "http://www.websocket-test.com", "http://coolaf.com", "*" )
            .addInterceptors(subscriberInterceptor);
    }
}
