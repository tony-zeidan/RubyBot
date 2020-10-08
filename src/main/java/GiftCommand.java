//jda imports
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
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
public class GiftCommand extends RubyCommand {

    private EmbedBuilder eb;

    /**
     * Default constructor for objects of ShowLoveCommand.
     * Creates a new command object with a new command word.
     */
    public GiftCommand()
    {
        super.word=new CommandWord("gift",CommandCategory.GENERAL,"A nice way to show some love to your audience as well!",BotInformation.BOT_PREFIX+"gift (@user)");
        super.permissionHandler.addPermissions(CommandDefinitions.TEXT_PERMISSIONS_BOT,new Permission[] {Permission.MESSAGE_WRITE,Permission.MESSAGE_MENTION_EVERYONE,Permission.MESSAGE_EMBED_LINKS});
        eb = new EmbedBuilder();
    }

    /**
     * Gift implementation of execute.
     * Given a mentionable object in the string or no mention at all,
     * generates a lovely message containing a GIF image (thumbnail) for the target mentionable.
     *
     * @param msg The message to process
     * @param channel The channel in which the message originated
     * @param guild The guild in which the message originated
     * @param author The author of the message
     */
    public void execute(Message msg, TextChannel channel, Guild guild, Member author)
    {
        //clear the embed builder from last time
        eb.clear();

        //get the bots member status
        Member self = guild.getSelfMember();

        if (!super.checkPermissions(self,author,channel,null)) return;
        super.canWrite = true;

        //split the message into the corresponding arguments
        String[] msgParts = msg.getContentRaw().split("\\s+");
        List<String> args = Arrays.asList(msgParts).subList(1,msgParts.length);

        if (args.size()==1) {
            List<Member> members = msg.getMentionedMembers();
            if (members.isEmpty()) {
                super.writeErrorMessage(channel,"You had an argument but it wasn't a mention to a member!");
                System.out.println("No user mentioned, but args > 0");
                return;
            }
            Member target = members.get(0);
            eb.setAuthor(author.getEffectiveName());
            eb.setColor(Color.RED);
            eb.setImage("https://thumbs.gfycat.com/BelatedRespectfulArachnid-size_restricted.gif");
            eb.addField("Gift:","A gift to " + (target).getAsMention() + "!",false);
            eb.setThumbnail((target.getUser().getEffectiveAvatarUrl()));
            eb.setFooter("A Ruby may not last forever.",author.getUser().getEffectiveAvatarUrl());
            channel.sendMessage(eb.build()).queue();

        }
        //attempt to mention everyone in the message if no user is specified
        else if (args.size()==0) {
            eb.setAuthor(author.getEffectiveName());
            eb.setColor(Color.RED);
            eb.setImage("https://thumbs.gfycat.com/BelatedRespectfulArachnid-size_restricted.gif");
            eb.addField("Gift:","A gift to everyone!",false);
            eb.setThumbnail(guild.getIconUrl());
            eb.setFooter("A Ruby may not last forever.",author.getUser().getEffectiveAvatarUrl());
            channel.sendMessage(eb.build()).queue();
        }
        //too many args
        else {
            super.writeErrorMessage(channel,"You input too many arguments!");
            System.out.println("Invalid argument length given");
        }
    }
}

