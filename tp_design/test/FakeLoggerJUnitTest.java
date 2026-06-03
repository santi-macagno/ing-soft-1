import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class FakeLoggerJUnitTest {

    @Test
    public void recordsMessages() {
        FakeLogger logger = new FakeLogger();
        logger.logInfo("hi");
        logger.logWarning("w");
        logger.logError("error");
        logger.logDebug("d");

        List<String> msgs = logger.getMessages();
        assertEquals(4, msgs.size());
        assertTrue(msgs.contains("INFO: hi"));
        assertTrue(msgs.contains("WARN: w"));
        assertTrue(msgs.contains("ERROR: error"));
        assertTrue(msgs.contains("DEBUG: d"));
    }
}