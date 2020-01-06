/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.database.entity.type;

import java.util.stream.Stream;

/**
 *
 * @author iHaru
 */
public enum DiscordAdminType {
    OWNNER(0),
    ADMIN(1)
    ;
    
    private final int index;
    
    DiscordAdminType(int  index) {
        this.index = index;
    }
    
    public static DiscordAdminType of(int  index) {
        return Stream.of(DiscordAdminType.values())
          .filter(p -> p.getIndex() == index)
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }
    
}
