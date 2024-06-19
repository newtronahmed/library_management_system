package com.example.librarymanagemensystem.models;

import java.util.Date;

public class Transaction {
    private int id;
    private int bookID;
    private int patronID;
    private Date issueDate;
    private Date returnDate;

    // Constructor
    public Transaction(int id, int bookID, int patronID, Date issueDate, Date returnDate) {
        this.id = id;
        this.bookID = bookID;
        this.patronID = patronID;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getPatronID() {
        return patronID;
    }

    public void setPatronID(int patronID) {
        this.patronID = patronID;
    }

    public java.sql.Date getIssueDate() {
        return (java.sql.Date) issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public java.sql.Date getReturnDate() {
        return (java.sql.Date) returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}

