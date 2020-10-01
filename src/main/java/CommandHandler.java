import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * CommandHandler class.
 * Contains commands and the associated code with executing them
 * when a message is received.
 *
 * @author Tony Abou-Zeidan
 * @version Mar 4, 2020
 */
public class CommandHandler extends ListenerAdapter
{
    private HashMap<String, RubyCommand> validCommands;

    public CommandHandler()
    {
        validCommands = new HashMap<>();
    }

    public void addCommand(String name,RubyCommand command)
    {
        validCommands.put(name,command);
    }

    public HashMap<String, RubyCommand> getValidCommands()
    {
        return validCommands;
    }

    /**
     * Acts as the event listener for when the bot receives a message.
     * Checks if the message is a valid command, and executes the command.
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


        //HashMap for printing and storing current message information
        HashMap<String,String> messageInfo = new HashMap<>();
        messageInfo.put("Guild Name",guild.getName());
        messageInfo.put("Channel Name",channel.getName());
        messageInfo.put("Channel Type",channel.getType().name());
        messageInfo.put("Author String",author.getEffectiveName());
        messageInfo.put("Author Id",author.getId());
        messageInfo.put("Message",message.getContentRaw());
        System.out.println(messageInfo);

        String first_word = content.split(" ")[0];
        first_word = first_word.substring(BotInformation.BOT_PREFIX.length());

        //check if the command starts with the prefix
        if (content.startsWith(BotInformation.BOT_PREFIX))
        {
            //check if valid command
            if (validCommands.containsKey(first_word))
            {
                RubyCommand cmd = validCommands.get(first_word);
                System.out.println(cmd.getWord().getName().toUpperCase() + " command executed!");
                cmd.execute(message,channel,guild,author);
            }
            else
            {
                System.out.println("Not a valid command!");
            }
        }

    }
}
