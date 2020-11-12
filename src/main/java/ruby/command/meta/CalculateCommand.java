package ruby.command.meta;

import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

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
        RCommand test = new RCommand(new RCommandWord("test"));
        add.setAliases("addition","+");
        divide.setAliases("div","divideby","/");
        setAliases("evaluate","calc");
        addSubCommand(test);
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

    private static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else if (func.equals("log")) x = Math.log(x);
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    @Override
    public void execute(List<String> arguments) {
        RCommand sub = findSubCommand(arguments);
        if (sub!=null) {
            System.out.println(sub.getWord().getName());
            sub.execute(arguments.subList(1,arguments.size()));
        } else {
            //join all other arguments together and attempt to solve
            //an equation that may or may not be correct
            String expr = String.join("",arguments);
            try {
                System.out.println(eval(expr));
            } catch (RuntimeException e) {
                System.out.println("Error occurred during evaluation");
            }
        }
    }
}
