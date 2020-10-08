//jda imports
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.internal.utils.PermissionUtil;

import java.util.Arrays;
import java.util.List;

/**
 * ShowLoveCommand class. This is extends the main command class,
 * and provides its own functionality for the ShowLoveCommand specifically.
 *
 * Syntax: prefix + showlove 'target audience' OR prefix + showlove
 *
 * @author Tony Abou-Zeidan
 * @version Feb 27, 2020 : Version 1.0.2
 */
public class ShowLoveCommand extends RubyCommand {

    /**
     * Default constructor for objects of ShowLoveCommand.
     * Creates a new command object with a new command word.
     */
    public ShowLoveCommand() {
        super.word=new CommandWord("showlove",CommandCategory.GENERAL,"A nice way to show some love to your audience!",BotInformation.BOT_PREFIX+"showlove (@user : optional)");
    }

    /**
     * ShowLove implementation of execute.
     * Given a mentionable object in the string or no mention at all,
     * generates a lovely message for the target mentionable.
     *
     * @param msg The message to process
     * @param channel The channel in which the message originated
     * @param guild The guild in which the message originated
     * @param author The author of the message
     */
    public void execute(Message msg, TextChannel channel, Guild guild, Member author) {
        //get the bots member status
        Member self = guild.getSelfMember();

        if (!super.checkPermission(channel,self,Permission.MESSAGE_WRITE)) return;

        String[] msgParts = msg.getContentRaw().split("\\s+");
        List<String> args = Arrays.asList(msgParts).subList(1,msgParts.length);

        if (args.size()==1) {

            //get the target audience or user and send message
            List<Member> members = msg.getMentionedMembers();

            if (members.size()==0) {
                super.writeErrorMessage(channel,"You had an argument but it wasn't a mention to a member!");
                System.out.println("No member mentioned");
                return;
            }
            channel.sendMessage("Hey " + members.get(0).getAsMention() + "! Hope you are having a wonderful day! :heart::heart::heart:").queue();

        } else if (args.size()==0) {
            if (!super.checkPermission(channel,author,Permission.MESSAGE_MENTION_EVERYONE)) return;
            channel.sendMessage( "Hey everyone! Hope you are all having a wonderful day! :heart::heart::heart:").queue();
        } else {
            super.writeErrorMessage(channel,"You must input none or one argument, a member!");
            System.out.println("Invalid argument length given");
        }
    }
}

