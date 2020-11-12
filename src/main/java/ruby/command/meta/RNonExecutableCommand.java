package ruby.command.meta;

public abstract class RNonExecutableCommand extends RCommand {
    public RNonExecutableCommand(RCommandWord word) {
        super(word);
    }

    public void execute(String content) {
        //write error message here
    }
}
