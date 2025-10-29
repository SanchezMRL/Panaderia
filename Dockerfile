# ============================
# 🧱 Etapa 1: Construcción con Maven (usa cache de dependencias)
# ============================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# ✅ Copiamos solo el pom.xml primero para aprovechar la caché de Maven
COPY pom.xml .

# 🔥 Descarga dependencias sin compilar el proyecto
RUN mvn dependency:go-offline -B

# ✅ Ahora copiamos el código fuente
COPY src ./src

# ⚙️ Compila el JAR (sin ejecutar tests)
RUN mvn clean package -DskipTests

# ============================
# ☁️ Etapa 2: Imagen final ligera para ejecución
# ============================
FROM eclipse-temurin:17-jdk-alpine

# ✅ Imagen "alpine" = mucho más rápida y liviana (~200MB menos)
WORKDIR /app

# ✅ Copiamos el JAR desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Variables de entorno (Render las puede sobreescribir)
ENV DB_URL=""
ENV DB_USER=""
ENV DB_PASS=""

# Exponemos el puerto que Render usa por defecto
EXPOSE 8080

# ✅ Ejecuta el JAR
ENTRYPOINT ["java", "-jar", "app.jar"]

