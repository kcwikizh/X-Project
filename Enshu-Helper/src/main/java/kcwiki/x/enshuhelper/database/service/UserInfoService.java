/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.database.service;

import java.util.ArrayList;
import java.util.List;
import kcwiki.x.enshuhelper.database.dao.UserInfoDao;
import kcwiki.x.enshuhelper.database.entity.UserDataEntity;
import kcwiki.x.enshuhelper.initializer.AppConfigs;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author iHaru
 */
@Service
public class UserInfoService {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UserInfoService.class);
    
    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private UserInfoDao userInfoDao;
    
    public UserDataEntity findByGameid(long gameid) {
        return userInfoDao.selectOne(appConfigs.getDatabase_tables_userdata(), gameid);
    }
    
    public List<UserDataEntity> findByGameids(List<Long> gameids) {
        if(gameids.isEmpty())
            return new ArrayList<>();
        return userInfoDao.selectBatch(appConfigs.getDatabase_tables_userdata(), gameids);
    }
    
    public int addUser(UserDataEntity userDataEntity) {
        return userInfoDao.insertOne(appConfigs.getDatabase_tables_userdata(), userDataEntity);
    }
    
    public int updateUser(UserDataEntity userDataEntity) {
        return userInfoDao.udpateOne(appConfigs.getDatabase_tables_userdata(), userDataEntity);
    }
    
    public int updateUsers(List<UserDataEntity> list) {
        return userInfoDao.udpateBatch(appConfigs.getDatabase_tables_userdata(), list);
    }
    
    public int deleteUser(long gameid) {
        return userInfoDao.deleteOne(appConfigs.getDatabase_tables_userdata(), gameid);
    }
}
