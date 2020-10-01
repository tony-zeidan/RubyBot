//jda imports
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

/**
 * This class provides the main functionality for the Ruby discord bot.
 *
 * Rework of bot main functionality.
 *
 * @author Tony Abou-Zeidan
 * @version Feb 28, 2020
 */
public class RubyMain {

    public static void main(String[] args)
    {
        try
        {
            //event waiter for jda-utilities
            EventWaiter ew = new EventWaiter();

            //create a command handler for the bot
            CommandHandler ch = new CommandHandler();


            ChatModerator cm = new ChatModerator();

            //add commands to the handler
            ch.addCommand("showlove", new ShowLoveCommand());
            ch.addCommand("gift", new GiftCommand());
            ch.addCommand("deafen",new DeafenCommand());
            ch.addCommand("mute",new MuteCommand());
            ch.addCommand("purge",new PurgeCommand());
            ch.addCommand("kick",new KickCommand());
            ch.addCommand("ban",new BanCommand());
            ch.addCommand("assignrole",new RoleAssignCommand());
            ch.addCommand("assignnick",new ChangeNicknameCommand());
            ch.addCommand("slowmode",new SlowModeCommand());
            ch.addCommand("list",new GuildListCommand(ew));
            ch.addCommand("help",new HelpCommand(ew,ch));
            ch.addCommand("hello",new GreetingCommand());

            //create the instance of our api with intents
            JDA api = JDABuilder.create(BotInformation.BOT_TOKEN,
                    GatewayIntent.GUILD_MESSAGE_REACTIONS,
                    GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_VOICE_STATES,
                    GatewayIntent.GUILD_BANS,
                    GatewayIntent.GUILD_PRESENCES,
                    GatewayIntent.GUILD_MESSAGES)
                    .addEventListeners(ch,ew,cm)
                    .setActivity(Activity.playing("Guild Management"))
                    .setStatus(OnlineStatus.DO_NOT_DISTURB)
                    .build();
        }
        catch (LoginException e)
        {
            System.out.println("Login failed");
        }
    }

}
