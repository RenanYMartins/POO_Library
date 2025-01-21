package com.example.demo.controller.model;

public class Book {
	private int id;
	private String title;
	private int copiesAvailable;
	
	public Book() {}
	
	public Book(int id, String title, int copiesAvailable) {
		this.id = id;
		this.title = title;
		this.copiesAvailable = copiesAvailable;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	public void setCopiesAvailable(int copiesAvailable) {
		this.copiesAvailable = copiesAvailable;
	}
	
	public int getCopiesAvailable() {
		return copiesAvailable;
	}
}
