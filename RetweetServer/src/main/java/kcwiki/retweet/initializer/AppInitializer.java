/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.retweet.initializer;

import java.io.File;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import kcwiki.retweet.cache.inmem.AppDataCache;
import kcwiki.retweet.cache.inmem.RuntimeValue;
import static kcwiki.retweet.constant.ConstantValue.TEMP_FOLDER;
import kcwiki.retweet.core.RetweetController;
import static org.iharu.constant.ConstantValue.LINESEPARATOR;
import org.iharu.constant.ConstantValue;
import org.iharu.initializer.InitializerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.TwitterException;

/**
 *
 * @author iHaru
 */
@Component
public class AppInitializer implements InitializerInterface {
    private static final Logger LOG = LoggerFactory.getLogger(AppInitializer.class);
    
    @Autowired
    AppConfig appConfig;
    @Autowired 
    RuntimeValue RUNTIME;
    
    @Autowired 
    RetweetController retweetController;
    
    
    boolean isInit = false;
    
    @PostConstruct
    @Override
    public void initMethod() {
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "32");
        if(appConfig == null){
            LOG.error("找不到程序主配置文件 程序初始化失败。");
        }
    }
    
    @PreDestroy
    @Override
    public void destroyMethod() {
        
    }
    
    @Override
    public void init() throws IOException, TwitterException {
        LOG.info("Retweet Server: initialization started");
        isInit = true;
        long startTime=System.currentTimeMillis();
        createFolder();
        retweetController.init();
        long endTime=System.currentTimeMillis();
        LOG.info("allow use proxy: {}", appConfig.isAllow_use_proxy());
        LOG.info("AppRoot folder: {}", RUNTIME.APPROOT);
        LOG.info("Temp folder: {}", TEMP_FOLDER);
        LOG.info("WebRoot folder: {}", RUNTIME.WEBROOT_FOLDER);
        LOG.info("IMAGE_FOLDER path: {}", RUNTIME.IMAGE_FOLDER);
        LOG.info("AVATAR_FOLDER path: {}", RUNTIME.AVATAR_FOLDER);
        LOG.info("IMAGE_URL path: {}", RUNTIME.IMAGE_URL);
        LOG.info("AVATAR_URL path: {}", RUNTIME.AVATAR_URL);
        if (isInit) {
            AppDataCache.IsAppInit = true;
            LOG.info("Retweet Server: initialization completed in {} ms{}", endTime-startTime, LINESEPARATOR);
        } else {
            LOG.error("Retweet Server: initialization failed in {} ms{}", endTime-startTime, LINESEPARATOR);
            System.exit(0);
        }
    }
    
    private void createFolder(){
        File file;
        String filepath;
        filepath = TEMP_FOLDER;
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
        filepath = RUNTIME.WEBROOT_FOLDER;
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
        filepath = RUNTIME.IMAGE_FOLDER;
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
        filepath = RUNTIME.AVATAR_FOLDER;
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
    }
}
