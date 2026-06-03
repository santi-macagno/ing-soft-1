import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AlertObserverMockTest {

    @Test
    public void logsErrorWhenEtaExceedsThreshold() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(buffer));

            AlertObserver observer = new AlertObserver(80, 15);
            TransportSnapshot snapshot = new TransportSnapshot("T", 50.0, 5.0, 99);

            observer.update(snapshot);

            String output = buffer.toString();
            assertTrue(output.contains("[ERROR]"));
            assertTrue(output.contains("ETA excesivo"));
            assertFalse(output.contains("[WARN]"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void doesNotCallLoggerWhenBothConditionsFalse() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(buffer));

            AlertObserver observer = new AlertObserver(80, 15);
            TransportSnapshot snapshot = new TransportSnapshot("T", 10.0, 1.0, 5);

            observer.update(snapshot);

            assertTrue(buffer.toString().isBlank());
        } finally {
            System.setOut(originalOut);
        }
    }
}