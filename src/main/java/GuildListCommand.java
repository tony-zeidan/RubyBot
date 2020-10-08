import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.Paginator;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.exceptions.PermissionException;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * GuildListCommand class. This is extends the main command class,
 * and provides its own functionality for the GuildList specifically.
 *
 * Syntax: prefix + list (members | channels | roles | guilds)
 *
 * @author Tony Abou-Zeidan
 * @version Feb 29, 2020
 */
public class GuildListCommand extends RubyNestedCommand {

    private final Paginator.Builder pb;
    /**
     * Default constructor for objects of GuildListCommand.
     * Creates a new command object with a new command word.
     */
    public GuildListCommand(EventWaiter ew) {

        super.word = new CommandWord("list", CommandCategory.GUILD_STATISTICS, "Provides a list of: members, channels", BotInformation.BOT_PREFIX + "list (members | channels | roles | guilds)");
        super.setPermissions(Permission.MESSAGE_WRITE,Permission.MESSAGE_ADD_REACTION);

        pb = new Paginator.Builder()
                .setColumns(1)
                .setItemsPerPage(7)
                .waitOnSinglePage(false)
                .setFinalAction(m -> {
                    try {
                        m.clearReactions().queue();
                    } catch (PermissionException ex) {
                        m.delete().queue();
                    }
                })
                .setEventWaiter(ew)
                .setTimeout(1, TimeUnit.MINUTES);
    }

    /**
     * GuidListCommand implementation of execute.
     * Given a valid string argument, list the corresponding
     * guild statistics/bot statistics.
     *
     * @param msg The message to process
     * @param channel The channel in which the message originated
     * @param guild The guild in which the message originated
     * @param author The author of the message
     */
    public void execute(Message msg, TextChannel channel, Guild guild, Member author) {
        Member self = guild.getSelfMember();

        //check if bot has sufficient permissions
        if (!super.checkPermissions(channel,self,author)) return;

        String[] msgParts = msg.getContentRaw().split("\\s+");
        List<String> args = Arrays.asList(msgParts).subList(1,msgParts.length);

        //exactly one string argument
        if (args.size()==1) {

            pb.clearItems();
            String toList = args.get(0).toLowerCase();

            if (toList.equals("members")) {                 //list the members in the guild

                List<Member> members = guild.getMembers();
                pb.setText("Members in " + guild.getName());

                for (Member m : members) {
                    String memberString = "```"+m.getEffectiveName() + " ~ " + m.getOnlineStatus().getKey() + " ~ " + m.getUser().getJDA().getPresence().getActivity().toString();
                    if (m.getUser().isBot()) {
                        memberString += " (BOT) ";
                    }
                    pb.addItems(memberString+"```");
                }

            } else if (toList.equals("channels")) {         //list the channels in the guild

                List<GuildChannel> channels = guild.getChannels();
                pb.setText("Channels in " + guild.getName());
                for (GuildChannel c : channels)
                {
                    String channelString = "```"+c.getName();
                    if (c.getType().equals(ChannelType.CATEGORY))
                        channelString += " ~ category";
                    pb.addItems(channelString+"```");
                }

            } else if (toList.equals("roles")) {            //list the roles in the guild

                List<Role> roles = guild.getRoles();
                pb.setText("Roles in " + guild.getName());

                for (Role r : roles) {
                    pb.addItems("```"+r.getName()+"```");
                }

            } else if (toList.equals("guilds")) {               //list guilds the bot is in

                pb.setText("Guilds " + self.getUser().getName() + " is connected to");
                msg.getJDA().getGuilds().stream()
                        .map(g -> "```"+g.getName()+" ~ "+g.getMembers().size()+" Members```")
                        .forEach(pb::addItems);

            } else {                                            //user chose an invalid listing operation

                super.writeErrorMessage(channel,"You did not input a valid statistic to list!");
                System.out.println("Not a valid thing to list");
                return;
            }
            Paginator p = pb
                    .setColor(Color.RED)
                    .setUsers(author.getUser())
                    .build();
            p.paginate(channel,1);

        } else {                    //too many args

            super.writeErrorMessage(channel,"You must give one and only one argument, the thing to list!");
            System.out.println("Invalid argument length given");
        }
    }

}
