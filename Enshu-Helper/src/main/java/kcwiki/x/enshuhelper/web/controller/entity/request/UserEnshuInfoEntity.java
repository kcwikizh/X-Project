/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.web.controller.entity.request;

import java.util.List;

/**
 *
 * @author iHaru
 */
public class UserEnshuInfoEntity {
    private int  api_member_id;
    private List<Integer> api_enemy_list;
    private UserEnshuRegisterType  register_type;

    /**
     * @return the api_member_id
     */
    public int getApi_member_id() {
        return api_member_id;
    }

    /**
     * @param api_member_id the api_member_id to set
     */
    public void setApi_member_id(int api_member_id) {
        this.api_member_id = api_member_id;
    }

    /**
     * @return the api_enemy_list
     */
    public List<Integer> getApi_enemy_list() {
        return api_enemy_list;
    }

    /**
     * @param api_enemy_list the api_enemy_list to set
     */
    public void setApi_enemy_list(List<Integer> api_enemy_list) {
        this.api_enemy_list = api_enemy_list;
    }

    /**
     * @return the register_type
     */
    public UserEnshuRegisterType getRegister_type() {
        return register_type;
    }

    /**
     * @param register_type the register_type to set
     */
    public void setRegister_type(UserEnshuRegisterType register_type) {
        this.register_type = register_type;
    }

    
}
