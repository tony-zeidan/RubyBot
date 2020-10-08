import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.Paginator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.exceptions.PermissionException;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * HelpCommand class. This is extends the main command class,
 * and provides its own functionality for the Help specifically.
 *
 * Syntax: prefix + help (command name : optional)
 *
 * @author Tony Abou-Zeidan
 * @version Feb 29, 2020
 */
public class HelpCommand extends RubyCommand
{

    private final Paginator.Builder pb;
    private CommandHandler ch;
    /**
     * Default constructor for objects of HelpCommand.
     * Creates a new command object with a new command word.
     */
    public HelpCommand(EventWaiter ew,CommandHandler ch)
    {
        super.word=new CommandWord("help",CommandCategory.GENERAL,"Provides a list of commands and what they do.",BotInformation.BOT_PREFIX+"help (command name : optional)");
        this.ch = ch;
        pb = new Paginator.Builder()
            .setColumns(1)
            .setItemsPerPage(15)
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
     * HelpCommand implementation of execute.
     * If no string is given, generate a paginated message
     * containing all the commands and their descriptions.
     * If a string is given and it is valid, print a message
     * containing the name of the command, it's description
     * and it's syntax.
     *
     * @param msg The message to process
     * @param channel The channel in which the message originated
     * @param guild The guild in which the message originated
     * @param author The author of the message
     */
    public void execute(Message msg, TextChannel channel, Guild guild, Member author) {
        Member self = guild.getSelfMember();

        //check if bot has permissions
        if (!super.checkPermission(channel,self,Permission.MESSAGE_WRITE)) return;

        String[] msgParts = msg.getContentRaw().split("\\s+");
        List<String> args = Arrays.asList(msgParts).subList(1, msgParts.length);

        HashMap<String, RubyCommand> commands = ch.getValidCommands();

        pb.clearItems();

        //show all commands
        if (args.size()==0) {

            pb.setText("Ruby Commands:");
            for (String k : commands.keySet()) {
                RubyCommand com = commands.get(k);
                pb.addItems("**"+com.getWord().getName()+"**");
                pb.addItems("*"+com.getWord().getCategory()+"*");
                pb.addItems("```- "+com.getWord().getDescription()+"```");
            }

            Paginator p = pb
                    .setColor(Color.RED)
                    .setUsers(author.getUser())
                    .build();
            p.paginate(channel,1);

            //a specific command requires helping
        } else if (args.size()==1) {

            String target = args.get(0).toLowerCase();

            if (!commands.containsKey(target)) {
                super.writeErrorMessage(channel,"You did not input a valid command for help with!");
                System.out.println("Not a valid command");
                return;
            }
            CommandWord com = commands.get(target).getWord();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(com.getName());
            eb.setColor(Color.RED);
            eb.addField("Category","*"+com.getCategory()+"*",false);
            eb.addField("Description",com.getDescription(),false);
            eb.addField("Syntax","```"+com.getSyntax()+"```",false);
            eb.setFooter("",self.getUser().getEffectiveAvatarUrl());
            channel.sendMessage(eb.build()).queue();

            //argument length > 1
        } else {
            super.writeErrorMessage(channel,"You must input none or one arguments, a string!");
            System.out.println("Invalid argument length given");
        }

    }
}
