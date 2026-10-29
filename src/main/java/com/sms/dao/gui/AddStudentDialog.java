package com.sms.dao.gui;

import com.sms.dao.StudentDAO;
import com.sms.model.Student;
import com.sms.model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class AddStudentDialog extends JDialog {
    private JTextField userIdField, firstNameField, lastNameField, emailField, phoneField, dobField;
    private JButton submitButton;
    private StudentDAO studentDAO;
    private StudentDashboard parentDashboard;
    

    public AddStudentDialog(StudentDashboard parent) {
        super(parent, "Add New Student", true);
        this.parentDashboard = parent;
        this.studentDAO = new StudentDAO();

        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(7, 2, 10, 10));

        // Form Fields
        add(new JLabel("  User ID:"));
        userIdField = new JTextField();
        add(userIdField);

        add(new JLabel("  First Name:"));
        firstNameField = new JTextField();
        add(firstNameField);

        add(new JLabel("  Last Name:"));
        lastNameField = new JTextField();
        add(lastNameField);

        add(new JLabel("  Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("  Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("  DOB (YYYY-MM-DD):"));
        dobField = new JTextField();
        add(dobField);

        // Submit Button
        submitButton = new JButton("Save Student");
        add(new JLabel()); // Empty space for alignment
        add(submitButton);

        // Action Listener for Submitting
        submitButton.addActionListener(e -> saveStudent());
    }

    private void saveStudent() {
        try {
            int userId = Integer.parseInt(userIdField.getText().trim());
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            Date dob = Date.valueOf(dobField.getText().trim());

            Student newStudent = new Student(0, userId, firstName, lastName, email, phone, dob);
            boolean success = studentDAO.addStudent(newStudent);

            if (success) {
                JOptionPane.showMessageDialog(this, "Student added successfully!");
                parentDashboard.loadStudentDataManually(); // Refresh table
                dispose(); // Close dialog
            } else {
                JOptionPane.showMessageDialog(this, "Error adding student. Check if User ID exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input format! Please check your fields and DOB (YYYY-MM-DD).", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}