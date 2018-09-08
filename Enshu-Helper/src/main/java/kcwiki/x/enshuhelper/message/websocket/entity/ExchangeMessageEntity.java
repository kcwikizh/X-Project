/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.message.websocket.entity;

import kcwiki.x.enshuhelper.message.websocket.types.ExchangeMessageTypes;

/**
 *
 * @author x5171
 */
public class ExchangeMessageEntity extends BaseMessageEntity {
    private ExchangeMessageTypes exchangeMessageTypes;

    /**
     * @return the exchangeMessageTypes
     */
    public ExchangeMessageTypes getExchangeMessageTypes() {
        return exchangeMessageTypes;
    }

    /**
     * @param exchangeMessageTypes the exchangeMessageTypes to set
     */
    public void setExchangeMessageTypes(ExchangeMessageTypes exchangeMessageTypes) {
        this.exchangeMessageTypes = exchangeMessageTypes;
    }

}
