/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.initializer;

import java.io.File;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import kcwiki.akashi.cache.inmem.AppDataCache;
import kcwiki.akashi.core.generator.ShipDataGenerator;
import kcwiki.akashi.core.generator.SlotitemDataGenerator;
import static org.iharu.constant.ConstantValue.LINESEPARATOR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
public class AppInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(AppInitializer.class);
    
    @Autowired
    AppConfig appConfig;
    @Autowired 
    ShipDataGenerator shipDataGenerator;
    @Autowired 
    SlotitemDataGenerator slotitemDataGenerator;
    
    boolean isInit = false;
    
    @PostConstruct
    public void initMethod() {
        if(appConfig == null){
            LOG.error("找不到程序主配置文件 程序初始化失败。");
            System.exit(0);
        }
    }
    
    @PreDestroy
    public void destroyMethod() {
        
    }
    
    public void init(){
        
        LOG.info("Akashi Toolkit Server: initialization started");
        long startTime = System.currentTimeMillis();
        isInit = true;
        initData();
//        checkDatabase();
//        createFolder();
        long endTime = System.currentTimeMillis();
        if (isInit) {
            AppDataCache.isAppInit = true;
            LOG.info("Akashi Toolkit Server: initialization completed in {} ms{}", endTime-startTime, LINESEPARATOR);
        } else {
            LOG.error("Akashi Toolkit Server: initialization failed in {} ms{}", endTime-startTime, LINESEPARATOR);
            System.exit(0);
        }
    }
    
    private void initData() {
        shipDataGenerator.generate();
        slotitemDataGenerator.generate();
    }
    
    private void checkDatabase() {
        
    }
    
}
