import java.util.HashMap;
import java.util.Map;

public abstract class RubyNestedCommand extends RubyCommand {

    private Map<String,CommandWord> subCommands = new HashMap<>();

    protected void addSubCommand(String name,CommandWord subCommand) {
        subCommands.put(name,subCommand);
    }
}
