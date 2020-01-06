/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.web.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import kcwiki.x.enshuhelper.cache.inmem.AppDataCache;
import kcwiki.x.enshuhelper.core.entity.DiscordUserDataBO;
import kcwiki.x.enshuhelper.database.entity.DiscordUserDataDO;
import kcwiki.x.enshuhelper.database.entity.UserDataDO;
import kcwiki.x.enshuhelper.database.service.DiscordUserDataService;
import kcwiki.x.enshuhelper.database.service.LogService;
import kcwiki.x.enshuhelper.database.service.SystemInfoService;
import kcwiki.x.enshuhelper.database.service.UserInfoService;
import kcwiki.x.enshuhelper.discord.DiscordBotController;
import kcwiki.x.enshuhelper.message.websocket.MessagePublisher;
import kcwiki.x.enshuhelper.message.websocket.entity.UserData;
import kcwiki.x.enshuhelper.message.websocket.types.ModuleType;
import static org.iharu.constant.ConstantValue.LINESEPARATOR;
import org.iharu.type.MsgType;
import kcwiki.x.enshuhelper.web.controller.entity.request.UserEnshuInfoEntity;
import kcwiki.x.enshuhelper.web.controller.entity.request.UserEnshuRegisterType;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.proto.web.WebResponseProto;
import static org.iharu.type.BaseHttpStatus.FAILURE;
import static org.iharu.type.BaseHttpStatus.SUCCESS;
import org.iharu.util.JsonUtils;
import org.iharu.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



/**
 *
 * @author iHaru
 */

@RestController
@RequestMapping(value = {"/enshuhelper"}, produces = "application/json;charset=UTF-8")
public class Upload extends BaseController {
    static final Logger LOG = LoggerFactory.getLogger(Upload.class);
    
    @Autowired
    private LogService logService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private DiscordUserDataService discordUserDataService;
    @Autowired
    SystemInfoService systemInfoService;
    @Autowired
    MessagePublisher messagePublisher;
    @Autowired
    DiscordBotController discordBotController;
    
    @PostMapping(value="/query")
    @ResponseBody
    public WebResponseProto query(@RequestBody String reqBody) throws JsonProcessingException
    {
//        LOG.debug("reqBody: {}", reqBody);
        UserEnshuInfoEntity userEnshuInfoEntity;
        if(!AppDataCache.isReadyReceive){
            return GenBaseResponse(FAILURE, "服务器数据处理中，暂时不能查询，请稍后再试。");
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            userEnshuInfoEntity = objectMapper.readValue(reqBody,
                new TypeReference<UserEnshuInfoEntity>(){});
        } catch (IOException ex) {
            LOG.error(
                    "尝试对{}进行反序列化操作时发生错误。{} comming data with: {}", 
                    request.getServletPath(),
                    LINESEPARATOR,
                    reqBody
            );
            return GenBaseResponse(FAILURE, "上传的数据格式有误，请检查数据是否完整。");
        }
        int memberID = userEnshuInfoEntity.getApi_member_id();
        LOG.info("query memberID is {}。", memberID);
        
        if(userEnshuInfoEntity.getRegister_type() == UserEnshuRegisterType.QQ){
            List<UserData> rs = new ArrayList<>();
            if(!AppDataCache.matchCache.containsKey(memberID)){
                UserDataDO userDataEntity = userInfoService.findByGameid(memberID);
                if(userDataEntity == null) {
                    return GenBaseResponse(FAILURE, "找不到用户，请确认你已经在服务器注册。");
                } else if (userDataEntity.isBlock()) {
                    logService.addLog(MsgType.WARN, String.format("%s已被禁止使用本服务。", memberID));
                    return GenBaseResponse(FAILURE, "抱歉，你已被禁止使用本服务。");
                }
                rs.add(userData2matchInfo(userDataEntity));
            } else {
                rs.add(userData2matchInfo(AppDataCache.matchCache.get(memberID)));
            }
            List<Integer> querylist = new ArrayList<>();
            LOG.trace("Api_enemy_list: {}", userEnshuInfoEntity.getApi_enemy_list());
            if(userEnshuInfoEntity.getApi_enemy_list().isEmpty()) {
                return GenBaseResponse(FAILURE, "待匹配项为空，请勿反复提交。");
            }
            userEnshuInfoEntity.getApi_enemy_list().forEach(item -> {
                if(AppDataCache.matchCache.containsKey(item)){
                    rs.add(userData2matchInfo(AppDataCache.matchCache.get(item)));
                } else {
                    querylist.add(item);
                }
            });
//            LOG.info("querylist: {}", querylist);
//            LOG.info("AppDataCache.matchCache: {}", AppDataCache.matchCache);
            List<UserDataDO> userDataList = userInfoService.findByGameids(querylist);
            if(!userDataList.isEmpty()) {
                userDataList.forEach(item -> {
                    rs.add(userData2matchInfo(item));
                    AppDataCache.matchCache.put(item.getGameid(), item);
                });
            }
    //        LOG.info("AppDataCache.matchCache: {}", AppDataCache.matchCache.keySet());
            if( rs.size() < 2 ) {
                return GenBaseResponse(FAILURE, "暂时没有适配的玩家信息。");
            } else {
                CompletableFuture.runAsync(() -> {
                    AppDataCache.queryCount.increment();
                    AppDataCache.matchCount.add(rs.size()-1);
                    messagePublisher.publish(JsonUtils.object2json(rs), ModuleType.EnshuHelperInform);

                    try{
                        systemInfoService.updateCount();
                        rs.forEach(item -> {
                            if(item.getMemberid() == memberID)
                                return;
                            userInfoService.updateUser(AppDataCache.matchCache.get(item.getMemberid()));
                        });
                    }catch (Exception ex){
                        LOG.error(ExceptionUtils.getStackTrace(ex));
                    }
                });
                List<UserData> rsp = new ArrayList<>();
                rs.forEach(item -> {
                    if(item.getMemberid() == memberID)
                        return;
                    UserData matchInfo = new UserData();
                    matchInfo.setMemberid(item.getMemberid());
                    matchInfo.setTeitoku(item.getTeitoku());
                    matchInfo.setQqgroup(item.getQqgroup());
                    matchInfo.setComments(item.getComments());
                    rsp.add(matchInfo);
                });
                logService.addLog(MsgType.INFO, String.format("%s已匹配成功。", memberID));
                return GenResponse(SUCCESS, rsp);
            }
        } else if(userEnshuInfoEntity.getRegister_type() == UserEnshuRegisterType.DISCORD){
            List<DiscordUserDataBO> rs = new ArrayList<>();
            if(!AppDataCache.discordMatchCache.containsKey(memberID)){
                DiscordUserDataBO discordUserDataBO = DiscordUserDataBO.generateBOByDO(discordUserDataService.getDiscordUserByGameId(memberID));
                if(discordUserDataBO == null) {
                    return GenBaseResponse(FAILURE, "找不到用户，请确认你已经在服务器注册。");
                } else if (discordUserDataBO.isBlocked()) {
                    logService.addLog(MsgType.WARN, String.format("%s已被禁止使用本服务。", memberID));
                    return GenBaseResponse(FAILURE, "抱歉，你已被禁止使用本服务。");
                }
                rs.add(discordUserDataBO);
            } else {
                rs.add(AppDataCache.discordMatchCache.get(memberID));
            }
            List<Integer> querylist = new ArrayList<>();
            LOG.trace("Api_enemy_list: {}", userEnshuInfoEntity.getApi_enemy_list());
            if(userEnshuInfoEntity.getApi_enemy_list().isEmpty()) {
                return GenBaseResponse(FAILURE, "待匹配项为空，请勿反复提交。");
            }
            userEnshuInfoEntity.getApi_enemy_list().forEach(item -> {
                if(AppDataCache.discordMatchCache.containsKey(item)){
                    rs.add(AppDataCache.discordMatchCache.get(item));
                } else {
                    querylist.add(item);
                }
            });
//            LOG.info("querylist: {}", querylist);
//            LOG.info("AppDataCache.matchCache: {}", AppDataCache.matchCache);
            List<DiscordUserDataBO> userDataList = DiscordUserDataBO.listBOByDO(discordUserDataService.listDiscordUsersByGameId(querylist));
            if(!userDataList.isEmpty()) {
                userDataList.forEach(item -> {
                    rs.add(item);
                    AppDataCache.discordMatchCache.put(item.getGameId(), item);
                });
            }
    //        LOG.info("AppDataCache.matchCache: {}", AppDataCache.matchCache.keySet());
            if( rs.size() < 2 ) {
                return GenBaseResponse(FAILURE, "暂时没有适配的玩家信息。");
            } else {
                CompletableFuture.runAsync(() -> {
                    AppDataCache.queryCount.increment();
                    AppDataCache.matchCount.add(rs.size()-1);
                    discordBotController.sendMatchedMsg(rs);

                    try{
                        systemInfoService.updateCount();
                    }catch (Exception ex){
                        LOG.error(ExceptionUtils.getStackTrace(ex));
                    }
                });
                List<UserData> rsp = new ArrayList<>();
                rs.forEach(item -> {
                    if(item.getGameId()== memberID)
                        return;
                    UserData matchInfo = new UserData();
                    matchInfo.setMemberid(item.getGameId());
                    matchInfo.setTeitoku(item.getTeitoku());
                    matchInfo.setQqgroup(discordBotController.findGuildName(item.getGuildId()));
                    matchInfo.setComments(item.getComments());
                    rsp.add(matchInfo);
                });
                logService.addLog(MsgType.INFO, String.format("%s已匹配成功。", memberID));
                return GenResponse(SUCCESS, rsp);
            }
        }
        return GenBaseResponse(FAILURE, "暂时没有适配的玩家信息。");
    }
    
    private UserData userData2matchInfo(UserDataDO userDataEntity){
        UserData matchInfo = new UserData();
        matchInfo.setMemberid(userDataEntity.getGameid());
        matchInfo.setTeitoku(userDataEntity.getTeitoku());
        matchInfo.setQq(userDataEntity.getQq());
        matchInfo.setQqgroup(userDataEntity.getQqgroup());
        matchInfo.setComments(userDataEntity.getComments());
        return matchInfo;
    }
     
}
