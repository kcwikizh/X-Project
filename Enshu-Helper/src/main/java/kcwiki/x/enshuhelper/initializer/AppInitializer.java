/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.initializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.io.CharStreams;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import kcwiki.x.enshuhelper.cache.inmem.AppDataCache;
import kcwiki.x.enshuhelper.cache.inmem.RuntimeValue;
import kcwiki.x.enshuhelper.constant.ConstantValue;
import kcwiki.x.enshuhelper.database.entity.SystemParamDO;
import kcwiki.x.enshuhelper.database.service.SystemInfoService;
import kcwiki.x.enshuhelper.database.service.UtilsService;
import kcwiki.x.enshuhelper.discord.DiscordBotController;
import static org.iharu.constant.ConstantValue.LINESEPARATOR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import kcwiki.x.enshuhelper.httpclient.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.iharu.util.JsonUtils;
import kcwiki.x.enshuhelper.database.dao.UtilsDAO;

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
    UtilsDAO utilsDao;
    @Autowired
    UtilsService utilsService;
    @Autowired
    SystemInfoService systemInfoService;
    @Autowired
    HttpClientConfig httpClientConfig;
    @Autowired 
    RuntimeValue RUNTIME;
    @Autowired
    DiscordBotController discordBotController;
    
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
        
        LOG.info("X-Project Enshu-Helper: initialization started");
        long startTime = System.currentTimeMillis();
        isInit = true;
        checkDatabase();
        getKcServers();
        createFolder();
        discordBotController.start();
        AppDataCache.isReadyReceive = true;
        AppDataCache.isAppInit = true;
        long endTime = System.currentTimeMillis();
        if (isInit) {
            LOG.info("X-Project Enshu-Helper: initialization completed in {} ms{}", endTime-startTime, LINESEPARATOR);
        } else {
            LOG.error("X-Project Enshu-Helper: initialization failed in {} ms{}", endTime-startTime, LINESEPARATOR);
            System.exit(0);
        }
        LOG.info("Mail_title:{}", appConfig.getMail_title());
        LOG.info("queryCount:{}", AppDataCache.queryCount.longValue());
        LOG.info("matchCount:{}{}", AppDataCache.matchCount.longValue(), LINESEPARATOR);
    }
    
    private void getKcServers() {
        try {
            HttpEntity responseEntity = HttpClients.createDefault().execute(new HttpGet(appConfig.getKcwiki_api_servers())).getEntity();
            if(responseEntity==null) {
                return;
            }
            String repBody;
            try (final Reader reader = new InputStreamReader(responseEntity.getContent(), StandardCharsets.UTF_8)) {
                repBody = CharStreams.toString(reader);
            }
            if(StringUtils.isBlank(repBody))
                return;
            List<Map<String, Object>> servers = JsonUtils.json2object(repBody, new TypeReference<List<Map<String, Object>>>(){});
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
        String tbname = appConfig.getDatabase_tables_systemlog();
        if(!utilsService.existTable(tbname)) {
            utilsService.createSystemLogTable(tbname);
        }
        tbname = appConfig.getDatabase_tables_systemparams();
        if(!utilsService.existTable(tbname)) {
            utilsService.createSystemParamsTable(tbname);
            Date date = new Date();
            SystemParamDO systemParamEntity = new SystemParamDO();
            systemParamEntity.setName("queryCount");
            systemParamEntity.setValue("0");
            systemParamEntity.setLastmodified(date);
            systemInfoService.insertOne(systemParamEntity);
            systemParamEntity = new SystemParamDO();
            systemParamEntity.setName("matchCount");
            systemParamEntity.setValue("0");
            systemParamEntity.setLastmodified(date);
            systemInfoService.insertOne(systemParamEntity);
        } else {
            SystemParamDO systemParamEntity = systemInfoService.findByName("queryCount");
            if(systemParamEntity != null) {
                AppDataCache.queryCount.add(Long.valueOf(systemParamEntity.getValue()));
            }
            systemParamEntity = systemInfoService.findByName("matchCount");
            if(systemParamEntity != null) {
                AppDataCache.matchCount.add(Long.valueOf(systemParamEntity.getValue()));
            }
        }
        tbname = appConfig.getDatabase_tables_userdata();
        if(!utilsService.existTable(tbname)) {
            utilsService.createUserDataTable(tbname);
        }
        utilsService.createDiscordAdminDataTable();
        utilsService.createDiscordUserDataTable();
    }
    
    private void createFolder(){
        File file;
        String filepath;
        filepath = ConstantValue.TEMP_FOLDER;
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
        filepath = RUNTIME.WORKSPACE_FOLDER;
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
        filepath = RUNTIME.PUBLISH_FOLDER;
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
        filepath = RUNTIME.PRIVATEDATA_FOLDER;
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
    }
}
