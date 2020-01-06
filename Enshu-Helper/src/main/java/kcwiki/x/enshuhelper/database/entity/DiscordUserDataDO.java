/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.database.entity;

import java.util.Date;
import org.iharu.auth2.utils.AuthenticationUtils;

/**
 *
 * @author iHaru
 */
public class DiscordUserDataDO {
    private int id;
    private String teitoku;
    private int gameId;
    private String guildId;
    private String channelId;
    private String memberId;
    private String comments;
    private boolean blocked = false;
    private String blockReason = "";
    private String token;
    private String grantBy;
    private Date timestamp;
    
    public DiscordUserDataDO(){}
    
    public DiscordUserDataDO(String teitoku, int gameId, 
            String guildId, String channelId, String memberId,
            String comments, String grantBy){
        this.teitoku = teitoku;
        this.gameId = gameId;
        this.guildId = guildId;
        this.channelId = channelId;
        this.memberId = memberId;
        this.comments = comments;
        this.grantBy = grantBy;
        this.token = AuthenticationUtils.GenToken();
        this.timestamp = new Date();
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
     * @return the teitoku
     */
    public String getTeitoku() {
        return teitoku;
    }

    /**
     * @param teitoku the teitoku to set
     */
    public void setTeitoku(String teitoku) {
        this.teitoku = teitoku;
    }

    /**
     * @return the gameId
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * @param gameId the gameId to set
     */
    public void setGameId(int gameId) {
        this.gameId = gameId;
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
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the blocked
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * @param blocked the blocked to set
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    /**
     * @return the blockReason
     */
    public String getBlockReason() {
        return blockReason;
    }

    /**
     * @param blockReason the blockReason to set
     */
    public void setBlockReason(String blockReason) {
        this.blockReason = blockReason;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the grantBy
     */
    public String getGrantBy() {
        return grantBy;
    }

    /**
     * @param grantBy the grantBy to set
     */
    public void setGrantBy(String grantBy) {
        this.grantBy = grantBy;
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
