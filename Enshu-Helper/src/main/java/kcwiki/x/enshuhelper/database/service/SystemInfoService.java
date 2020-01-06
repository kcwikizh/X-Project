/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.database.service;

import java.util.Date;
import java.util.List;
import kcwiki.x.enshuhelper.cache.inmem.AppDataCache;
import kcwiki.x.enshuhelper.database.entity.SystemParamDO;
import kcwiki.x.enshuhelper.initializer.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kcwiki.x.enshuhelper.database.dao.SystemInfoDAO;

/**
 *
 * @author iHaru
 */
@Service
public class SystemInfoService {
    
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private SystemInfoDAO systemInfoDAO;
    
    public List<SystemParamDO> getAll(int gameid) {
        return systemInfoDAO.selectAllParams(appConfig.getDatabase_tables_systemparams());
    }
    
    public SystemParamDO findByName(String name) {
        return systemInfoDAO.selectOne(appConfig.getDatabase_tables_systemparams(), name);
    }
    
    public int insertOne(SystemParamDO systemParamEntity) {
        return systemInfoDAO.insertOne(appConfig.getDatabase_tables_systemparams(), systemParamEntity);
    }
    
    public int updateOne(SystemParamDO systemParamEntity) {
        return systemInfoDAO.udpateOne(appConfig.getDatabase_tables_systemparams(), systemParamEntity);
    }
    
    public void updateCount() {
        Date date = new Date();
        SystemParamDO systemParamEntity = new SystemParamDO();
        systemParamEntity.setName("queryCount");
        systemParamEntity.setValue(AppDataCache.queryCount.toString());
        systemParamEntity.setLastmodified(date);
        updateOne(systemParamEntity);
        systemParamEntity = new SystemParamDO();
        systemParamEntity.setName("matchCount");
        systemParamEntity.setValue(AppDataCache.matchCount.toString());
        systemParamEntity.setLastmodified(date);
        updateOne(systemParamEntity);
    }
    
}
