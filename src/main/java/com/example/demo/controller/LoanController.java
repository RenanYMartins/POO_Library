package com.example.demo.controller;

import com.example.demo.controller.model.Book;
import com.example.demo.controller.model.BookLoan;
import com.example.demo.controller.model.Loan;
import com.example.demo.controller.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {
	private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }
    
    @GetMapping
    public ResponseEntity<List<Loan>> listAll() {
        try {
            List<Loan> loans = loanService.getAllLoans();
            return new ResponseEntity<>(loans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            Loan loan = loanService.getLoanById(id);
            return new ResponseEntity<>(loan, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping
    public ResponseEntity<String> insertLoan(@RequestBody Loan loan) {
        try {
            loanService.createLoan(loan);
            return new ResponseEntity<>("Empréstimo criado com sucesso!", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao criar empréstimo.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
