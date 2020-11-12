package ruby.command.meta;

import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class TicTacToeCommand extends RExecutableCommand {
    public TicTacToeCommand() {
        super(new RCommandWord("tictactoe")
                .setCategory(CommandCategory.GENERAL)
                .setDescription("A basic version of tictactoe")
                .setSyntax("tictactoe")
        );
        RExecutableCommand challenge = new RExecutableCommand(new RCommandWord("challenge")
                .setDescription("Challenges a player to a game of tic tac toe")) {
            @Override
            public void execute(List<String> arguments) {

            }
        };
        addSubCommand(challenge);
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