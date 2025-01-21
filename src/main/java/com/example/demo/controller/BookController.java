package com.example.demo.controller;

import com.example.demo.controller.dao.*;

import com.example.demo.controller.model.Book;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
	private  final DAOBook daoBook;
	
	public BookController(DAOBook daoBook) {
		this.daoBook = daoBook;
	}
	
	@GetMapping
    public ResponseEntity<List<Book>> listAll() {
        try {
            List<Book> books = daoBook.getAll();
            return new ResponseEntity<>(books, HttpStatus.OK); 
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  
        }
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable int id) {
        try {
            Book book = daoBook.getById(id);
            if (book == null) {            	
            	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            	
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
	
	@PostMapping
    public ResponseEntity<String> insertBook(@RequestBody Book book) {
        try { 
            daoBook.create(book);
            return new ResponseEntity<>("Livro criado com sucesso!", HttpStatus.CREATED);  
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>("Erro ao criar livro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Book book) {
        try {
        	Book isBookValid = daoBook.getById(id);
        	
        	if(isBookValid == null) {
        		return new ResponseEntity<>("Livro não encontrado", HttpStatus.NOT_FOUND);
        	}
            
        	book.setId(id);
            daoBook.update(book);
            return new ResponseEntity<>("Livro atualizado com sucesso!", HttpStatus.OK); 
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>("Erro ao atualizar livro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            Book book = new Book();
            
        	book= daoBook.getById(id);
        	
        	if(book == null) {
        		return new ResponseEntity<>("Livro não encontrado", HttpStatus.NOT_FOUND);
        	}
        	
            book.setId(id);
            daoBook.delete(book);
            return new ResponseEntity<>("Livro deletado com sucesso!", HttpStatus.NO_CONTENT);  
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>("Erro ao deletar livro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
