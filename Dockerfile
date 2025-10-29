# ============================
# 🧱 Etapa 1: Build con Maven + caché de dependencias
# ============================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# ⚡ Copiamos solo el pom.xml primero para aprovechar la caché
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 📦 Luego copiamos el resto del código
COPY src ./src

# 🚀 Construcción del JAR (sin tests)
RUN mvn clean package -DskipTests

# ============================
# ☁️ Etapa 2: Imagen ligera para ejecución
# ============================
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# 🔥 Copiamos solo el JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Variables que Render sobreescribirá
ENV DB_URL=""
ENV DB_USER=""
ENV DB_PASS=""
ENV PORT=8080

# Exponemos el puerto (Render lo usará)
EXPOSE 8080

# 💡 Usa JAVA_OPTS para ajustar memoria si es necesario
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
