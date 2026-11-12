# ========================================================
# Dockerfile for Sensor Calibration Management System
# Subject: CCS342 - DevOps (Anna University)
# Student: KALAIVARSAN V (Roll 25)
# ========================================================

# Multi-Stage Build: Stage 1 - Build Artifact with Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
# Download dependencies for caching
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2 - Slim Production Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /opt/app

# Security: Run as non-root user
RUN addgroup -S devopsgroup && adduser -S devopsuser -G devopsgroup
USER devopsuser

COPY --from=build /app/target/sensor-calibration-system-1.0.0.jar app.jar

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --retries=3   CMD wget -qO- http://localhost:8080/api/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
