package ruby.command.meta;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

import java.util.List;

public abstract class RExecutableCommand extends RCommand {

    private CommandPermissionHandler handler;

    public RExecutableCommand(RCommandWord commandWord) {
        super(commandWord);
        handler = new CommandPermissionHandler();
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

    @Override
    public abstract void execute(List<String> arguments);
}