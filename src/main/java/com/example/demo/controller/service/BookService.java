package com.example.demo.controller.service;

import com.example.demo.controller.dao.DAOBook;
import com.example.demo.controller.dao.DAOBookLoan;
import com.example.demo.controller.model.Book;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class BookService {
	private final DAOBook daoBook;
	private final DAOBookLoan daoBookLoan;
	
	public BookService(DAOBook daoBook, DAOBookLoan daoBookLoan) {
        this.daoBook = daoBook;
        this.daoBookLoan = daoBookLoan;
    }
	
	public List<Book> getAllBooks() throws SQLException, ClassNotFoundException {
        return daoBook.getAll();
    }

    public Book getBookById(int id) throws SQLException, ClassNotFoundException {
        if (id <= 0) {
            throw new IllegalArgumentException("The ID must be > zero");
        }
        
        Book book = daoBook.getById(id);
        if(book != null) {
        	return book;
        } else {
        	throw new IllegalArgumentException("A book with this ID does not exist.");
        }
    }

    public void createBook(Book book) throws SQLException, ClassNotFoundException {
        validateBook(book);
        if (daoBook.existsByTitle(book.getTitle())) {
            throw new IllegalArgumentException("A book with this title already exists.");
        }
        daoBook.create(book);
    }

    public void updateBook(int id, Book book) throws SQLException, ClassNotFoundException {
        if (daoBook.getById(id) == null) {
            throw new IllegalArgumentException("Book not found for update.");
        }

        book.setId(id);
        validateBook(book);
        daoBook.update(book);
    }

    public void deleteBook(int id) throws SQLException, ClassNotFoundException {
        if (daoBook.getById(id) == null) {
            throw new IllegalArgumentException("Book not found for deletion.");
        }
        
        boolean loansActive = daoBookLoan.existsLoanWithBookId(id);
        
        if(loansActive) {
        	throw new IllegalArgumentException("Delete not allowed. There are loans with this ID book.");
        }
        
        Book book = new Book();
        book.setId(id);
        boolean bookDeleted = daoBook.delete(book);
        if (!bookDeleted) {
            throw new RuntimeException("Failed to delete the book. Please try again.");
        }
    }

    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("The book title cannot be empty.");
        }
        if (book.getCopiesAvailable() < 0) {
            throw new IllegalArgumentException("The number of available copies cannot be negative.");
        }
    }
}