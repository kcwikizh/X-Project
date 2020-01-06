/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.database.entity;

import java.util.Date;
/**
 *
 * @author iHaru
 */
public class UserDataDO {
    private long id;
    private String teitoku;
    private int gameid;
    private String qq;
    private String qqgroup;
    private int hitcount;
    private String token;
    private String comments;
    private boolean block;
    private Date timestamp;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
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
     * @return the qq
     */
    public String getQq() {
        return qq;
    }

    /**
     * @param qq the qq to set
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * @return the qqgroup
     */
    public String getQqgroup() {
        return qqgroup;
    }

    /**
     * @param qqgroup the qqgroup to set
     */
    public void setQqgroup(String qqgroup) {
        this.qqgroup = qqgroup;
    }

    /**
     * @return the hitcount
     */
    public int getHitcount() {
        return hitcount;
    }

    /**
     * @param hitcount the hitcount to set
     */
    public void setHitcount(int hitcount) {
        this.hitcount = hitcount;
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
     * @return the block
     */
    public boolean isBlock() {
        return block;
    }

    /**
     * @param block the block to set
     */
    public void setBlock(boolean block) {
        this.block = block;
    }

    /**
     * @return the gameid
     */
    public int getGameid() {
        return gameid;
    }

    /**
     * @param gameid the gameid to set
     */
    public void setGameid(int gameid) {
        this.gameid = gameid;
    }
    
}
