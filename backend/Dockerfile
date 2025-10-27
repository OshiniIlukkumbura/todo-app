# ===== BACKEND DOCKERFILE =====
# Stage 1: build
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Copy everything and build JAR
COPY . .
RUN ./mvnw clean package -DskipTests

# Stage 2: runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Install netcat for wait-for-it
RUN apt-get update && apt-get install -y netcat && rm -rf /var/lib/apt/lists/*

# Copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Copy wait-for-it script
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Expose backend port
EXPOSE 8088

# Wait for MySQL then start app
# ENTRYPOINT ["/wait-for-it.sh", "db:3306", "-t", "60", "--", "java", "-jar", "app.jar"]
# ENTRYPOINT ["/wait-for-it.sh", "db", "3306", "-t", "60", "--", "java", "-jar", "app.jar"]
ENTRYPOINT ["/wait-for-it.sh", "db:3306", "-t", "60", "--", "java", "-jar", "app.jar"]
