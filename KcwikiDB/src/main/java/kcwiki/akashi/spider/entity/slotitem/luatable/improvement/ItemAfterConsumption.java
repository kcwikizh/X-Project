/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.spider.entity.slotitem.luatable.improvement;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author iHaru
 */
public class ItemAfterConsumption {
    @JsonAlias("装备")
    @JsonProperty("wiki_id")
    private String wikiId;
    @JsonAlias("等级")
    private int level;

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

}
