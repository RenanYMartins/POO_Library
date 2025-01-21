package com.example.demo.controller.model;

public class BookLoan {
	private int id;
	private int bookId;
	private int loanId;
	
	public BookLoan(int bookId, int loanId) {
		this.bookId = bookId;
		this.loanId = loanId;
	}
}
