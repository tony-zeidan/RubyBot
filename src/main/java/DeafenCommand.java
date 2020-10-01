import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * DeafenCommand class. This is extends the main command class,
 * and provides its own functionality for the DeafenCommand specifically.
 *
 * Syntax: prefix + deafen (@user)
 *
 * @author Tony Abou-Zeidan
 * @version Feb 28, 2020
 */
public class DeafenCommand extends RubyCommand {

    /**
     * Default constructor for objects of DeafenCommand.
     * Creates a new command object with a new command word.
     */
    public DeafenCommand() {
        super.word=new CommandWord("deafen","voice chat management","Server deafens a given user.",BotInformation.BOT_PREFIX+"deafen (@user)");
        super.permissionHandler.addPermissions(CommandDefinitions.VOICE_PERMISSIONS_BOT,new Permission[] {Permission.VOICE_DEAF_OTHERS});
        super.permissionHandler.addPermissions(CommandDefinitions.VOICE_PERMISSIONS_MEMBER,new Permission[] {Permission.VOICE_DEAF_OTHERS});
    }

    /**
     * Deafen implementation of execute.
     * Given a mentionable object in the string or no mention at all,
     * guild deafens the target member.
     *
     * @param msg The message to process
     * @param channel The channel in which the message originated
     * @param guild The guild in which the message originated
     * @param author The author of the message
     */
    public void execute(Message msg, TextChannel channel, Guild guild, Member author)
    {
        Member self = guild.getSelfMember();
        super.canWrite = self.hasPermission(channel,Permission.MESSAGE_WRITE);  //check if the bot can send messages

        String[] msgParts = msg.getContentRaw().split("\\s+");
        List<String> args = Arrays.asList(msgParts).subList(1,msgParts.length);

        //argument size should be 1
        if (args.size()==1) {

            //attempt to obtain the mentioned user
            List<Member> members = msg.getMentionedMembers();
            if (members.isEmpty()) {
                super.writeErrorMessage(channel,"You input an argument but it wasn't a mention to a member!");
                System.out.println("No user mentioned");
                return;
            }

            Member target = members.get(0);

            //get the voice channel the user is in
            VoiceChannel vc = target.getVoiceState().getChannel();

            //check if the user isn't in a voice channel
            if (vc==null) {
                    super.writeErrorMessage(channel,"The target member is not in a voice channel!");
                System.out.println("User is not in a voice channel");
                return;
            }

            //check if the author and bot have enough permissions to execute the command
            if (!super.checkPermissions(self,author,channel,vc)) return;

            //check the hierarchy to see if the bot and author can both interact with the target member
            if (!super.canTargetMember(self,author,target,channel)) return;

            //toggle the deafening of the target
            target.deafen(!target.getVoiceState().isGuildDeafened()).queue();
        } else {
            //send error message
            super.writeErrorMessage(channel,"You must have one and only one argument, a user!");
            System.out.println("Invalid argument length");
        }
    }


}
