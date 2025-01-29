package com.example.demo.controller.service;

import com.example.demo.controller.dao.DAOBook;

import com.example.demo.controller.dao.DAOBookLoan;
import com.example.demo.controller.dao.DAOLoan;
import com.example.demo.controller.model.Book;
import com.example.demo.controller.model.Loan;
import com.example.demo.controller.model.BookLoan;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Component
public class LoanService {
	private final DAOLoan daoLoan;
    private final DAOBook daoBook;
    private final DAOBookLoan daoBookLoan;
	
	public LoanService(DAOLoan daoLoan, DAOBook daoBook, DAOBookLoan daoBookLoan) {
		this.daoLoan = daoLoan;
        this.daoBook = daoBook;
        this.daoBookLoan = daoBookLoan;
    }
	
	public List<Loan> getAllLoans() throws SQLException, ClassNotFoundException {
        return daoLoan.getAll();
    }
	
	
	public Loan getLoanDetailsById(int loanId) throws SQLException, ClassNotFoundException {
	    if (loanId <= 0) {
	        throw new IllegalArgumentException("The loan ID must be greater than zero.");
	    }
	    return daoLoan.getLoanWithBookDetails(loanId);
	}


	public void createLoan(Loan loan) throws SQLException, ClassNotFoundException {
	    for (BookLoan bookLoan : loan.getBookLoans()) {
	        Book book = daoBook.getById(bookLoan.getBookId());
	        if (book == null || book.getCopiesAvailable() < bookLoan.getAmount()) {
	            throw new IllegalArgumentException("Book ID " + bookLoan.getBookId() + " is not available in the required quantity.");
	        }
	    }

	    int loanId = daoLoan.create(loan);

	    for (BookLoan bookLoan : loan.getBookLoans()) {
	        daoBookLoan.createBookLoan(loanId, bookLoan.getBookId(), bookLoan.getAmount());
	        daoBook.decrementCopiesAvailable(bookLoan.getBookId(), bookLoan.getAmount());
	    }
	}


	public void returnLoan(int loanId) throws SQLException, ClassNotFoundException {
	    if (loanId <= 0) {
	        throw new IllegalArgumentException("The loan ID must be greater than zero.");
	    }

	    Loan loan = daoLoan.getById(loanId);
	    if (loan == null) {
	        throw new IllegalArgumentException("Loan ID " + loanId + " not found.");
	    }
	    if (loan.getIsReturned()) {
	        throw new IllegalStateException("Loan ID " + loanId + " has already been returned.");
	    }

	    daoLoan.returnLoan(loanId);
	}

	public void deleteLoan(int id) throws SQLException, ClassNotFoundException {
        if (daoLoan.getById(id) == null) {
            throw new IllegalArgumentException("Loan not found for deletion.");
        }
        if (daoLoan.loanActiveById(id)) {
        	throw new IllegalArgumentException("Loan was not returned. It cannot be deleted.");
        }
        Loan loan = new Loan();
        loan.setId(id);
        boolean loanDeleted = daoLoan.delete(loan);
        if (!loanDeleted) {
            throw new RuntimeException("Failed to delete the loan. Please try again.");
        }
    }
}
