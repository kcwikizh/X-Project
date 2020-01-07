/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.core.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kcwiki.akashi.cache.inmem.AppDataCache;
import kcwiki.akashi.core.entity.ApiDataBO;
import kcwiki.akashi.core.entity.SlotitemBO;
import kcwiki.akashi.core.spider.SlotitemDataSpider;
import kcwiki.akashi.core.spider.SlotitemDataSpider;
import kcwiki.akashi.database.model.ApiContent;
import kcwiki.akashi.database.repository.ApiContentRepository;
import kcwiki.akashi.spider.entity.slotitem.kcdata.KcDataSlotitemDO;
import kcwiki.akashi.spider.entity.slotitem.luatable.LuaSlotitemDO;
import kcwiki.akashi.web.entity.type.DataType;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.util.JsonUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
public class SlotitemDataGenerator extends DataGenerator {
    private final static  org.slf4j.Logger LOG = LoggerFactory.getLogger(SlotitemDataGenerator.class);
    
    @Autowired
    private ApiContentRepository apiContentRepository;
    
    @Override
    public boolean generate(){
        SlotitemDataSpider spider = new SlotitemDataSpider();
        Map<String, LuaSlotitemDO> luadata = spider.getWikiSlotitemData();
        List<KcDataSlotitemDO> kcdata = spider.getKcSlotitemData();
        if(luadata == null || kcdata == null){
            return false;
        }
        Map<Integer, KcDataSlotitemDO> id2KcDataSlotitemDO = new HashMap();
        kcdata.forEach(item -> {
            id2KcDataSlotitemDO.put(item.getId(), item);
        });
        List<SlotitemBO> data = new ArrayList();
        luadata.forEach((k, v) -> {
            try{
                SlotitemBO slotitemBO = new SlotitemBO(v, id2KcDataSlotitemDO.get(v.getId()));
                slotitemBO.setWikiId(k);
                data.add(slotitemBO);
            }catch(Exception ex){
                LOG.error("", ex);
            }
        });
        try{
            updateApiData(apiContentRepository, new ApiDataBO(DataType.SLOTITEM, data));
        } catch(Exception ex){
            ExceptionUtils.getStackTrace(ex);
            LOG.error("", ex);
            return false;
        }
        return true;
    }
}
