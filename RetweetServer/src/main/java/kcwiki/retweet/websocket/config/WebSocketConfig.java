/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.retweet.websocket.config;

import kcwiki.retweet.websocket.handler.RetweetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//@Configuration
//@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    
    @Autowired
    private RetweetHandler retweetHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
                ;
        registry.addHandler(retweetHandler, "/websocket/retweet")
                .setAllowedOrigins("https://api.x.kcwiki.org", "http://localhost:48080", "http://www.websocket-test.com", "http://coolaf.com", "null")
                ;
    }

}
