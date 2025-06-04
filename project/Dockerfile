# Етап 1: Збірка Spring Boot застосунку
FROM gradle:7.6.0-jdk17 AS builder
WORKDIR /app
COPY . .                             # все з `project/` копіюється у /app
RUN gradle build --no-daemon        # збираємо JAR

# Етап 2: Запуск
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]