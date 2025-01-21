package com.example.demo;

public class Loan {
	private int id;
	private String borrowerName;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private List<Book> books;
    
    public Loan(String borrowerName, LocalDate loanDate) {
        this.borrowerName = borrowerName;
        this.loanDate = loanDate;
        this.returnDate = loanDate.plusDays(7);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
