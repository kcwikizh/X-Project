/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.spider.entity.ship.luatable;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author iHaru
 */
public class LuaShipDO {
    @JsonProperty("ID")
    protected int id;
    @JsonProperty("图鉴号")
    protected int sort;
    @JsonProperty("日文名")
    protected String nameJp;
    @JsonProperty("假名")
    protected String yomi;
    @JsonProperty("中文名")
    protected String nameZh;
    @JsonProperty("舰种")
    protected int stype;
    @JsonProperty("级别")
    protected List<Object> shipClass;
    @JsonProperty("数据")
    protected Statistics statistics;
    @JsonProperty("装备")
    protected Equipment equipment;
    @JsonProperty("消耗")
    protected Consumption consumption;
    @JsonProperty("获得")
    protected Availability availability;
    @JsonProperty("改修")
    protected Modernization modernization;
    @JsonProperty("解体")
    protected Scrap scrap;
    @JsonProperty("改造")
    protected Remodel remodel;
    @JsonProperty("画师")
    protected String artist;
    @JsonProperty("声优")
    protected String seiyuu;
    @JsonProperty("英文WIKI")
    protected String wikiJp;
    @JsonProperty("日文WIKI")
    protected String wikiEn;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the nameJp
     */
    public String getNameJp() {
        return nameJp;
    }

    /**
     * @param nameJp the nameJp to set
     */
    public void setNameJp(String nameJp) {
        this.nameJp = nameJp;
    }

    /**
     * @return the yomi
     */
    public String getYomi() {
        return yomi;
    }

    /**
     * @param yomi the yomi to set
     */
    public void setYomi(String yomi) {
        this.yomi = yomi;
    }

    /**
     * @return the nameZh
     */
    public String getNameZh() {
        return nameZh;
    }

    /**
     * @param nameZh the nameZh to set
     */
    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    /**
     * @return the stype
     */
    public int getStype() {
        return stype;
    }

    /**
     * @param stype the stype to set
     */
    public void setStype(int stype) {
        this.stype = stype;
    }

    /**
     * @return the shipClass
     */
    public List<Object> getShipClass() {
        return shipClass;
    }

    /**
     * @param shipClass the shipClass to set
     */
    public void setShipClass(List<Object> shipClass) {
        this.shipClass = shipClass;
    }

    /**
     * @return the statistics
     */
    public Statistics getStatistics() {
        return statistics;
    }

    /**
     * @param statistics the statistics to set
     */
    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    /**
     * @return the equipment
     */
    public Equipment getEquipment() {
        return equipment;
    }

    /**
     * @param equipment the equipment to set
     */
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    /**
     * @return the consumption
     */
    public Consumption getConsumption() {
        return consumption;
    }

    /**
     * @param consumption the consumption to set
     */
    public void setConsumption(Consumption consumption) {
        this.consumption = consumption;
    }

    /**
     * @return the availability
     */
    public Availability getAvailability() {
        return availability;
    }

    /**
     * @param availability the availability to set
     */
    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    /**
     * @return the modernization
     */
    public Modernization getModernization() {
        return modernization;
    }

    /**
     * @param modernization the modernization to set
     */
    public void setModernization(Modernization modernization) {
        this.modernization = modernization;
    }

    /**
     * @return the scrap
     */
    public Scrap getScrap() {
        return scrap;
    }

    /**
     * @param scrap the scrap to set
     */
    public void setScrap(Scrap scrap) {
        this.scrap = scrap;
    }

    /**
     * @return the artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * @param artist the artist to set
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * @return the seiyuu
     */
    public String getSeiyuu() {
        return seiyuu;
    }

    /**
     * @param seiyuu the seiyuu to set
     */
    public void setSeiyuu(String seiyuu) {
        this.seiyuu = seiyuu;
    }

    /**
     * @return the wikiJp
     */
    public String getWikiJp() {
        return wikiJp;
    }

    /**
     * @param wikiJp the wikiJp to set
     */
    public void setWikiJp(String wikiJp) {
        this.wikiJp = wikiJp;
    }

    /**
     * @return the wikiEn
     */
    public String getWikiEn() {
        return wikiEn;
    }

    /**
     * @param wikiEn the wikiEn to set
     */
    public void setWikiEn(String wikiEn) {
        this.wikiEn = wikiEn;
    }

    /**
     * @return the remodel
     */
    public Remodel getRemodel() {
        return remodel;
    }

    /**
     * @param remodel the remodel to set
     */
    public void setRemodel(Remodel remodel) {
        this.remodel = remodel;
    }
    
}
