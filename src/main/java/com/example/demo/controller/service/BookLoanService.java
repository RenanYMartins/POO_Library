package com.example.demo.controller.service;

import com.example.demo.controller.dao.DAOBook;
import com.example.demo.controller.dao.DAOBookLoan;
import com.example.demo.controller.model.Book;
import com.example.demo.controller.model.BookLoan;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Component
public class BookLoanService {
private final DAOBookLoan daoBookLoan;
	
	public BookLoanService(DAOLoanBook daoLoanBook) {
        this.daoLoanBook = daoLoanBook;
    }

	public void createBook(BookLoan bookLoan) throws SQLException, ClassNotFoundException {
//        validateBookLoan(bookLoan);
        daoBookLoan.create(bookLoan);
    }
	
	private void validateBook(BookLoan bookLoan) {
//        if (bookLoan.getTitle() == null || book.getTitle().trim().isEmpty()) {
//            throw new IllegalArgumentException("O título do livro não pode ser vazio.");
//        }
//        if (book.getCopiesAvailable() < 0) {
//            throw new IllegalArgumentException("A quantidade de cópias disponíveis não pode ser negativa.");
//        }
    }
}
