/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.spider.entity.ship.luatable;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 *
 * @author iHaru
 */
public class Availability {
    @JsonAlias("掉落")
    private int drop;
    @JsonAlias("改造")
    private int remodel;
    @JsonAlias("建造")
    private int construction;
    @JsonAlias("时间")
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

    /**
     * @return the remodel
     */
    public int getRemodel() {
        return remodel;
    }

    /**
     * @param remodel the remodel to set
     */
    public void setRemodel(int remodel) {
        this.remodel = remodel;
    }
}
