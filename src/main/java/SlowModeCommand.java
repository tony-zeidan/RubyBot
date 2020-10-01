import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.internal.managers.ChannelManagerImpl;

import java.util.Arrays;
import java.util.List;

/**
 * SlowMode class. This is extends the main command class,
 * and provides its own functionality for the SlowMode specifically.
 *
 * Syntax: prefix + slowmode (time)
 *
 * @author Tony Abou-Zeidan
 * @version Feb 27, 2020
 */
public class SlowModeCommand extends RubyCommand
{
    /**
     * Default constructor for objects of SlowMode.
     * Creates a new command object with a new command word.
     */
    public SlowModeCommand() {
        super.word=new CommandWord("slowmode","text channel management","Sets the slowmode status of the current text channel.",BotInformation.BOT_PREFIX+"slowmode (time)");
        super.permissionHandler.addPermissions(CommandDefinitions.TEXT_PERMISSIONS_BOT,new Permission[] {Permission.MESSAGE_MANAGE});
        super.permissionHandler.addPermissions(CommandDefinitions.TEXT_PERMISSIONS_MEMBER,new Permission[] {Permission.MESSAGE_MANAGE});
    }

    /**
     * SlowMode implementation of execute.
     * Given a single integer argument, set the current
     * slowmode status of the text channel accordingly.
     *
     * @param msg The message to process
     * @param channel The channel in which the message originated
     * @param guild The guild in which the message originated
     * @param author The author of the message
     */
    public void execute(Message msg, TextChannel channel, Guild guild, Member author) {

        Member self = guild.getSelfMember();

        if (!super.checkPermissions(self,author,channel,null)) return;
        super.canWrite = self.hasPermission(channel,Permission.MESSAGE_WRITE);

        String[] msgParts = msg.getContentRaw().split(" ");
        List<String> args = Arrays.asList(msgParts).subList(1, msgParts.length);

        //argument size should be 1
        if (args.size() == 1) {

            //attempt to parse the given integer and set the slowmode of the channel
            try {

                ChannelManagerImpl cm = new ChannelManagerImpl(channel);
                cm.setSlowmode(Integer.parseInt(args.get(0))).queue();

            } catch (NumberFormatException e) {         //not a number
                super.writeErrorMessage(channel,"You input an argument but it wasn't an integer!");
                System.out.println("Invalid integer argument");
            }

        } else {
            //too many arguments given
            super.writeErrorMessage(channel,"You must input one and only one argument, an integer!");
            System.out.println("Invalid argument length given");
        }


    }

}
