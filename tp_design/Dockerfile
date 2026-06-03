FROM eclipse-temurin:17-jdk

WORKDIR /workspace

# Install curl for dependency downloads
RUN apt-get update && apt-get install -y curl ca-certificates && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /opt/tp-design-deps && \
	curl -fL -o /opt/tp-design-deps/junit-platform-console-standalone-1.9.3.jar https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.3/junit-platform-console-standalone-1.9.3.jar && \
	curl -fL -o /opt/tp-design-deps/mockito-core-4.11.0.jar https://repo1.maven.org/maven2/org/mockito/mockito-core/4.11.0/mockito-core-4.11.0.jar && \
	curl -fL -o /opt/tp-design-deps/objenesis-3.2.jar https://repo1.maven.org/maven2/org/objenesis/objenesis/3.2/objenesis-3.2.jar && \
	curl -fL -o /opt/tp-design-deps/byte-buddy-1.12.22.jar https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy/1.12.22/byte-buddy-1.12.22.jar && \
	curl -fL -o /opt/tp-design-deps/byte-buddy-agent-1.12.22.jar https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy-agent/1.12.22/byte-buddy-agent-1.12.22.jar

# Copy project files into the image
COPY . /workspace

RUN chmod +x run-tests.sh || true

ENV TP_DESIGN_DEPS=/opt/tp-design-deps

# Default command runs the test script which compiles and runs tests using the image dependencies
CMD ["bash", "/workspace/run-tests.sh"]
