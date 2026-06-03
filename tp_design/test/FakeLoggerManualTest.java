import java.util.List;

public class FakeLoggerManualTest {
    public static void main(String[] args) {
        FakeLogger logger = new FakeLogger();
        logger.logInfo("hi");
        logger.logWarning("tizzi");
        logger.logError("e");
        logger.logDebug("d");

        List<String> msgs = logger.getMessages();
        System.out.println("Messages count: " + msgs.size());
        for (String m : msgs) System.out.println(m);

        if (msgs.size() == 4 && msgs.contains("INFO: hi") && msgs.contains("WARN: tizzi") && msgs.contains("ERROR: e") && msgs.contains("DEBUG: d")) {
            System.out.println("FakeLogger test: OK");
            System.exit(0);
        } else {
            System.out.println("FakeLogger test: FAILED");
            System.exit(2);
        }
    }
}