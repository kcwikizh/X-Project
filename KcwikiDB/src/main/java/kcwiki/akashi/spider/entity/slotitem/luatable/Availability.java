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
public class Availability {
    @JsonProperty("掉落")
    private int drop;
    @JsonProperty("改造")
    private int implementation;
    @JsonProperty("建造")
    private int construction;
    @JsonProperty("时间")
    private int time;

    /**
     * @return the drop
     */
    public int getDrop() {
        return drop;
    }

    /**
     * @param drop the drop to set
     */
    public void setDrop(int drop) {
        this.drop = drop;
    }

    /**
     * @return the implementation
     */
    public int getImplementation() {
        return implementation;
    }

    /**
     * @param implementation the implementation to set
     */
    public void setImplementation(int implementation) {
        this.implementation = implementation;
    }

    /**
     * @return the construction
     */
    public int getConstruction() {
        return construction;
    }

    /**
     * @param construction the construction to set
     */
    public void setConstruction(int construction) {
        this.construction = construction;
    }

    /**
     * @return the time
     */
    public int getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(int time) {
        this.time = time;
    }
}
