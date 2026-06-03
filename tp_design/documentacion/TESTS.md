# Tests — tp_design

Este documento explica en detalle la suite de tests para el ejercicio `tp_design` y cómo ejecutarlos de forma práctica en tu máquina sea por script o con docker tambien puede usarse en ci.

## Estructura de tests en el proyecto

- Código fuente y tests están en `trabajos-practicos/tp_design`.
- Tests existentes:
  - `TresholdAlertService_Test.java` — pruebas unitarias del servicio de umbral (5 tests).
  - `AlertObserverTest.java` — tests basados en implementaciones concretas (`AlwaysAlertService`, `NeverAlertService`).
  - `AlertObserverMockTest.java` — tests que usan Mockito para mocks y verificaciones.
  - `FakeLoggerJUnitTest.java` — test unitario para `FakeLogger`.
  - Además añadimos `FakeLoggerManualTest.java` como una prueba rápida ejecutable con `main()` (útil si no quieres descargar dependencias JUnit).

Ficheros clave:
- `FakeLogger.java`, `logger.java`, `Logger.java` — infraestructura de logging usada por los tests.
- `TransportSnapshot.java` — clase que contienen los datos usados por `AlertObserver` y otros tests.
- `run-tests.sh` — script práctico que automatiza descarga de dependencias, compilación y ejecución de la suite.

## Dependencias necesarias

Algunos tests usan JUnit (Jupiter) y Mockito. Para ejecutar toda la suite necesitamos estas bibliotecas (el script las descarga automáticamente en `lib/`):

- `junit-platform-console-standalone` (1.9.3): lanzador y ejecutor de tests JUnit.
- `mockito-core` (4.11.0): para mocks y verifications usados en `AlertObserverMockTest`.
- `objenesis` (3.2): dependencia de Mockito.
- `byte-buddy` y `byte-buddy-agent` (1.12.22): requisitos para el mock maker inline de Mockito.

Si faltan las dependencias de Byte Buddy, Mockito fallará en tiempo de ejecución con un error del tipo "Could not initialize plugin: interface org.mockito.plugins.MockMaker" o mensajes sobre `GraalImageCode`.

## Scripts y comandos recomendados

1) Test rápido (sin JUnit ni Mockito):

```bash
cd trabajos-practicos/tp_design
javac FakeLogger.java FakeLoggerManualTest.java
java FakeLoggerManualTest
```

Este método es útil para una verificación local rápida; no ejecuta los tests JUnit.

2) Ejecutar todos los tests JUnit con Docker:

```bash
cd trabajos-practicos/tp_design
docker compose up --build --abort-on-container-exit
```

3) Ejecutar toda la suite de forma directa si ya tienes la imagen construida:

```bash
cd trabajos-practicos/tp_design
./run-tests.sh
```

El script `run-tests.sh` hará lo siguiente:
- usa las dependencias descargadas por Docker en `/opt/tp-design-deps`.
- borra `out/`, compila el código fuente y los tests en `test/`, y ejecuta el `junit-platform-console` escaneando el classpath compilado.

4) Ejecutar una sola clase de test (útil para depuración):

```bash
cd trabajos-practicos/tp_design
java -jar /opt/tp-design-deps/junit-platform-console-standalone-1.9.3.jar --class-path "out:/opt/tp-design-deps/*" --select-class=FakeLoggerJUnitTest
```

5) Limpieza rápida antes de compilar si ves clases antiguas:

```bash
cd trabajos-practicos/tp_design
rm -rf out
```

## Salida y códigos de retorno

- El comando del `junit-platform-console` imprime un resumen con número de tests ejecutados, éxitos y fallos. El proceso devuelve código `0` si todos los tests pasan, distinto de `0` si hay fallos.
- `run-tests.sh` usa `set -e` por lo que fallará inmediatamente en la primera orden que salga con código distinto de `0`.


## Recomendaciones para CI

- En CI (GitHub Actions / GitLab CI / etc.) evita comitear los jars binarios. En su lugar, construye la imagen Docker y ejecuta `./run-tests.sh` dentro del contenedor o usa `docker compose up --build --abort-on-container-exit`.
- Ejemplo mínimo de job (GitHub Actions):

```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up Java
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '17'
      - name: Run tests
        run: |
          cd trabajos-practicos/tp_design
          docker-compose up --build --abort-on-container-exit
```

## Ejecutar tests dentro de Docker (recomendado para windows)

Hacer los tests dentro de un contenedor Docker
- se puede usar este docker para modificarlo y usar el ci de github actions
- Usar `docker-compose` *obviamente descarguenlo*:

```bash
cd trabajos-practicos/tp_design
docker compose up --build --abort-on-container-exit
```


