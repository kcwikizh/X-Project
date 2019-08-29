/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.xtraffic.websocket;

import java.io.IOException;
import java.util.HashMap;
import org.iharu.websocket.client.BaseWebsocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;

/**
 *
 * @author iHaru
 */
public class ModuleWebsocketClient extends BaseWebsocketClient {
    private static final Logger LOG = LoggerFactory.getLogger(BaseWebsocketClient.class);
    
    private final String privateKey;
    
    public ModuleWebsocketClient(String key, String name, HashMap headers, String url, ModuleWebsocketClientCallBack callback) {
        super(name, headers, url, callback);
        privateKey = key;
    }
    
    @Override
    public void send(String payload) throws IOException{
        if(!webSocketSession.isOpen()){
            Instance.connect();
            if(!webSocketSession.isOpen()){
                LOG.warn("webSocketSession: {} closed.", getName());
                return;
            }
        }
        webSocketSession.sendMessage(new TextMessage(payload));
    }
    
    @Override
    public void send(byte[] payload) throws IOException{
        if(!webSocketSession.isOpen()){
            Instance.connect();
            if(!webSocketSession.isOpen()){
                LOG.warn("webSocketSession: {} closed.", getName());
                return;
            }
        }
        webSocketSession.sendMessage(new BinaryMessage(payload));
    }

    /**
     * @return the privateKey
     */
    public String getPrivateKey() {
        return privateKey;
    }
    
}
