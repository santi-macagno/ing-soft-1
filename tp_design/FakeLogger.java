import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements logger_interface {

    private List<String> messages = new ArrayList<>();

    @Override
    public void logWarning(String message) {
        messages.add("WARN: " + message);
    }

    @Override
    public void logError(String message) {
        messages.add("ERROR: " + message);
    }

    @Override
    public void logInfo(String message) {
        messages.add("INFO: " + message);
    }

    @Override
    public void logDebug(String message) {
        messages.add("DEBUG: " + message);
    }

    public List<String> getMessages() {
        return messages;
    }
}