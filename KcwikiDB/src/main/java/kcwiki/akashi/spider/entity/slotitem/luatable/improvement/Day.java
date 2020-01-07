/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.spider.entity.slotitem.luatable.improvement;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.List;

/**
 *
 * @author iHaru
 */
public class Day {
    @JsonAlias("日")
    private List<String> sunday;
    @JsonAlias("一")
    private List<String> monday;
    @JsonAlias("二")
    private List<String> tuesday;
    @JsonAlias("三")
    private List<String> wednesday;
    @JsonAlias("四")
    private List<String> thursday;
    @JsonAlias("五")
    private List<String> friday;
    @JsonAlias("六")
    private List<String> saturday;

    /**
     * @return the sunday
     */
    public List<String> getSunday() {
        return sunday;
    }

    /**
     * @param sunday the sunday to set
     */
    public void setSunday(List<String> sunday) {
        this.sunday = sunday;
    }

    /**
     * @return the monday
     */
    public List<String> getMonday() {
        return monday;
    }

    /**
     * @param monday the monday to set
     */
    public void setMonday(List<String> monday) {
        this.monday = monday;
    }

    /**
     * @return the tuesday
     */
    public List<String> getTuesday() {
        return tuesday;
    }

    /**
     * @param tuesday the tuesday to set
     */
    public void setTuesday(List<String> tuesday) {
        this.tuesday = tuesday;
    }

    /**
     * @return the wednesday
     */
    public List<String> getWednesday() {
        return wednesday;
    }

    /**
     * @param wednesday the wednesday to set
     */
    public void setWednesday(List<String> wednesday) {
        this.wednesday = wednesday;
    }

    /**
     * @return the thursday
     */
    public List<String> getThursday() {
        return thursday;
    }

    /**
     * @param thursday the thursday to set
     */
    public void setThursday(List<String> thursday) {
        this.thursday = thursday;
    }

    /**
     * @return the friday
     */
    public List<String> getFriday() {
        return friday;
    }

    /**
     * @param friday the friday to set
     */
    public void setFriday(List<String> friday) {
        this.friday = friday;
    }

    /**
     * @return the saturday
     */
    public List<String> getSaturday() {
        return saturday;
    }

    /**
     * @param saturday the saturday to set
     */
    public void setSaturday(List<String> saturday) {
        this.saturday = saturday;
    }
}
