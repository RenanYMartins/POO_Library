package com.example.demo;

public class Book {
	private int id;
	private String title;
	private int copiesAvailable;
	
	public Book(int id, String title, int copiesAvailable) {
		this.title = title;
		this.copiesAvailable = copiesAvailable;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
        this.id = id;
    }
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getTitle() {
		return title;
	}

	public void setCopiesAvailable(int copiesAvailable) {
		this.copiesAvailable = copiesAvailable;
	}
	
	public int getCopiesAvailable() {
		return copiesAvailable;
	}
}
