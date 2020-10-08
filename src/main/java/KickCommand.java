import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Arrays;
import java.util.List;

/**
 * KickCommand class. This is extends the main command class,
 * and provides its own functionality for the Kick specifically.
 *
 * Syntax: prefix + kick 'target user'
 *
 * @author Tony Abou-Zeidan
 * @version Feb 27, 2020 : Version 1.0.1
 */
public class KickCommand extends RubyCommand {

    /**
     * Default constructor for objects of KickCommand.
     * Creates a new command object with a new command word.
     */
    public KickCommand() {
        super.word=new CommandWord("kick",CommandCategory.MEMBER_MANAGEMENT,"Kicks the specified member from the guild.",BotInformation.BOT_PREFIX+"kick (@user)");
        super.permissionHandler.addPermissions(CommandDefinitions.GUILD_PERMISSIONS_BOT,new Permission[] {Permission.KICK_MEMBERS});
        super.permissionHandler.addPermissions(CommandDefinitions.GUILD_PERMISSIONS_MEMBER,new Permission[] {Permission.KICK_MEMBERS});
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
        if (!super.checkPermissions(self,author,null,null)) return;
        super.canWrite = self.hasPermission(channel,Permission.MESSAGE_WRITE);

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
            if (!super.canTargetMember(self,author,target,channel)) return;

            //kick the member
            guild.kick(target,author.getUser().getName() + " has given you a kick.").queue();

        } else {
            super.writeErrorMessage(channel,"You must have one and only one argument, a member!");
            System.out.println("Invalid argument length given");
        }
    }
}
