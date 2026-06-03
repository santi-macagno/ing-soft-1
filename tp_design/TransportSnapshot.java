import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransportSnapshot {
    private String name;
    private double cost;
    private double distance;
    private int ETA;
    private LocalDateTime timestamp;

    public TransportSnapshot(String name, double cost, double distance, int ETA) {
        this.name = name;
        this.cost = cost;
        this.distance = distance;
        this.ETA = ETA;
        this.timestamp = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }
    
    public double getCost() {
        return cost;
    }
    
    public double getDistance() {
        return distance;    
    }

    public int getETA() {
        return ETA;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return timestamp.format(formatter);
    }
}
