/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.core.spider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import kcwiki.akashi.core.entity.ShipBO;
import kcwiki.akashi.spider.entity.ship.kcdata.KcDataShipDO;
import kcwiki.akashi.spider.entity.slotitem.kcdata.KcDataSlotitemDO;
import kcwiki.akashi.spider.entity.slotitem.luatable.Availability;
import kcwiki.akashi.spider.entity.slotitem.luatable.LuaSlotitemDO;
import kcwiki.akashi.spider.entity.slotitem.luatable.Scrap;
import kcwiki.akashi.spider.entity.slotitem.luatable.Statistics;
import kcwiki.akashi.spider.entity.slotitem.luatable.bonuses.BonusStatistics;
import kcwiki.akashi.spider.entity.slotitem.luatable.bonuses.BonusStatus;
import kcwiki.akashi.spider.entity.slotitem.luatable.bonuses.Bonuses;
import kcwiki.akashi.spider.entity.slotitem.luatable.improvement.Improvement;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.util.HttpUtils;
import org.iharu.util.JsonUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class SlotitemDataSpider {
    private final static  org.slf4j.Logger LOG = LoggerFactory.getLogger(SlotitemDataSpider.class);
    
    public Map<String, LuaSlotitemDO> getWikiSlotitemData(){
        try {
            String body = HttpUtils.GetBody("http://bot.kcwiki.moe/json/items.json");
            Map<String, LuaSlotitemDO> data = new HashMap();
            Map<String, JsonNode> source = JsonUtils.json2object(body, new TypeReference<Map<String, JsonNode>>(){});
            for(Map.Entry<String, JsonNode> item:source.entrySet()){
                data.put(item.getKey(), convert2LuaSlotitem(item.getValue()));
            }
            return data;
        } catch (IOException ex) {
            ExceptionUtils.getStackTrace(ex);
            LOG.error("", ex);
        }
        return null;
    }
    
    public List<KcDataSlotitemDO> getKcSlotitemData(){
        try {
            String body = HttpUtils.GetBody("http://kcwikizh.github.io/kcdata/slotitem/all.json");
            return JsonUtils.json2object(body, new TypeReference<List<KcDataSlotitemDO>>(){});
        } catch (IOException ex) {
            ExceptionUtils.getStackTrace(ex);
            LOG.error("", ex);
        }
        return null;
    }
    
    private LuaSlotitemDO convert2LuaSlotitem(JsonNode jsonNode) throws IOException{
        LuaSlotitemDO luaSlotitemDO = new LuaSlotitemDO();
        Iterator<Map.Entry<String, JsonNode>> iterator = jsonNode.fields(); 
        Map.Entry<String, JsonNode> item; 
        while (iterator.hasNext()) {
            item = iterator.next();
            switch(item.getKey()){
                case "ID": {
                    luaSlotitemDO.setId(item.getValue().asInt());
                    break;
                }
                case "日文名": {
                    luaSlotitemDO.setNameJp(item.getValue().asText());
                    break;
                }
                case "中文名": {
                    luaSlotitemDO.setNameZh(item.getValue().asText());
                    break;
                }
                case "类别": {
                    luaSlotitemDO.setType(new ArrayList());
                    ((ArrayNode) item.getValue()).forEach(subItem -> {
                        luaSlotitemDO.getType().add(subItem.asInt());
                    });
                    break;
                }
                case "稀有度": {
                    luaSlotitemDO.setRarity(item.getValue().asText());
                    break;
                }
                case "状态": {
                    luaSlotitemDO.setAvailability(
                            JsonUtils.json2object(item.getValue().toString(), new TypeReference<Availability>(){})
                    );
                    break;
                }
                case "属性": {
                    luaSlotitemDO.setStatistics(
                            JsonUtils.json2object(item.getValue().toString(), new TypeReference<Statistics>(){})
                    );
                    break;
                }
                case "废弃": {
                    luaSlotitemDO.setScrap(
                            JsonUtils.json2object(item.getValue().toString(), new TypeReference<Scrap>(){})
                    );
                    break;
                }
                case "装备适用": {
                    luaSlotitemDO.setRefittable(new ArrayList());
                    ((ArrayNode) item.getValue()).forEach(subItem -> {
                        luaSlotitemDO.getRefittable().add(subItem.asText());
                    });
                    break;
                }
                case "备注": {
                    luaSlotitemDO.setNotes(item.getValue().asText());
                    break;
                }
                case "英文Wiki": {
                    luaSlotitemDO.setWikiEn(item.getValue().asText());
                    break;
                }
                case "日文Wiki": {
                    luaSlotitemDO.setWikiJp(item.getValue().asText());
                    break;
                }
                default: {
                    if(item.getKey().startsWith("装备改修")){
                        luaSlotitemDO.getImprovement().add(
                                JsonUtils.json2object(item.getValue().toString(), new TypeReference<Improvement>(){})
                        );
                    } else if(item.getKey().startsWith("额外收益")){
                        Bonuses bonuses = new Bonuses();
                        bonuses.setShip(new ArrayList());
                        if(item.getValue().has("适用舰娘")){
                            ((ArrayNode) item.getValue().get("适用舰娘")).forEach(subItem -> {
                                bonuses.getShip().add(subItem.asText());
                            });
                        }
                        bonuses.setType(item.getValue().get("收益类型").asText());
                        switch (bonuses.getType()) {
                            case "数量":
                                Map<String, BonusStatistics> bonusesMap = JsonUtils.json2object(item.getValue().get("收益属性").toString(),
                                        new TypeReference<Map<String, BonusStatistics>>(){});
                                bonuses.setBonusStatus(new ArrayList());
                                bonusesMap.forEach((k, v) -> {
                                    bonuses.getBonusStatus().add(new BonusStatus(Integer.valueOf(k), v));
                                });
                                break;
                            case "改修":
                                bonusesMap = JsonUtils.json2object(item.getValue().get("收益属性").toString(),
                                        new TypeReference<Map<String, BonusStatistics>>(){});
                                bonuses.setBonusStatus(new ArrayList());
                                bonusesMap.forEach((k, v) -> {
                                    bonuses.getBonusStatus().add(new BonusStatus(Integer.valueOf(k), v));
                                });
                                break;
                            case "区域":
                                bonusesMap = JsonUtils.json2object(item.getValue().get("收益属性").toString(),
                                        new TypeReference<Map<String, BonusStatistics>>(){});
                                bonuses.setBonusStatus(new ArrayList());
                                bonusesMap.forEach((k, v) -> {
                                    bonuses.getBonusStatus().add(new BonusStatus(k, v));
                                });
                                break;
                            case "通用":
                                bonuses.setBonusStatus(new ArrayList());
                                bonuses.getBonusStatus().add(
                                        new BonusStatus(
                                                JsonUtils.json2object(item.getValue().get("收益属性").toString(),
                                                        new TypeReference<BonusStatistics>(){})));
                                break;
                            default:
                                throw new UnsupportedOperationException("Not supported yet.");
                        }
                        luaSlotitemDO.getBonuses().add(bonuses);
                    }
                }
            }
        }
        return luaSlotitemDO;
    }
    
}
