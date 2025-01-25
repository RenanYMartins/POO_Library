package com.example.demo.controller.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Date;

public class Loan {
	private int id;
	private String borrowerName;
	private LocalDate loanDate;
	private LocalDate returnDate;
	private boolean returned;
//	private List<Book> books;

	public Loan() {
        this.loanDate = LocalDate.now();
        this.returnDate = this.loanDate.plusDays(7);
    }
	
	public Loan(int id, String borrowerName, LocalDate loanDate, LocalDate returnDate, boolean returned) {
		this.borrowerName = borrowerName;
		this.loanDate = (loanDate != null) ? loanDate : LocalDate.now();
	    this.returnDate = (returnDate == null) ? loanDate.plusDays(7) : returnDate;
	    this.returned = returned;
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
	
	public boolean getIsReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}
}
