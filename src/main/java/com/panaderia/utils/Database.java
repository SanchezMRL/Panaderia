package com.panaderia.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    /**
     * Obtiene una conexión a la base de datos PostgreSQL usando variables de entorno:
     * DB_URL, DB_USER, DB_PASS
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Carga del driver de PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Tomamos los valores desde las variables de entorno
            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASS");

            // Verifica que las variables no sean nulas
            if (url == null || user == null || password == null) {
                throw new SQLException("Variables de entorno de la base de datos no configuradas correctamente.");
            }

            // Retorna la conexión
            return DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL Driver not found", e);
        }
    }
}
