/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.flipkart.zjsonpatch.JsonDiff;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import kcwiki.akashi.cache.inmem.AppDataCache;
import kcwiki.akashi.core.entity.ShipBO;
import kcwiki.akashi.core.entity.SlotitemBO;
import kcwiki.akashi.database.model.ApiContent;
import kcwiki.akashi.database.repository.ApiContentRepository;
import kcwiki.akashi.spider.entity.ship.luatable.LuaShipDO;
import kcwiki.akashi.web.entity.type.DataType;
import org.iharu.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
public class PatchGenerator<T> {
    
    @Autowired
    private ApiContentRepository apiContentRepository;
    
    public JsonNode generatePatch(DataType type, String hash) throws IOException{
        ApiContent apiContent = apiContentRepository.findByDataTypeAndHash(type.getName(), hash);
        if(apiContent == null)
            return null;
        switch (type){
            case SHIP: {
                return JsonUtils.diffObject(
                        JsonUtils.json2object(apiContent.getDataContent(), new TypeReference<List<ShipBO>>(){}), 
                        AppDataCache.API_DATA_CACHE.get(type).getData());
            }
            case SLOTITEM: {
                return JsonUtils.diffObject(
                        JsonUtils.json2object(apiContent.getDataContent(), new TypeReference<List<SlotitemBO>>(){}), 
                        AppDataCache.API_DATA_CACHE.get(type).getData());
            }
            default:
                throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
