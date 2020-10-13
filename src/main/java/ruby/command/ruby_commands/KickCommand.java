package ruby.command.ruby_commands;

import ruby.command.meta.CommandCategory;
import ruby.command.meta.CommandWord;
import ruby.command.meta.RubyCommand;
import ruby.core.BotInformation;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Arrays;
import java.util.List;

/**
 * ruby.command.ruby_commands.KickCommand class. This is extends the main ruby.command class,
 * and provides its own functionality for the Kick specifically.
 *
 * Syntax: prefix + kick 'target user'
 *
 * @author Tony Abou-Zeidan
 * @version Feb 27, 2020 : Version 1.0.1
 */
public class KickCommand extends RubyCommand {

    /**
     * Default constructor for objects of ruby.command.ruby_commands.KickCommand.
     * Creates a new ruby.command object with a new ruby.command word.
     */
    public KickCommand() {
        super.word=new CommandWord("kick", CommandCategory.MEMBER_MANAGEMENT,"Kicks the specified member from the guild.", BotInformation.BOT_PREFIX+"kick (@user)");
        super.setPermissions(Permission.KICK_MEMBERS);
    }

    /**
     * Kick implementation of execute.
     * Given one and only one member mention, attempt
     * to kick the member from the guild.
     *
     * @param msg The message to process
     * @param channel The channel in which the message originated
     * @param guild The guild in which the message originated
     * @param author The author of the message
     */
    public void execute(Message msg, TextChannel channel, Guild guild, Member author) {

        Member self = guild.getSelfMember();

        //check if the bot and author have sufficient permissions to execute
        if (!super.checkPermissions(self,author)) return;
        super.canWrite = super.checkPermission(channel,self,Permission.MESSAGE_WRITE);

        String[] msgParts = msg.getContentRaw().split("\\s+");
        List<String> args = Arrays.asList(msgParts).subList(1,msgParts.length);

        //should only have one mention argument
        if (args.size()==1) {

            List<Member> members = msg.getMentionedMembers();
            if (members.isEmpty()) {
                super.writeErrorMessage(channel,"You had an argument but it wasn't a mention to a member!");
                System.out.println("No mentioned members");
                return;
            }
            Member target = members.get(0);

            //check if the bot and author can interact with the target member
            if (!super.canInteractTarget(self,author,target)) return;

            //kick the member
            guild.kick(target,author.getUser().getName() + " has given you a kick.").queue();

        } else {
            super.writeErrorMessage(channel,"You must have one and only one argument, a member!");
            System.out.println("Invalid argument length given");
        }
    }
}
