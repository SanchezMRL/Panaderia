package com.panaderia.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:postgresql://dpg-d2ob2u6r433s738kc4k0-a.oregon-postgres.render.com:5432/panaderia_db_q3ri";
    private static final String USER = "panaderia_db_q3ri_user";
    private static final String PASSWORD = "1U9Vxaj7dHRcIEAz6LM1WjvvKBDzGqRm";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL Driver not found", e);
        }
    }
}
