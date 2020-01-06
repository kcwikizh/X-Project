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
public class KcDataShipDO {
    private int id;
    @JsonProperty("sort_no")
    private int sort;
    private String name;
    private String yomi;
    private int stype;
    private int ctype;
    private int cnum;
    private int backs;
    @JsonProperty("after_lv")
    private int afterLv;
    @JsonProperty("after_ship_id")
    private int afterShipId;
    @JsonProperty("get_mes")
    private String getMsg;
    @JsonProperty("voice_f")
    private int voice;
    private String filename;
    @JsonProperty("file_version")
    private List<String> fileVersion;
    @JsonProperty("book_table_id")
    private List<Integer> bookTableId;
    @JsonProperty("book_sinfo")
    private String bookShipInfo;
    private Stats stats;
    private Graph graph;
    @JsonProperty("wiki_id")
    private String wikiId;
    @JsonProperty("chinese_name")
    private String chineseName;
    @JsonProperty("can_drop")
    private boolean canDrop;
    @JsonProperty("can_construct")
    private boolean canConstruct;

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
     * @return the yomi
     */
    public String getYomi() {
        return yomi;
    }

    /**
     * @param yomi the yomi to set
     */
    public void setYomi(String yomi) {
        this.yomi = yomi;
    }

    /**
     * @return the stype
     */
    public int getStype() {
        return stype;
    }

    /**
     * @param stype the stype to set
     */
    public void setStype(int stype) {
        this.stype = stype;
    }

    /**
     * @return the ctype
     */
    public int getCtype() {
        return ctype;
    }

    /**
     * @param ctype the ctype to set
     */
    public void setCtype(int ctype) {
        this.ctype = ctype;
    }

    /**
     * @return the cnum
     */
    public int getCnum() {
        return cnum;
    }

    /**
     * @param cnum the cnum to set
     */
    public void setCnum(int cnum) {
        this.cnum = cnum;
    }

    /**
     * @return the backs
     */
    public int getBacks() {
        return backs;
    }

    /**
     * @param backs the backs to set
     */
    public void setBacks(int backs) {
        this.backs = backs;
    }

    /**
     * @return the afterLv
     */
    public int getAfterLv() {
        return afterLv;
    }

    /**
     * @param afterLv the afterLv to set
     */
    public void setAfterLv(int afterLv) {
        this.afterLv = afterLv;
    }

    /**
     * @return the afterShipId
     */
    public int getAfterShipId() {
        return afterShipId;
    }

    /**
     * @param afterShipId the afterShipId to set
     */
    public void setAfterShipId(int afterShipId) {
        this.afterShipId = afterShipId;
    }

    /**
     * @return the getMsg
     */
    public String getGetMsg() {
        return getMsg;
    }

    /**
     * @param getMsg the getMsg to set
     */
    public void setGetMsg(String getMsg) {
        this.getMsg = getMsg;
    }

    /**
     * @return the voice
     */
    public int getVoice() {
        return voice;
    }

    /**
     * @param voice the voice to set
     */
    public void setVoice(int voice) {
        this.voice = voice;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the fileVersion
     */
    public List<String> getFileVersion() {
        return fileVersion;
    }

    /**
     * @param fileVersion the fileVersion to set
     */
    public void setFileVersion(List<String> fileVersion) {
        this.fileVersion = fileVersion;
    }

    /**
     * @return the bookTableId
     */
    public List<Integer> getBookTableId() {
        return bookTableId;
    }

    /**
     * @param bookTableId the bookTableId to set
     */
    public void setBookTableId(List<Integer> bookTableId) {
        this.bookTableId = bookTableId;
    }

    /**
     * @return the bookShipInfo
     */
    public String getBookShipInfo() {
        return bookShipInfo;
    }

    /**
     * @param bookShipInfo the bookShipInfo to set
     */
    public void setBookShipInfo(String bookShipInfo) {
        this.bookShipInfo = bookShipInfo;
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
     * @return the graph
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * @param graph the graph to set
     */
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    /**
     * @return the wikiId
     */
    public String getWikiId() {
        return wikiId;
    }

    /**
     * @param wikiId the wikiId to set
     */
    public void setWikiId(String wikiId) {
        this.wikiId = wikiId;
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

    /**
     * @return the canDrop
     */
    public boolean isCanDrop() {
        return canDrop;
    }

    /**
     * @param canDrop the canDrop to set
     */
    public void setCanDrop(boolean canDrop) {
        this.canDrop = canDrop;
    }

    /**
     * @return the canConstruct
     */
    public boolean isCanConstruct() {
        return canConstruct;
    }

    /**
     * @param canConstruct the canConstruct to set
     */
    public void setCanConstruct(boolean canConstruct) {
        this.canConstruct = canConstruct;
    }
}