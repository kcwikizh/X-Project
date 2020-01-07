/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.spider.entity.slotitem.luatable.bonuses;

/**
 *
 * @author iHaru
 */
public class BonusStatus {
    private BonusStatistics bonusStatistics;
    private String area;
    private int amount;
    
    public BonusStatus() {}
    
    public BonusStatus(BonusStatistics bonusStatistics) {
        this.bonusStatistics = bonusStatistics;
    }
    
    public BonusStatus(int amount, BonusStatistics bonusStatistics) {
        this.amount = amount;
        this.bonusStatistics = bonusStatistics;
    }

    public BonusStatus(String area, BonusStatistics bonusStatistics) {
        this.area = area;
        this.bonusStatistics = bonusStatistics;
    }
    
    /**
     * @return the bonusStatistics
     */
    public BonusStatistics getBonusStatistics() {
        return bonusStatistics;
    }

    /**
     * @param bonusStatistics the bonusStatistics to set
     */
    public void setBonusStatistics(BonusStatistics bonusStatistics) {
        this.bonusStatistics = bonusStatistics;
    }

    /**
     * @return the area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
