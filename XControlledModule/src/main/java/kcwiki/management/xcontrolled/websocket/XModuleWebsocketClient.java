/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.xcontrolled.websocket;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import kcwiki.management.xtraffic.keepalive.KeepAlive;
import org.iharu.crypto.aes.AesUtils;
import kcwiki.management.xtraffic.protobuf.ProtobufUtils;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.websocket.client.BaseWebsocketClient;
import org.iharu.websocket.util.WebsocketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;

/**
 *
 * @author iHaru
 */
public class XModuleWebsocketClient extends BaseWebsocketClient {
    private static final Logger LOG = LoggerFactory.getLogger(XModuleWebsocketClient.class);
    
    private final byte[] symmetricKey;
    private KeepAlive keepAliveExecutor = null;
    
    public XModuleWebsocketClient(String name, HashMap headers, String url, byte[] symmetricKey, XModuleWebsocketClientCallBack callback, XModuleReconnectCallBack reconnectCallBack) {
        super(name, headers, url, callback, reconnectCallBack);
        this.symmetricKey = symmetricKey;
    }
    
    public void keepalive(int period){
        if(keepAliveExecutor != null)
            return;
        Runnable keepAliveTask = () -> {
            if(isShutdown()){
                shutdown();
                return;
            }
            send(WebsocketUtils.PINGMessage());
        };
        keepAliveExecutor = KeepAlive.newTask(keepAliveTask, period);
        LOG.info("XModuleWebsocketClient: {} keepalive task set. period: {}", name, period);
    }
    
    public void shutdown(){
        close();
        if(keepAliveExecutor != null)
            keepAliveExecutor.forceStopKeepAliveTask();
    }
    
    public boolean send(WebsocketProto payload) {
        if(!webSocketSession.isOpen()){
            instance.connect();
            if(!webSocketSession.isOpen()){
                LOG.warn("webSocketSession: {} closed.", getName());
                return false;
            }
        }
        byte[] data = null;
        try {
            data = AesUtils.Encrypt(ProtobufUtils.TransforAndConvert(payload), symmetricKey);
            if(data.length == 0)
                return false;
            webSocketSession.sendMessage(new BinaryMessage(data));
            return true;
        } catch (InvalidAlgorithmParameterException | InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            LOG.error("XControlledModule: {} Encrypt data failed", getName(), ex);
        } catch (IOException ex) {
            LOG.error("XControlledModule: {} Send data failed", getName(), ex);
        }
        return false;
    }
    
    @Override
    public void send(String payload) throws IOException {
        if(!webSocketSession.isOpen()){
            instance.connect();
            if(!webSocketSession.isOpen()){
                LOG.warn("webSocketSession: {} closed.", getName());
                return;
            }
        }
        webSocketSession.sendMessage(new TextMessage(payload));
    }
    
    @Override
    public void send(byte[] payload) throws IOException {
        if(!webSocketSession.isOpen()){
            instance.connect();
            if(!webSocketSession.isOpen()){
                LOG.warn("webSocketSession: {} closed.", getName());
                return;
            }
        }
        webSocketSession.sendMessage(new BinaryMessage(payload));
    }
    
}
