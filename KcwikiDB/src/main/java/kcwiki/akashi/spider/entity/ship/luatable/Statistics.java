/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.spider.entity.ship.luatable;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.List;

/**
 *
 * @author iHaru
 */
public class Statistics {
    @JsonAlias("耐久")
    private List<Integer> hp;
    @JsonAlias("火力")
    private List<Integer> firepower;
    @JsonAlias("装甲")
    private List<Integer> armor;
    @JsonAlias("雷装")
    private List<Integer> torpedo;
    @JsonAlias("回避")
    private List<Integer> evasion;
    @JsonAlias("对空")
    private List<Integer> aa;
    @JsonAlias("对潜")
    private List<Integer> asw;
    @JsonAlias("索敌")
    private List<Integer> los;
    @JsonAlias("运")
    private List<Integer> luck;
    @JsonAlias("速力")
    private int speed;
    @JsonAlias("射程")
    private int range;
    @JsonAlias("稀有")
    private int rarity;

    /**
     * @return the hp
     */
    public List<Integer> getHp() {
        return hp;
    }

    /**
     * @param hp the hp to set
     */
    public void setHp(List<Integer> hp) {
        this.hp = hp;
    }

    /**
     * @return the firepower
     */
    public List<Integer> getFirepower() {
        return firepower;
    }

    /**
     * @param firepower the firepower to set
     */
    public void setFirepower(List<Integer> firepower) {
        this.firepower = firepower;
    }

    /**
     * @return the armor
     */
    public List<Integer> getArmor() {
        return armor;
    }

    /**
     * @param armor the armor to set
     */
    public void setArmor(List<Integer> armor) {
        this.armor = armor;
    }

    /**
     * @return the torpedo
     */
    public List<Integer> getTorpedo() {
        return torpedo;
    }

    /**
     * @param torpedo the torpedo to set
     */
    public void setTorpedo(List<Integer> torpedo) {
        this.torpedo = torpedo;
    }

    /**
     * @return the evasion
     */
    public List<Integer> getEvasion() {
        return evasion;
    }

    /**
     * @param evasion the evasion to set
     */
    public void setEvasion(List<Integer> evasion) {
        this.evasion = evasion;
    }

    /**
     * @return the aa
     */
    public List<Integer> getAa() {
        return aa;
    }

    /**
     * @param aa the aa to set
     */
    public void setAa(List<Integer> aa) {
        this.aa = aa;
    }

    /**
     * @return the asw
     */
    public List<Integer> getAsw() {
        return asw;
    }

    /**
     * @param asw the asw to set
     */
    public void setAsw(List<Integer> asw) {
        this.asw = asw;
    }

    /**
     * @return the los
     */
    public List<Integer> getLos() {
        return los;
    }

    /**
     * @param los the los to set
     */
    public void setLos(List<Integer> los) {
        this.los = los;
    }

    /**
     * @return the luck
     */
    public List<Integer> getLuck() {
        return luck;
    }

    /**
     * @param luck the luck to set
     */
    public void setLuck(List<Integer> luck) {
        this.luck = luck;
    }

    /**
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
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

    /**
     * @return the rarity
     */
    public int getRarity() {
        return rarity;
    }

    /**
     * @param rarity the rarity to set
     */
    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

}
