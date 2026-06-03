public class AlertObserver implements observer {
    private logger log;
    private double costThreshold;
    private int etaThreshold;
    
    public AlertObserver(double costThreshold, int etaThreshold) {
        this.log = logger.getInstance();
        this.costThreshold = costThreshold;
        this.etaThreshold = etaThreshold;
    }
    
    @Override
    public void update(TransportSnapshot snapshot) {
        // Verificar si el costo supera el umbral
        if (snapshot.getCost() > costThreshold) {
            log.logWarning("ALERTA: Costo excesivo - $" + String.format("%.2f", snapshot.getCost()) + 
                          " (límite: $" + costThreshold + ")");
        }
        
        // Verificar si el ETA supera el umbral
        if (snapshot.getETA() > etaThreshold) {
            log.logError("ALERTA CRÍTICA: ETA excesivo - " + snapshot.getETA() + 
                        " minutos (límite: " + etaThreshold + " minutos)");
        }
    }
}
