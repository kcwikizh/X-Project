/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.message.websocket;

import java.util.Date;
import kcwiki.x.enshuhelper.cache.inmem.AppDataCache;
import kcwiki.x.enshuhelper.database.entity.UserDataDO;
import kcwiki.x.enshuhelper.database.service.LogService;
import kcwiki.x.enshuhelper.database.service.UserInfoService;
import kcwiki.x.enshuhelper.initializer.AppConfig;
import kcwiki.x.enshuhelper.message.websocket.entity.UserData;
import org.iharu.type.MsgType;
import org.iharu.util.CommontUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
public class MessageProcessor {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MessageProcessor.class);
    
    @Autowired
    private AppConfig appConfig;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    LogService logService;
    
    public int enshuHelperRegister(UserData userData){
        int gameid = userData.getMemberid();
        String qqgroup = userData.getQqgroup();
        UserDataDO userDataEntity = userInfoService.findByGameid(gameid);
        if(userDataEntity != null) {
            return 0;
        }
        userDataEntity = new UserDataDO();
        userDataEntity.setGameid(gameid);
        userDataEntity.setQq(userData.getQq());
        userDataEntity.setQqgroup(qqgroup);
        userDataEntity.setToken(CommontUtils.GenUUID());
        userDataEntity.setTeitoku(userData.getTeitoku());
        userDataEntity.setComments(userData.getComments());
        userDataEntity.setTimestamp(new Date()); 
        int rs = userInfoService.addUser(userDataEntity);
        if(rs > 0){
            logService.addLog(MsgType.INFO, String.format("玩家%s在%s群注册。", gameid, qqgroup));
            return 1;
        }
        return -1;
    }
    
    public int enshuHelperUnregister(UserData userData){
        long gameid = userData.getMemberid();
        String qqgroup = userData.getQqgroup();
        int rs = -1;
        UserDataDO userDataEntity = userInfoService.findByGameid(gameid);
        if(userDataEntity != null){
            if(!userDataEntity.getQqgroup().equals(qqgroup)) {
                LOG.warn("玩家{}在{}群注册，{}群管理员没有删除权限。", gameid, userDataEntity.getQqgroup(), qqgroup);
                logService.addLog(MsgType.WARN, String.format("玩家%s在%s群注册，%s群管理员没有删除权限。", gameid, userDataEntity.getQqgroup(), qqgroup));
                return -2;
            }
            rs = userInfoService.deleteUser(gameid);
            AppDataCache.matchCache.remove(gameid);
        }
        return rs;
    }
}
