# Usamos Tomcat 9 oficial
FROM tomcat:9.0

# Copiamos tu WAR compilado al directorio ROOT de Tomcat
COPY target/panaderia.war /usr/local/tomcat/webapps/ROOT.war

# Exponemos el puerto 8080 (el que Tomcat usa)
EXPOSE 8080
