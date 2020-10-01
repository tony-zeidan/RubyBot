//jda imports
import net.dv8tion.jda.api.entities.*;


/**
 * RubyCommand class. An abstract class containing the structure
 * for the functionality of all sub command classes.
 *
 * @author Tony Abou-Zeidan
 * @version Feb 29, 2020
 */
public abstract class RubyCommand implements CommandDefinitions {

    //associated command word
    protected CommandWord word;
    protected PermissionHandler permissionHandler = new PermissionHandler();
    protected boolean canWrite;

    /**
     * Checks all permission values for the author of the message and the bot in order
     * to determine whether the bot and author both have:
     *
     * - Sufficient guild (server) permissions
     * - Sufficient text channel permissions (according to the given text channel)
     * - Sufficient voice channel permissions (according to the given voice channel)
     *
     * @param bot The bot as a member of the guild
     * @param author The author of the message (command)
     * @param channel The text channel to check if the bot and author have permissions in
     * @param vc The voice channel to check if the bot and author have permissions in
     * @return Whether the permissions for the command are satisfied
     */
    protected boolean checkPermissions(Member bot, Member author, TextChannel channel, VoiceChannel vc) {

        //check if the bot and author have sufficient permissions in the current guild
        if (!permissionHandler.checkGuildPermissions(bot,CommandDefinitions.GUILD_PERMISSIONS_BOT)) {
            writeErrorMessage(channel,"The bot does not have permissions for this!");
            System.out.println("Insufficient guild permissions for the bot");
            return false;
        }

        if (!permissionHandler.checkGuildPermissions(author,CommandDefinitions.GUILD_PERMISSIONS_MEMBER)) {
            writeErrorMessage(channel,"The author does not have permissions for this!");
            System.out.println("Insufficient guild permissions for the member");
            return false;
        }

        //check if the author and bot have sufficient permissions for the text channel
        if (channel != null) {
            if (!permissionHandler.checkChannelPermissions(bot, channel, CommandDefinitions.TEXT_PERMISSIONS_BOT)) {
                writeErrorMessage(channel, "The bot does not have permissions for this!");
                System.out.println("Insufficient text channel permissions for the bot");
                return false;
            }

            if (!permissionHandler.checkChannelPermissions(author, channel, CommandDefinitions.TEXT_PERMISSIONS_MEMBER)) {
                writeErrorMessage(channel, "The author does not have permissions for this!");
                System.out.println("Insufficient text channel permissions for the author");
                return false;
            }
        }

        //check if the author and bot have sufficient permissions for the voice channel
        if (vc != null) {
            if (!permissionHandler.checkChannelPermissions(bot, channel, CommandDefinitions.VOICE_PERMISSIONS_BOT)) {
                writeErrorMessage(channel, "The bot does not have permissions for this!");
                System.out.println("Insufficient voice channel permissions for the bot");
                return false;
            }

            if (!permissionHandler.checkChannelPermissions(author, channel, CommandDefinitions.VOICE_PERMISSIONS_MEMBER)) {
                writeErrorMessage(channel, "The author does not have permissions for this!");
                System.out.println("Insufficient voice channel permissions for the author");
                return false;
            }
        }
        return true;
    }

    protected boolean canTargetMember(Member bot,Member author,Member target,TextChannel channel) {
        if (!bot.canInteract(target)) {
            writeErrorMessage(channel,"The bot can not interact with the target! (target >= bot)");
            System.out.println("Bot can not interact with member");
            return false;
        }

        if (!author.canInteract(target)) {
            writeErrorMessage(channel,"The author can not interact with the target! (target >= author)");
            System.out.println("Author can not interact with member");
            return false;
        }
        return true;
    }



    /**
     * Mutator for associated command word.
     *
     * @return The command word
     */
    public CommandWord getWord()
    {
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

    /**
     * Sends an error message to the specified channel, along
     * with the syntax of the command being used.
     *
     * @param channel The channel to send the message to
     * @param message The error part of the message
     */
    protected void writeErrorMessage(TextChannel channel, String message) {
        writeMessage(channel,message + "```\nCommand: " + word.getName().toUpperCase() + " ~ Usage: " + word.getSyntax()+"```");
    }

    /**
     * Structure for the implementation of executing commands.
     *
     * @param msg The message to process
     * @param channel The channel in which the message originated
     * @param guild The guild in which the message originated
     * @param author The author of the message
     */
    public abstract void execute(Message msg, TextChannel channel, Guild guild, Member author);
}
