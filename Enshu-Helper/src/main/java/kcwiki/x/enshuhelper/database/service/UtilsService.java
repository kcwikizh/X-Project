/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.database.service;

import kcwiki.x.enshuhelper.initializer.AppConfig;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kcwiki.x.enshuhelper.database.dao.UtilsDAO;

/**
 *
 * @author iHaru
 */
@Service
public class UtilsService {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UtilsService.class);
    
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private UtilsDAO utilsDAO;
    
    
    public boolean createSystemLogTable(String tablename) {
        utilsDAO.createSystemLogTable(tablename);
        return addLog(tablename);
    }
    
    public boolean createSystemParamsTable(String tablename) {
        utilsDAO.createSystemParamsTable(tablename);
        return addLog(tablename);
    }
    
    public boolean createUserDataTable(String tablename) {
        utilsDAO.createUserDataTable(tablename);
        return addLog(tablename);
    }
    
    public boolean createDiscordUserDataTable() {
        utilsDAO.createDiscordUserDataTable();
        return addLog("data_user_discord");
    }
    
    public boolean createDiscordAdminDataTable() {
        utilsDAO.createDiscordAdminDataTable();
        return addLog("data_admin_discord");
    }
    
    public void truncateTable(String tablename) {
        if(existTable(tablename))
            utilsDAO.truncateTable(tablename);
    }
    
    public boolean existTable(String tablename) {
        int result = utilsDAO.existTable(appConfig.getDatabase_name(), tablename);
        return result>0;
    }
    
    private boolean addLog(String tablename) {
        if(existTable(tablename)) {
            LOG.info("创建数据库表: `{}`成功。", tablename);
            return true;
        } else {
            LOG.error("创建数据库表: `{}`失败。", tablename);
            return false;
        }
    }
    
}
