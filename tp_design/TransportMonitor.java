import java.util.ArrayList;
import java.util.List;

public class TransportMonitor {
    private List<observer> observers;
    private TransportStrategy currentStrategy;
    private final logger log;
    
    public TransportMonitor(TransportStrategy initialStrategy) {
        this.currentStrategy = initialStrategy;
        this.observers = new ArrayList<>();
        this.log = logger.getInstance();
    }
    
    // Suscribir un observador
    public void subscribe(observer obs) {
        if (!observers.contains(obs)) {
            observers.add(obs);
            log.logDebug("Observer suscrito al monitor");
        }
    }
    
    // Desuscribir un observador
    public void unsubscribe(observer obs) {
        if (observers.remove(obs)) {
            log.logDebug("Observer desuscrito del monitor");
        }
    }
    
    // Cambiar la estrategia activa en tiempo de ejecución
    public void setActiveStrategy(TransportStrategy strategy) {
        this.currentStrategy = strategy;
        log.logInfo("Estrategia de transporte cambiada a: " + strategy.getName());
    }
    
    // Obtener la estrategia activa
    public TransportStrategy getActiveStrategy() {
        return currentStrategy;
    }
    
    // Ejecutar un ciclo de actualización
    public void updateCycle() {
        if (currentStrategy == null) {
            log.logError("No hay estrategia activa configurada");
            return;
        }
        
        // Obtener valores actuales de la estrategia
        String name = currentStrategy.getName();
        double cost = currentStrategy.getCost();
        double distance = currentStrategy.getDistance();
        int eta = currentStrategy.getETA();
        
        // Crear snapshot con los valores actuales
        TransportSnapshot snapshot = new TransportSnapshot(name, cost, distance, eta);
        
        // Notificar a todos los observadores
        notifyObservers(snapshot);
    }
    
    // Notificar a todos los observadores suscritos
    private void notifyObservers(TransportSnapshot snapshot) {
        for (observer obs : observers) {
            obs.update(snapshot);
        }
    }
    
    // Ejecutar múltiples ciclos
    public void runCycles(int numberOfCycles) {
        log.logInfo("Iniciando " + numberOfCycles + " ciclos de monitoreo");
        for (int i = 0; i < numberOfCycles; i++) {
            log.logDebug("--- Ciclo " + (i + 1) + " ---");
            updateCycle();
            try {
                Thread.sleep(500); // Pequeña pausa entre ciclos para legibilidad
            } catch (InterruptedException e) {
                log.logError("Ciclo interrumpido: " + e.getMessage());
            }
        }
        log.logInfo("Monitoreo finalizado");
    }
}
