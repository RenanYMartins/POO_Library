package com.example.demo.controller.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Date;

public class Loan {
	private int id;
	private String borrowerName;
	private LocalDate loanDate;
	private LocalDate returnDate;
	private List<BookLoan> bookLoans;
	private boolean returned;
	private List<String> bookTitles;
	
	public Loan() {
		this.loanDate = LocalDate.now();
        this.returnDate = this.loanDate.plusDays(7);
	}
	
	public Loan(int id, String borrowerName, LocalDate returnDate, List<BookLoan> bookLoans) {
        this.borrowerName = borrowerName;
        this.loanDate = (loanDate != null) ? loanDate : LocalDate.now();
        this.returnDate = (returnDate == null) ? loanDate.plusDays(7) : returnDate;
        this.bookLoans = bookLoans;
    }
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setBorrowerName(String borrowerName) {
		this.borrowerName= borrowerName;
	}
	
	public String getBorrowerName() {
		return borrowerName;
	}
	
	public void setLoanDate(LocalDate loanDate) {
		this.loanDate = (loanDate != null) ? loanDate : LocalDate.now();
	}
	
	public LocalDate getLoanDate() {
		return loanDate;
	}
	
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = (returnDate != null) ? returnDate : this.loanDate.plusDays(7);
	}
	
	public LocalDate getReturnDate() {
		return returnDate;
	}
	
	public List<BookLoan> getBookLoans() {
        return bookLoans;
    }

    public void setBookLoans(List<BookLoan> bookLoans) {
        this.bookLoans = bookLoans;
    }
    
    public boolean getIsReturned() {
		return returned;
	}
    
	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	public List<String> getBookTitles() {
	    return bookTitles;
	}

	public void setBookTitles(List<String> bookTitles) {
	    this.bookTitles = bookTitles;
	}
}
