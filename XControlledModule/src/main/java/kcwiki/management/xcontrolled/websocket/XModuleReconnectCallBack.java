/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.xcontrolled.websocket;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import kcwiki.management.xcontrolled.core.XModuleController;
import kcwiki.management.xcontrolled.exception.XControlledModuleConnectFailException;
import org.iharu.websocket.client.BaseWebsocketClient;
import org.iharu.websocket.client.ReconnectCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class XModuleReconnectCallBack extends ReconnectCallback {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(XModuleReconnectCallBack.class);
    
    private final XModuleController controller;
    private final XModuleWebsocketClientCallBack callback;
    private boolean isShutdown = false;
    
    public XModuleReconnectCallBack(XModuleController controller, XModuleWebsocketClientCallBack callback) {
        this.controller = controller;
        this.callback = callback;
    }

    @Override
    public void handleException(BaseWebsocketClient wsClient, Throwable ex) {
        LOG.info("XModuleReconnectCallBack: {} - {} - handleException", wsClient.getName(), wsClient.getClientID(), ex);
        if(wsClient.isShutdown())
            return;
        if(ex instanceof ExecutionException){
            isShutdown = true;
            wsClient.close();
            CompletableFuture.runAsync(() -> {
                for(;;) {
                    try {
                        Thread.sleep(1000 * 30);
                    } catch (InterruptedException ex1) {
                        
                    }
                    try{
                        controller.connect(callback, this);
                        isShutdown = false;
                        break;
                    } catch (Exception ex1){
                        LOG.info("XModuleReconnectCallBack reconnect failed: {} - Exception: {}", wsClient.getName(), ex.getMessage());
                        if(ex instanceof XControlledModuleConnectFailException){
                            
                        }
                    }
                }
            });
        }
    }

    @Override
    public boolean isStopReconnect(BaseWebsocketClient wsClient) {
        if(!getClientID().equals(wsClient.getClientID()))
            return true;
        return isShutdown;
    }

    @Override
    protected Logger getImplLogger() {
        return LOG;
    }
    
}
