/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.retweet.core;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import kcwiki.retweet.cache.inmem.AppDataCache;
import kcwiki.retweet.cache.inmem.RuntimeValue;
import kcwiki.retweet.core.client.TwitterClient;
import kcwiki.retweet.core.entity.retwitter.Rertweet;
import kcwiki.retweet.core.userid.MonitorList;
import kcwiki.retweet.initializer.AppConfig;
import kcwiki.retweet.message.websocket.MessagePublisher;
import static org.iharu.constant.ConstantValue.LINESEPARATOR;
import org.iharu.spring.SpringUtils;
import org.iharu.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 *
 * @author iHaru
 */
@Component
public class RetweetController {
    private static final Logger LOG = LoggerFactory.getLogger(RetweetController.class);
    
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private RuntimeValue runtimeValue;
    @Autowired
    private MonitorList monitorList;
    @Autowired
    private MessagePublisher messagePublisher;
    
    TwitterClient twitterClient;
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);   
        
    @PostConstruct
    public void initMethod() throws TwitterException {
        if(appConfig.isAllow_use_proxy()) {
            twitterClient = new TwitterClient(
                    appConfig.getTwitter_api(), appConfig.getTwitter_apisec(),
                    appConfig.getTwitter_token(), appConfig.getTwitter_tokensec(),
                    appConfig.getProxy_host(), appConfig.getProxy_port());
        } else {
            twitterClient = new TwitterClient(
                    appConfig.getTwitter_api(), appConfig.getTwitter_apisec(),
                    appConfig.getTwitter_token(), appConfig.getTwitter_tokensec());
        }
    }
    
    @PreDestroy
    public void destroyMethod() {
        scheduledExecutorService.shutdownNow();
    }
    
    public void init() throws TwitterException{
//        for(long userID:monitorList.getAvatarList()){
//            AppDataCache.AvatarMap.put(userID, twitterClient.GetUserByUserID(userID).getOriginalProfileImageURLHttps());
//        }
        for(long userID:monitorList.getTweetList()){
            List<Status> statuses = twitterClient.GetStatusByUserID(userID, 0);
            statuses.sort((s1, s2) -> Long.compare(s1.getCreatedAt().getTime(), s2.getCreatedAt().getTime()));
            AppDataCache.StatusMap.put(userID, statuses.get(statuses.size()-1).getCreatedAt().getTime());
        }
        runScanner();
    }
    
    private void runScanner(){
        if(appConfig.isAllow_use_proxy()) {
//        scheduledExecutorService.scheduleWithFixedDelay(new AvatarScanTask(runtimeValue, messagePublisher, twitterClient), 5, 10, TimeUnit.SECONDS);  
            scheduledExecutorService.scheduleWithFixedDelay(new StatusScanTask(runtimeValue, messagePublisher, twitterClient, appConfig.getProxy_host(), appConfig.getProxy_port()), 5, 10, TimeUnit.SECONDS);  
        } else {
            scheduledExecutorService.scheduleWithFixedDelay(new StatusScanTask(runtimeValue, messagePublisher, twitterClient, null, -1), 5, 10, TimeUnit.SECONDS);  
        }
    }
    
    public void HandleUser(User user){
        long userID = user.getId();
        String avatarURL = user.getOriginalProfileImageURLHttps();
        
        Rertweet rertweet = new Rertweet();
        rertweet.setFromID(user.getId());
        rertweet.setFromName(user.getName());
        if(AppDataCache.AvatarMap.containsKey(userID)) {
            if(!avatarURL.equals(AppDataCache.AvatarMap.get(userID))){
                AppDataCache.AvatarMap.put(userID, avatarURL);
                String filename = new File(avatarURL).getName();
                try {
                    if(appConfig.isAllow_use_proxy()) {
                        HttpUtils.DownloadFile(avatarURL, runtimeValue.AVATAR_FOLDER, filename, appConfig.getProxy_host(), appConfig.getProxy_port());
                    } else {
                        HttpUtils.DownloadFile(avatarURL, runtimeValue.AVATAR_FOLDER, filename);
                    }
                    rertweet.setAvatar(getAvatarUrl(filename));
                } catch (IOException ex) {
                    LOG.warn("HandleStatus media download error。 url: {}", avatarURL, ex);
                }
            }
        } else {
            AppDataCache.AvatarMap.put(userID, avatarURL);
        }
        messagePublisher.publish(rertweet);
    }
    
    public void HandleStatus(Status status){
        long userID = status.getUser().getId();
        String avatarURL = status.getUser().getOriginalProfileImageURLHttps();
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        Rertweet rertweet = new Rertweet();
        String msg = "@" + status.getUser().getName() + LINESEPARATOR
                        + format.format(status.getCreatedAt()) + "\t" + format.getTimeZone().getID() + LINESEPARATOR
                        + LINESEPARATOR
                        + status.getText();
//                        + "avatar"
//                        + status.getUser().getOriginalProfileImageURL();
        rertweet.setFromID(status.getUser().getId());
        rertweet.setFromName(status.getUser().getName());
        if(AppDataCache.AvatarMap.containsKey(userID)) {
            if(!avatarURL.equals(AppDataCache.AvatarMap.get(userID))){
                AppDataCache.AvatarMap.put(userID, avatarURL);
                String filename = new File(avatarURL).getName();
                try {
                    if(appConfig.isAllow_use_proxy()) {
                        HttpUtils.DownloadFile(avatarURL, runtimeValue.AVATAR_FOLDER, filename, appConfig.getProxy_host(), appConfig.getProxy_port());
                    } else {
                        HttpUtils.DownloadFile(avatarURL, runtimeValue.AVATAR_FOLDER, filename);
                    }
                    rertweet.setAvatar(getAvatarUrl(filename));
                } catch (IOException ex) {
                    LOG.warn("HandleStatus media download error。 url: {}", avatarURL, ex);
                }
            }
        } else {
            if(monitorList.getAvatarList().contains(userID))
                AppDataCache.AvatarMap.put(userID, avatarURL);
        }
        if(status.getMediaEntities().length > 0) {
            List<String> imgs = new ArrayList<>();
            for(MediaEntity media :status.getMediaEntities()){
                if(!media.getType().equals("photo")){
                    continue;
                }
                msg = msg.replace(media.getURL(), "") ;
                String filename = String.valueOf(media.getId()) + ".jpg";
                try {
                    if(appConfig.isAllow_use_proxy()) {
                        HttpUtils.DownloadFile(media.getMediaURLHttps(), runtimeValue.IMAGE_FOLDER, filename, appConfig.getProxy_host(), appConfig.getProxy_port());
                    } else {
                        HttpUtils.DownloadFile(media.getMediaURLHttps(), runtimeValue.IMAGE_FOLDER, filename);
                    }
                    imgs.add(getImageUrl(filename));
                } catch (IOException ex) {
                    LOG.warn("HandleStatus media download error。 url: {}", media.getMediaURLHttps(), ex);
                }
            }
            if(!imgs.isEmpty()) {
               rertweet.setImages(imgs);
            }
        }
        rertweet.setMessage(msg);
        messagePublisher.publish(rertweet);
    }
    
    public void HandleStatuses(List<Status> statuses){
        if(statuses == null || statuses.isEmpty())
            return;
        statuses.sort((s1, s2) -> Long.compare(s1.getCreatedAt().getTime(), s2.getCreatedAt().getTime()));
        long userID = statuses.get(0).getUser().getId();
        if(!AppDataCache.StatusMap.containsKey(userID)) {
            AppDataCache.StatusMap.put(userID, statuses.get(statuses.size()-1).getCreatedAt().getTime());
            return;
        }
        long timestamp = AppDataCache.StatusMap.get(userID);
        statuses.stream().filter((status) -> !(timestamp >= status.getCreatedAt().getTime())).forEachOrdered((status) -> {
            CompletableFuture.runAsync(() -> {
                HandleStatus(status);
            });
        });
        AppDataCache.StatusMap.put(userID, statuses.get(statuses.size()-1).getCreatedAt().getTime());
        statuses.clear();
    }
    
    private String getAvatarUrl(String filename){
        return runtimeValue.AVATAR_URL + filename;
    }
    
    private String getImageUrl(String filename){
        return runtimeValue.IMAGE_URL + filename;
    }
    
    private static String getExtension(String type) {
        if (type.equals("photo")) {
            return "jpg";
        } else if (type.equals("video")) {
            return "mp4";
        } else if (type.equals("animated_gif")) {
            return "gif";
        } else {
            return "err";
        }
    }
    
}

class ScanTask{
    
    protected RuntimeValue runtimeValue;
    protected MessagePublisher messagePublisher;
    protected String host; 
    protected int port; 
    
    public ScanTask(RuntimeValue runtimeValue, MessagePublisher messagePublisher, String host, int port) {
        this.runtimeValue = runtimeValue;
        this.messagePublisher = messagePublisher;
        this.host = host;
        this.port = port;
    }
    
    protected String getAvatarUrl(String filename){
        return runtimeValue.AVATAR_URL + filename;
    }
    
    protected String getImageUrl(String filename){
        return runtimeValue.IMAGE_URL + filename;
    }
    
}

class AvatarScanTask extends ScanTask implements Runnable {  
    private static final Logger LOG = LoggerFactory.getLogger(AvatarScanTask.class);
    
    private final TwitterClient twitterClient;
    private final MonitorList monitorList;
    
    public AvatarScanTask(RuntimeValue runtimeValue, MessagePublisher messagePublisher, TwitterClient twitterClient, String host, int port){
        super(runtimeValue, messagePublisher, host, port);
        this.twitterClient = twitterClient;
        monitorList = SpringUtils.getBean(MonitorList.class);
    }
    
    @Override  
    public void run() {  
        monitorList.getAvatarList().forEach(userID -> {
            try {
                handleUser(twitterClient.GetUserByUserID(userID));
            } catch (TwitterException ex) {
                LOG.error("AvatarScanTask Error", ex);
            }
        });
    }  
    
    private void handleUser(User user){
        long userID = user.getId();
        String avatarURL = user.getOriginalProfileImageURLHttps();
        
        Rertweet rertweet = new Rertweet();
        rertweet.setFromID(user.getId());
        rertweet.setFromName(user.getName());
        if(AppDataCache.AvatarMap.containsKey(userID)) {
            if(!avatarURL.equals(AppDataCache.AvatarMap.get(userID))){
                AppDataCache.AvatarMap.put(userID, avatarURL);
                String filename = new File(avatarURL).getName();
                try {
                    if(host != null && !host.trim().isEmpty() && port > 0) {
                        HttpUtils.DownloadFile(avatarURL, runtimeValue.AVATAR_FOLDER, filename, host, port);
                    } else {
                        HttpUtils.DownloadFile(avatarURL, runtimeValue.AVATAR_FOLDER, filename);
                    }
                    rertweet.setAvatar(getAvatarUrl(filename));
                } catch (IOException ex) {
                    LOG.warn("HandleStatus media download error。 url: {}", avatarURL, ex);
                }
            }
        } else {
            AppDataCache.AvatarMap.put(userID, avatarURL);
        }
        messagePublisher.publish(rertweet);
    }
}  

class StatusScanTask extends ScanTask implements Runnable {  
    private static final Logger LOG = LoggerFactory.getLogger(StatusScanTask.class);
  
    private final TwitterClient twitterClient;
    private final MonitorList monitorList;
    
    public StatusScanTask(RuntimeValue runtimeValue, MessagePublisher messagePublisher, TwitterClient twitterClient, String host, int port){
        super(runtimeValue, messagePublisher, host, port);
        this.twitterClient = twitterClient;
        monitorList = SpringUtils.getBean(MonitorList.class);
    }
    
    @Override  
    public void run() {  
         monitorList.getTweetList().forEach(userID -> {
            try {
                handleStatuses(twitterClient.GetStatusByUserID(userID, AppDataCache.StatusMap.get(userID)));
            } catch (TwitterException ex) {
                LOG.error("StatusScanTask Error", ex);
            }
        });
    }  
    
    private void handleStatus(Status status){
        long userID = status.getUser().getId();
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        Rertweet rertweet = new Rertweet();
        String msg = "@" + status.getUser().getName() + LINESEPARATOR
                        + format.format(status.getCreatedAt()) + "\t" + format.getTimeZone().getID() + LINESEPARATOR
                        + LINESEPARATOR
                        + status.getText();
//                        + "avatar"
//                        + status.getUser().getOriginalProfileImageURL();
        rertweet.setFromID(userID);
        rertweet.setFromName(status.getUser().getName());
        
        if(status.getMediaEntities().length > 0) {
            List<String> imgs = new ArrayList<>();
            for(MediaEntity media :status.getMediaEntities()){
                if(!media.getType().equals("photo")){
                    continue;
                }
                msg = msg.replace(media.getURL(), "") ;
                String filename = String.valueOf(media.getId()) + ".jpg";
                try {
                    if(host != null && !host.trim().isEmpty() && port > 0) {
                        HttpUtils.DownloadFile(media.getMediaURLHttps(), runtimeValue.IMAGE_FOLDER, filename, host, port);
                    } else {
                        HttpUtils.DownloadFile(media.getMediaURLHttps(), runtimeValue.IMAGE_FOLDER, filename);
                    }
                    imgs.add(getImageUrl(filename));
                } catch (IOException ex) {
                    LOG.warn("HandleStatus media download error。 url: {}", media.getMediaURLHttps(), ex);
                }
            }
            if(!imgs.isEmpty()) {
               rertweet.setImages(imgs);
            }
        }
        rertweet.setMessage(msg);
        messagePublisher.publish(rertweet);
    }
    
    private void handleStatuses(List<Status> statuses){
        if(statuses == null || statuses.isEmpty())
            return;
        statuses.sort((s1, s2) -> Long.compare(s1.getCreatedAt().getTime(), s2.getCreatedAt().getTime()));
        Status laststatus = statuses.get(statuses.size()-1);
        long userID = laststatus.getUser().getId();
        if(!AppDataCache.StatusMap.containsKey(userID)) {
            AppDataCache.StatusMap.put(userID, laststatus.getCreatedAt().getTime());
            return;
        }
        String avatarURL = laststatus.getUser().getOriginalProfileImageURLHttps();
        if(AppDataCache.AvatarMap.containsKey(userID)) {
            if(!avatarURL.equals(AppDataCache.AvatarMap.get(userID))){
                AppDataCache.AvatarMap.put(userID, avatarURL);
                String filename = new File(avatarURL).getName();
                try {
                    if(host != null && !host.trim().isEmpty() && port > 0) {
                        HttpUtils.DownloadFile(avatarURL, runtimeValue.AVATAR_FOLDER, filename, host, port);
                    } else {
                        HttpUtils.DownloadFile(avatarURL, runtimeValue.AVATAR_FOLDER, filename);
                    }
                    Rertweet rertweet = new Rertweet();
                    rertweet.setFromID(userID);
                    rertweet.setFromName(laststatus.getUser().getName());
                    rertweet.setAvatar(getAvatarUrl(filename));
                    messagePublisher.publish(rertweet);
                } catch (IOException ex) {
                    LOG.warn("HandleStatus media download error。 url: {}", avatarURL, ex);
                }
            }
        } else {
            if(monitorList.getAvatarList().contains(userID))
                AppDataCache.AvatarMap.put(userID, avatarURL);
        }
        long timestamp = AppDataCache.StatusMap.get(userID);
        statuses.stream().filter((status) -> !(timestamp >= status.getCreatedAt().getTime())).forEachOrdered((status) -> {
            CompletableFuture.runAsync(() -> {
                handleStatus(status);
            });
        });
        AppDataCache.StatusMap.put(userID, statuses.get(statuses.size()-1).getCreatedAt().getTime());
        statuses.clear();
    }
}  