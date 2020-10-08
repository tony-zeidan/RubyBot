import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ChangeNicknameCommand class. This is extends the main command class,
 * and provides its own functionality for the ChangeNickname specifically.
 *
 * Syntax: prefix + assignnick (@user) (nickname)
 *
 * @author Tony Abou-Zeidan
 * @version Feb 27, 2020
 */
public class ChangeNicknameCommand extends RubyCommand {

    private static final Pattern CHOICE = Pattern.compile("<@!(\\d+)>((\\s+.+)+)?");

    /**
     * Default constructor for objects of ChangeNicknameCommand.
     * Creates a new command object with a new command word.
     */
    public ChangeNicknameCommand() {
        super.word=new CommandWord("assignnick",CommandCategory.MEMBER_MANAGEMENT,"Assigns the nickname to the mentioned user.",BotInformation.BOT_PREFIX+"assignnick (@user) (nickname)");
    }

    /**
     * ChangeNickname implementation of execute.
     * Given a mentioned member and a proceeding string,
     * attempt to change the members nickname to that string.
     *
     * @param msg The message to process
     * @param channel The channel in which the message originated
     * @param guild The guild in which the message originated
     * @param author The author of the message
     */
    public void execute(Message msg, TextChannel channel, Guild guild, Member author) {
        Member self = guild.getSelfMember();

        //check if the bot and author have sufficient permissions for this command
        if (!super.checkPermissions(self,author,null,null)) return;
        super.canWrite = self.hasPermission(channel,Permission.MESSAGE_WRITE);

        //check if the regex pattern matches
        Matcher m = CHOICE.matcher(msg.getContentRaw().substring(13));
        if (m.matches()) {

            //get the target member from the matcher
            Member target = guild.getMemberById(m.group(1));

            if (target==null) return;

            boolean isSelf = (target.equals(self));
            boolean isAuthor = (target.equals(author));

            /*TODO:
            fix the problem with the bot regocnizing a normal case as a special case instead.
             */

            //special case: the target to change is the bot or the author
            if (!((target.equals(self))&&self.hasPermission(Permission.NICKNAME_CHANGE))) {
                super.writeErrorMessage(channel,"The bot does not have enough permissions for that!");
                System.out.println("The target to nickname change was the bot and the bot does not have permissions");
                return;
            } else if (!((target.equals(author))&&author.hasPermission(Permission.NICKNAME_CHANGE))) {
                super.writeErrorMessage(channel,"The author does not have enough permissions for that!");
                System.out.println("The target to nickname change was the author and the author does not have permissions");
                return;
            }

            //check if the matcher had one or two matches
            if (m.groupCount()>=2) {

                target.modifyNickname(m.group(3)).queue();
            } else {
                target.modifyNickname(target.getUser().getName()).queue();
            }
        } else {
            //send error message
            super.writeErrorMessage(channel,"You must have more than two arguments, a member and a string!");
            System.out.println("Invalid argument length given");
        }
    }
}
