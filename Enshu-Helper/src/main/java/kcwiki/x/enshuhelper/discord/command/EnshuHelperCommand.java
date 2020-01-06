/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.discord.command;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import kcwiki.x.enshuhelper.database.entity.DiscordAdminDataDO;
import kcwiki.x.enshuhelper.database.entity.DiscordUserDataDO;
import kcwiki.x.enshuhelper.database.entity.type.DiscordAdminType;
import kcwiki.x.enshuhelper.database.service.DiscordAdminDataService;
import kcwiki.x.enshuhelper.database.service.DiscordUserDataService;
import kcwiki.x.enshuhelper.discord.DiscordAutoConfigs;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.iharu.discord.bot.DiscordBot;
import org.iharu.discord.bot.command.Command;
import org.iharu.discord.bot.command.CommandUtils;
import org.iharu.discord.bot.command.type.CommandType;
import org.iharu.spring.SpringUtils;

/**
 *
 * @author iHaru
 */
public class EnshuHelperCommand implements Command {

    private final DiscordBot bot;

    private final String[] aliases = new String[]{"eh-register", "eh-unregister", "eh-grant", "eh-cancelgrant"};
    
    private DiscordAutoConfigs discordAutoConfigs;
    private DiscordAdminDataService discordAdminDataService;
    private DiscordUserDataService discordUserDataService;

    public EnshuHelperCommand(DiscordBot bot) {
        this.bot = bot;
        discordAutoConfigs = SpringUtils.getBean(DiscordAutoConfigs.class);
        discordAdminDataService = SpringUtils.getBean(DiscordAdminDataService.class);
        discordUserDataService = SpringUtils.getBean(DiscordUserDataService.class);
    }

    @Override
    public String getLabel() {
        return aliases[0];
    }

    @Override
    public String[] getAliases() {
        return aliases;
    }

    @Override
    public String getDescription() {
        return "stats.description";
    }

    @Override
    public String getUsage() {
        return "stats.usage";
    }

    @Override
    public CommandType getType() {
        return CommandType.UTILITY;
    }

    @Override
    public void handle(Message message, Event event, String[] args) throws Throwable {
        String authorID = message.getAuthor().getId();
        switch(args[0].toLowerCase()){
            case "eh-register":{
                if(discordAdminDataService.getDiscordAdminByGuildIdAndChannelIdAndMemberId(
                            message.getGuild().getId(), message.getChannel().getId(), authorID) == null)
                {
                        return;
                }
                if(message.getMentionedUsers().isEmpty() 
                        || (message.getMentionedUsers().size() == 1 
                            && message.getMentionedUsers().get(0).getIdLong() == bot.getBotID())){
                    message.getChannel().sendMessage(CommandUtils.generateMentionedString(authorID) 
                            + "\t please mention a user").queue();
                    return;
                }else if(message.getMentionedUsers().size() > 2){
                    message.getChannel().sendMessage(CommandUtils.generateMentionedString(authorID) 
                            + "\t mentioned user must less than 3").queue();
                    return;
                }
                if(args.length != 2){
                    message.getChannel().sendMessage(CommandUtils.generateMentionedString(authorID) 
                            + "\t string format error").queue();
                    return;
                }
                String[] params = args[1].trim().split(",", 3);
                if(params.length != 3){
                    message.getChannel().sendMessage(CommandUtils.generateMentionedString(authorID) 
                            + "\t string format error").queue();
                    return;
                }
                params[2] = params[2].split(" ", 2)[0];
                int gameID = -1;
                try{
                    gameID = Integer.valueOf(params[0].trim());
                }catch(NumberFormatException ex){
                    message.getChannel().sendMessage(CommandUtils.generateMentionedString(authorID) + "\t" + params[0].trim() + "is not a numeric").queue();
                    return;
                }
                DiscordUserDataDO userDO = discordUserDataService.getDiscordUserByGameId(gameID);
                if(userDO != null){
                    message.getChannel().sendMessage(CommandUtils.generateMentionedString(authorID) 
                            + "\t user " + gameID + " has registered").queue();
                } else {
                    String memberID;
                    if(message.getMentionedUsers().size() == 1){
                        memberID = message.getMentionedUsers().get(0).getId();
                    } else if(message.getMentionedUsers().get(1).getIdLong() != bot.getBotID()){
                        memberID = message.getMentionedUsers().get(1).getId();
                    } else {
                        message.getChannel().sendMessage(CommandUtils.generateMentionedString(authorID) 
                            + "\t string format error").queue();
                        return;
                    }
                    if(discordUserDataService.saveDiscordUser(
                            new DiscordUserDataDO(params[1].trim(), gameID, 
                            message.getGuild().getId(), message.getChannel().getId(), memberID,
                            params[2].trim(), authorID)
                    ) > 0){
                        message.getChannel().sendMessage("user " + gameID + " register success").queue();
                    } else {
                        message.getChannel().sendMessage(CommandUtils.generateMentionedString(authorID)  + "\t user " + gameID + " register failed, ").queue();
                    }
                }
                break;
            }
            case "eh-unregister":{
                if(discordAdminDataService.getDiscordAdminByGuildIdAndChannelIdAndMemberId(
                            message.getGuild().getId(), message.getChannel().getId(), authorID) == null)
                {
                        return;
                }
                if(args.length != 2){
                    message.getChannel().sendMessage(CommandUtils.generateMentionedString(authorID) 
                            + "\t string format error").queue();
                    return;
                }
                int gameID = -1;
                try{
                    gameID = Integer.valueOf(args[1].trim());
                }catch(NumberFormatException ex){
                    message.getChannel().sendMessage(CommandUtils.generateMentionedString(authorID) 
                            + "\t" + args[1].trim() + "is not a numeric").queue();
                    return;
                }
                DiscordUserDataDO userDO = discordUserDataService.getDiscordUserByGameId(gameID);
                if(userDO == null){
                    message.getChannel().sendMessage(CommandUtils.generateMentionedString(authorID) 
                            + "\t user " + gameID + " not exist").queue();
                    return;
                } else if(userDO.isBlocked()){
                    message.getChannel().sendMessage(CommandUtils.generateMentionedString(authorID) 
                            + "\t user " + gameID + " has been blocked, unregister failed").queue();
                    return;
                }
                if(message.getGuild().getId().equals(userDO.getGuildId())
                        && message.getChannel().getId().equals(userDO.getChannelId())){
                    if(discordUserDataService.removeDiscordUserByGameId(gameID) > 0){
                        message.getChannel().sendMessage("user " + gameID + " has been removed").queue();
                    }
                } else {
                    message.getChannel().sendMessage(CommandUtils.generateMentionedString(authorID) 
                            + "\t user " + gameID + " is not registered in current server").queue();
                }
                break;
            }
            case "eh-grant":{
                if(message.getMentionedUsers().isEmpty() 
                        || (message.getMentionedUsers().size() == 1 
                            && message.getMentionedUsers().get(0).getIdLong() == bot.getBotID())){
                    message.getChannel().sendMessage(CommandUtils.generateMentionedString(authorID) 
                            + "\t please mention a user").queue();
                    return;
                }
                if(!discordAutoConfigs.getMemberids().contains(message.getAuthor().getIdLong())) {
                    message.getChannel().sendMessage(CommandUtils.generateMentionedString(authorID) + "\t permission denied").queue();
                    return;
                }
                Date date = new Date();
                StringBuilder memtion = new StringBuilder();
                message.getMentionedMembers().forEach(member -> {
                    if(member.getIdLong() == bot.getBotID())
                        return;
                    if(discordAdminDataService.getDiscordAdminByGuildIdAndChannelIdAndMemberId(
                            message.getGuild().getId(), message.getChannel().getId(), member.getId()) != null)
                    {
                        memtion.append(member.getEffectiveName()).append(" ");
                        return;
                    }
                        
                    if(discordAdminDataService.saveDiscordAdmin(
                            new DiscordAdminDataDO(message.getGuild().getId(), message.getChannel().getId(), member.getId(),
                            member.getEffectiveName(), DiscordAdminType.ADMIN, date)
                    ) > 0){
                        memtion.append(member.getEffectiveName()).append(" ");
                    }
                });
                message.getChannel().sendMessage(memtion.toString() + "\t "+ "admin granted").queue();
                break;
            }
            case "eh-cancelgrant":{
                if(message.getMentionedUsers().isEmpty() 
                        || (message.getMentionedUsers().size() == 1 
                            && message.getMentionedUsers().get(0).getIdLong() == bot.getBotID())){
                    message.getChannel().sendMessage(CommandUtils.generateMentionedString(authorID) 
                            + "\t please mention a user").queue();
                    return;
                }
                if(!discordAutoConfigs.getMemberids().contains(message.getAuthor().getIdLong())) {
                    message.getChannel().sendMessage(CommandUtils.generateMentionedString(authorID) + "\t permission denied").queue();
                    return;
                }
                StringBuilder memtion = new StringBuilder();
                message.getMentionedMembers().forEach(member -> {
                    if(member.getIdLong() == bot.getBotID())
                        return;
                    if(discordAdminDataService.getDiscordAdminByGuildIdAndChannelIdAndMemberId(
                            message.getGuild().getId(), message.getChannel().getId(), member.getId()
                    ) == null)
                    {
                        memtion.append(member.getEffectiveName()).append(" ");
                        return;
                    }
                    if(discordAdminDataService.removeDiscordAdminByGuildIdAndChannelIdAndMemberId(
                            message.getGuild().getId(), message.getChannel().getId(), member.getId()) > 0){
                        memtion.append(member.getEffectiveName()).append(" ");
                    }
                });
                message.getChannel().sendMessage(memtion.toString() + "\t " + "admin removed").queue();
                break;
            }
            default: {
            
            }
        }
    }
    
}
