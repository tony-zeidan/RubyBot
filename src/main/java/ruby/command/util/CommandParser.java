package ruby.command.util;

import net.dv8tion.jda.api.entities.Message;

import java.util.Arrays;
import java.util.List;

/**
 * CommandParser class provides functions to aid in parsing the information found in a valid command,
 * when it is input into discord.
 */
public class CommandParser {

    /**
     * Retrieve the arguments found within a message representing a valid command.
     *
     * @param msg The message that contains the valid message
     * @return The list containing the arguments found within the message
     */
    public static List<String> getArguments(Message msg) {

        //split the message contents
        String[] msgParts = msg.getContentRaw().split("\\s+");

        //remove the prefix and command word
        return Arrays.asList(msgParts).subList(1,msgParts.length);
    }
}
