package com.careshare.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Update these if your database name, host, or port differ
    private static final String URL = "jdbc:derby://localhost:1527/CareShareDB";
    private static final String USER = "app";
    private static final String PASSWORD = "app";

    public static Connection createConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return conn;
    }
}