package com.sms.util;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/student_management_system";
    private static final String USER = "root"; // Change this if your MySQL username is different
    private static final String PASSWORD = "Arpit@2004"; // Change this to your actual MySQL password

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Database Connection Failed! Error: " + e.getMessage());
        }
        return connection;
    }
}