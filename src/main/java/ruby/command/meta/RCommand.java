package ruby.command.meta;//jda imports
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.*;


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
    private List<String> aliases;

    public RCommand(RCommandWord word) {
        this.word=word;
        subCommands = new HashMap<>();
        aliases = new ArrayList<>();
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

    protected boolean isSubCommand(String text) {
        if (subCommands.containsKey(text)) {
            return true;
        } else {
            for (RCommand rc : subCommands.values()) {
                if (rc.isAlias(text)) return true;
            }
            return false;
        }
    }

    protected void setAliases(String... aliases) {
        this.aliases = Arrays.asList(aliases);
    }

    public boolean isAlias(String text) {
        return aliases.contains(text);
    }

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

    protected RCommand findSubCommand(List<String> arguments) {
        return findSubRec(null,arguments);
    }

    private RCommand findSubRec(RCommand previous,List<String> arguments) {
        if (arguments==null||arguments.isEmpty()) return previous;
        String subString = arguments.get(0);
        if (isSubCommand(subString)) {
            return findSubRec(subCommands.get(subString),arguments.subList(1,arguments.size()));
        }
        return previous;
    }

    public void execute(List<String> arguments) {
        System.out.println("This command can not be executed!");
    }
}
