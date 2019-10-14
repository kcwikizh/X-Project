/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.controlcenter.websocket.entity;

/**
 *
 * @author iHaru
 */
public class ByteArrayContainer {
    private byte[] array;
    
    public ByteArrayContainer(){}
    
    public ByteArrayContainer(byte[] array){
        this.array = array;
    }
    

    /**
     * @return the array
     */
    public byte[] getArray() {
        return array;
    }

    /**
     * @param array the array to set
     */
    public void setArray(byte[] array) {
        this.array = array;
    }
}
