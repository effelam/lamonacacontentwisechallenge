# Stage 1: build del progetto con Gradle
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app

# Copia tutto il progetto
COPY . .

# Costruisce il progetto e genera il .jar
RUN gradle clean build -x test --no-daemon

# Stage 2: immagine finale pulita
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia solo il JAR compilato dalla fase precedente
COPY --from=builder /app/build/libs/lamonacacontentwisechallenge-0.0.1-SNAPSHOT.jar app.jar

# Espone la porta dell'app (assicurati che sia corretta)
EXPOSE 8080

# Comando di avvio
ENTRYPOINT ["java", "-jar", "app.jar"]
