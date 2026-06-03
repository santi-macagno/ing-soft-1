# TP Design: Singleton, Strategy y Observer

## Objetivo

Este trabajo práctico demuestra tres patrones de diseño aplicados a un sistema simple de monitoreo de transporte:

- **Singleton** para centralizar el logger.
- **Strategy** para intercambiar medios de transporte en tiempo de ejecución.
- **Observer** para notificar a distintos consumidores del estado del sistema sin acoplarlos al sujeto.

La evidencia principal está en las clases [`logger.java`](logger.java), [`TransportStrategy.java`](TransportStrategy.java), [`auto.java`](auto.java), [`bici.java`](bici.java), [`helicoptero.java`](helicoptero.java), [`TransportMonitor.java`](TransportMonitor.java), [`observer.java`](observer.java), [`ConsolePrinter.java`](ConsolePrinter.java), [`AlertObserver.java`](AlertObserver.java) y [`Main.java`](Main.java).

## Evidencia del diseño

### Singleton

La clase [`logger.java`](logger.java) usa una instancia estática privada y un constructor privado. El acceso se hace por `getInstance()`, que devuelve siempre la misma referencia. En [`Main.java`](Main.java) y en los observers se reutiliza esa misma instancia para registrar mensajes.

### Strategy

La interfaz [`TransportStrategy.java`](TransportStrategy.java) define el contrato común: `getName()`, `getCost()`, `getDistance()` y `getETA()`. Las clases [`auto.java`](auto.java), [`bici.java`](bici.java) y [`helicoptero.java`](helicoptero.java) implementan ese contrato con valores distintos. En [`Main.java`](Main.java), [`TransportMonitor.java`](TransportMonitor.java) cambia la estrategia activa en tiempo de ejecución sin modificar la lógica del monitor.

### Observer

La interfaz [`observer.java`](observer.java) define `update(TransportSnapshot snapshot)`. [`TransportMonitor.java`](TransportMonitor.java) mantiene una lista de observadores, crea un [`TransportSnapshot`](TransportSnapshot.java) en cada ciclo y luego llama a `update()` sobre cada suscriptor. [`ConsolePrinter.java`](ConsolePrinter.java) y [`AlertObserver.java`](AlertObserver.java) reaccionan de forma distinta ante el mismo snapshot.


### Singleton: por qué un logger global es un buen candidato

Un logger global suele ser buen candidato para Singleton porque normalmente se quiere una sola fuente de verdad para el registro de eventos: mismo formato, misma configuración, misma salida y acceso simple desde cualquier parte del programa. eso se ve en [`logger.java`](logger.java), donde toda la aplicación llama a la misma instancia mediante `getInstance()`.

El problema en aplicaciones multihilo es que la implementación actual no es segura frente a concurrencia. Si dos hilos llaman `getInstance()` al mismo tiempo, podrían crear más de una instancia. Además, escribir en consola desde varios hilos puede mezclar mensajes o producir salidas difíciles de leer.

La solución sería hacer la inicialización segura, por ejemplo con inicialización estática, un Singleton basado en enum, o sincronización/lazy initialization correcta. Si además el logger se usa concurrentemente, también conviene proteger la escritura o delegarla a una cola/buffer interno.

### Strategy: qué habría que hacer para agregar un nuevo medio de transporte

Si quisiera agregar un nuevo medio de transporte, no debería modificar `TransportMonitor`. Lo correcto sería crear una nueva clase que implemente [`TransportStrategy.java`](TransportStrategy.java), por ejemplo `colectivo.java` o `subte.java`, y luego instanciarla donde corresponda.

Esa propiedad corresponde al principio **Open/Closed Principle**: el sistema queda abierto para extensión, pero cerrado para modificación. El monitor consume la abstracción y no depende de clases concretas.

### Observer: qué pasa si un observer tarda mucho

Si un observer tarda mucho en procesar la notificación, el `notifyObservers()` de [`TransportMonitor.java`](TransportMonitor.java) queda bloqueado esperando a que termine ese `update()`. En otras palabras, el ritmo del subject pasa a depender del observer más lento.

Para desacoplarlos, una opción es notificar de forma asíncrona: por ejemplo, ejecutar cada `update()` en otro hilo, usar una cola de eventos o un executor. Así el subject sigue avanzando y los observers consumen los mensajes a su propio ritmo. En este trabajo práctico la notificación es síncrona, por lo que la latencia de un observer afecta al ciclo completo.

### Integración: por qué ConsolePrinter y AlertObserver usan el mismo logger sin conocerse

Eso es posible porque ambos dependen de la abstracción del logger global, no entre sí. [`ConsolePrinter.java`](ConsolePrinter.java) y [`AlertObserver.java`](AlertObserver.java) solo conocen a [`logger.java`](logger.java) y al contrato [`observer.java`](observer.java); no necesitan referencia mutua.

El desacople lo da el diseño por contratos: el subject entrega un [`TransportSnapshot`](TransportSnapshot.java) y cada observer decide qué hacer con él. Uno imprime estado detallado y el otro dispara alertas según umbrales. Ambos comparten infraestructura de logging, pero no comparten lógica de negocio.

## Resultados observados

- El programa inicia con un logger único y registra el arranque del sistema.
- El monitor puede comenzar con una estrategia inicial y luego cambiar entre taxi, bicicleta y helicóptero sin alterar la estructura del monitor.
- Cada ciclo genera un snapshot con nombre, costo, distancia, ETA y timestamp.
- `ConsolePrinter` muestra el estado completo del transporte.
- `AlertObserver` informa alertas si el costo supera el umbral o si el ETA excede el límite configurado.
- La misma base de notificación sirve para presentar información y para disparar advertencias, lo que confirma el desacople entre subject y observers.

## Conclusión

En el trabajo se muestra una implementación de tres patrones complementarios. Singleton resuelve el acceso centralizado al logger, Strategy permite variar el medio de transporte sin reescribir el monitor, y Observer distribuye el estado del sistema a múltiples consumidores con responsabilidades distintas.

En conjunto, el resultado es un diseño extensible, legible y fácil de ampliar con nuevos transportes u observers sin tocar el núcleo del flujo.
