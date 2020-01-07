/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.spider.entity.slotitem.luatable.bonuses;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author iHaru
 */
public class Bonuses<T> {
    @JsonAlias("适用舰娘")
    private List<String> ship;
    @JsonAlias("收益类型")
    private String type;
//    @JsonAlias("收益属性")
//    private T bonusStatistics;
    @JsonProperty("bonus_status")
    private List<BonusStatus> bonusStatus;

    /**
     * @return the ship
     */
    public List<String> getShip() {
        return ship;
    }

    /**
     * @param ship the ship to set
     */
    public void setShip(List<String> ship) {
        this.ship = ship;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the bonusStatus
     */
    public List<BonusStatus> getBonusStatus() {
        return bonusStatus;
    }

    /**
     * @param bonusStatus the bonusStatus to set
     */
    public void setBonusStatus(List<BonusStatus> bonusStatus) {
        this.bonusStatus = bonusStatus;
    }

}
