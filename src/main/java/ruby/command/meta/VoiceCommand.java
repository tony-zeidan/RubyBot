package ruby.command.meta;

import java.util.List;

public class VoiceCommand extends RExecutableCommand {

    public VoiceCommand() {
        super(new RCommandWord("voice")
        .setDescription("Used to manage guild members in voice chat."));
    }

    @Override
    public void execute(List<String> arguments) {
        RCommand sub = findSubCommand(arguments);
        if (sub!=null) {
            System.out.println(sub.getWord().getName());
            sub.execute(arguments.subList(1,arguments.size()));
        } else {
            System.out.println("Do the main command");
        }
    }
}
