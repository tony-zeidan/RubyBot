import net.dv8tion.jda.api.Permission;

import java.util.List;

public enum CommandCategory {
    GENERAL,
    GUILD_STATISTICS,
    TEXT_CHANNEL_MANAGEMENT,
    VOICE_CHANNEL_MANAGEMENT,
    MEMBER_MANAGEMENT,
    GUILD_MANAGEMENT,
    BOT_MANAGEMENT;

    private Permission[] basePermissions;

    private CommandCategory(Permission... basePermissions) {
        this.basePermissions = basePermissions;
    }
}
