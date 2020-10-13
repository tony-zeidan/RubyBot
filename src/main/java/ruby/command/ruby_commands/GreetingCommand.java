package ruby.command.ruby_commands;

import ruby.command.meta.CommandCategory;
import ruby.command.meta.CommandWord;
import ruby.command.meta.RubyCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Arrays;
import java.util.List;

/**
 * ruby.command.ruby_commands.GreetingCommand class. This is extends the main ruby.command class,
 * and provides its own functionality for the Greeting specifically.
 *
 * Syntax: prefix + hello
 *
 * @author Tony Abou-Zeidan
 * @version Mar 2, 2020
 */
public class GreetingCommand extends RubyCommand {

    /**
     * Default constructor for objects of ruby.command.ruby_commands.GreetingCommand.
     * Creates a new ruby.command object with a new ruby.command word.
     */
    public GreetingCommand() {
        super.word=new CommandWord("greeting", CommandCategory.GENERAL,"Greets Ruby bot kindly.","hello");
    }

    /**
     * ruby.command.ruby_commands.GreetingCommand implementation of execute.
     * Simply return a greeting to the author.
     *
     * @param msg The message to process
     * @param channel The channel in which the message originated
     * @param guild The guild in which the message originated
     * @param author The author of the message
     */
    public void execute(Message msg, TextChannel channel, Guild guild, Member author) {

        Member self = guild.getSelfMember();

        //check if the author and bot have enough permissions to execute the ruby.command
        if (!super.checkPermission(channel,self,Permission.MESSAGE_WRITE)) return;

        String[] msgParts = msg.getContentRaw().split("\\s+");
        List<String> args = Arrays.asList(msgParts).subList(1,msgParts.length);

        //should be one argument only
        if (args.size()==0) {
            channel.sendMessage("Hi there " + author.getAsMention() + "!").queue();

        } else {
            writeErrorMessage(channel,"You must input no arguments!");
            System.out.println("Invalid argument length given");
        }
    }
}
