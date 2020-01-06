/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.core.spider;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import kcwiki.akashi.spider.entity.ship.kcdata.KcDataShipDO;
import kcwiki.akashi.spider.entity.ship.luatable.LuaShipDO;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.util.HttpUtils;
import org.iharu.util.JsonUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class ShipDataSpider {
    private final static  org.slf4j.Logger LOG = LoggerFactory.getLogger(ShipDataSpider.class);
    
    public Map<String, LuaShipDO> getWikiShipData(){
        try {
            String body = HttpUtils.GetBody("http://bot.kcwiki.moe/json/ships.json");
            return JsonUtils.json2object(body, new TypeReference<Map<String, LuaShipDO>>(){});
        } catch (IOException ex) {
            ExceptionUtils.getStackTrace(ex);
            LOG.error("", ex);
        }
        return null;
    }
    
    public List<KcDataShipDO> getKcShipData(){
        try {
            String body = HttpUtils.GetBody("http://kcwikizh.github.io/kcdata/ship/all.json");
            return JsonUtils.json2object(body, new TypeReference<List<KcDataShipDO>>(){});
        } catch (IOException ex) {
            ExceptionUtils.getStackTrace(ex);
            LOG.error("", ex);
        }
        return null;
    }
    
}
