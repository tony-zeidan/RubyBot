package ruby.command.meta;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class RExecutableCommand extends RCommand {


    private CommandPermissionHandler handler;
    private RCommandRunner runner;

    public RExecutableCommand(RCommandWord commandWord) {
        super(commandWord);
        handler = new CommandPermissionHandler();
        runner = null;
    }

    public void setRunner(RCommandRunner runner) {
        this.runner = runner;
    }

    protected void setPermissions(Permission... permission) {
        handler.setPermissions(permission);
    }

    protected boolean checkPermissions(Member self, Member member) {
        return handler.checkPermissions(self,member);
    }

    protected boolean checkPermissions(GuildChannel channel, Member self, Member member) {
        return handler.checkPermissions(channel,self,member);
    }

    protected boolean checkPermission(Member member, Permission permission) {
        return handler.checkPermission(member,permission);
    }

    protected boolean checkPermission(GuildChannel channel, Member member, Permission permission) {
        return handler.checkPermission(channel,member,permission);
    }

    protected boolean canInteractTarget(Member self, Member member, Member target) {
        return handler.canInteractTarget(self,member,target);
    }

    public void execute(Message msg);
}
