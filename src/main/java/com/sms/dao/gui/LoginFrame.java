package com.sms.dao.gui;

import com.sms.dao.UserDAO;
import com.sms.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;
    private UserDAO userDAO;

    public LoginFrame() {
        userDAO = new UserDAO();

        setTitle("Student Management System - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        // Form Components
        add(new JLabel("  Select Role:"));
        String[] roles = {"Admin", "Teacher", "Student"};
        roleComboBox = new JComboBox<>(roles);
        add(roleComboBox);

        add(new JLabel("  Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("  Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Login");
        add(new JLabel()); // Empty space for layout alignment
        add(loginButton);

        // Login Button Action Listener
        loginButton.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String selectedRole = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Authenticate using UserDAO
        User user = userDAO.login(username, password);

        if (user != null) {
            // Verify if the database role matches the selected role dropdown
            if (user.getRole().equalsIgnoreCase(selectedRole)) {
                JOptionPane.showMessageDialog(this, "Login Successful! Welcome, " + user.getUsername() + " [" + user.getRole() + "]");
                
                // Close the login window
                dispose();

                // Open the Main Dashboard and pass the logged-in user details
                SwingUtilities.invokeLater(() -> {
                    StudentDashboard dashboard = new StudentDashboard(user);
                    dashboard.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this, "Role mismatch! This account is not registered as an " + selectedRole, "Access Denied", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}