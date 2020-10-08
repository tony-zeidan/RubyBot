import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Arrays;
import java.util.List;

/**
 * BanCommand class. This is extends the main command class,
 * and provides its own functionality for the Ban specifically.
 *
 * Syntax: prefix + ban 'target user'
 *
 * @author Tony Abou-Zeidan
 * @version Feb 27, 2020 : Version 1.0.1
 */
public class BanCommand extends RubyCommand {

    /**
     * Default constructor for objects of BanCommand.
     * Creates a new command object with a new command word.
     */
    public BanCommand() {
        super.word=new CommandWord("ban",CommandCategory.MEMBER_MANAGEMENT,"Bans the specified member from the guild.",BotInformation.BOT_PREFIX+"ban 'target member'");
        super.setPermissions(Permission.BAN_MEMBERS);
    }

    /**
     * Ban implementation of execute.
     * Given one and only one member mention, attempt
     * to ban the member from the guild.
     *
     * @param msg The message to process
     * @param channel The channel in which the message originated
     * @param guild The guild in which the message originated
     * @param author The author of the message
     */
    public void execute(Message msg, TextChannel channel, Guild guild, Member author)
    {
        Member self = guild.getSelfMember();

        //check if the bot and author meet the permissions of this command
        if (!super.checkPermissions(self,author)) return;
        canWrite = super.checkPermission(channel,self,Permission.MESSAGE_WRITE);

        String[] msgParts = msg.getContentRaw().split("\\s+");
        List<String> args = Arrays.asList(msgParts).subList(1,msgParts.length);

        //should only be one argument
        if (args.size()==1) {

            List<Member> members = msg.getMentionedMembers();

            //no members were mentioned
            if (members.isEmpty()) {
                super.writeErrorMessage(channel,"You must have one single argument, which is a mention to a member");
                System.out.println("No mentioned members");
                return;
            }

            Member target = members.get(0);

            //check if the bot and author can interact with the target member
            if (!super.canInteractTarget(self,author,target)) return;

            //ban the target for 10 days
            guild.ban(target,10,author.getUser().getName() + " has dropped the ban hammer on you.").queue();

        } else {
            super.writeErrorMessage(channel,"You must input one and only one argument, a member!");
            System.out.println("Invalid argument length given");
        }
    }
}
