package com.ehealth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC helper using environment variables (recommended) or default values.
 * Set environment variables: EHEALTH_DB_URL, EHEALTH_DB_USER, EHEALTH_DB_PASS
 */
public class DatabaseConnection {
    private static final String URL = System.getenv().getOrDefault("EHEALTH_DB_URL", "jdbc:mysql://localhost:3306/ehealth?serverTimezone=UTC");
    private static final String USER = System.getenv().getOrDefault("EHEALTH_DB_USER", "root");
    private static final String PASS = System.getenv().getOrDefault("EHEALTH_DB_PASS", "password");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
