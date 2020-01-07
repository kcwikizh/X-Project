/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.core.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kcwiki.akashi.cache.inmem.AppDataCache;
import kcwiki.akashi.core.entity.ApiDataBO;
import kcwiki.akashi.core.entity.ShipBO;
import kcwiki.akashi.core.spider.ShipDataSpider;
import kcwiki.akashi.database.model.ApiContent;
import kcwiki.akashi.database.repository.ApiContentRepository;
import kcwiki.akashi.spider.entity.ship.kcdata.KcDataShipDO;
import kcwiki.akashi.spider.entity.ship.luatable.LuaShipDO;
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
public class ShipDataGenerator extends DataGenerator {
    private final static  org.slf4j.Logger LOG = LoggerFactory.getLogger(ShipDataGenerator.class);
    
    @Autowired
    private ApiContentRepository apiContentRepository;
    
    @Override
    public boolean generate(){
        ShipDataSpider spider = new ShipDataSpider();
        Map<String, LuaShipDO> luadata = spider.getWikiShipData();
        List<KcDataShipDO> kcdata = spider.getKcShipData();
        List<ShipBO> data = new ArrayList();
        kcdata.forEach(ship -> {
            if(ship.getWikiId() == null || ship.getId() > 1500)
                return;
            try{
                data.add(new ShipBO(luadata.get(ship.getWikiId()), ship));
            }catch(Exception ex){
                LOG.error("", ex);
            }
        });
        try{
            updateApiData(apiContentRepository, new ApiDataBO(DataType.SHIP, data));
        } catch(Exception ex){
            ExceptionUtils.getStackTrace(ex);
            LOG.error("", ex);
            return false;
        }
        return true;
    }
}
