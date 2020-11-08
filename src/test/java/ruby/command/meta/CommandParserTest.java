package ruby.command.meta;

import org.junit.Test;
import ruby.command.util.CommandParser;

import java.util.List;

import static org.junit.Assert.*;

public class CommandParserTest {

    private CommandParser.Parser parser;

    @Test
    public void testParse() {
        parser = new CommandParser.Parser();
        boolean b = parser.parse("r/ronin example example");
        assertTrue(b);
    }

    @Test
    public void testArguments() {
        parser = new CommandParser.Parser();
        boolean b = parser.parse("r/ronin example tony");
        assertTrue(b);
        List<String> args = parser.getArguments();
        assertEquals(args.size(),parser.getArgumentNumber());
        assertEquals(args.get(0),"example");
        assertEquals(args.get(1),"tony");
        b = parser.parse("r/tony example");
        assertFalse(b);
        args=parser.getArguments();
        assertNull(args);
        assertEquals(0,parser.);
    }
}