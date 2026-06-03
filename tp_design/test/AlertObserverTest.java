import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AlertObserverTest {
    
    @Test
    public void shouldLogWhenCostExceedsThreshold() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(buffer));

            AlertObserver observer = new AlertObserver(30, 25);
            TransportSnapshot snapshot = new TransportSnapshot("T", 100.0, 0.0, 10);

            observer.update(snapshot);

            String output = buffer.toString();
            assertTrue(output.contains("[WARN]"));
            assertTrue(output.contains("Costo excesivo"));
            assertFalse(output.contains("[ERROR]"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void shouldNotLogWhenValuesAreBelowThresholds() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(buffer));

            AlertObserver observer = new AlertObserver(30, 25);
            TransportSnapshot snapshot = new TransportSnapshot("T", 10.0, 0.0, 10);

            observer.update(snapshot);

            assertTrue(buffer.toString().isBlank());
        } finally {
            System.setOut(originalOut);
        }
    }
}