package ruby.command.util;

import net.dv8tion.jda.api.entities.Message;

import java.util.Arrays;
import java.util.List;

public class CommandParser {

    public static List<String> getArguments(Message msg) {

        //split the message contents
        String[] msgParts = msg.getContentRaw().split("\\s+");

        //remove the prefix and command word
        return Arrays.asList(msgParts).subList(1,msgParts.length);
    }
}
