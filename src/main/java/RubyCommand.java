//jda imports
import com.jagrosh.jdautilities.command.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * RubyCommand class. An abstract class containing the structure
 * for the functionality of all sub command classes.
 *
 * @author Tony Abou-Zeidan
 * @version Feb 29, 2020
 */
public abstract class RubyCommand {

    //associated command word
    protected CommandWord word;
    protected boolean canWrite;
    private CommandPermissionHandler handler = new CommandPermissionHandler();

    /**
     * Mutator for associated command word.
     *
     * @return The command word
     */
    public CommandWord getWord() {
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

    protected void setPermissions(Permission... permission) {
        handler.setPermissions(permission);
    }

    protected boolean checkPermissions(Member self, Member member) {
        return handler.checkPermissions(self,member);
    }

    protected boolean checkPermissions(GuildChannel channel,Member self, Member member) {
        return handler.checkPermissions(channel,self,member);
    }

    protected boolean checkPermission(Member member, Permission permission) {
        return handler.checkPermission(member,permission);
    }

    protected boolean checkPermission(GuildChannel channel, Member member, Permission permission) {
        return handler.checkPermission(channel,member,permission);
    }

    protected boolean canInteractTarget(Member self, Member member, Member target) {
        return handler.canInteractTarget(self,member,target);
    }



    /**
     * Sends an error message to the specified channel, along
     * with the syntax of the command being used.
     *
     * @param channel The channel to send the message to
     * @param message The error part of the message
     */
    protected void writeErrorMessage(TextChannel channel, String message) {
        writeMessage(channel, message + "```\nCommand: " + word.getName().toUpperCase() + " ~ Usage: " + word.getSyntax() + "```");
    }


    /**
     * Structure for the implementation of executing commands.
     *
     * @param msg     The message to process
     * @param channel The channel in which the message originated
     * @param guild   The guild in which the message originated
     * @param author  The author of the message
     */
    public abstract void execute(Message msg, TextChannel channel, Guild guild, Member author);
}

