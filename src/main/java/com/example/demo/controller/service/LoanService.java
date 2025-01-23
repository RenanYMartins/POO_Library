package com.example.demo.controller.service;

import com.example.demo.controller.dao.DAOLoan;
import com.example.demo.controller.model.Book;
import com.example.demo.controller.model.Loan;
//import com.example.demo.controller.model.BookLoan;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Component
public class LoanService {
	private final DAOLoan daoLoan;
	
	public LoanService(DAOLoan daoLoan) {
        this.daoLoan = daoLoan;
    }
	
	public List<Loan> getAllLoans() throws SQLException, ClassNotFoundException {
        return daoLoan.getAll();
    }
	
	public Loan getLoanById(int id) throws SQLException, ClassNotFoundException {
        if (id <= 0) {
            throw new IllegalArgumentException("The ID must be > zero");
        }
        return daoLoan.getById(id);
    }

	public void createLoan(Loan loan) throws SQLException, ClassNotFoundException {
//        validateBookLoan(bookLoan);
        daoLoan.create(loan);
    }
	

}
