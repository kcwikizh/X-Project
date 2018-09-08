/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.database.service;

import kcwiki.x.enshuhelper.database.dao.UtilsDao;
import kcwiki.x.enshuhelper.initializer.AppConfigs;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author x5171
 */
@Service
public class UtilsService {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UtilsService.class);
    
    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private UtilsDao utilsDao;
    
    
    public boolean createSystemLogTable(String tablename) {
        utilsDao.createSystemLogTable(tablename);
        return addLog(tablename);
    }
    
    public boolean createSystemParamsTable(String tablename) {
        utilsDao.createSystemParamsTable(tablename);
        return addLog(tablename);
    }
    
    public boolean createUserDataTable(String tablename) {
        utilsDao.createUserDataTable(tablename);
        return addLog(tablename);
    }
    
    public void truncateTable(String tablename) {
        if(existTable(tablename))
            utilsDao.truncateTable(tablename);
    }
    
    public boolean existTable(String tablename) {
        int result = utilsDao.existTable(appConfigs.getDatabase_name(), tablename);
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
