# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY wallet /app/wallet
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app
ENV TNS_ADMIN=/app/wallet
ENV JAVA_OPTS="-Xms128m -Xmx512m -XX:MaxRAMPercentage=70.0 -Djava.security.egd=file:/dev/./urandom"
COPY --from=build /app/target/gestion-cursos-cloud-0.0.1-SNAPSHOT.jar app.jar
COPY --from=build /app/wallet /app/wallet
RUN sed -i 's/\r$//' /app/wallet/tnsnames.ora 2>/dev/null || true
RUN useradd -ms /bin/sh appuser && chown -R appuser:appuser /app
USER appuser
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
