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
public class Equipment {
    @JsonAlias("格数")
    private int slot;
    @JsonAlias("搭载")
    private List<Integer> aircraft;
    @JsonAlias("初期装备")
    private List<Object> stock;

    /**
     * @return the slot
     */
    public int getSlot() {
        return slot;
    }

    /**
     * @param slot the slot to set
     */
    public void setSlot(int slot) {
        this.slot = slot;
    }

    /**
     * @return the aircraft
     */
    public List<Integer> getAircraft() {
        return aircraft;
    }

    /**
     * @param aircraft the aircraft to set
     */
    public void setAircraft(List<Integer> aircraft) {
        this.aircraft = aircraft;
    }

    /**
     * @return the stock
     */
    public List<Object> getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(List<Object> stock) {
        this.stock = stock;
    }
}
