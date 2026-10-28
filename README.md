# Student-Management-System
# 🎓 Student Management System

A robust, object-oriented Java desktop application designed to manage student records, attendance, and academic grades. Built from scratch using core Java, JDBC, and a normalized MySQL database backend.

---

## 🚀 Features

* **Role-Based Authentication & Login:** Secure access control supporting different user roles (`Admin`, `Teacher`, `Student`).
* **Complete Student CRUD Operations:** Add, view, update, and delete student profiles seamlessly.
* **Database-Driven Architecture:** Uses a Data Access Object (DAO) pattern to decouple business logic from database operations.
* **Advanced Reporting Ready:** Built on a normalized 6-table relational schema designed for complex SQL JOIN and aggregate reporting queries.

---

## 🛠️ Tech Stack

* **Programming Language:** Java (JDK 17+)
* **Database:** MySQL
* **Database Connectivity:** JDBC (Java Database Connectivity) with `PreparedStatement` (Protection against SQL Injection)
* **Architecture Design Pattern:** DAO (Data Access Object) & OOP Principles (Encapsulation, Inheritance)
* **Build Tool & Dependency Management:** Maven
* **IDE:** VS Code / IntelliJ IDEA

---

## 📂 Project Architecture

The project follows a clean, modular package structure adhering to separation of concerns:
```text
src/
└── main/
    └── java/
        └── com/
            └── sms/
                ├── dao/         # Data Access Objects (SQL queries & database communication)
                ├── model/       # Object-Oriented Domain Models (User, Student, Course, etc.)
                ├── util/        # Utility classes (Database connection manager)
                └── Main.java    # Application entry point & testing sandbox