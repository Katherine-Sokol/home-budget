FROM openjdk:17-jdk-slim

# Створюємо робочу директорію
WORKDIR /app

# Копіюємо файли проєкту
COPY . .

# Будуємо JAR-файл
RUN chmod +x gradlew
RUN ./gradlew build --no-daemon

# Запускаємо застосунок
CMD ["java", "-jar", "build/libs/project-0.0.1-SNAPSHOT.jar"]