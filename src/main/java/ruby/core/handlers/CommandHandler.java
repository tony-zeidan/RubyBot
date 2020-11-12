package ruby.core.handlers;

import ruby.command.meta.RCommand;
import ruby.command.meta.RubyCommand;
import ruby.core.BotInformation;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ruby.core.debugging.BotListenerUI;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * ruby.core.handlers.CommandHandler class.
 * Contains commands and the associated code with executing them
 * when a message is received.
 *
 * @author Tony Abou-Zeidan
 * @version Mar 4, 2020
 */
public class CommandHandler extends ListenerAdapter
{
    private BotListenerUI ui;

    private HashMap<String, RCommand> validCommands;

    public CommandHandler()
    {
        ui = new BotListenerUI();
        validCommands = new HashMap<>();
    }

    public void addCommand(String name,RCommand command)
    {
        validCommands.put(name,command);
    }

    public HashMap<String, RCommand> getValidCommands()
    {
        return validCommands;
    }

    private RCommand getCommand(String text) {
        if (validCommands.containsKey(text)) return validCommands.get(text);
        for (String name : validCommands.keySet()) {
            RCommand rc = validCommands.get(name);
            if (rc.isAlias(text)) return rc;
        }
        return null;
    }

    /**
     * Acts as the event listener for when the bot receives a message.
     * Checks if the message is a valid ruby.command, and executes the ruby.command.
     *
     * @param event The event to be processed
     */
    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event)
    {
        Member author = event.getMember();
        if (author.getUser().isBot())
            return;

        //get event statistics
        Guild guild = event.getGuild();                     //guild of occurrence
        TextChannel channel = event.getChannel();           //channel of occurrence
        Message message = event.getMessage();               //message object
        String content = message.getContentDisplay();       //formatted content string
        Member self = guild.getSelfMember();                //the bot as a member of the guild

        //some messages have no string in them
        if (content.equals(""))
            return;

        String commandName = "Not a ruby.command";
        String[] splitText = content.toLowerCase().split("\\s+");
        String first_word = splitText[0];
        if (first_word.length() > BotInformation.BOT_PREFIX.length()) {

            first_word = first_word.substring(BotInformation.BOT_PREFIX.length());

            //check if the ruby.command starts with the prefix
            if (content.startsWith(BotInformation.BOT_PREFIX)) {
                //check if valid ruby.command
                RCommand cmd = getCommand(first_word);
                if (cmd!=null) {
                    commandName = cmd.getWord().getName().toUpperCase();
                    List<String> arguments = Arrays.asList(splitText).subList(1,splitText.length);
                    cmd.execute(arguments);
                } else {
                    System.out.println("Not a valid ruby command!");
                }
            }
        }

        //add message to the user interface
        ui.addMessage(new String[]{
                guild.getName(),
                channel.getName(),
                channel.getType().toString(),
                author.getEffectiveName(),
                author.getId(),
                message.getContentRaw(),
                commandName
        });

    }
}
