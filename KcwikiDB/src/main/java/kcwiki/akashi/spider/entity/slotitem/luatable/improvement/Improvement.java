/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.spider.entity.slotitem.luatable.improvement;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 *
 * @author iHaru
 */
public class Improvement {
    @JsonAlias("资源消费")
    private Consumption consumption;
    @JsonProperty("prophase_consumption")
    @JsonAlias("初期消费")
    private ItemConsumption prophaseConsumption;
    @JsonProperty("metaphase_consumption")
    @JsonAlias("中段消费")
    private ItemConsumption metaphaseConsumption;
    @JsonProperty("telophase_consumption")
    @JsonAlias("更新消费")
    private ItemConsumption telophaseConsumption;
    @JsonProperty("telophase_item")
    @JsonAlias("更新装备")
    private ItemAfterConsumption telophaseItem;
    @JsonProperty("improvement_date")
    @JsonAlias("日期")
    private Day improvementDate;
    @JsonProperty("improvement_notes")
    @JsonAlias("改修备注")
    private String improvementNotes;

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
     * @return the prophaseConsumption
     */
    public ItemConsumption getProphaseConsumption() {
        return prophaseConsumption;
    }

    /**
     * @param prophaseConsumption the prophaseConsumption to set
     */
    public void setProphaseConsumption(ItemConsumption prophaseConsumption) {
        this.prophaseConsumption = prophaseConsumption;
    }

    /**
     * @return the metaphaseConsumption
     */
    public ItemConsumption getMetaphaseConsumption() {
        return metaphaseConsumption;
    }

    /**
     * @param metaphaseConsumption the metaphaseConsumption to set
     */
    public void setMetaphaseConsumption(ItemConsumption metaphaseConsumption) {
        this.metaphaseConsumption = metaphaseConsumption;
    }

    /**
     * @return the telophaseConsumption
     */
    public ItemConsumption getTelophaseConsumption() {
        return telophaseConsumption;
    }

    /**
     * @param telophaseConsumption the telophaseConsumption to set
     */
    public void setTelophaseConsumption(ItemConsumption telophaseConsumption) {
        this.telophaseConsumption = telophaseConsumption;
    }

    /**
     * @return the telophaseItem
     */
    public ItemAfterConsumption getTelophaseItem() {
        return telophaseItem;
    }

    /**
     * @param telophaseItem the telophaseItem to set
     */
    public void setTelophaseItem(ItemAfterConsumption telophaseItem) {
        this.telophaseItem = telophaseItem;
    }

    /**
     * @return the improvementNotes
     */
    public String getImprovementNotes() {
        return improvementNotes;
    }

    /**
     * @param improvementNotes the improvementNotes to set
     */
    public void setImprovementNotes(String improvementNotes) {
        this.improvementNotes = improvementNotes;
    }

    /**
     * @return the improvementDate
     */
    public Day getImprovementDate() {
        return improvementDate;
    }

    /**
     * @param improvementDate the improvementDate to set
     */
    public void setImprovementDate(Day improvementDate) {
        this.improvementDate = improvementDate;
    }
}
