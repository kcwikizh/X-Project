package kcwiki.msgtransfer.websocket.config;

import kcwiki.msgtransfer.websocket.handler.ProtobufTransferHandler;
import kcwiki.msgtransfer.websocket.handler.TransferHandler;
import kcwiki.msgtransfer.websocket.interceptor.TransferInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig
  implements WebSocketConfigurer
{
    @Autowired
    private TransferHandler transferHandler;
    @Autowired
    private ProtobufTransferHandler protobufTransferHandler;
    @Autowired
    private TransferInterceptor transferInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
    {
        registry
            .addHandler(transferHandler, "/websocket/transfer")
            .setAllowedOrigins("https://api.x.kcwiki.org", "http://localhost:48080", "http://www.blue-zero.com", "http://coolaf.com", "null" )
            .addInterceptors(transferInterceptor);
        registry
            .addHandler(protobufTransferHandler, "/websocket/protobuftransfer")
            .setAllowedOrigins("https://api.x.kcwiki.org", "http://localhost:48080", "http://www.blue-zero.com", "http://coolaf.com", "null" )
            .addInterceptors(transferInterceptor);
    }
}
