/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.retweet.core.userid;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@ConfigurationProperties(prefix = "myprops.twitter.userids")
@Component
public class MonitorList {
    private List<Long> avatarList;
    private List<Long> tweetList;

    /**
     * @return the avatarList
     */
    public List<Long> getAvatarList() {
        return avatarList;
    }

    /**
     * @param avatarList the avatarList to set
     */
    public void setAvatarList(List<Long> avatarList) {
        this.avatarList = avatarList;
    }

    /**
     * @return the tweetList
     */
    public List<Long> getTweetList() {
        return tweetList;
    }

    /**
     * @param tweetList the tweetList to set
     */
    public void setTweetList(List<Long> tweetList) {
        this.tweetList = tweetList;
    }
}
