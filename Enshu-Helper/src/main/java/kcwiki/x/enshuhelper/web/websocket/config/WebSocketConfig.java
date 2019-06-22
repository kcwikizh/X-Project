/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.web.websocket.config;

import kcwiki.x.enshuhelper.web.websocket.handler.AdministratorHandler;
import kcwiki.x.enshuhelper.web.websocket.handler.ExchangeHandler;
import kcwiki.x.enshuhelper.web.websocket.handler.ExchangeHandlerKai;
import kcwiki.x.enshuhelper.web.websocket.interceptor.AdministratorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    ExchangeHandler exchangeHandler;
    @Autowired
    ExchangeHandlerKai exchangeHandlerKai;
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(new AdministratorHandler(), "/websocket/admin").addInterceptors(new AdministratorInterceptor());
        registry.addHandler(exchangeHandler, "/websocket/push").setAllowedOrigins("*");
//        registry.addHandler(exchangeHandlerKai, "/websocket/guestkai").setAllowedOrigins("*");
    }
    
}
