/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.discord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.security.auth.login.LoginException;
import kcwiki.x.enshuhelper.core.entity.DiscordUserDataBO;
import kcwiki.x.enshuhelper.discord.command.EnshuHelperCommand;
import kcwiki.x.enshuhelper.initializer.AppConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import org.iharu.discord.bot.DiscordBot;
import org.iharu.discord.bot.command.CommandUtils;
import org.iharu.discord.bot.command.PingCommand;
import org.iharu.discord.bot.command.StatsCommand;
import org.iharu.discord.bot.configuration.BotSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 * https://www.programcreek.com/java-api-examples/?code=gabixdev%2FKyoko%2FKyoko-master%2Fsrc%2Fmain%2Fjava%2Fme%2Fgabixdev%2Fkyoko%2Fcommand%2Futil%2FStatsCommand.java#
 */
@Component
public class DiscordBotController {
    private static final Logger LOG = LoggerFactory.getLogger(DiscordBotController.class);
    
    @Autowired
    AppConfig appConfig;
    
    private JDA client;
    private String token;
    private BotSettings botSettings;
    private DiscordBot bot;
    
    @PostConstruct
    public void initMethod()
    {
        token = appConfig.getDiscord_token();
        botSettings = new BotSettings(token, "!svb.");
        bot = new DiscordBot("", botSettings);
    }
    
    @PreDestroy
    public void destroyMethod() {
        client.shutdownNow();
    }
    
    public void start(){
        try {
//            client = new JDABuilder(token).addEventListeners(new EventHandler()).addEventListeners(new DiscordListener()).setAutoReconnect(true).build().awaitReady();
            
            bot.start(new PingCommand(bot), new StatsCommand(bot), new EnshuHelperCommand(bot));
            client = bot.getJda();
        } catch (InterruptedException | LoginException | RateLimitedException ex) {
            LOG.error("{}", ex);
        }
    }
    
    public void sendMatchedMsg(List<DiscordUserDataBO> matchedUsers){
        try{
            DiscordUserDataBO author = matchedUsers.get(0);
            matchedUsers.remove(0);
            Map<String, List<String>> memberListByGuildID = new HashMap();
            Map<String, String> channelIDByMemberID = new HashMap();
            
            matchedUsers.forEach(member -> {
                if(!memberListByGuildID.containsKey(member.getGuildId()))
                    memberListByGuildID.put(member.getGuildId(), new ArrayList());
                memberListByGuildID.get(member.getGuildId()).add(member.getMemberId());
                channelIDByMemberID.put(member.getMemberId(), member.getChannelId());
            });
            
            memberListByGuildID.forEach((guildId, list) -> {
                StringBuilder sb = new StringBuilder();
                if(guildId.equals(author.getGuildId())){
                    sb.append("PVP Assistant Reminder").append("\r\n")
                        .append(CommandUtils.generateMentionedString(author.getMemberId()))
                        .append(" matched succeed").append("\r\n");
                    list.forEach(memberID -> {
                        sb.append(CommandUtils.generateMentionedString(memberID)).append("\r\n");
                    });
                    sb.append(list.size()).append(" ").append(list.size() > 2 ? "members":"member");
                    if(!sendMsg(guildId, author.getChannelId(), sb.toString())){
                        LOG.warn("sendMatchedMsg failed. {} {}", author.getMemberId(), author.getGameId());
                    }
                } else {
                    sb.append("PVP Assistant Reminder").append("\r\n");
                    list.forEach(memberID -> {
                        sb.append(CommandUtils.generateMentionedString(memberID)).append("\r\n");
                    });
                    sb.append("someone matched you from ").append(findGuildName(author.getGuildId()));
                    if(!sendMsg(guildId, channelIDByMemberID.get(list.get(0)), sb.toString())){
                        LOG.warn("sendMatchedMsg failed. {} {}", author.getMemberId(), author.getGameId());
                    }
                }  
            });
        } catch (Exception ex) {
            LOG.error("", ex);
        }
    }
    
    private EmbedBuilder getMatchedSuccessBuilder() {
        return new EmbedBuilder()
                .setTitle("PVP Assistant Reminder")
                .setColor(botSettings.getSuccessColor());
    }
    
    public boolean sendMsg(String guildID, String channelID, String msg){
        Guild guild = client.getGuildById(guildID);
            if(guild == null){
                LOG.error("guild not found: {}", guildID);
                return false;
            }
            TextChannel channel = guild.getTextChannelById(channelID);
            if(channel == null){
                LOG.error("channel not found: {} - {}", guild.getName(),  channelID);
                return false;
            }
        channel.sendMessage(msg).queue();
        return true;
    }
    
    public String findGuildName(String GuildID){ 
        Guild guild = client.getGuildById(GuildID);
        if(guild == null){
            LOG.error(token);
            return "Guild Not Found";
        }
        return guild.getName();
    }
    
}
