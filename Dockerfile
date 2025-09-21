# Etapa 1: build con Maven y JDK 11
FROM maven:3.9.3-eclipse-temurin-11 AS build
COPY pom.xml /app/
COPY src /app/src/
WORKDIR /app
RUN mvn clean package

# Etapa 2: Tomcat
FROM tomcat:9.0
COPY --from=build /app/target/panaderia.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
