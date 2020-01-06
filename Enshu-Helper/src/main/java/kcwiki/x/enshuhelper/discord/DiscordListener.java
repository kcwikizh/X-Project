/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.enshuhelper.discord;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 *
 * @author iHaru
 * https://github.com/DV8FromTheWorld/JDA/blob/master/src/examples/java/MessageListenerExample.java 
 */
public class DiscordListener extends ListenerAdapter {
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        //Event specific information
        User author = event.getAuthor();                //The user that sent the message
        boolean bot = author.isBot();                    //This boolean is useful to determine if the User that
                                                        // sent the Message is a BOT or not!
        if (bot) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();           //The message that was received.
//        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
                                                        //  This could be a TextChannel, PrivateChannel, or Group!
        long selfID = event.getJDA().getSelfUser().getIdLong();
        if(!message.getMentionedUsers().isEmpty()) {
            if(message.getMentionedUsers().get(0).isBot())
                return;
        }
            
        
        
        String content = message.getContentRaw(); 
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)

        String msg = message.getContentDisplay();              //This returns a human readable version of the Message. Similar to
                                                        // what you would see in the client.

        
        if (event.isFromType(ChannelType.TEXT))         //If this message was sent to a Guild TextChannel
        {
            //Because we now know that this message was sent in a Guild, we can do guild specific things
            // Note, if you don't check the ChannelType before using these methods, they might return null due
            // the message possibly not being from a Guild!

            Guild guild = event.getGuild();             //The Guild that this message was sent in. (note, in the API, Guilds are Servers)
            TextChannel textChannel = event.getTextChannel(); //The TextChannel that this message was sent to.
            Member member = event.getMember();          //This Member that sent the message. Contains Guild specific information about the User!

            String name;
            if (message.isWebhookMessage())
            {
                name = author.getName();                //If this is a Webhook message, then there is no Member associated
            }                                           // with the User, thus we default to the author for name.
            else
            {
                name = member.getEffectiveName();       //This will either use the Member's nickname if they have one,
            }                                           // otherwise it will default to their username. (User#getName())

            System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, msg);
            if (content.equals("!ping"))
            {
                message.getAuthor().openPrivateChannel().queue(channel -> { // this is a lambda expression
                    // the channel is the successful response
                    channel.sendMessage("onTextChannel").queue();
                });
//                try {
//                    message.getAuthor().openPrivateChannel().complete()
//                        .sendMessage("onPrivateChannel").queue();
//                  } catch (ErrorResponseException ignored) {
//                      System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, msg);
//                  }
                message.getChannel();
    //            message.getChannel().getName();
    //            message.getGuild().getMembers().stream()
    //                .map(Member::getUser)
    //                .forEach(user -> sendMessage(user, "ZDAROVA"));
                MessageChannel channel = event.getChannel();
                channel.sendMessage("<@"+message.getAuthor().getId()+"> \tpong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
            }
        }
        else if (event.isFromType(ChannelType.PRIVATE)) //If this message was sent to a PrivateChannel
        {
            //The message was sent in a PrivateChannel.
            //In this example we don't directly use the privateChannel, however, be sure, there are uses for it!
            PrivateChannel privateChannel = event.getPrivateChannel();
            privateChannel.sendMessage("onPrivateChannel").queue();
            System.out.printf("[PRIV]<%s>: %s\n", author.getName(), msg);
        }

    }
    
    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        //Member author = event.getMember(); //User who sent message, member of guild
        User author = event.getAuthor();
        MessageChannel channel = event.getChannel();
        Message message = event.getMessage(); //Message recieved
        String msg = message.getContentDisplay().trim().toLowerCase(); // String readable content of message
        //Guild guild = event.getGuild(); //Get info about the server this message is recieved on
        //Long guildID = guild.getIdLong(); //guild unique id
        if (msg.length() > 0) {
//            if (privCmdList.isEmpty()) {
//                channel.sendMessage(i18n.localize(dbMan, channel, "error.commandlist_not_initiated")).queue();
//            } else {
//                for (String commandName : privCmdList.keySet()) {
//                    if (msg.startsWith(commandName)) {
//                        Integer cmdCount = commandName.length();
//                        String parameters = msg.substring(cmdCount);
//
//                        privCmdList.get(commandName).execute(author, channel, message, parameters, privCmdList);
//                    }
//
//                }
//            }
        }
    }

}