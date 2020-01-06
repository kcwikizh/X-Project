/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.database.service;

import java.util.List;
import kcwiki.x.enshuhelper.database.dao.DiscordAdminDataDAO;
import kcwiki.x.enshuhelper.database.entity.DiscordAdminDataDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author iHaru
 */
@Service
public class DiscordAdminDataService {
    
    @Autowired
    private DiscordAdminDataDAO discordAdminDataDAO;
    
    public List<DiscordAdminDataDO> listAllDiscordAdmins() {
        return discordAdminDataDAO.listAllDiscordAdmins();
    }
    
    public DiscordAdminDataDO getDiscordAdminByGuildIdAndChannelIdAndMemberId(String guildId, String channelId, String memberId) {
        return discordAdminDataDAO.getDiscordAdminByGuildIdAndChannelIdAndMemberId(guildId, channelId, memberId);
    }
    
    public int saveDiscordAdmin(DiscordAdminDataDO discordAdminDataDO) {
        return discordAdminDataDAO.saveDiscordAdmin(discordAdminDataDO);
    }
    
    public int removeDiscordAdminByGuildIdAndChannelIdAndMemberId(String guildId, String channelId, String memberId) {
        return discordAdminDataDAO.removeDiscordAdminByGuildIdAndChannelIdAndMemberId(guildId, channelId, memberId);
    }
    
}
