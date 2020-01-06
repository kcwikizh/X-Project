/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.message.websocket.entity;

/**
 *
 * @author iHaru
 */
public class UserData {
    private int memberid;
    private String teitoku;
    private String qq;
    private String qqgroup;
    private String comments;

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
     * @return the memberid
     */
    public int getMemberid() {
        return memberid;
    }

    /**
     * @param memberid the memberid to set
     */
    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }
}
