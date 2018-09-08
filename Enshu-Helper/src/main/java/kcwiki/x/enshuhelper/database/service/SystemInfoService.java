/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.database.service;

import java.util.Date;
import java.util.List;
import kcwiki.x.enshuhelper.cache.inmem.AppDataCache;
import kcwiki.x.enshuhelper.database.dao.SystemInfoDao;
import kcwiki.x.enshuhelper.database.entity.SystemParamEntity;
import kcwiki.x.enshuhelper.initializer.AppConfigs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author x5171
 */
@Service
public class SystemInfoService {
    
    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private SystemInfoDao systemInfoDao;
    
    public List<SystemParamEntity> getAll(int gameid) {
        return systemInfoDao.selectAllParams(appConfigs.getDatabase_tables_systemparams());
    }
    
    public SystemParamEntity findByName(String name) {
        return systemInfoDao.selectOne(appConfigs.getDatabase_tables_systemparams(), name);
    }
    
    public int insertOne(SystemParamEntity systemParamEntity) {
        return systemInfoDao.insertOne(appConfigs.getDatabase_tables_systemparams(), systemParamEntity);
    }
    
    public int updateOne(SystemParamEntity systemParamEntity) {
        return systemInfoDao.udpateOne(appConfigs.getDatabase_tables_systemparams(), systemParamEntity);
    }
    
    public void updateCount() {
        Date date = new Date();
        SystemParamEntity systemParamEntity = new SystemParamEntity();
        systemParamEntity.setName("queryCount");
        systemParamEntity.setValue(AppDataCache.queryCount.toString());
        systemParamEntity.setLastmodified(date);
        updateOne(systemParamEntity);
        systemParamEntity = new SystemParamEntity();
        systemParamEntity.setName("matchCount");
        systemParamEntity.setValue(AppDataCache.matchCount.toString());
        systemParamEntity.setLastmodified(date);
        updateOne(systemParamEntity);
    }
    
}
