package com.example.librarymanagemensystem.models;

import java.util.Date;

public class Reservation {
    private int id;
    private int bookId;
    private int patronId;
    private Date reservationDate;
    private String status;

    // Constructors, getters, and setters
    public Reservation(int bookId, int patronId, Date reservationDate, String status) {
        this.bookId = bookId;
        this.patronId = patronId;
        this.reservationDate = reservationDate;
        this.status = status;
    }
    public Reservation(int id,int bookId, int patronId, Date reservationDate, String status) {
        this.id = id;
        this.bookId = bookId;
        this.patronId = patronId;
        this.reservationDate = reservationDate;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }
    // Getters and setters

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPatronId() {
        return patronId;
    }

    public void setPatronId(int patronId) {
        this.patronId = patronId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
