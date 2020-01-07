/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.spider.entity.slotitem.luatable.bonuses;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 *
 * @author iHaru
 */
public class BonusStatistics {
    @JsonAlias("火力")
    private int firepower;
    @JsonAlias("雷装")
    private int torpedo;
    @JsonAlias("对空")
    private int aa;
    @JsonAlias("对潜")
    private int asw;
    @JsonAlias("装甲")
    private int armor;
    @JsonAlias("回避")
    private int evasion;
    @JsonAlias("索敌")
    private int los;
    @JsonAlias("射程")
    private int range;

    /**
     * @return the firepower
     */
    public int getFirepower() {
        return firepower;
    }

    /**
     * @param firepower the firepower to set
     */
    public void setFirepower(int firepower) {
        this.firepower = firepower;
    }

    /**
     * @return the torpedo
     */
    public int getTorpedo() {
        return torpedo;
    }

    /**
     * @param torpedo the torpedo to set
     */
    public void setTorpedo(int torpedo) {
        this.torpedo = torpedo;
    }

    /**
     * @return the aa
     */
    public int getAa() {
        return aa;
    }

    /**
     * @param aa the aa to set
     */
    public void setAa(int aa) {
        this.aa = aa;
    }

    /**
     * @return the asw
     */
    public int getAsw() {
        return asw;
    }

    /**
     * @param asw the asw to set
     */
    public void setAsw(int asw) {
        this.asw = asw;
    }

    /**
     * @return the armor
     */
    public int getArmor() {
        return armor;
    }

    /**
     * @param armor the armor to set
     */
    public void setArmor(int armor) {
        this.armor = armor;
    }

    /**
     * @return the evasion
     */
    public int getEvasion() {
        return evasion;
    }

    /**
     * @param evasion the evasion to set
     */
    public void setEvasion(int evasion) {
        this.evasion = evasion;
    }

    /**
     * @return the los
     */
    public int getLos() {
        return los;
    }

    /**
     * @param los the los to set
     */
    public void setLos(int los) {
        this.los = los;
    }

    /**
     * @return the range
     */
    public int getRange() {
        return range;
    }

    /**
     * @param range the range to set
     */
    public void setRange(int range) {
        this.range = range;
    }
}
