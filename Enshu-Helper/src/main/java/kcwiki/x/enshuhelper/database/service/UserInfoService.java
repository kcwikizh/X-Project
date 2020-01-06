/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.database.service;

import java.util.ArrayList;
import java.util.List;
import kcwiki.x.enshuhelper.database.entity.UserDataDO;
import kcwiki.x.enshuhelper.initializer.AppConfig;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kcwiki.x.enshuhelper.database.dao.UserInfoDAO;

/**
 *
 * @author iHaru
 */
@Service
public class UserInfoService {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UserInfoService.class);
    
    @Autowired
    private AppConfig appConfigs;
    @Autowired
    private UserInfoDAO userInfoDAO;
    
    public UserDataDO findByGameid(long gameid) {
        return userInfoDAO.selectOne(appConfigs.getDatabase_tables_userdata(), gameid);
    }
    
    public List<UserDataDO> findByGameids(List<Integer> gameids) {
        if(gameids.isEmpty())
            return new ArrayList<>();
        return userInfoDAO.selectBatch(appConfigs.getDatabase_tables_userdata(), gameids);
    }
    
    public int addUser(UserDataDO userDataEntity) {
        return userInfoDAO.insertOne(appConfigs.getDatabase_tables_userdata(), userDataEntity);
    }
    
    public int updateUser(UserDataDO userDataEntity) {
        return userInfoDAO.udpateOne(appConfigs.getDatabase_tables_userdata(), userDataEntity);
    }
    
    public int updateUsers(List<UserDataDO> list) {
        return userInfoDAO.udpateBatch(appConfigs.getDatabase_tables_userdata(), list);
    }
    
    public int deleteUser(long gameid) {
        return userInfoDAO.deleteOne(appConfigs.getDatabase_tables_userdata(), gameid);
    }
}
