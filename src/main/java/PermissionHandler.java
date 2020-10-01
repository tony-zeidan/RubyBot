import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;

import java.util.HashMap;

public class PermissionHandler implements CommandDefinitions {

    private HashMap<String,Permission[]> requiredPermissions;

    public PermissionHandler() {
        requiredPermissions = new HashMap<>();
        addPermissions(CommandDefinitions.GUILD_PERMISSIONS_BOT,null);
        addPermissions(CommandDefinitions.GUILD_PERMISSIONS_MEMBER,null);
        addPermissions(CommandDefinitions.TEXT_PERMISSIONS_BOT,null);
        addPermissions(CommandDefinitions.TEXT_PERMISSIONS_MEMBER,null);
        addPermissions(CommandDefinitions.VOICE_PERMISSIONS_BOT,null);
        addPermissions(CommandDefinitions.VOICE_PERMISSIONS_MEMBER,null);
    }

    public void addPermissions(String type, Permission[] permissions) {
        requiredPermissions.put(type,permissions);
    }

    public Permission[] getPermissions(String type) {
        return requiredPermissions.get(type);
    }

    public boolean checkChannelPermissions(Member target, GuildChannel channel, String name) {

        if (requiredPermissions.get(name) == null) return true;

        for (Permission perm : requiredPermissions.get(name)) {
            if (!target.hasPermission(channel,perm)) return false;
        }
        return true;
    }

    public boolean checkGuildPermissions(Member target,String name) {

        if (requiredPermissions.get(name) == null) return true;

        for (Permission perm : requiredPermissions.get(name)) {
            if (!target.hasPermission(perm)) return false;
        }
        return true;
    }
}
