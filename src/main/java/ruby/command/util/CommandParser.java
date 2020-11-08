package ruby.command.util;

import net.dv8tion.jda.api.entities.Message;
import ruby.core.BotInformation;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {

    private static int argumentNumber;
    private static String commandWord;
    private static List<String> arguments;

    public static class Parser {

        public static boolean parse(String contentRaw) {
            //String contentRaw = message.getContentRaw();
            argumentNumber = 0;
            commandWord = null;
            arguments = null;

            if (contentRaw==null) return false;
            String[] msgParts = contentRaw.split("\\s+");
            arguments = Arrays.asList(msgParts).subList(1,msgParts.length);
            commandWord = msgParts[0];
            argumentNumber = arguments.size();

            return false;
        }

        public static List<String> getArguments() {
            return arguments;
        }

        public static String getCommandWord() {return commandWord; }

        public static String getArgument(int index) { return arguments.get(index); }

        public static boolean hasArguments() { return (arguments==null||arguments.size()==0); }

        public static int getArgumentNumber() {
            return argumentNumber;
        }
    }
}
