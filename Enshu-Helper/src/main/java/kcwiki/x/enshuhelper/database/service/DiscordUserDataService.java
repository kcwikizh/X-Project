/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.database.service;

import java.util.List;
import kcwiki.x.enshuhelper.database.dao.DiscordUserDataDAO;
import kcwiki.x.enshuhelper.database.entity.DiscordUserDataDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author iHaru
 */
@Service
public class DiscordUserDataService {
    
    @Autowired
    private DiscordUserDataDAO discordUserDataDAO;
    
    public List<DiscordUserDataDO> listAllDiscordUsers() {
        return discordUserDataDAO.listAllDiscordUsers();
    }
    
    public List<DiscordUserDataDO> listDiscordUsersByGameId(List<Integer> list) {
        return discordUserDataDAO.listDiscordUsersByGameId(list);
    }
    
    public int saveDiscordUser(DiscordUserDataDO discordAdminDataDO) {
        return discordUserDataDAO.saveDiscordUser(discordAdminDataDO);
    }
    
    public int removeDiscordUserByGameId(int gameId){
        return discordUserDataDAO.removeDiscordUserByGameId(gameId);
    }
    
    public DiscordUserDataDO getDiscordUserByGameId(int gameId){
        return discordUserDataDAO.getDiscordUserByGameId(gameId);
    }
}
