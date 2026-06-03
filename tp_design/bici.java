import java.util.Random;

public class bici implements TransportStrategy
{
    private String name;
    private Random random;
    
    public bici() {
        this.name = "Bicicleta";
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
        // Costo entre 1 y 3 dólares
        return 1 + (random.nextDouble() * 2);
    }

    @Override
    public double getDistance()
    {
        // Distancia entre 1 y 10 km
        return 1 + (random.nextDouble() * 9);
    }

    @Override
    public int getETA()
    {
        // ETA entre 10 y 40 minutos
        return 10 + random.nextInt(31);
    }
}