package com.example.demo.controller.model;

public class BookLoan {
    private int bookId;
    private int loanId;
    private int amount;
    
    public BookLoan(int bookId, int loanId, int amount) {
        this.bookId = bookId;
        this.loanId = loanId;
        this.amount = amount;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

