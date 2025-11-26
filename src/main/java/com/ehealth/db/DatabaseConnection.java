package com.ehealth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Simple JDBC connection helper.
 * Configure the URL, USER and PASS according to your local database.
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ehealth";
    private static final String USER = "root";
    private static final String PASS = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
