import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TresholdAlertService_Test {
    private final TresholdAlertService test = new TresholdAlertService(25, 15);

    @Test
    void costUndeeTresholdReturnsFalse()
    {
        assertFalse(test.shouldAlertCost(24));
    }

    @Test
    void costEqualTresholdReturnsFalse()
    {
        assertFalse(test.shouldAlertCost(25)); //cuando es igual retorna falso
    }

    @Test
    void costOverTresholdReturnsTrue()
    {
        assertTrue(test.shouldAlertCost(26));
    }

    @Test
    void ETAUnderTresholdReturnsFalse()
    {
        assertFalse(test.shouldAlertETA(14));
    }

    @Test
    void ETAOverTresholdReturnsTrue()
    {
        assertTrue(test.shouldAlertETA(16));
    }
}