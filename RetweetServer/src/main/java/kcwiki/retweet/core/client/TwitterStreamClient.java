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
import twitter4j.DirectMessage;
import twitter4j.FilterQuery;
import twitter4j.RateLimitStatusEvent;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.util.function.Consumer;

/**
 *
 * @author iHaru
 */
public class TwitterStreamClient {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterStreamClient.class);
    
    private final TwitterStream twitterStream;
    
    public TwitterStreamClient(String oAuthConsumerKey, String oAuthConsumerSecret, String oAuthAccessToken, String oAuthAccessTokenSecret) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey(oAuthConsumerKey);
        cb.setOAuthConsumerSecret(oAuthConsumerSecret);
        cb.setOAuthAccessToken(oAuthAccessToken);
        cb.setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
        cb.setJSONStoreEnabled(true);
        cb.setTweetModeExtended(true); //Plus other options
        cb.setGZIPEnabled(true);
        twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
    }
    
    public TwitterStreamClient(String oAuthConsumerKey, String oAuthConsumerSecret, String oAuthAccessToken, String oAuthAccessTokenSecret, String host, int port) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey(oAuthConsumerKey);
        cb.setOAuthConsumerSecret(oAuthConsumerSecret);
        cb.setOAuthAccessToken(oAuthAccessToken);
        cb.setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
        cb.setJSONStoreEnabled(true);
        cb.setTweetModeExtended(true); //Plus other options
        cb.setGZIPEnabled(true);
        cb.setHttpProxyHost(host);
        cb.setHttpProxyPort(port);
        twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
    }
    
    public void RunSample() throws TwitterException{
        twitterStream.onStatus(new Consumer<Status>() {
            @Override
            public void accept(Status status) {
                LOG.info("@" + status.getUser().getScreenName() + " - " 
                        + status.getText() + "\r\n"
                        + "avatar"  + "\r\n"
                        + status.getUser().getOriginalProfileImageURL() + "\r\n"
                );
            }
        });
        twitterStream.onException(new Consumer<Exception>() {
            @Override
            public void accept(Exception ex) {
                LOG.error("userStreamListener got error", ex);
            }
        });
        twitterStream.onRateLimitStatus(new Consumer<RateLimitStatusEvent>() {
            @Override
            public void accept(RateLimitStatusEvent t) {
                LOG.info(t.toString());
            }
        });
        twitterStream.sample();
    }
    
    public void SetFilter(long[] follow, String[] track) throws TwitterException{
        FilterQuery query = new FilterQuery();
        query.follow(follow);
        if(track != null && track.length > 0)
            query.track(track);
        twitterStream.filter(query);
    }
    
    public void SetLister() throws TwitterException{
//        StatusListener statuslistener = new StatusListener() {
//            @Override
//            public void onStatus(Status status) {
//                LOG.info("@" + status.getUser().getScreenName() + " - " + status.getText());
//            }
//            @Override
//            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
//                LOG.info("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
//            }
//            @Override
//            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
//                LOG.info("Got track limitation notice:" + numberOfLimitedStatuses);
//            }
//            @Override
//            public void onScrubGeo(long userId, long upToStatusId) {
//                LOG.info("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
//            }
//            @Override
//            public void onStallWarning(StallWarning warning) {
//                LOG.info("Got stall warning:" + warning);
//            }
//            @Override
//            public void onException(Exception ex) {
//                LOG.error("statuslistener got error", ex);
//            }
//        };
        UserStreamListener userStreamListener = new UserStreamListener() {
            @Override
            public void onDeletionNotice(long l, long l1) {
                LOG.info("onDeletionNotice");
            }

            @Override
            public void onFriendList(long[] longs) {
                LOG.info("onFriendList");
            }

            @Override
            public void onFavorite(User user, User user1, Status status) {
                LOG.info("onFavorite");
            }

            @Override
            public void onUnfavorite(User user, User user1, Status status) {
                LOG.info("onUnfavorite");
            }

            @Override
            public void onFollow(User user, User user1) {
                LOG.info("onFollow");
            }

            @Override
            public void onUnfollow(User user, User user1) {
                LOG.info("onUnfollow");
            }

            @Override
            public void onDirectMessage(DirectMessage dm) {
                LOG.info("onDirectMessage");
            }

            @Override
            public void onUserListMemberAddition(User user, User user1, UserList ul) {
                LOG.info("onUserListMemberAddition");
            }

            @Override
            public void onUserListMemberDeletion(User user, User user1, UserList ul) {
                LOG.info("onUserListMemberDeletion");
            }

            @Override
            public void onUserListSubscription(User user, User user1, UserList ul) {
                LOG.info("onUserListSubscription");
            }

            @Override
            public void onUserListUnsubscription(User user, User user1, UserList ul) {
                LOG.info("onUserListUnsubscription");
            }

            @Override
            public void onUserListCreation(User user, UserList ul) {
                LOG.info("onUserListCreation");
            }

            @Override
            public void onUserListUpdate(User user, UserList ul) {
                LOG.info("onUserListUpdate");
            }

            @Override
            public void onUserListDeletion(User user, UserList ul) {
                LOG.info("onUserListDeletion");
            }

            @Override
            public void onUserProfileUpdate(User user) {
                LOG.info("onUserProfileUpdate" + user.getOriginalProfileImageURL() );
            }

            @Override
            public void onUserSuspension(long l) {
                LOG.info("onUserSuspension");
            }

            @Override
            public void onUserDeletion(long l) {
                LOG.info("onUserDeletion");
            }

            @Override
            public void onBlock(User user, User user1) {
                LOG.info("onBlock");
            }

            @Override
            public void onUnblock(User user, User user1) {
                LOG.info("onUnblock");
            }

            @Override
            public void onRetweetedRetweet(User user, User user1, Status status) {
                LOG.info("onRetweetedRetweet");
            }

            @Override
            public void onFavoritedRetweet(User user, User user1, Status status) {
                LOG.info("onFavoritedRetweet");
            }

            @Override
            public void onQuotedTweet(User user, User user1, Status status) {
                LOG.info("onQuotedTweet");
            }

            @Override
            public void onStatus(Status status) {
                LOG.info("@" + status.getUser().getScreenName() + " - " 
                        + status.getText() + "\r\n"
                        + "avatar"  + "\r\n"
                        + status.getUser().getOriginalProfileImageURL() + "\r\n"
                );
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice sdn) {
                LOG.info("onDeletionNotice");
            }

            @Override
            public void onTrackLimitationNotice(int i) {
                LOG.info("onTrackLimitationNotice");
            }

            @Override
            public void onScrubGeo(long l, long l1) {
                LOG.info("onScrubGeo");
            }

            @Override
            public void onStallWarning(StallWarning sw) {
                LOG.info("onStallWarning");
            }

            @Override
            public void onException(Exception ex) {
                LOG.error("userStreamListener got error", ex);
            }
        };
        twitterStream.addListener(userStreamListener);
    }
    
    public void CloseClient(){
        twitterStream.cleanUp();
        twitterStream.shutdown();
    }
    
}
