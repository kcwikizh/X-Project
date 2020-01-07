/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.core.generator;

import kcwiki.akashi.cache.inmem.AppDataCache;
import kcwiki.akashi.core.entity.ApiDataBO;
import kcwiki.akashi.database.model.ApiContent;
import kcwiki.akashi.database.repository.ApiContentRepository;
import org.iharu.util.JsonUtils;

/**
 *
 * @author iHaru
 */
public abstract class DataGenerator {
    
    public abstract boolean generate();
    
    protected void updateApiData(ApiContentRepository apiContentRepository, ApiDataBO apiDataBO){
        ApiContent apiContent = apiContentRepository.findTopByDataTypeOrderByTimestampDesc(apiDataBO.getDataType().getName());
        if(apiContent != null && apiContent.getHash().equals(apiDataBO.getHash())){
            apiDataBO.setTimestamp(apiContent.getTimestamp().getTime());
            AppDataCache.API_DATA_CACHE.put(apiDataBO.getDataType(), apiDataBO);
        } else {
            AppDataCache.API_DATA_CACHE.put(apiDataBO.getDataType(), apiDataBO);
            apiContent = new ApiContent();
            apiContent.setDataContent(JsonUtils.object2json(apiDataBO.getData()));
            apiContent.setHash(apiDataBO.getHash());
            apiContent.setDataType(apiDataBO.getDataType().getName());
            apiContentRepository.save(apiContent);
        }
    }
}
