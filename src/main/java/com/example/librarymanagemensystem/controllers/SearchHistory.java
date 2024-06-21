package com.example.librarymanagemensystem.controllers;

// SearchHistory.java

import com.example.librarymanagemensystem.models.Book;

import java.util.Stack;

public class SearchHistory {
    private Stack<Book> historyStack = new Stack<>();

    public void addSearch(Book book) {
        historyStack.push(book);
    }

    public Book getMostRecentSearch() {
        return historyStack.isEmpty() ? null : historyStack.peek();
    }

    public void clearHistory() {
        historyStack.clear();
    }

    public Stack<Book> getHistoryStack() {
        return historyStack;
    }
}

