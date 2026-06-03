import java.util.Random;

public class auto implements TransportStrategy
{
    private String name;
    private Random random;
    
    public auto() {
        this.name = "Taxi";
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
        // Costo entre 8 y 15 dólares
        return 8 + (random.nextDouble() * 7);
    }

    @Override
    public double getDistance()
    {
        // Distancia entre 5 y 20 km
        return 5 + (random.nextDouble() * 15);
    }

    @Override
    public int getETA()
    {
        // ETA entre 5 y 15 minutos
        return 5 + random.nextInt(11);
    }
}