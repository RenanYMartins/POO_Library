package com.example.demo.controller;

import com.example.demo.controller.model.BookLoan;
//import com.example.demo.controller.service.BookLoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/booksLoan")
public class BookLoanController {
//	private final BookLoanService bookLoanService;
//
//    public BookLoanController(BookLoanService bookLoanService) {
//        this.bookLoanService = bookLoanService;
//    }
//    
//    @PostMapping
//    public ResponseEntity<String> insertBookLoan(@RequestBody BookLoan bookLoan) {
//        try {
//            bookLoanService.createBookLoan(bookLoan);
//            return new ResponseEntity<>("Empréstimo criado com sucesso!", HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Erro ao criar livro.", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    		

}
