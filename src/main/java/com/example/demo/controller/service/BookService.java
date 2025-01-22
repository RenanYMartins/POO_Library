package com.example.demo.controller.service;

import com.example.demo.controller.dao.DAOBook;
import com.example.demo.controller.model.Book;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Component
public class BookService {
	private final DAOBook daoBook;
	
	public BookService(DAOBook daoBook) {
        this.daoBook = daoBook;
    }
	
	public List<Book> getAllBooks() throws SQLException, ClassNotFoundException {
        return daoBook.getAll();
    }

    public Book getBookById(int id) throws SQLException, ClassNotFoundException {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID do livro deve ser maior que zero.");
        }
        return daoBook.getById(id);
    }

    public void createBook(Book book) throws SQLException, ClassNotFoundException {
        validateBook(book);
        daoBook.create(book);
    }

    public void updateBook(int id, Book book) throws SQLException, ClassNotFoundException {
        if (daoBook.getById(id) == null) {
            throw new IllegalArgumentException("Livro não encontrado para atualização.");
        }

        book.setId(id);
        validateBook(book);
        daoBook.update(book);
    }

    public void deleteBook(int id) throws SQLException, ClassNotFoundException {
        if (daoBook.getById(id) == null) {
            throw new IllegalArgumentException("Livro não encontrado para exclusão.");
        }
        Book book = new Book();
        book.setId(id);
        daoBook.delete(book);
    }

    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("O título do livro não pode ser vazio.");
        }
        if (book.getCopiesAvailable() < 0) {
            throw new IllegalArgumentException("A quantidade de cópias disponíveis não pode ser negativa.");
        }
    }
}