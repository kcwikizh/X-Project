/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.web.controller.entity.request;

import java.util.List;

/**
 *
 * @author x5171
 */
public class UserEnshuInfoEntity {
    private long  api_member_id;
    private List<Long> api_enemy_list;

    /**
     * @return the api_member_id
     */
    public long getApi_member_id() {
        return api_member_id;
    }

    /**
     * @param api_member_id the api_member_id to set
     */
    public void setApi_member_id(long api_member_id) {
        this.api_member_id = api_member_id;
    }

    /**
     * @return the api_enemy_list
     */
    public List<Long> getApi_enemy_list() {
        return api_enemy_list;
    }

    /**
     * @param api_enemy_list the api_enemy_list to set
     */
    public void setApi_enemy_list(List<Long> api_enemy_list) {
        this.api_enemy_list = api_enemy_list;
    }

    
}
