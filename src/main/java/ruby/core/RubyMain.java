package ruby.core;//jda imports
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import ruby.command.meta.CalculateCommand;
import ruby.command.meta.TicTacToeCommand;
import ruby.command.ruby_commands.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.GatewayIntent;
import javax.security.auth.login.LoginException;

import ruby.core.handlers.ChatModerator;
import ruby.core.handlers.CommandHandler;

//TODO: fix implementation of processing arguments in a ruby.command (new class)
//TODO: testing all commands in any scenarios

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

            //create a ruby.command handler for the bot
            CommandHandler ch = new CommandHandler();

            ChatModerator cm = new ChatModerator();

            //add commands to the handler
            ch.addCommand("calculate",new CalculateCommand());
            ch.addCommand("tictactoe",new TicTacToeCommand());

            //create the instance of our api with intents
            JDA api = JDABuilder.create(BotInformation.BOT_TOKEN,
                    GatewayIntent.GUILD_MESSAGE_REACTIONS,
                    GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_VOICE_STATES,
                    GatewayIntent.GUILD_BANS,
                    GatewayIntent.GUILD_PRESENCES,
                    GatewayIntent.GUILD_EMOJIS,
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
