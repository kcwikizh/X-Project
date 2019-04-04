/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.message.websocket;

import java.util.Date;
import kcwiki.x.enshuhelper.cache.inmem.AppDataCache;
import kcwiki.x.enshuhelper.database.entity.UserDataEntity;
import kcwiki.x.enshuhelper.database.service.LogService;
import kcwiki.x.enshuhelper.database.service.UserInfoService;
import kcwiki.x.enshuhelper.initializer.AppConfigs;
import kcwiki.x.enshuhelper.tools.CommontUtils;
import kcwiki.x.enshuhelper.types.LogTypes;
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
    private AppConfigs appConfigs;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    LogService logService;
    
    public int enshuHelperRegister(String payload){
        LOG.debug("enshuHelperRegister - payload: {}", payload);
        String[] args = payload.split(",");
        long gameid = Long.valueOf(args[0]);
        String qqgroup = args[2];
        UserDataEntity userDataEntity = userInfoService.findByGameid(gameid);
        if(userDataEntity != null) {
            return 0;
        }
        userDataEntity = new UserDataEntity();
        userDataEntity.setGameid(gameid);
        userDataEntity.setQq(args[1]);
        userDataEntity.setQqgroup(qqgroup);
        userDataEntity.setToken(CommontUtils.UUIDGen());
        userDataEntity.setTeitoku(args[3]);
        userDataEntity.setComments(args[4]);
        userDataEntity.setTimestamp(new Date()); 
        int rs = userInfoService.addUser(userDataEntity);
        if(rs > 0){
            logService.addLog(LogTypes.INFO, String.format("玩家%s在%s群注册。", gameid, qqgroup));
            return 1;
        }
        return -1;
    }
    
    public int enshuHelperUnregister(String payload){
        LOG.debug("enshuHelperUnregister - payload: {}", payload);
        String[] args = payload.split(",");
        long gameid = Long.valueOf(args[0]);
        String qqgroup = args[1];
        int rs = -1;
        UserDataEntity userDataEntity = userInfoService.findByGameid(gameid);
        if(userDataEntity != null){
            if(!userDataEntity.getQqgroup().equals(qqgroup)) {
                LOG.warn("玩家{}在{}群注册，{}群管理员没有删除权限。", gameid, userDataEntity.getQqgroup(), qqgroup);
                logService.addLog(LogTypes.WARN, String.format("玩家%s在%s群注册，%s群管理员没有删除权限。", gameid, userDataEntity.getQqgroup(), qqgroup));
                return -2;
            }
            rs = userInfoService.deleteUser(gameid);
            AppDataCache.matchCache.remove(gameid);
        }
        return rs;
    }
}
