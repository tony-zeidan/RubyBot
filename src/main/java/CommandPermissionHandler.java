import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.internal.utils.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

public class CommandPermissionHandler {

    private Permission[] permissions;

    public CommandPermissionHandler() {
        permissions = null;
    }

    public void setPermissions(Permission... permissions) {
        this.permissions = permissions;
    }

    public boolean checkPermissions(Member self, Member member) {
        return PermissionUtil.checkPermission(self,permissions) && PermissionUtil.checkPermission(member,permissions);
    }

    public boolean checkPermissions(GuildChannel channel, Member self, Member member) {
        return PermissionUtil.checkPermission(channel,self,permissions) && PermissionUtil.checkPermission(channel,member,permissions);
    }

    public boolean canInteractTarget(Member self, Member member, Member target) {
        return PermissionUtil.canInteract(self,target) && PermissionUtil.canInteract(member,target);
    }

    public boolean checkPermission(Member member, Permission permission) {
        return PermissionUtil.checkPermission(member,permission);
    }

    public boolean checkPermission(GuildChannel channel, Member member, Permission permission) {
        return PermissionUtil.checkPermission(channel,member,permission);
    }
}
