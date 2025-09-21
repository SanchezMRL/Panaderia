# Panaderia Web (Maven WAR) - FIXED

He corregido los servlets y añadido utilidades para parsear fechas. Este ZIP debe compilar en Java 11 con Maven.

## Ejecutar localmente

1. Asegúrate de tener Java 11+ y Maven instalados.
2. Desde el directorio del proyecto:
```bash
mvn clean package
mvn tomcat7:run
```
o importar a NetBeans y ejecutar con un Tomcat 9 configurado.

## Endpoints principales
- `/api/producto/{id}` GET → devuelve JSON del producto
- `/api/pedido` POST → insertar pedido (acepta JSON como en `registrar.html`)
- `/api/pago` POST → registra pago
- `/api/opinion` POST → registra opinión

## Base de datos
Se incluyó `src/main/resources/db/create_tables.sql` con las tablas necesarias.

## Credenciales usadas (como solicitaste)
- Host: dpg-d2ob2u6r433s738kc4k0-a.oregon-postgres.render.com:5432
- Database: panaderia_db_q3ri
- User: panaderia_db_q3ri_user
- Password: 1U9Vxaj7dHRcIEAz6LM1WjvvKBDzGqRm
