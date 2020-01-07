/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.spider.entity.slotitem.luatable;

import kcwiki.akashi.spider.entity.ship.luatable.*;
import com.fasterxml.jackson.annotation.JsonAlias;

/**
 *
 * @author iHaru
 */
public class Availability {
    @JsonAlias("开发")
    private int build;
    @JsonAlias("改修")
    private int improvement;
    @JsonAlias("更新")
    private int remodel;
    @JsonAlias("熟练")
    private int skilled;

    /**
     * @return the build
     */
    public int getBuild() {
        return build;
    }

    /**
     * @param build the build to set
     */
    public void setBuild(int build) {
        this.build = build;
    }

    /**
     * @return the improvement
     */
    public int getImprovement() {
        return improvement;
    }

    /**
     * @param improvement the improvement to set
     */
    public void setImprovement(int improvement) {
        this.improvement = improvement;
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

    /**
     * @return the skilled
     */
    public int getSkilled() {
        return skilled;
    }

    /**
     * @param skilled the skilled to set
     */
    public void setSkilled(int skilled) {
        this.skilled = skilled;
    }

}
