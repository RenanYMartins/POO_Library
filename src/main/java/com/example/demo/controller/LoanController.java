package com.example.demo.controller;

import com.example.demo.controller.model.Loan;
import com.example.demo.controller.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> getLoanDetails(@PathVariable int id) {
        try {
            Loan loan = loanService.getLoanDetailsById(id);
            if (loan == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found");
            }

            return ResponseEntity.ok(Map.of(
                "borrowerName", loan.getBorrowerName(),
                "loanDate", loan.getLoanDate(),
                "returnDate", loan.getReturnDate(),
                "status", loan.getIsReturned() ? "Returned" : "Not Returned",
                "bookTitles", loan.getBookTitles()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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
    
    @PutMapping("/{id}/return")
    public ResponseEntity<?> returnLoan(@PathVariable int id) {
        try {
            loanService.returnLoan(id);
            return ResponseEntity.ok("Loan ID " + id + " has been successfully returned.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            loanService.deleteLoan(id);
            return new ResponseEntity<>("Loan deleted successfully!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error when deleting loan.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
