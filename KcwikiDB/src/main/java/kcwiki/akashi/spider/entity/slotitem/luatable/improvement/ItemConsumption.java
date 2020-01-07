/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.spider.entity.slotitem.luatable.improvement;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author iHaru
 */
public class ItemConsumption {
    @JsonProperty("development_material")
    @JsonAlias("开发")
    private List<Integer> developmentMaterial;
    @JsonProperty("improvement_material")
    @JsonAlias("改修")
    private List<Integer> improvementMaterial;
    @JsonAlias("装备数")
    private int quantity;
    @JsonAlias("装备")
    @JsonProperty("wiki_id")
    private String wikiId;

    /**
     * @return the developmentMaterial
     */
    public List<Integer> getDevelopmentMaterial() {
        return developmentMaterial;
    }

    /**
     * @param developmentMaterial the developmentMaterial to set
     */
    public void setDevelopmentMaterial(List<Integer> developmentMaterial) {
        this.developmentMaterial = developmentMaterial;
    }

    /**
     * @return the improvementMaterial
     */
    public List<Integer> getImprovementMaterial() {
        return improvementMaterial;
    }

    /**
     * @param improvementMaterial the improvementMaterial to set
     */
    public void setImprovementMaterial(List<Integer> improvementMaterial) {
        this.improvementMaterial = improvementMaterial;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

}
