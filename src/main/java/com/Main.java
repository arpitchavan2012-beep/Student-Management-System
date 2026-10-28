package com;

import com.sms.dao.StudentDAO;
import com.sms.dao.UserDAO;
import com.sms.model.Student;
import com.sms.model.User;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        StudentDAO studentDAO = new StudentDAO();
        
        User loggedInUser = null;

        System.out.println("=========================================");
        System.out.println("   WELCOME TO STUDENT MANAGEMENT SYSTEM  ");
        System.out.println("=========================================");

        // --- LOGIN LOOP ---
        while (loggedInUser == null) {
            System.out.println("\n--- LOGIN SCREEN ---");
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            loggedInUser = userDAO.login(username, password);

            if (loggedInUser != null) {
                System.out.println("\nSUCCESS: Logged in as " + loggedInUser.getUsername() + " [Role: " + loggedInUser.getRole() + "]");
            } else {
                System.out.println("\nFAILURE: Invalid username or password. Please try again.");
            }
        }

        // --- MAIN APPLICATION MENU ---
        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU (" + loggedInUser.getRole().toUpperCase() + " PORTAL) ---");
            System.out.println("1. Add New Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student Details");
            System.out.println("4. Delete Student");
            System.out.println("5. Logout / Exit");
            System.out.print("Choose an option (1-5): ");

            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
            } else {
                System.out.println("Invalid input! Please enter a number between 1 and 5.");
                scanner.next();
                continue;
            }

            switch (choice) {
                case 1:
                    // Only Admin or Teacher can add students (example of role check)
                    if (loggedInUser.getRole().equals("Student")) {
                        System.out.println("ACCESS DENIED: Students cannot add records.");
                        break;
                    }
                    
                    System.out.print("Enter User ID (must exist in users table): ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter First Name: ");
                    String firstName = scanner.nextLine();

                    System.out.print("Enter Last Name: ");
                    String lastName = scanner.nextLine();

                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter Phone: ");
                    String phone = scanner.nextLine();

                    System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
                    String dobStr = scanner.nextLine();

                    Student newStudent = new Student(0, userId, firstName, lastName, email, phone, Date.valueOf(dobStr));
                    boolean added = studentDAO.addStudent(newStudent);
                    
                    if (added) {
                        System.out.println("SUCCESS: Student added successfully!");
                    } else {
                        System.out.println("ERROR: Could not add student. Check if User ID exists.");
                    }
                    break;

                case 2:
                    List<Student> students = studentDAO.getAllStudents();
                    System.out.println("\n--- STUDENT LIST ---");
                    if (students.isEmpty()) {
                        System.out.println("No students found in the database.");
                    } else {
                        for (Student s : students) {
                            System.out.println("ID: " + s.getStudentId() + 
                                               " | Name: " + s.getFirstName() + " " + s.getLastName() + 
                                               " | Email: " + s.getEmail() + 
                                               " | Phone: " + s.getPhone() + 
                                               " | DOB: " + s.getDateOfBirth());
                        }
                    }
                    break;

                case 3:
                    if (loggedInUser.getRole().equals("Student")) {
                        System.out.println("ACCESS DENIED: Students cannot update records.");
                        break;
                    }

                    System.out.print("Enter Student ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter New First Name: ");
                    String newFirst = scanner.nextLine();

                    System.out.print("Enter New Last Name: ");
                    String newLast = scanner.nextLine();

                    System.out.print("Enter New Email: ");
                    String newEmail = scanner.nextLine();

                    System.out.print("Enter New Phone: ");
                    String newPhone = scanner.nextLine();

                    System.out.print("Enter New Date of Birth (YYYY-MM-DD): ");
                    String newDobStr = scanner.nextLine();

                    Student updateStudent = new Student(updateId, 0, newFirst, newLast, newEmail, newPhone, Date.valueOf(newDobStr));
                    boolean updated = studentDAO.updateStudent(updateStudent);

                    if (updated) {
                        System.out.println("SUCCESS: Student updated successfully!");
                    } else {
                        System.out.println("ERROR: Could not update student. Check if Student ID exists.");
                    }
                    break;

                case 4:
                    if (!loggedInUser.getRole().equals("Admin")) {
                        System.out.println("ACCESS DENIED: Only Admins can delete student records.");
                        break;
                    }

                    System.out.print("Enter Student ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();

                    boolean deleted = studentDAO.deleteStudent(deleteId);
                    if (deleted) {
                        System.out.println("SUCCESS: Student deleted successfully!");
                    } else {
                        System.out.println("ERROR: Could not delete student. Check ID.");
                    }
                    break;

                case 5:
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option! Please choose between 1 and 5.");
            }
        }
        scanner.close();
    }
}