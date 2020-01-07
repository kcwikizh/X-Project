/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.akashi.web.entity.type;

/**
 *
 * @author iHaru
 */
public enum DataType {
    SHIP("ship", "/api/ship"),
    SLOTITEM("slotitem", "/api/slotitem"),
    ;
    
    private String name;
    private String url;
    
    DataType(String name, String url){
        this.name = name;
        this.url = url;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
}
