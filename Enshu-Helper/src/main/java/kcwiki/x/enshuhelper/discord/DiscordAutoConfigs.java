/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.discord;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@ConfigurationProperties(prefix = "myprops.discord.superadmin")
@Component
public class DiscordAutoConfigs {
    private List<Long> memberids;
    private List<String> cmds;

    /**
     * @return the memberids
     */
    public List<Long> getMemberids() {
        return memberids;
    }

    /**
     * @param memberids the memberids to set
     */
    public void setMemberids(List<Long> memberids) {
        this.memberids = memberids;
    }

    /**
     * @return the cmds
     */
    public List<String> getCmds() {
        return cmds;
    }

    /**
     * @param cmds the cmds to set
     */
    public void setCmds(List<String> cmds) {
        this.cmds = cmds;
    }

}
