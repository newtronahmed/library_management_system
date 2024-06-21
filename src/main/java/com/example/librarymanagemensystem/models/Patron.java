package com.example.librarymanagemensystem.models;
import java.sql.Date;

public class Patron {
    private int id;
    private String name;
    private String email;
    private Date membershipDate;

    public Patron(String name, String email, Date membershipDate){
        this.name = name;
        this.email = email;
        this.membershipDate = membershipDate;
    }

    // Constructor
    public Patron(int id, String name, String email, Date membershipDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.membershipDate = membershipDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public java.sql.Date getMembershipDate() {
        return (java.sql.Date) membershipDate;
    }

    public void setMembershipDate(Date membershipDate) {
        this.membershipDate = membershipDate;
    }
}

