/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.database.entity;

import java.util.Date;
import kcwiki.x.enshuhelper.database.entity.type.DiscordAdminType;

/**
 *
 * @author iHaru
 */
public class DiscordAdminDataDO {
    private int id;
    private String guildId;
    private String channelId;
    private String memberId;
    private String name;
    private DiscordAdminType role;
    private Date timestamp;
    
    public DiscordAdminDataDO(){}
    
    public DiscordAdminDataDO(String guildId, String channelId, String memberId, String name, DiscordAdminType role, Date timestamp){
        this.guildId = guildId;
        this.channelId = channelId;
        this.memberId = memberId;
        this.name = name;
        this.role = role;
        this.timestamp = timestamp;
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

    /**
     * @return the guildId
     */
    public String getGuildId() {
        return guildId;
    }

    /**
     * @param guildId the guildId to set
     */
    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    /**
     * @return the channelId
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * @param channelId the channelId to set
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * @return the memberId
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * @param memberId the memberId to set
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the role
     */
    public DiscordAdminType getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(DiscordAdminType role) {
        this.role = role;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
