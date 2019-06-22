/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.msgtransfer.websocket.client;

import org.iharu.websocket.client.BaseClientCallBack;

/**
 *
 * @author iHaru
 */
public abstract class BaseClient extends BaseClientCallBack {
    
    protected abstract void send();
    
}
