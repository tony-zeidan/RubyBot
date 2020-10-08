import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * PurgeCommand class. This is extends the main command class,
 * and provides its own functionality for the PurgeCommand specifically.
 *
 * Syntax: prefix + purge 'amount of messages' OR prefix + purge (only 10 messages)
 *
 * @author Tony Abou-Zeidan
 * @version Feb 27, 2020 : Version 1.0.2
 */
public class PurgeCommand extends RubyCommand {

    /**
     * Default constructor for objects of PurgeCommand.
     * Creates a new command object with a new command word.
     */
    public PurgeCommand()
    {
        super.word=new CommandWord("purge",CommandCategory.TEXT_CHANNEL_MANAGEMENT,"Deletes a given amount of messages from chat",BotInformation.BOT_PREFIX+"purge (amount : optional (default=10))");
        super.setPermissions(Permission.MESSAGE_HISTORY,Permission.MESSAGE_MANAGE);
    }

    /**
     * Purge implementation of execute.
     * Given an integer argument, this command will delete the
     * past 'n' messages from the chat. If no argument is given
     * delete only the past 10 messages.
     *
     * @param msg The message to process
     * @param channel The channel in which the message originated
     * @param guild The guild in which the message originated
     * @param author The author of the message
     */
    public void execute(Message msg, TextChannel channel, Guild guild, Member author)
    {
        Member self = guild.getSelfMember();
        if (!super.checkPermissions(channel,self,author)) return;
        super.canWrite = self.hasPermission(channel,Permission.MESSAGE_WRITE);

        //check if the author has permissions


        String[] msgParts = msg.getContentRaw().split("\\s+");
        List<String> args = Arrays.asList(msgParts).subList(1,msgParts.length);
        int deleteAmount = 10;

        //exactly 1 integer arg
        if (args.size()==1) {
            //attempt to parse the integer
            try {
                deleteAmount = Integer.parseInt(args.get(0));
                if (deleteAmount>100) {
                    super.writeErrorMessage(channel,"You can't purge more than 100 messages!");
                    System.out.println("Attempted to purge 100+ messages");
                    return;
                }
            } catch(NumberFormatException e) {
                super.writeErrorMessage(channel,"You must input an integer argument!");
                System.out.println("Could not parse integer");
                return;
            }

        } else if (args.size()>1) {
            super.writeErrorMessage(channel,"You must input one and only one integer!");
            System.out.println("Too many args input");
            return;
        }
        //get message history
        List<Message> messageList;
        MessageHistory history = new MessageHistory(channel);
        messageList = history.retrievePast(deleteAmount).complete();

        //queue each message for deletion
        channel.deleteMessages(messageList).queue();

        super.writeMessage(channel,deleteAmount + " messages purged from chat.");
        history = new MessageHistory(channel);
        List<Message> last = history.retrievePast(1).complete();
        //channel.deleteMessages(last).delay(Duration.ofSeconds(5));

        System.out.println(deleteAmount + " messages purged from the chat.");
    }
}
