# ============================
# Etapa 1: build con Maven y Java 17
# ============================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ============================
# Etapa 2: ejecutar el JAR
# ============================
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render inyecta automáticamente las variables de entorno
ENV DB_URL=""
ENV DB_USER=""
ENV DB_PASS=""

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
