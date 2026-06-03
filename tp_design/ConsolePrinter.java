public class ConsolePrinter implements observer {
    private logger log;
    
    public ConsolePrinter() {
        this.log = logger.getInstance();
    }
    
    @Override
    public void update(TransportSnapshot snapshot) {
        log.logInfo("=== ESTADO DEL TRANSPORTE ===");
        log.logDebug("Timestamp: " + snapshot.getFormattedTimestamp());
        log.logDebug("Transporte: " + snapshot.getName());
        log.logDebug("Costo: $" + String.format("%.2f", snapshot.getCost()));
        log.logDebug("Distancia: " + String.format("%.2f", snapshot.getDistance()) + " km");
        log.logDebug("ETA: " + snapshot.getETA() + " minutos");
    }
}
