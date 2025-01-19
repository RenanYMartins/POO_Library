package com.example.demo;

public class BookLoan {
    private int id;
    private int bookId;
    private int loanId;

    public BookLoan() {}

    public BookLoan(int bookId, int loanId) {
        this.bookId = bookId;
        this.loanId = loanId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}

