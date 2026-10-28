package com.sms.dao;

import com.sms.model.User;
import com.sms.util.DBConnection;

import java.sql.*;

public class UserDAO {

    // Authenticate user by username and password
    public User login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password_hash = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password); // Note: In production, use password hashing libraries like BCrypt
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Return a User object if credentials match
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getString("role")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Login database error: " + e.getMessage());
        }
        
        return null; // Return null if login fails
    }
}