/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.types;

/**
 *
 * @author iHaru
 */
public enum FileTypes implements EnumBase {
    Core(0),
    ShipVoice(1),
    Ship(2),
    Slotitem(3),
    Furniture(4),
    Useitem(5),
    Payitem(6),
    Mapbgm(7),
    Mapinfo(8)
    ;
    
    private int code;
    
    FileTypes(int code) {
        this.code = code;
    }
    
    @Override
    public String getName() {
        switch(code) {
            default:
                return "未知";
            case 0:
                return "测试";
            case 1:
                return "普通";
            case 2:
                return "警告";
            case 3:
                return "错误"; 
        }
    }

    @Override
    public int getCode() {
        return code;
    }
}
