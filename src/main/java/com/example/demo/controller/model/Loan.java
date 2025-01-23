package com.example.demo.controller.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Date;

public class Loan {
	private int id;
	private String borrowerName;
	private LocalDate loanDate;
	private LocalDate returnDate;
//	private List<Book> books;
	
	public Loan() {}
	
	public Loan(int id, String borrowerName, LocalDate loanDate, LocalDate returnDate) {
		this.borrowerName = borrowerName;
		this.loanDate = loanDate;
	    this.returnDate = (returnDate == null) ? loanDate.plusDays(7) : returnDate;
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
		this.loanDate = LocalDate.now();
	}
	
	public LocalDate getLoanDate() {
		return loanDate;
	}
	
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	
	public LocalDate getReturnDate() {
		return returnDate;
	}
}
