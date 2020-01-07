/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.spider.entity.slotitem.luatable;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import kcwiki.akashi.spider.entity.slotitem.luatable.bonuses.Bonuses;
import kcwiki.akashi.spider.entity.slotitem.luatable.improvement.Improvement;

/**
 *
 * @author iHaru
 */
public class LuaSlotitemDO {
    @JsonAlias("ID")
    protected int id;
    @JsonProperty(value= "name_jp")
    @JsonAlias("日文名")
    protected String nameJp;
    @JsonProperty(value= "name_zh")
    @JsonAlias("中文名")
    protected String nameZh;
    @JsonAlias("类别")
    protected List<Integer> type;
    @JsonAlias("稀有度")
    protected String rarity;
    @JsonAlias("状态")
    protected Availability availability;
    @JsonAlias("属性")
    protected Statistics statistics;
    @JsonAlias("废弃")
    protected Scrap scrap;
    @JsonAlias("装备适用")
    protected List<String> refittable;
    @JsonAlias("备注")
    protected String notes;
    @JsonAlias("额外收益")
    protected List<Bonuses> bonuses = new ArrayList();
    @JsonAlias("装备改修")
    protected List<Improvement> improvement = new ArrayList();
    @JsonProperty(value= "wiki_jp")
    @JsonAlias("英文Wiki")
    protected String wikiJp;
    @JsonProperty(value= "wiki_en")
    @JsonAlias("日文Wiki")
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
     * @return the type
     */
    public List<Integer> getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(List<Integer> type) {
        this.type = type;
    }

    /**
     * @return the rarity
     */
    public String getRarity() {
        return rarity;
    }

    /**
     * @param rarity the rarity to set
     */
    public void setRarity(String rarity) {
        this.rarity = rarity;
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
     * @return the refittable
     */
    public List<String> getRefittable() {
        return refittable;
    }

    /**
     * @param refittable the refittable to set
     */
    public void setRefittable(List<String> refittable) {
        this.refittable = refittable;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the bonuses
     */
    public List<Bonuses> getBonuses() {
        return bonuses;
    }

    /**
     * @param bonuses the bonuses to set
     */
    public void setBonuses(List<Bonuses> bonuses) {
        this.bonuses = bonuses;
    }

    /**
     * @return the improvement
     */
    public List<Improvement> getImprovement() {
        return improvement;
    }

    /**
     * @param improvement the improvement to set
     */
    public void setImprovement(List<Improvement> improvement) {
        this.improvement = improvement;
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

}
