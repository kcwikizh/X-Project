/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.core.entity;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import kcwiki.x.enshuhelper.database.entity.DiscordUserDataDO;

/**
 *
 * @author iHaru
 */
public class DiscordUserDataBO {
    private String teitoku;
    private int gameId;
    private String guildId;
    private String channelId;
    private String memberId;
    private String comments;
    private boolean blocked = false;
    private String blockReason = "";
    private String token;
    
    public DiscordUserDataBO(){}
    
    public static DiscordUserDataBO generateBOByDO(DiscordUserDataDO discordUserDataDO){
        if(discordUserDataDO == null)
            return null;
        return new DiscordUserDataBO(discordUserDataDO);
    }
    
    public static List<DiscordUserDataBO> listBOByDO(List<DiscordUserDataDO> list){
        if(list == null)
            return null;
        return list.stream().map(discordUserDataDO -> generateBOByDO(discordUserDataDO)).collect(Collectors.toList());
    }
    
    private DiscordUserDataBO(DiscordUserDataDO discordUserDataDO){
        this.teitoku = discordUserDataDO.getTeitoku();
        this.gameId = discordUserDataDO.getGameId();
        this.guildId = discordUserDataDO.getGuildId();
        this.channelId = discordUserDataDO.getChannelId();
        this.memberId = discordUserDataDO.getMemberId();
        this.comments = discordUserDataDO.getComments();
        this.blocked = discordUserDataDO.isBlocked();
        this.blockReason = discordUserDataDO.getBlockReason();
        this.token = discordUserDataDO.getToken();
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

}
