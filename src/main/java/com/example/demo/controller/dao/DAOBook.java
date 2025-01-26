package com.example.demo.controller.dao;

import com.example.demo.controller.model.Book;
import com.example.demo.config.MySQLConn;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DAOBook {
    public DAOBook() throws SQLException, ClassNotFoundException {
    	this.createTable();
    }

    public void createTable() throws SQLException, ClassNotFoundException {
        final Connection conn = MySQLConn.getConnection();

        if (conn == null) {
            System.err.println("Connection with database failed :(");
            return;
        }

        String sql = "CREATE TABLE IF NOT EXISTS BOOK ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "title VARCHAR(255) NOT NULL, "
                + "copiesAvailable INT NOT NULL);";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
            System.out.println("Book table created!");
        } catch (SQLException e) {
            System.err.println("Error while creating book table: " + e.getMessage());
            throw e;
        }
    }
    
    public List<Book> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM BOOK";
        List<Book> books = new ArrayList<>();

        try (final Connection conn = MySQLConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book bookDTO = new Book();
                bookDTO.setId(rs.getInt("id"));
                bookDTO.setTitle(rs.getString("title"));
                bookDTO.setCopiesAvailable(rs.getInt("copiesAvailable"));
                books.add(bookDTO);
            }

            books.forEach(book -> {
                System.out.println("Book ID: " + book.getId());
                System.out.println("Title: " + book.getTitle());
                System.out.println("Amount: " + book.getCopiesAvailable());
            });

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error while searching the books: " + e.getMessage());
            throw e;
        }

        return books;
    }
    
    public Book getById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM BOOK WHERE id = ?";
        Book bookDTO = null;

        try (Connection conn = MySQLConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	bookDTO = new Book();
                	bookDTO.setId(rs.getInt("id"));
                	bookDTO.setTitle(rs.getString("title"));
                	bookDTO.setCopiesAvailable(rs.getInt("copiesAvailable"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error while searching a book by ID: " + e.getMessage());
            throw e;
        }
        return bookDTO;
    }

    public void create(Book book) throws SQLException, ClassNotFoundException {
        Connection conn = MySQLConn.getConnection();

        if (conn == null)
        {
            System.err.println("Connection to the database was not established.");
            return;
        }

        String sql = "INSERT INTO BOOK (title, copiesAvailable) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getCopiesAvailable());
            ps.execute();
            System.out.println("Book registered successfully!");
        } catch (SQLException e) {
            System.err.println("Error while creating book: " + e.getMessage());
            throw e;
        }
    }
    
    public void update(Book bookDTO) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE BOOK SET title = ?, copiesAvailable = ? WHERE id = ?";

        try (Connection conn = MySQLConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bookDTO.getTitle());
            ps.setInt(2, bookDTO.getCopiesAvailable());
            ps.setInt(3, bookDTO.getId());

            int search = ps.executeUpdate();

            if (search > 0) {
                System.out.println("Error deleting book:!");
            } else {
                System.out.println("Book not found.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error updating book: " + e.getMessage());
            throw e;
        }
    }
    
    public boolean delete(Book bookDTO) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM BOOK WHERE id = ?";
        try (Connection conn = MySQLConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookDTO.getId());
            int rowsAffected = ps.executeUpdate(); 
            return rowsAffected > 0; 
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error when deleting book: " + e.getMessage());
            throw e;
        }
    }

    
    public boolean existsByTitle(String title) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM BOOK WHERE title = ?";
        try (Connection conn = MySQLConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error checking existence of title: " + e.getMessage());
            throw e;
        }
        return false;
    }
    
    
    public void decrementCopiesAvailable(int bookId, int amount) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE BOOK SET copiesAvailable = copiesAvailable - ? WHERE id = ? AND copiesAvailable > 0";
        try (Connection conn = MySQLConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

        	ps.setInt(1, amount);
            ps.setInt(2, bookId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new IllegalArgumentException("No available copies for book ID: " + bookId);
            }
        } catch (SQLException e) {
            System.err.println("Error updating copies available: " + e.getMessage());
            throw e;
        }
    }


}
