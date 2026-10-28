package com.sms.dao;

import com.sms.model.Student;
import com.sms.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // 1. CREATE: Add a new student to the database
    public boolean addStudent(Student student) {
        String query = "INSERT INTO students (user_id, first_name, last_name, email, phone, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, student.getUserId());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getLastName());
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getPhone());
            pstmt.setDate(6, student.getDateOfBirth());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    // 2. READ: Get a list of all students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("student_id"),
                    rs.getInt("user_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getDate("date_of_birth")
                );
                students.add(student);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching students: " + e.getMessage());
        }
        
        return students;
    }
    // 3. UPDATE: Update an existing student's details
    public boolean updateStudent(Student student) {
        String query = "UPDATE students SET first_name = ?, last_name = ?, email = ?, phone = ?, date_of_birth = ? WHERE student_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getPhone());
            pstmt.setDate(5, student.getDateOfBirth());
            pstmt.setInt(6, student.getStudentId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            return false;
        }
    }

    // 4. DELETE: Remove a student from the database by ID
    public boolean deleteStudent(int studentId) {
        String query = "DELETE FROM students WHERE student_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, studentId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }
}