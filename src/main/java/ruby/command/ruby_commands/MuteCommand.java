package ruby.command.ruby_commands;

import ruby.command.meta.CommandCategory;
import ruby.command.meta.CommandWord;
import ruby.command.meta.RubyCommand;
import ruby.core.BotInformation;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.Arrays;
import java.util.List;

/**
 * ruby.command.ruby_commands.MuteCommand class. This is extends the main ruby.command class,
 * and provides its own functionality for the ruby.command.ruby_commands.MuteCommand specifically.
 *
 * Syntax: prefix + mute (@user)
 *
 * @author Tony Abou-Zeidan
 * @version Feb 28, 2020
 */
public class MuteCommand extends RubyCommand {

    /**
     * Default constructor for objects of ruby.command.ruby_commands.MuteCommand.
     * Creates a new ruby.command object with a new ruby.command word.
     */
    public MuteCommand() {
        super.word=new CommandWord("mute", CommandCategory.VOICE_CHANNEL_MANAGEMENT,"Server mutes a given user.", BotInformation.BOT_PREFIX+"mute (@user)");
        super.setPermissions(Permission.VOICE_MUTE_OTHERS);
    }

    /**
     * Mute implementation of execute.
     * Given a mentionable object in the string or no mention at all,
     * guild mutes the target member.
     *
     * @param msg The message to process
     * @param channel The channel in which the message originated
     * @param guild The guild in which the message originated
     * @param author The author of the message
     */
    public void execute(Message msg, TextChannel channel, Guild guild, Member author) {

        Member self = guild.getSelfMember();

        super.canWrite = super.checkPermission(channel,self,Permission.MESSAGE_WRITE);

        String[] msgParts = msg.getContentRaw().split("\\s+");
        List<String> args = Arrays.asList(msgParts).subList(1,msgParts.length);

        //should be 1 argument only
        if (args.size()==1) {

            List<Member> members = msg.getMentionedMembers();

            if (members.size()==0) {    //no target specified but argument given
                super.writeErrorMessage(channel,"You had an argument but it wasn't a mention to a member!");
                System.out.println("No user mentioned");
                return;
            }

            Member target = members.get(0);
            VoiceChannel vc = target.getVoiceState().getChannel();

            //check if the target is in a voice channel
            if (vc == null) {
                super.writeErrorMessage(channel, "The target member isn't in a voice channel!");
                System.out.println("User is not in a voice channel");
                return;
            }

            //check if the author and bot have enough permissions to execute the ruby.command
            if (!super.checkPermissions(vc,self,author)) return;

            //check the hierarchy to see if the bot and author can both interact with the target member
            if (!super.canInteractTarget(self,author,target)) return;

            //toggle the muting on the target member
            target.mute(!target.getVoiceState().isGuildMuted()).queue();
        } else {
            //send error message
            super.writeErrorMessage(channel,"You must have one and only one argument, a user!");
            System.out.println("Invalid argument length");
        }
    }


}