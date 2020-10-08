import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.Arrays;
import java.util.List;

/**
 * RoleAssign class. This is extends the main command class,
 * and provides its own functionality for the RoleAssign specifically.
 *
 * Syntax: prefix + assignrole (@role) (@user)
 *
 * @author Tony Abou-Zeidan
 * @version Feb 27, 2020
 */
public class RoleAssignCommand extends RubyCommand {

    /**
     * Default constructor for objects of RoleAssignCommand.
     * Creates a new command object with a new command word.
     */
    public RoleAssignCommand() {
        super.word=new CommandWord("assignrole",CommandCategory.MEMBER_MANAGEMENT,"Assigns the mentioned role to the mentioned user.",BotInformation.BOT_PREFIX+"assignrole (@role) (@user)");
        super.setPermissions(Permission.MANAGE_ROLES);

        //super.permissionHandler.addPermissions(CommandDefinitions.GUILD_PERMISSIONS_BOT,new Permission[] {Permission.MANAGE_ROLES});
        //super.permissionHandler.addPermissions(CommandDefinitions.GUILD_PERMISSIONS_MEMBER,new Permission[] {Permission.MANAGE_ROLES});
    }

    /**
     * RoleAssign implementation of execute.
     * Given a mentioned role and member, apply the role
     * to the member if the member does not have the role already,
     * otherwise remove the role.
     *
     * @param msg The message to process
     * @param channel The channel in which the message originated
     * @param guild The guild in which the message originated
     * @param author The author of the message
     */
    public void execute(Message msg, TextChannel channel, Guild guild, Member author) {

        Member self = guild.getSelfMember();

        //check if the bot and author has sufficient permissions
        if (!super.checkPermissions(self,author)) return;
        super.canWrite = super.checkPermission(channel,self,Permission.MESSAGE_WRITE);

        String[] msgParts = msg.getContentRaw().split("\\s+");
        List<String> args = Arrays.asList(msgParts).subList(1, msgParts.length);
        List<IMentionable> mentions = msg.getMentions();

        if (args.size() == 2) {

            List<Member> members = msg.getMentionedMembers();
            List<Role> roles = msg.getMentionedRoles();

            if (members.isEmpty()||roles.isEmpty()) {
                super.writeErrorMessage(channel,"You either didn't mention a role, or a member.");
                System.out.println("Either no role or no member mentioned");
                return;
            }

            Member targetMember = members.get(0);
            Role targetRole = roles.get(0);

            //check if all permissions are met
            if (!super.canInteractTarget(self,author,targetMember)) return;

            if (!self.canInteract(targetRole)) {
                super.writeErrorMessage(channel,"The bot is unable to interact with the given role! (role >= bot)");
                System.out.println("Bot is unable to interact with role");
                return;
            }
            if (!author.canInteract(targetRole)) {
                super.writeErrorMessage(channel,"The author is unable to interact with the given role! (role >= author)");
                System.out.println("Author is unable to interact with role");
                return;
            }

            //check if the target already has the role
            if ((targetMember.getRoles().contains(targetRole))) {
                guild.removeRoleFromMember(targetMember, targetRole).queue();
            } else {
                guild.addRoleToMember(targetMember, targetRole).queue();
            }
        } else {
            super.writeErrorMessage(channel,"You must input two and only two arguments, a role and a member!");
            System.out.println("Invalid argument length given");
        }
    }
}
