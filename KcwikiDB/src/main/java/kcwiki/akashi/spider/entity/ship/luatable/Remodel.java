/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.spider.entity.ship.luatable;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author iHaru
 */
public class Remodel {
    @JsonAlias("等级")
    private int level;
    @JsonAlias("弹药")
    private int ammo;
    @JsonAlias("钢材")
    private int steel;
    @JsonAlias("改造前")
    @JsonProperty(value= "wiki_id_beford")
    private String wikiIdBefore;
    @JsonAlias("改造后")
    @JsonProperty(value= "wiki_id_after")
    private String wikiIdAfter;
    @JsonAlias("图纸")
    @JsonProperty(value= "extra_needs")
    private String extraNeeds;

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the ammo
     */
    public int getAmmo() {
        return ammo;
    }

    /**
     * @param ammo the ammo to set
     */
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    /**
     * @return the steel
     */
    public int getSteel() {
        return steel;
    }

    /**
     * @param steel the steel to set
     */
    public void setSteel(int steel) {
        this.steel = steel;
    }

    /**
     * @return the wikiIdBefore
     */
    public String getWikiIdBefore() {
        return wikiIdBefore;
    }

    /**
     * @param wikiIdBefore the wikiIdBefore to set
     */
    public void setWikiIdBefore(String wikiIdBefore) {
        this.wikiIdBefore = wikiIdBefore;
    }

    /**
     * @return the wikiIdAfter
     */
    public String getWikiIdAfter() {
        return wikiIdAfter;
    }

    /**
     * @param wikiIdAfter the wikiIdAfter to set
     */
    public void setWikiIdAfter(String wikiIdAfter) {
        this.wikiIdAfter = wikiIdAfter;
    }

    /**
     * @return the extraNeeds
     */
    public String getExtraNeeds() {
        return extraNeeds;
    }

    /**
     * @param extraNeeds the extraNeeds to set
     */
    public void setExtraNeeds(String extraNeeds) {
        this.extraNeeds = extraNeeds;
    }
    
}
