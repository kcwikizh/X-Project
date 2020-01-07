/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kcwiki.akashi.core.entity.ShipBO;
import kcwiki.akashi.core.spider.ShipDataSpider;
import kcwiki.akashi.spider.entity.ship.kcdata.KcDataShipDO;
import kcwiki.akashi.spider.entity.ship.luatable.LuaShipDO;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.util.JsonUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class ShipDataGenerator {
    private final static  org.slf4j.Logger LOG = LoggerFactory.getLogger(ShipDataGenerator.class);
    
    public void generate(){
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
                ExceptionUtils.getStackTrace(ex);
            }
        });
        JsonUtils.object2json(data);
    }
}
