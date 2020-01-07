/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kcwiki.akashi.cache.inmem.AppDataCache;
import kcwiki.akashi.core.PatchGenerator;
import kcwiki.akashi.web.entity.FetchDataRequestDTO;
import kcwiki.akashi.web.entity.VersionDTO;
import kcwiki.akashi.web.entity.type.DataType;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.BaseHttpStatus;
import org.iharu.util.JsonUtils;
import org.iharu.util.StringUtils;
import org.iharu.web.BaseController;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author iHaru
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class Api extends BaseController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Api.class);
    
    @Autowired
    private PatchGenerator patchGenerator;
    
    @GetMapping("/debug")
    public WebResponseProto debug() {
        WebResponseProto proto = GenResponse(BaseHttpStatus.SUCCESS, AppDataCache.API_DATA_CACHE);
        return proto;
    }
    
    @GetMapping("/version")
    public WebResponseProto version() {
        if(AppDataCache.API_RESPONSE_CACHE.containsKey("/api/version"))
            return AppDataCache.API_RESPONSE_CACHE.get("/api/version");
        List<VersionDTO> list = new ArrayList();
        AppDataCache.API_DATA_CACHE.values().forEach(data -> {
            list.add(new VersionDTO(data.getDataType(), data.getHash(), data.getTimestamp()));
        });
        WebResponseProto proto = GenResponse(BaseHttpStatus.SUCCESS, list);
//        AppDataCache.API_RESPONSE_CACHE.put("/api/version", proto);
        return proto;
    }
    
    @PostMapping("/ship")
    public WebResponseProto ship(@RequestBody String reqBody) {
        FetchDataRequestDTO fetchDataRequestDTO = JsonUtils.json2objectWithoutThrowException(reqBody, FetchDataRequestDTO.class);
        if(fetchDataRequestDTO == null)
            return GenResponse(BaseHttpStatus.FAILURE, "request params error", null);
        if(fetchDataRequestDTO.getTimestamp() <=0 || StringUtils.isNullOrEmpty(fetchDataRequestDTO.getHash())){
            if(AppDataCache.API_RESPONSE_CACHE.containsKey(DataType.SHIP.getUrl()))
                return AppDataCache.API_RESPONSE_CACHE.get(DataType.SHIP.getUrl());
            WebResponseProto proto = GenResponse(BaseHttpStatus.SUCCESS, AppDataCache.API_DATA_CACHE.get(DataType.SHIP).getData());
            AppDataCache.API_RESPONSE_CACHE.put(DataType.SHIP.getUrl(), proto);
            return proto;
        } else {
            return getPatchedResponse(DataType.SLOTITEM, fetchDataRequestDTO.getHash());
        }
    }
    
    @PostMapping("/slotitem")
    public WebResponseProto slotitem(@RequestBody String reqBody) {
        FetchDataRequestDTO fetchDataRequestDTO = JsonUtils.json2objectWithoutThrowException(reqBody, FetchDataRequestDTO.class);
        if(fetchDataRequestDTO == null)
            return GenResponse(BaseHttpStatus.FAILURE, "request params error", null);
        if(fetchDataRequestDTO.getTimestamp() <=0 || StringUtils.isNullOrEmpty(fetchDataRequestDTO.getHash())){
            if(AppDataCache.API_RESPONSE_CACHE.containsKey(DataType.SLOTITEM.getUrl()))
                return AppDataCache.API_RESPONSE_CACHE.get(DataType.SLOTITEM.getUrl());
            WebResponseProto proto = GenResponse(BaseHttpStatus.SUCCESS, AppDataCache.API_DATA_CACHE.get(DataType.SLOTITEM).getData());
            AppDataCache.API_RESPONSE_CACHE.put(DataType.SLOTITEM.getUrl(), proto);
            return proto;
        } else {
            return getPatchedResponse(DataType.SLOTITEM, fetchDataRequestDTO.getHash());
        }
    }
    
    private WebResponseProto getPatchedResponse(DataType type, String hash){
        try {
            JsonNode patch = patchGenerator.generatePatch(type, hash);
            if(patch == null){
                return GenResponse(BaseHttpStatus.FAILURE, "hash not exist", hash);
            }
            if(patch.size() == 0){
                return GenResponse(BaseHttpStatus.SUCCESS, "not modified", null);
            }
            return GenResponse(BaseHttpStatus.SUCCESS, null, patch);
        } catch (IOException ex) {
            LOG.error("", ex);
            return GenResponse(BaseHttpStatus.ERROR, "got error", null);
        }
    }
    
}
