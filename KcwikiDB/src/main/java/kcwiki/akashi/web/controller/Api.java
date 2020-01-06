/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.web.controller;

import java.util.ArrayList;
import java.util.List;
import kcwiki.akashi.cache.inmem.AppDataCache;
import kcwiki.akashi.web.entity.VersionDTO;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.BaseHttpStatus;
import org.iharu.web.BaseController;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    @GetMapping("/version")
    public WebResponseProto version() {
        List<VersionDTO> list = new ArrayList();
        AppDataCache.API_DATA_CACHE.values().forEach(data -> {
            list.add(new VersionDTO(data.getDataType(), data.getHash(), data.getTimestamp()));
        });
        return GenResponse(BaseHttpStatus.SUCCESS, list);
    }
    
}
