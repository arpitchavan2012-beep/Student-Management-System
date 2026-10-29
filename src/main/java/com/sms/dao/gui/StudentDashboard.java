package com.sms.dao.gui;

import com.sms.dao.StudentDAO;
import com.sms.model.Student;
import com.sms.model.User; // Make sure this import is present!

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentDashboard extends JFrame {
    private StudentDAO studentDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    private User loggedInUser; // 1. Added field for logged-in user

    // 2. Updated constructor that accepts the User object
    public StudentDashboard(User user) {
        this.loggedInUser = user;
        studentDAO = new StudentDAO();

        // Configure JFrame Window Settings
        setTitle("Student Management System - Dashboard (" + loggedInUser.getRole() + " Portal)");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout Setup
        setLayout(new BorderLayout());

        // Top Title Label showing who is logged in
        JLabel titleLabel = new JLabel("Student Records Dashboard - Logged in as: " + loggedInUser.getUsername() + " (" + loggedInUser.getRole() + ")", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Center Table Setup
        String[] columnNames = {"ID", "User ID", "First Name", "Last Name", "Email", "Phone", "DOB"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton refreshButton = new JButton("Refresh Data");
        JButton addButton = new JButton("Add Student");
        JButton deleteButton = new JButton("Delete Selected");

        buttonPanel.add(refreshButton);
        
        // Role-based button visibility
        if (!loggedInUser.getRole().equalsIgnoreCase("Student")) {
            buttonPanel.add(addButton);
        }
        if (loggedInUser.getRole().equalsIgnoreCase("Admin")) {
            buttonPanel.add(deleteButton);
        }
        
        add(buttonPanel, BorderLayout.SOUTH);

        // Load data initially
        loadStudentDataManually();

        // Button Actions
        refreshButton.addActionListener(e -> loadStudentDataManually());

        addButton.addActionListener(e -> {
            AddStudentDialog dialog = new AddStudentDialog(this);
            dialog.setVisible(true);
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int studentId = (int) tableModel.getValueAt(selectedRow, 0);
                boolean success = studentDAO.deleteStudent(studentId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                    loadStudentDataManually();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete student.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a student row from the table first.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    public void loadStudentDataManually() {
    tableModel.setRowCount(0); // Clear existing table rows
    List<Student> students = studentDAO.getAllStudents();
    
    for (Student s : students) {
        // If logged in as a Student, only show their own record
        if (loggedInUser.getRole().equalsIgnoreCase("Student")) {
            if (s.getUserId() == loggedInUser.getUserId()) {
                Object[] row = {
                    s.getStudentId(),
                    s.getUserId(),
                    s.getFirstName(),
                    s.getLastName(),
                    s.getEmail(),
                    s.getPhone(),
                    s.getDateOfBirth()
                };
                tableModel.addRow(row);
                break; // Stop once we found the student's own record
            }
        } else {
            // Admins and Teachers can see all student records
            Object[] row = {
                s.getStudentId(),
                s.getUserId(),
                s.getFirstName(),
                s.getLastName(),
                s.getEmail(),
                s.getPhone(),
                s.getDateOfBirth()
            };
            tableModel.addRow(row);
        }
    }
}
}