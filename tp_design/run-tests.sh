#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"

DEPS_DIR="${TP_DESIGN_DEPS:-/opt/tp-design-deps}"
OUT_DIR="${TP_DESIGN_OUT:-out}"
JUNIT_JAR="$DEPS_DIR/junit-platform-console-standalone-1.9.3.jar"
CP="$JUNIT_JAR:$DEPS_DIR/mockito-core-4.11.0.jar:$DEPS_DIR/objenesis-3.2.jar:$DEPS_DIR/byte-buddy-1.12.22.jar:$DEPS_DIR/byte-buddy-agent-1.12.22.jar"

if [ ! -f "$JUNIT_JAR" ]; then
  echo "Missing Docker-provided test dependencies in $DEPS_DIR. Build and run the Docker image."
  exit 1
fi

echo "Cleaning old classes..."
rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

echo "Compiling sources..."
javac -d "$OUT_DIR" -cp "$CP" *.java test/*.java

echo "Running JUnit tests (scan classpath)..."
java -jar "$JUNIT_JAR" --class-path "$OUT_DIR:$CP" --scan-class-path

echo "Done."
