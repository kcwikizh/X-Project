/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.controlcenter.database.service;

import kcwiki.management.controlcenter.database.dao.UtilsMapper;
import kcwiki.management.controlcenter.initializer.AppConfig;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private UtilsMapper utilsMapper;
    
    public void createSystemLogTable() {
        utilsMapper.createSystemLogTable();
    }
    
    public void createAuthorizationLogTable() {
        utilsMapper.createAuthorizationLogTable();
    }
    
    public void createModuleAuthorizationTable() {
        utilsMapper.createModuleAuthorizationTable();
    }
            
    public void createModuleIdentityTable() {
        utilsMapper.createModuleIdentityTable();
    }
    
    public void createModuleTokenTable() {
        utilsMapper.createModuleTokenTable();
    }
    
    public void createUserAuthenticationTable() {
        utilsMapper.createUserAuthenticationTable();
    }
    
    public void truncateTable(String tablename) {
        if(existTable(tablename))
            utilsMapper.truncateTable(tablename);
    }
    
    public boolean existTable(String tablename) {
        return utilsMapper.existTable(appConfig.getMyprops_database_name(), tablename) > 0;
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
