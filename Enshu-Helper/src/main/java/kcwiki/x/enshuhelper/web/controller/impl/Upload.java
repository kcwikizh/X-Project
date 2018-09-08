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
import kcwiki.x.enshuhelper.database.entity.UserDataEntity;
import kcwiki.x.enshuhelper.database.service.LogService;
import kcwiki.x.enshuhelper.database.service.SystemInfoService;
import kcwiki.x.enshuhelper.database.service.UserInfoService;
import kcwiki.x.enshuhelper.message.websocket.MessagePublisher;
import kcwiki.x.enshuhelper.message.websocket.entity.ExchangeMessageEntity;
import kcwiki.x.enshuhelper.message.websocket.types.ExchangeMessageTypes;
import kcwiki.x.enshuhelper.message.websocket.types.PublishStatus;
import kcwiki.x.enshuhelper.message.websocket.types.PublishTypes;
import static kcwiki.x.enshuhelper.tools.ConstantValue.LINESEPARATOR;
import kcwiki.x.enshuhelper.types.LogTypes;
import kcwiki.x.enshuhelper.web.controller.BaseController;
import kcwiki.x.enshuhelper.web.controller.entity.queryresponse.MatchInfo;
import kcwiki.x.enshuhelper.web.controller.entity.queryresponse.QueryResponse;
import kcwiki.x.enshuhelper.web.controller.entity.request.UserEnshuInfoEntity;
import kcwiki.x.enshuhelper.web.controller.types.HttpRepStatus;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
 * @author x5171
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
    SystemInfoService systemInfoService;
    @Autowired
    MessagePublisher messagePublisher;
    
    @PostMapping(value="/query")
    @ResponseBody
    public String query(@RequestBody String reqBody) throws JsonProcessingException
    {
        LOG.debug("reqBody: {}", reqBody);
        UserEnshuInfoEntity userEnshuInfoEntity;
        if(!AppDataCache.isReadyReceive){
            return Object2Json(BaseResponseGen(HttpRepStatus.FAILURE, "服务器数据处理中，暂时不能查询，请稍后再试。"));
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
            return Object2Json(BaseResponseGen(HttpRepStatus.FAILURE, "上传的数据格式有误，请检查数据是否完整。"));
        }
        long memberID = userEnshuInfoEntity.getApi_member_id();
        LOG.info("query memberID is {}。", memberID);
        List<MatchInfo> rs = new ArrayList<>();
        if(!AppDataCache.matchCache.containsKey(memberID)){
            UserDataEntity userDataEntity = userInfoService.findByGameid(memberID);
            if(userDataEntity == null) {
                return Object2Json(BaseResponseGen(HttpRepStatus.FAILURE, "找不到用户，请确认你已经在服务器注册。"));
            } else if (userDataEntity.isBlock()) {
                logService.addLog(LogTypes.WARN, String.format("%s已被禁止使用本服务。", memberID));
                return Object2Json(BaseResponseGen(HttpRepStatus.FAILURE, "抱歉，你已被禁止使用本服务。"));
            }
            rs.add(userData2matchInfo(userDataEntity));
        } else {
            rs.add(userData2matchInfo(AppDataCache.matchCache.get(memberID)));
        }
        List<Long> querylist = new ArrayList<>();
        LOG.trace("Api_enemy_list: {}", userEnshuInfoEntity.getApi_enemy_list());
        if(userEnshuInfoEntity.getApi_enemy_list().isEmpty()) {
            return Object2Json(BaseResponseGen(HttpRepStatus.FAILURE, "待匹配项为空，请勿反复提交。"));
        }
        userEnshuInfoEntity.getApi_enemy_list().forEach(item -> {
            if(AppDataCache.matchCache.containsKey(item)){
                rs.add(userData2matchInfo(AppDataCache.matchCache.get(item)));
            } else {
                querylist.add(item);
            }
        });
//        LOG.info("querylist: {}", querylist);
//        LOG.info("AppDataCache.matchCache: {}", AppDataCache.matchCache);
        List<UserDataEntity> userDataList = userInfoService.findByGameids(querylist);
        if(!userDataList.isEmpty()) {
            userDataList.forEach(item -> {
                rs.add(userData2matchInfo(item));
                AppDataCache.matchCache.put(item.getGameid(), item);
            });
        }
//        LOG.info("AppDataCache.matchCache: {}", AppDataCache.matchCache.keySet());
        if( rs.size() < 2 ) {
            return Object2Json(BaseResponseGen(HttpRepStatus.FAILURE, "暂时没有适配的玩家信息。"));
        } else {
            CompletableFuture.runAsync(() -> {
                AppDataCache.queryCount.increment();
                AppDataCache.matchCount.add(rs.size()-1);
                try{
                    ExchangeMessageEntity exchangeMessageEntity = new ExchangeMessageEntity();
                    exchangeMessageEntity.setStatus(HttpRepStatus.SUCCESS);
                    exchangeMessageEntity.setCode(HttpRepStatus.SUCCESS.getCode());
                    exchangeMessageEntity.setExchangeMessageTypes(ExchangeMessageTypes.EnshuHelperInform);
                    exchangeMessageEntity.setPayload(Object2Json(rs));
//                    LOG.trace("exchangeMessageEntity: {}", Object2Json(exchangeMessageEntity));
                    messagePublisher.publish(Object2Json(exchangeMessageEntity), PublishTypes.Guest, PublishStatus.SUCCESS);
                } catch (JsonProcessingException ex){
                    LOG.error(ExceptionUtils.getStackTrace(ex));
                }
                
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
//            logService.addLog(LogTypes.DEBUG, "成功插入");
            List<MatchInfo> rsp = new ArrayList<>();
            rs.forEach(item -> {
                if(item.getMemberid() == memberID)
                    return;
                MatchInfo matchInfo = new MatchInfo();
                matchInfo.setMemberid(item.getMemberid());
                matchInfo.setTeitoku(item.getTeitoku());
                matchInfo.setQqgroup(item.getQqgroup());
                matchInfo.setComments(item.getComments());
                rsp.add(matchInfo);
            });
            QueryResponse queryResponse = new QueryResponse();
            queryResponse.setStatus(HttpRepStatus.SUCCESS);
            queryResponse.setCode(HttpRepStatus.SUCCESS.getCode());
            queryResponse.setMatchlist(rsp);
            logService.addLog(LogTypes.INFO, String.format("%s已匹配成功。", memberID));
            return Object2Json(queryResponse);
        }
    }
    
    private MatchInfo userData2matchInfo(UserDataEntity userDataEntity){
        MatchInfo matchInfo = new MatchInfo();
        matchInfo.setMemberid(userDataEntity.getGameid());
        matchInfo.setTeitoku(userDataEntity.getTeitoku());
        matchInfo.setQq(userDataEntity.getQq());
        matchInfo.setQqgroup(userDataEntity.getQqgroup());
        matchInfo.setComments(userDataEntity.getComments());
        return matchInfo;
    }
     
}
