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
    SHIP("ship"),
    ;
    private String name;
    
    DataType(String name){
        this.name = name;
    }
}
