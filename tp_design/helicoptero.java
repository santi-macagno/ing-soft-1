import java.util.Random;

public class helicoptero implements TransportStrategy
{
    private String name;
    private Random random;
    
    public helicoptero() {
        this.name = "Helicóptero";
        this.random = new Random();
    }
    
    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public double getCost()
    {
        // Costo entre 50 y 120 dólares
        return 50 + (random.nextDouble() * 70);
    }

    @Override
    public double getDistance()
    {
        // Distancia entre 10 y 100 km
        return 10 + (random.nextDouble() * 90);
    }

    @Override
    public int getETA()
    {
        // ETA entre 2 y 8 minutos
        return 2 + random.nextInt(7);
    }
}