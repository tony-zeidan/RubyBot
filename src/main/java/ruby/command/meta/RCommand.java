package ruby.command.meta;//jda imports
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.HashMap;
import java.util.Map;


/**
 * ruby.command.meta.RubyCommand class. An abstract class containing the structure
 * for the functionality of all sub ruby.command classes.
 *
 * @author Tony Abou-Zeidan
 * @version Feb 29, 2020
 */
public abstract class RCommand {

    //associated ruby.command word
    protected RCommandWord word;
    protected boolean canWrite;
    private Map<String,RCommand> subCommands;

    public RCommand(RCommandWord word) {
        this.word=word;
    }

    /**
     * Mutator for associated ruby.command word.
     *
     * @return The ruby.command word
     */
    public RCommandWord getWord() {
        return word;
    }

    /**
     * Write a message to the specified channel.
     *
     * @param channel The channel to send the message to
     * @param message The message to write
     */
    protected void writeMessage(TextChannel channel, String message) {
        if (canWrite)
            channel.sendMessage(message).queue();
    }

    protected void addSubCommand(RCommand command) { subCommands.put(command.getWord().getName(),command); }

    /**
     * Sends an error message to the specified channel, along
     * with the syntax of the ruby.command being used.
     *
     * @param channel The channel to send the message to
     * @param message The error part of the message
     */
    protected void writeErrorMessage(TextChannel channel, String message) {
        writeMessage(channel, message + "```\nCommand: " + word.getName().toUpperCase() + " ~ Usage: " + word.getSyntax() + "```");
    }

    public abstract void execute(Message msg);
}
