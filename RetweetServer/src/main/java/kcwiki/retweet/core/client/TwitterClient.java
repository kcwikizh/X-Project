/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.retweet.core.client;

import java.util.ArrayList;
import java.util.List;
import org.iharu.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.RateLimitStatus;
import twitter4j.RateLimitStatusEvent;
import twitter4j.RateLimitStatusListener;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterObjectFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author iHaru
 */
public class TwitterClient {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterClient.class);
    
    private final Twitter twitter;
    
    public TwitterClient(String oAuthConsumerKey, String oAuthConsumerSecret, String oAuthAccessToken, String oAuthAccessTokenSecret) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey(oAuthConsumerKey);
        cb.setOAuthConsumerSecret(oAuthConsumerSecret);
        cb.setOAuthAccessToken(oAuthAccessToken);
        cb.setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
        cb.setJSONStoreEnabled(false);
        cb.setTweetModeExtended(true); //Plus other options
        cb.setGZIPEnabled(true);
        twitter=new TwitterFactory(cb.build()).getInstance();
    }
    
    public TwitterClient(String oAuthConsumerKey, String oAuthConsumerSecret, String oAuthAccessToken, String oAuthAccessTokenSecret, String host, int port) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey(oAuthConsumerKey);
        cb.setOAuthConsumerSecret(oAuthConsumerSecret);
        cb.setOAuthAccessToken(oAuthAccessToken);
        cb.setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
        cb.setJSONStoreEnabled(false);
        cb.setTweetModeExtended(true); //Plus other options
        cb.setGZIPEnabled(true);
        cb.setHttpProxyHost(host);
        cb.setHttpProxyPort(port);
        twitter=new TwitterFactory(cb.build()).getInstance();
    }
    
    public void RateLimiting() throws TwitterException{
        twitter.addRateLimitStatusListener(new RateLimitStatusListener() {
            @Override
            public void onRateLimitStatus(RateLimitStatusEvent event) {
                System.out.println("onRateLimitStatus" + event);
                boolean accountLimitStatusAcquired = event.isAccountRateLimitStatus();
                boolean ipLimitStatusAcquired = event.isIPRateLimitStatus();
                RateLimitStatus rateLimitStatus = event.getRateLimitStatus();
            }

            @Override
            public void onRateLimitReached(RateLimitStatusEvent event) {

            }
        });
    }
    
    public User GetUserByUserID(long uid) throws TwitterException{
        return twitter.showUser(uid);
    }
    
    public User GetUserByUserID(String uname) throws TwitterException{
        return twitter.showUser(uname);
    }
    
    public List<Status> GetStatusByUserID(long uid, long timestamp) throws TwitterException{
        List<Status> statuses = twitter.getUserTimeline(uid);
        List<Status> newstatus = new ArrayList();
        for (Status status : statuses) {
//            if(status.getCreatedAt().getTime() <= timestamp)
//                continue;
            newstatus.add(status);
        }
        return newstatus;
    }
    
}
