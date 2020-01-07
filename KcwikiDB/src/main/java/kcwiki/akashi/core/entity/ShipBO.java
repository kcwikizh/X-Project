/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.core.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import kcwiki.akashi.spider.entity.ship.kcdata.KcDataShipDO;
import kcwiki.akashi.spider.entity.ship.luatable.LuaShipDO;

/**
 *
 * @author iHaru
 */
public class ShipBO extends LuaShipDO {
    @JsonProperty(value= "wiki_id")
    private String wikiId;
    @JsonProperty(value= "voice_introduction")
    private String voiceIntroduction;
    @JsonProperty(value= "voice_info")
    private String voiceInfo;

    public ShipBO(){}
    
    public ShipBO(LuaShipDO luaData, KcDataShipDO kcData){
        this.wikiId = kcData.getWikiId();
        this.voiceIntroduction = kcData.getGetMsg();
        this.voiceInfo = kcData.getBookShipInfo();
        if(luaData != null){
            this.id = luaData.getId();
            this.sort = luaData.getSort();
            this.nameJp = luaData.getNameJp();
            this.yomi = luaData.getYomi();
            this.nameZh = luaData.getNameZh();
            this.stype = luaData.getStype();
            this.shipClass = luaData.getShipClass();
            this.statistics = luaData.getStatistics();
            this.equipment = luaData.getEquipment();
            this.consumption = luaData.getConsumption();
            this.availability = luaData.getAvailability();
            this.modernization = luaData.getModernization();
            this.scrap = luaData.getScrap();
            this.remodel = luaData.getRemodel();
            this.artist = luaData.getArtist();
            this.seiyuu = luaData.getSeiyuu();
            this.wikiJp = luaData.getNameJp();
            this.wikiEn = luaData.getWikiEn();
        } else {
            this.id = kcData.getId();
            this.sort = kcData.getSort();
            this.nameJp = kcData.getName();
            this.yomi = kcData.getYomi();
            this.nameZh = kcData.getChineseName();
            this.stype = kcData.getStype();
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
     * @return the voiceIntroduction
     */
    public String getVoiceIntroduction() {
        return voiceIntroduction;
    }

    /**
     * @param voiceIntroduction the voiceIntroduction to set
     */
    public void setVoiceIntroduction(String voiceIntroduction) {
        this.voiceIntroduction = voiceIntroduction;
    }

    /**
     * @return the voiceInfo
     */
    public String getVoiceInfo() {
        return voiceInfo;
    }

    /**
     * @param voiceInfo the voiceInfo to set
     */
    public void setVoiceInfo(String voiceInfo) {
        this.voiceInfo = voiceInfo;
    }
    
}
