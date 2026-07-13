package com.foodbank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Shared database connection utility.
 * NOTE: This class belongs to Qis's Authentication & User Management module.
 * It is reproduced here only so Ainaa's DAO classes compile/run standalone.
 * Replace the URL / USER / PASSWORD with the project's actual database credentials,
 * and remove this file once merged with Qis's copy to avoid duplication.
 */
public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/foodbank_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
