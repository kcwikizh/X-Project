/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.spider.entity.ship.luatable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author iHaru
 */
public class Remodel {
    @JsonProperty("等级")
    private int level;
    @JsonProperty("弹药")
    private int ammo;
    @JsonProperty("钢材")
    private int steel;
    @JsonProperty("改造前")
    private String wikiIdBefore;
    @JsonProperty("改造后")
    private String wikiIdAfter;
    @JsonProperty("图纸")
    private String extraItems;

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
     * @return the extraItems
     */
    public String getExtraItems() {
        return extraItems;
    }

    /**
     * @param extraItems the extraItems to set
     */
    public void setExtraItems(String extraItems) {
        this.extraItems = extraItems;
    }
    
}
