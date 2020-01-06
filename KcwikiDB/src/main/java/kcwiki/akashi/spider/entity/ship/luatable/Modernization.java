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
public class Modernization {
    @JsonProperty("耐久")
    private int hp;
    @JsonProperty("火力")
    private int firepower;
    @JsonProperty("装甲")
    private int armor;
    @JsonProperty("雷装")
    private int torpedo;
    @JsonProperty("回避")
    private int evasion;
    @JsonProperty("对空")
    private int aa;
    @JsonProperty("对潜")
    private int asw;
    @JsonProperty("运")
    private int luck;

    /**
     * @return the hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * @param hp the hp to set
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

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
     * @return the luck
     */
    public int getLuck() {
        return luck;
    }

    /**
     * @param luck the luck to set
     */
    public void setLuck(int luck) {
        this.luck = luck;
    }
}
