package ruby.command.meta;

import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class CalculateCommand extends RExecutableCommand {
    public CalculateCommand() {
        super(new RCommandWord("calculate")
                .setCategory(CommandCategory.GENERAL)
                .setDescription("A basic version of a calculator using the new method of sub commands")
                .setSyntax("calculate (add|div)")
        );
        RExecutableCommand add = new RExecutableCommand(new RCommandWord("add")
                .setDescription("Adds a sequence of numbers.")) {
            @Override
            public void execute(List<String> arguments) {
                try {
                    double result = applyOperationToList(arguments,'+');
                    System.out.println("Result: " + result);
                } catch (NumberFormatException e) {
                    System.out.println("Print error message");
                }
            }
        };
        RExecutableCommand divide = new RExecutableCommand(new RCommandWord("divide")
                .setDescription("Divides a sequence of numbers."))
        {
            @Override
            public void execute(List<String> arguments) {
                try {
                    double result = applyOperationToList(arguments,'/');
                    System.out.println("Result: " + result);
                } catch (NumberFormatException e) {
                    System.out.println("Print error message");
                }
            }
        };
        add.setAliases("addition","+");
        divide.setAliases("div","divideby","/");
        setAliases("evaluate","calc");
        addSubCommand(add);
        addSubCommand(divide);
    }

    private double applyOperationToList(List<String> arguments, char op) {
        double result = Double.parseDouble(arguments.get(0));
        for (int i = 1; i < arguments.size(); i ++) {
            double parsed = Double.parseDouble(arguments.get(i));
            if (op=='+') {
                result += parsed;
            } else if (op=='-') {
                result -= parsed;
            } else if (op=='*') {
                result *= parsed;
            } else if (op=='/') {
                result /= parsed;
            }
        }
        return result;
    }

    private List<Double> parseNumbers(List<String> arguments) {
        List<Double> numbers = new ArrayList<>();
        for (String arg : arguments) {
            numbers.add(Double.parseDouble(arg));
        }
        return numbers;
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
