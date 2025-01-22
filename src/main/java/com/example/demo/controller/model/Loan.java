package com.example.demo.controller.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Date;

public class Loan {
	private int id;
	private String borrowerName;
	private LocalDate loanDate;
	private LocalDate returnDate;
	private List<Book> books;
	
	public Loan(int id, String borrowerName, LocalDate loanDate, LocalDate returnDate, List<Book> books) {
		this.borrowerName = borrowerName;
		this.loanDate = loanDate;
		this.returnDate = loanDate.plusDays(7);
	}
}
