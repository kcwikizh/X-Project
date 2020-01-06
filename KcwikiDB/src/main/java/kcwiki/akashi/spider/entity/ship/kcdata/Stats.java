/**
  * Copyright 2020 bejson.com 
  */
package kcwiki.akashi.spider.entity.ship.kcdata;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Auto-generated: 2020-01-07 0:36:37
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Stats {

    private List<Integer> taik;
    private List<Integer> souk;
    private List<Integer> houg;
    private List<Integer> raig;
    private List<Integer> tyku;
    private List<Integer> luck;
    private int soku;
    private int leng;
    private int kaih;
    private int tais;
    @JsonProperty("slot_num")
    private int slotNum;
    @JsonProperty("max_eq")
    private List<Integer> maxEq;
    @JsonProperty("after_fuel")
    private int afterFuel;
    @JsonProperty("after_bull")
    private int afterBull;
    @JsonProperty("fuel_max")
    private int fuelMax;
    @JsonProperty("bull_max")
    private int bullMax;
    private List<Integer> broken;
    @JsonProperty("pow_up")
    private List<Integer> powUp;
    @JsonProperty("build_time")
    private int buildTime;

    /**
     * @return the taik
     */
    public List<Integer> getTaik() {
        return taik;
    }

    /**
     * @param taik the taik to set
     */
    public void setTaik(List<Integer> taik) {
        this.taik = taik;
    }

    /**
     * @return the souk
     */
    public List<Integer> getSouk() {
        return souk;
    }

    /**
     * @param souk the souk to set
     */
    public void setSouk(List<Integer> souk) {
        this.souk = souk;
    }

    /**
     * @return the houg
     */
    public List<Integer> getHoug() {
        return houg;
    }

    /**
     * @param houg the houg to set
     */
    public void setHoug(List<Integer> houg) {
        this.houg = houg;
    }

    /**
     * @return the raig
     */
    public List<Integer> getRaig() {
        return raig;
    }

    /**
     * @param raig the raig to set
     */
    public void setRaig(List<Integer> raig) {
        this.raig = raig;
    }

    /**
     * @return the tyku
     */
    public List<Integer> getTyku() {
        return tyku;
    }

    /**
     * @param tyku the tyku to set
     */
    public void setTyku(List<Integer> tyku) {
        this.tyku = tyku;
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
     * @return the soku
     */
    public int getSoku() {
        return soku;
    }

    /**
     * @param soku the soku to set
     */
    public void setSoku(int soku) {
        this.soku = soku;
    }

    /**
     * @return the leng
     */
    public int getLeng() {
        return leng;
    }

    /**
     * @param leng the leng to set
     */
    public void setLeng(int leng) {
        this.leng = leng;
    }

    /**
     * @return the kaih
     */
    public int getKaih() {
        return kaih;
    }

    /**
     * @param kaih the kaih to set
     */
    public void setKaih(int kaih) {
        this.kaih = kaih;
    }

    /**
     * @return the tais
     */
    public int getTais() {
        return tais;
    }

    /**
     * @param tais the tais to set
     */
    public void setTais(int tais) {
        this.tais = tais;
    }

    /**
     * @return the slotNum
     */
    public int getSlotNum() {
        return slotNum;
    }

    /**
     * @param slotNum the slotNum to set
     */
    public void setSlotNum(int slotNum) {
        this.slotNum = slotNum;
    }

    /**
     * @return the maxEq
     */
    public List<Integer> getMaxEq() {
        return maxEq;
    }

    /**
     * @param maxEq the maxEq to set
     */
    public void setMaxEq(List<Integer> maxEq) {
        this.maxEq = maxEq;
    }

    /**
     * @return the afterFuel
     */
    public int getAfterFuel() {
        return afterFuel;
    }

    /**
     * @param afterFuel the afterFuel to set
     */
    public void setAfterFuel(int afterFuel) {
        this.afterFuel = afterFuel;
    }

    /**
     * @return the afterBull
     */
    public int getAfterBull() {
        return afterBull;
    }

    /**
     * @param afterBull the afterBull to set
     */
    public void setAfterBull(int afterBull) {
        this.afterBull = afterBull;
    }

    /**
     * @return the fuelMax
     */
    public int getFuelMax() {
        return fuelMax;
    }

    /**
     * @param fuelMax the fuelMax to set
     */
    public void setFuelMax(int fuelMax) {
        this.fuelMax = fuelMax;
    }

    /**
     * @return the bullMax
     */
    public int getBullMax() {
        return bullMax;
    }

    /**
     * @param bullMax the bullMax to set
     */
    public void setBullMax(int bullMax) {
        this.bullMax = bullMax;
    }

    /**
     * @return the broken
     */
    public List<Integer> getBroken() {
        return broken;
    }

    /**
     * @param broken the broken to set
     */
    public void setBroken(List<Integer> broken) {
        this.broken = broken;
    }

    /**
     * @return the powUp
     */
    public List<Integer> getPowUp() {
        return powUp;
    }

    /**
     * @param powUp the powUp to set
     */
    public void setPowUp(List<Integer> powUp) {
        this.powUp = powUp;
    }

    /**
     * @return the buildTime
     */
    public int getBuildTime() {
        return buildTime;
    }

    /**
     * @param buildTime the buildTime to set
     */
    public void setBuildTime(int buildTime) {
        this.buildTime = buildTime;
    }

}