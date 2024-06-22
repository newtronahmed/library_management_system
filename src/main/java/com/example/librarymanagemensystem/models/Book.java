package com.example.librarymanagemensystem.models;


public class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
//    private int publishedYear;
    private Boolean isIssued;
    private int transactionCount;

    public Book (String title, String author, String genre){
        this.title = title;
        this.author = author;
        this.genre = genre;
    }
    // Constructor
    public Book(int id, String title, String author, String genre, boolean isIssued) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isIssued = isIssued;
    }
    public Book(int id, String title, String author, String genre, boolean isIssued, int transactionCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isIssued = isIssued;
        this.transactionCount = transactionCount;
    }

//    public Book(int id, String title, String author, boolean isIssued) {
//    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

//    public int getPublishedYear() {
//        return publishedYear;
//    }
//
//    public void setPublishedYear(int publishedYear) {
//        this.publishedYear = publishedYear;
//    }
    public Boolean getIsIssued(){
        return isIssued;
    }
    public void setIsIssued(Boolean isIssued){
        this.isIssued = isIssued;
    }
    @Override
    public String toString() {
        return title + " by " + author;
    }
    public String getDetails() {
        return String.format("ID: %d, Title: %s, Author: %s, IsIssued: %b",
                getId(), getTitle(), getAuthor(), getIsIssued());
    }
    public int getTransactionCount (){
        return transactionCount;
    }
    public int setTransactionCount (int val){
        return this.transactionCount = val;
    }

}

