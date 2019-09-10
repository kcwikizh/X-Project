/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.controlcenter.database.entity;

/**
 *
 * @author iHaru
 */
public class ModuleAuthorization {
    private int id;
    private String identity_name;
    private int receive;
    private int transmit;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the identity_name
     */
    public String getIdentity_name() {
        return identity_name;
    }

    /**
     * @param identity_name the identity_name to set
     */
    public void setIdentity_name(String identity_name) {
        this.identity_name = identity_name;
    }

    /**
     * @return the receive
     */
    public int getReceive() {
        return receive;
    }

    /**
     * @param receive the receive to set
     */
    public void setReceive(int receive) {
        this.receive = receive;
    }

    /**
     * @return the transmit
     */
    public int getTransmit() {
        return transmit;
    }

    /**
     * @param transmit the transmit to set
     */
    public void setTransmit(int transmit) {
        this.transmit = transmit;
    }

    
}
