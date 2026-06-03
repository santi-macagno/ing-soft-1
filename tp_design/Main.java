import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Obtener la instancia del logger (Singleton)
        logger log = logger.getInstance();
        log.logInfo("========== APLICACIÓN DE TRANSPORTE INICIADA ==========");
        
        // Crear estrategias de transporte (Strategy)
        TransportStrategy taxi = new auto();
        TransportStrategy bicicleta = new bici();
        TransportStrategy helicoptero = new helicoptero();
        
        log.logDebug("Estrategias disponibles: Taxi, Bicicleta, Helicóptero");
        
        // Crear el monitor de transporte (Observer Pattern - Subject)
        TransportMonitor monitor = new TransportMonitor(taxi);
        log.logInfo("Monitor de transporte creado con estrategia inicial: " + taxi.getName());
        
        // Crear los observadores concretos (Observer Pattern - Observers)
        ConsolePrinter consolePrinter = new ConsolePrinter();
        AlertObserver alertObserver = new AlertObserver(30, 25); // Umbrales: costo > 30, ETA > 25 min
        
        // Suscribir los observadores al monitor
        monitor.subscribe(consolePrinter);
        monitor.subscribe(alertObserver);
        log.logDebug("Observadores suscritos al monitor");
        
        // Ejecutar los primeros 10 ciclos con la estrategia inicial
        log.logInfo("--- FASE 1: Monitoreo con Taxi ---");
        monitor.runCycles(10);
        
        // Permitir cambio de estrategia por consola
        log.logInfo("\n--- FASE 2: Cambio de Estrategia ---");
        boolean continuar = true;
        
        while (continuar) {
            log.logInfo("¿Deseas cambiar la estrategia? (1=Taxi, 2=Bicicleta, 3=Helicóptero, 0=Salir)");
            System.out.print("Selecciona una opción: ");
            
            int opcion = 0;
            try {
                opcion = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();
                log.logError("Opción inválida");
                continue;
            }
            
            switch (opcion) {
                case 1:
                    monitor.setActiveStrategy(taxi);
                    monitor.runCycles(10);
                    break;
                case 2:
                    monitor.setActiveStrategy(bicicleta);
                    monitor.runCycles(10);
                    break;
                case 3:
                    monitor.setActiveStrategy(helicoptero);
                    monitor.runCycles(10);
                    break;
                case 0:
                    continuar = false;
                    log.logInfo("========== APLICACIÓN FINALIZADA ==========");
                    break;
                default:
                    log.logWarning("Opción no válida");
            }
        }
        
        scanner.close();
    }
}
