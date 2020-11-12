package ruby.command.meta;

import net.dv8tion.jda.api.entities.Message;

public class DemoCommand extends RCommand {

    public DemoCommand() {
        super(new RCommandWord("Demo")
                .setDescription("Demo command")
                .setCategory(CommandCategory.GENERAL)
                .setSyntax("demo (whatever)"));
        RCommand show = new RCommand(new RCommandWord("show")
                .setDescription("run command")
                .setCategory(CommandCategory.GENERAL)
                .setSyntax("demo (whatever)"));
        RExecutableCommand list = new RExecutableCommand(new RCommandWord("list")
                .setDescription("run command")
                .setCategory(CommandCategory.GENERAL)
                .setSyntax("demo (whatever)"));
        list.setRunner(new RCommandRunner() {
            @Override
            public void execute() {
                "hello"
            }
        });

        this.addSubCommand(show);
    }

}
