/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.initializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import kcwiki.x.enshuhelper.cache.inmem.AppDataCache;
import kcwiki.x.enshuhelper.database.dao.UtilsDao;
import kcwiki.x.enshuhelper.database.entity.SystemParamEntity;
import kcwiki.x.enshuhelper.database.service.SystemInfoService;
import kcwiki.x.enshuhelper.database.service.UtilsService;
import static kcwiki.x.enshuhelper.tools.ConstantValue.LINESEPARATOR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import kcwiki.x.enshuhelper.httpclient.*;
import static kcwiki.x.enshuhelper.tools.ConstantValue.TEMP_FOLDER;
import static kcwiki.x.enshuhelper.tools.ConstantValue.WEBROOT;

/**
 *
 * @author x5171
 */
@Component
public class AppInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(AppInitializer.class);
    
    @Autowired
    AppConfigs appConfigs;
    @Autowired
    UtilsDao utilsDao;
    @Autowired
    UtilsService utilsService;
    @Autowired
    SystemInfoService systemInfoService;
    @Autowired
    HttpClientConfig httpClientConfig;
    
    boolean isInit = false;
    
    @PostConstruct
    public void initMethod() {
        if(appConfigs == null){
            LOG.error("找不到程序主配置文件 程序初始化失败。");
            System.exit(0);
        }
    }
    
    @PreDestroy
    public void destroyMethod() {
        
    }
    
    public void init(){
        
        LOG.info("KanColle SenkaViewer: initialization started");
        long startTime = System.currentTimeMillis();
        isInit = true;
        checkDatabase();
        getKcServers();
        createFolder();
        AppDataCache.isReadyReceive = true;
        AppDataCache.isAppInit = true;
        long endTime = System.currentTimeMillis();
        if (isInit) {
            LOG.info("KanColle SenkaViewer: initialization completed in {} ms{}", endTime-startTime, LINESEPARATOR);
        } else {
            LOG.error("KanColle SenkaViewer: initialization failed in {} ms{}", endTime-startTime, LINESEPARATOR);
            System.exit(0);
        }
        LOG.info("Mail_title:{}", appConfigs.getMail_title());
        LOG.info("queryCount:{}", AppDataCache.queryCount.longValue());
        LOG.info("matchCount:{}{}", AppDataCache.matchCount.longValue(), LINESEPARATOR);
    }
    
    private void getKcServers() {
        try {
            String repBody = HttpUtils.getHttpBody(appConfigs.getKcwiki_api_servers(), httpClientConfig.makeProxyConfig(false));
            LOG.debug(repBody);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> servers = objectMapper.readValue(repBody,
                    new TypeReference<List<Map<String, Object>>>(){});
            servers.forEach((server) -> {
                AppDataCache.gameWorlds.put((Integer) server.get("id"), ((String) server.get("ip")).trim());
            });
            return;
        } catch (IOException ex) {
            LOG.error("系统初始化失败，获取KanColle游戏服务器列表时发生IOException。");
        }
        isInit = false;
    }
    
    private void checkDatabase() {
//        applicationContext.getAutowireCapableBeanFactory().autowireBean(utilsService);
        String tbname = appConfigs.getDatabase_tables_systemlog();
        if(!utilsService.existTable(tbname)) {
            utilsService.createSystemLogTable(tbname);
        }
        tbname = appConfigs.getDatabase_tables_systemparams();
        if(!utilsService.existTable(tbname)) {
            utilsService.createSystemParamsTable(tbname);
            Date date = new Date();
            SystemParamEntity systemParamEntity = new SystemParamEntity();
            systemParamEntity.setName("queryCount");
            systemParamEntity.setValue("0");
            systemParamEntity.setLastmodified(date);
            systemInfoService.insertOne(systemParamEntity);
            systemParamEntity = new SystemParamEntity();
            systemParamEntity.setName("matchCount");
            systemParamEntity.setValue("0");
            systemParamEntity.setLastmodified(date);
            systemInfoService.insertOne(systemParamEntity);
        } else {
            SystemParamEntity systemParamEntity = systemInfoService.findByName("queryCount");
            if(systemParamEntity != null) {
                AppDataCache.queryCount.add(Long.valueOf(systemParamEntity.getValue()));
            }
            systemParamEntity = systemInfoService.findByName("matchCount");
            if(systemParamEntity != null) {
                AppDataCache.matchCount.add(Long.valueOf(systemParamEntity.getValue()));
            }
        }
        tbname = appConfigs.getDatabase_tables_userdata();
        if(!utilsService.existTable(tbname)) {
            utilsService.createUserDataTable(tbname);
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
        filepath = String.format("%s%s", WEBROOT, appConfigs.getFolder_workspace());
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
        filepath = String.format("%s%s", WEBROOT, appConfigs.getFolder_publish());
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
        filepath = String.format("%s%s", WEBROOT, appConfigs.getFolder_privatedata());
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
    }
}
