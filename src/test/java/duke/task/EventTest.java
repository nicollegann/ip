package duke.task;

import duke.util.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @Test
    public void testStringConversion() {
        assertEquals("[E][ ] project meeting (at: 25 Jan 2022, 10:30 AM)",
                new Event("project meeting", Parser.parseDateTime("2022-01-25 10:30")).toString());
    }

    @Test
    public void getType() {
        assertEquals("E",
                new Event("project meeting", Parser.parseDateTime("2022-01-25 10:30")).getType());
    }
}
