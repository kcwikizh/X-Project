/**
  * Copyright 2020 bejson.com 
  */
package kcwiki.akashi.spider.entity.slotitem.kcdata;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Auto-generated: 2020-01-07 16:54:21
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class KcDataSlotitemDO {

    private int id;
    @JsonProperty("sort_no")
    private int sort;
    private String name;
    private List<Integer> type;
    private int rare;
    private List<Integer> broken;
    private String info;
    private int usebull;
    private String cost;
    private String distance;
    private Stats stats;
    @JsonIgnore
    private List<Improvement> improvement;
    @JsonProperty("chinese_name")
    private String chineseName;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the sort
     */
    public int getSort() {
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(int sort) {
        this.sort = sort;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public List<Integer> getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(List<Integer> type) {
        this.type = type;
    }

    /**
     * @return the rare
     */
    public int getRare() {
        return rare;
    }

    /**
     * @param rare the rare to set
     */
    public void setRare(int rare) {
        this.rare = rare;
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
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * @return the usebull
     */
    public int getUsebull() {
        return usebull;
    }

    /**
     * @param usebull the usebull to set
     */
    public void setUsebull(int usebull) {
        this.usebull = usebull;
    }

    /**
     * @return the cost
     */
    public String getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(String cost) {
        this.cost = cost;
    }

    /**
     * @return the distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /**
     * @return the stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * @param stats the stats to set
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    /**
     * @return the improvement
     */
    public List<Improvement> getImprovement() {
        return improvement;
    }

    /**
     * @param improvement the improvement to set
     */
    public void setImprovement(List<Improvement> improvement) {
        this.improvement = improvement;
    }

    /**
     * @return the chineseName
     */
    public String getChineseName() {
        return chineseName;
    }

    /**
     * @param chineseName the chineseName to set
     */
    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }
    

}