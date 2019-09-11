/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.retweet.core.entity.retwitter;

import java.util.List;
import kcwiki.retweet.core.type.PayloadType;

/**
 *
 * @author iHaru
 */
public class Rertweet {
    
    private long fromID;
    private String fromName;
    private String message;
    private String avatar;
    private List<String> images;

    /**
     * @return the fromID
     */
    public long getFromID() {
        return fromID;
    }

    /**
     * @param fromID the fromID to set
     */
    public void setFromID(long fromID) {
        this.fromID = fromID;
    }

    /**
     * @return the fromName
     */
    public String getFromName() {
        return fromName;
    }

    /**
     * @param fromName the fromName to set
     */
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar the avatar to set
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return the images
     */
    public List<String> getImages() {
        return images;
    }

    /**
     * @param images the images to set
     */
    public void setImages(List<String> images) {
        this.images = images;
    }
    
    
}
