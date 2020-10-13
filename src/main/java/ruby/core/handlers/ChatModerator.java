package ruby.core.handlers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.channel.category.CategoryCreateEvent;
import net.dv8tion.jda.api.events.channel.category.CategoryDeleteEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateNSFWEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateAfkChannelEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateDescriptionEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateIconEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateNameEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildDeafenEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildMuteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Arrays;
import java.util.List;


/**
 * ruby.core.handlers.ChatModerator class.
 * Provides functionality for curse word blocking,
 * and guild notifications.
 *
 * @author Tony Abou-Zeidan
 * @version Mar 3, 2020
 */
public class ChatModerator extends ListenerAdapter {

    private static final String RUBY_CHANNEL = "ruby-log";

    private List<String> curseWords = Arrays.asList(
            "fuck","shit","ass","nigger","bitch","hell","fucking","fuckin","fuckin'",
            "bitchin'","bitching"
    );

    private TextChannel hasRubyChannel(Guild guild) {


        Member self = guild.getSelfMember();

        List<TextChannel> channels = guild.getTextChannels();
        for (TextChannel c : channels)
        {
            if (c.getName().equals(RUBY_CHANNEL)&&self.hasPermission(c,Permission.MESSAGE_WRITE))
                return c;
        }
        return null;
    }

    /**
     * When a guild member updates their nickname,
     * notify the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onGuildMemberUpdateNickname(@Nonnull GuildMemberUpdateNicknameEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = hasRubyChannel(guild);

        if (channel==null)
            return;

        Member changed = event.getMember();

        channel.sendMessage(changed.getUser().getName() + " changed nickname: " + event.getNewNickname()).queue();
    }

    /**
     * When a member is banned from the guild,
     * update the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onGuildBan(@Nonnull GuildBanEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = hasRubyChannel(guild);

        if (channel==null)
            return;

        channel.sendMessage("Player banned: " + event.getUser().getName()).queue();
    }

    /**
     * When a member from the guild has been unbanned,
     * update the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onGuildUnban(@Nonnull GuildUnbanEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = hasRubyChannel(guild);

        if (channel==null)
            return;

        channel.sendMessage("Player unbanned: " + event.getUser().getName()).queue();
    }

    /**
     * When the name of the guild has been altered,
     * update the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onGuildUpdateName(@Nonnull GuildUpdateNameEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = hasRubyChannel(guild);

        if (channel==null)
            return;

        channel.sendMessage("Guild name updated: " + event.getNewName()).queue();
    }

    /**
     * When a member of the guild has left,
     * update the ruby log channel.
     *
     * @param event The event to be processed
     * @deprecated
     */
    @Override
    public void onGuildMemberLeave(@Nonnull GuildMemberLeaveEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = hasRubyChannel(guild);

        if (channel==null)
            return;

        channel.sendMessage("Member left guild: " + event.getMember().getUser().getName()).queue();
    }

    /**
     * When a role is created in the guild,
     * update the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onRoleCreate(@Nonnull RoleCreateEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = hasRubyChannel(guild);

        if (channel==null)
            return;

        channel.sendMessage("New role created: " + event.getRole().getAsMention()).queue();
    }

    /**
     * When a role is deleted from the guild,
     * notify the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onRoleDelete(@Nonnull RoleDeleteEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = hasRubyChannel(guild);

        if (channel==null)
            return;

        channel.sendMessage("Role deleted: " + event.getRole().getName()).queue();
    }

    /**
     * When the description of the guild is changed,
     * notify the ruby log channel.
     *
      * @param event The event to be processed
     */
    @Override
    public void onGuildUpdateDescription(@Nonnull GuildUpdateDescriptionEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = hasRubyChannel(guild);

        if (channel==null)
            return;

        channel.sendMessage("Guild description updated: " + event.getNewDescription()).queue();
    }

    /**
     * When the NSFW status of a text channel is changed,
     * notify the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onTextChannelUpdateNSFW(@Nonnull TextChannelUpdateNSFWEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = hasRubyChannel(guild);

        if (channel==null)
            return;

        TextChannel txt = event.getChannel();
        channel.sendMessage(txt.getAsMention() + " has updated NSFW policy: " + !event.getOldNSFW()).queue();
    }

    /**
     * When a category is created in the guild,
     * notify the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onCategoryCreate(@Nonnull CategoryCreateEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = hasRubyChannel(guild);

        if (channel==null)
            return;

        channel.sendMessage("New category created: " + event.getCategory().getName()).queue();
    }

    /**
     * When a category is deleted from the guild,
     * notify the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onCategoryDelete(@Nonnull CategoryDeleteEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = hasRubyChannel(guild);

        if (channel==null)
            return;
        
        channel.sendMessage("Category deleted: " + event.getCategory().getName()).queue();
    }

    /**
     * When a voice channel is created in the guild,
     * notify the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onVoiceChannelCreate(@Nonnull VoiceChannelCreateEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = hasRubyChannel(guild);

        if (channel==null)
            return;

        channel.sendMessage("New voice channel created: " + event.getChannel().getName()).queue();
    }

    /**
     * When a voice channel is deleted from the guild,
     * notify the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onVoiceChannelDelete(@Nonnull VoiceChannelDeleteEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = hasRubyChannel(guild);

        if (channel==null)
            return;

        channel.sendMessage("Voice channel deleted: " + event.getChannel().getName()).queue();
    }

    /**
     * When a role is assigned to a member in the guild,
     * notify the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onGuildMemberRoleAdd(@Nonnull GuildMemberRoleAddEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = hasRubyChannel(guild);

        if (channel == null)
            return;

        List<Role> roles = event.getRoles();
        Member enroled = event.getMember();

        StringBuilder message = new StringBuilder();
        message.append(enroled.getAsMention()).append(" has been granted the role(s) of");
        for (Role r : roles)
            message.append(" ").append(r.getAsMention());
        channel.sendMessage(message.toString()).queue();
    }

    /**
     * When a role is revoked from a member in the guild,
     * notify the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onGuildMemberRoleRemove(@Nonnull GuildMemberRoleRemoveEvent event) {

        Guild guild = event.getGuild();
        Member self = guild.getSelfMember();
        TextChannel channel = hasRubyChannel(guild);

        if (channel == null)
            return;

        List<Role> roles = event.getRoles();
        Member enroled = event.getMember();

        StringBuilder message = new StringBuilder("");
        message.append(enroled.getAsMention() + " has had the role(s) of");
        for (Role r : roles)
            message.append(" ").append(r.getAsMention());
        channel.sendMessage(message + " revoked!").queue();

    }

    /**
     * When a member is guild deafened,
     * notify the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onGuildVoiceGuildDeafen(@Nonnull GuildVoiceGuildDeafenEvent event) {

        Guild guild = event.getGuild();
        Member self = guild.getSelfMember();
        TextChannel channel = hasRubyChannel(guild);

        if (channel == null)
            return;

        Member deafened = event.getMember();
        if (deafened.getVoiceState().isGuildDeafened())
            channel.sendMessage(deafened.getAsMention() + " has been deafened.").queue();
        else
            channel.sendMessage(deafened.getAsMention() + " has been undeafened.").queue();
    }

    /**
     * When a member is guild muted,
     * notify the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onGuildVoiceGuildMute(@Nonnull GuildVoiceGuildMuteEvent event) {

        Guild guild = event.getGuild();
        Member self = guild.getSelfMember();
        TextChannel channel = hasRubyChannel(guild);

        if (channel == null)
            return;

        Member muted = event.getMember();
        if (muted.getVoiceState().isGuildMuted())
            channel.sendMessage(muted.getAsMention() + " has been muted.").queue();
        else
            channel.sendMessage(muted.getAsMention() + " has been unmuted.").queue();
    }

    /**
     * When a text channel is created in the guild,
     * notify the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onTextChannelCreate(@Nonnull TextChannelCreateEvent event) {

        Guild guild = event.getGuild();
        Member self = guild.getSelfMember();
        TextChannel channel = hasRubyChannel(guild);

         if (channel == null)
             return;

         TextChannel created = event.getChannel();

         channel.sendMessage("New text channel created: " + created.getAsMention()).queue();
    }

    /**
     * When a text channel is deleted in the guild,
     * notify the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onTextChannelDelete(@Nonnull TextChannelDeleteEvent event) {

        Guild guild = event.getGuild();
        Member self = guild.getSelfMember();
        TextChannel channel = hasRubyChannel(guild);

        if (channel == null)
            return;

        TextChannel created = event.getChannel();

        channel.sendMessage("Text channel deleted: " + created.getAsMention()).queue();
    }

    /**
     * When the ruby bot joins a guild,
     * attempt to create the ruby log channel, and send a welcome message.
     *
     * @param event The event to be processed
     */
    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {

        Guild guild = event.getGuild();
        Member self = guild.getSelfMember();
        TextChannel channel = guild.getDefaultChannel();

        if (channel != null && !self.hasPermission(channel, Permission.MESSAGE_WRITE))
            return;

        if (self.hasPermission(Permission.MANAGE_CHANNEL))
            guild.createTextChannel(RUBY_CHANNEL).queue();

        channel.sendMessage("Hey everyone! I'm Ruby, a guild management bot. It's a pleasure to meet you all!").queue();
    }

    /**
     * When a new member joins the guild,
     * send a welcome message to the default channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {

        Guild guild = event.getGuild();
        Member self = guild.getSelfMember();
        TextChannel channel = guild.getDefaultChannel();

        if (channel != null &&!self.hasPermission(channel, Permission.MESSAGE_WRITE))
            return;

        Member joined = event.getMember();

        channel.sendMessage("Everyone! Help me welcome to " + joined.getAsMention() + " to the server!").queue();
    }

    /**
     * When the icon of the guild is changed,
     * notify the ruby log channel.
     *
     * @param event The event to be processed
     */
    @Override
    public void onGuildUpdateIcon(@Nonnull GuildUpdateIconEvent event) {

        Guild guild = event.getGuild();
        TextChannel channel = hasRubyChannel(guild);

        if (channel==null)
            return;

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Guild Icon Changed");
        eb.setThumbnail(event.getOldIconUrl());
        eb.setImage(event.getNewIconUrl());
        eb.setAuthor(guild.getSelfMember().getEffectiveName());
        eb.setColor(Color.RED);
        channel.sendMessage(eb.build()).queue();
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {

        Guild guild = event.getGuild();
        Member self = guild.getSelfMember();
        TextChannel channel = event.getChannel();


        boolean manage = self.hasPermission(channel,Permission.MESSAGE_MANAGE);
        //curse word filter code
        /*
        for (String curse : curseWords)
        {
            for (String s : content.split("\\s+")) {
                if (s.toLowerCase().equals(curse)) {
                    if (manage) {
                        message.delete().complete();
                    }
                    System.out.println("Cursing detected");
                    return;
                }
            }
        }*/
    }
}
