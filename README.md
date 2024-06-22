# Library Management System - Project Requirements

## 1. Introduction

This document outlines the requirements for a Library Management System using JDBC, MySQL, and JavaFX. The system will allow users to manage books, patrons, and book issuance.

## 2. System Architecture

- Backend: Java with JDBC for database connectivity
- Database: MySQL
- Frontend: JavaFX for the user interface

## 3. Features and Functionality

### 3.1 Main Page

- Menu for navigating to various pages of the application
- Option to return to the main page from anywhere in the application

### 3.2 Manage Books

- View books with information on issuance status and transaction count
- Add multiple books simultaneously
- Commit new books to the database

### 3.3 Search Books

- Search for books by title
- Populate search terms into a stack for navigation
- Navigate between search results (forward and backward)

### 3.4 Manage Patrons

- View list of patrons
- Add multiple patrons simultaneously

### 3.5 Issue Books

- Select books for issuance (stored in a queue)
- Select a patron from the list
- Display only available (non-issued) books for selection
- Save transaction to issue books to the selected patron

## 4. Database Design

- SQL file provided for table creation
- Implement SQL queries with joins and aggregate functions
- Store database queries in a stack for operations

## 5. Technical Requirements

### 5.1 Object-Oriented Programming Principles

- Demonstrate inheritance through a base controller
- Implement encapsulation in model classes
- Show polymorphism through:
    - Models with different constructors
    - Book model overriding toString() method

### 5.2 Data Structures

- Utilize stacks for:
    - Storing and navigating search terms
    - Managing database queries
- Implement queues for managing book issuance

### 5.3 Database Operations

- Support adding multiple books simultaneously
- Allow adding multiple patrons at once
- Implement book issuance functionality

## 6. User Interface

- Develop using JavaFX
- Ensure intuitive navigation between different pages
- Provide clear visual feedback for user actions

## 7. Performance and Scalability

- Optimize database queries for efficient data retrieval
- Ensure responsiveness when handling large numbers of books and patrons

## 8. Testing and Quality Assurance

- Implement unit tests for critical functionality
- Perform integration testing for database operations
- Conduct user acceptance testing for the UI

## 9. Documentation

- Provide inline code comments for complex logic
- Create user manual for system operation
- Document database schema and relationships

## 10. Future Enhancements

- Implement book return functionality
- Add reporting features for library statistics
- Integrate with external book databases for additional information