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
    private int interact;

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
     * @return the interact
     */
    public int getInteract() {
        return interact;
    }

    /**
     * @param interact the interact to set
     */
    public void setInteract(int interact) {
        this.interact = interact;
    }

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
}
