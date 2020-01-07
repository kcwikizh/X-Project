/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.core.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import kcwiki.akashi.spider.entity.slotitem.kcdata.KcDataSlotitemDO;
import kcwiki.akashi.spider.entity.slotitem.luatable.LuaSlotitemDO;

/**
 *
 * @author iHaru
 */
public class SlotitemBO extends LuaSlotitemDO {
    @JsonProperty(value= "wiki_id")
    private String wikiId;
    @JsonProperty(value= "sort_no")
    private int sort;
    @JsonProperty(value= "item_info")
    private String itemInfo;

    public SlotitemBO(){}
    
    public SlotitemBO(LuaSlotitemDO luaData, KcDataSlotitemDO kcData){
        this.itemInfo = kcData.getInfo();
        this.sort = kcData.getSort();
        if(luaData != null){
            this.id = luaData.getId();
            this.nameJp = luaData.getNameJp();
            this.nameZh = luaData.getNameZh();
            this.type = luaData.getType();
            this.rarity = luaData.getRarity();
            this.availability = luaData.getAvailability();
            this.statistics = luaData.getStatistics();
            this.scrap = luaData.getScrap();
            this.refittable = luaData.getRefittable();
            this.notes = luaData.getNotes();
            this.bonuses = luaData.getBonuses();
            this.improvement = luaData.getImprovement();
            this.wikiJp = luaData.getNameJp();
            this.wikiEn = luaData.getWikiEn();
        } else {
            this.id = kcData.getId();
            this.sort = kcData.getSort();
            this.nameJp = kcData.getName();
            this.nameZh = kcData.getChineseName();
            kcData.getType().remove(kcData.getType().size()-1);
            this.type = kcData.getType();
            this.rarity = String.format("%"+kcData.getRare()+"s", "").replace(' ', '☆');
        }
    }

    /**
     * @return the wikiId
     */
    public String getWikiId() {
        return wikiId;
    }

    /**
     * @param wikiId the wikiId to set
     */
    public void setWikiId(String wikiId) {
        this.wikiId = wikiId;
    }

    /**
     * @return the sort
     */
    public int getSort() {
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(int sort) {
        this.sort = sort;
    }

    /**
     * @return the itemInfo
     */
    public String getItemInfo() {
        return itemInfo;
    }

    /**
     * @param itemInfo the itemInfo to set
     */
    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }

}
