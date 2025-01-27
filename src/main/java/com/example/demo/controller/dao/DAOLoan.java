package com.example.demo.controller.dao;

import com.example.demo.controller.model.Book;
import com.example.demo.controller.model.Loan;
import com.example.demo.controller.dao.DAOBookLoan;
import com.example.demo.config.MySQLConn;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Component
public class DAOLoan {
	
	private DAOBookLoan daoBookLoan;


	public DAOLoan(DAOBookLoan daoBookLoan) throws SQLException, ClassNotFoundException {
        this.daoBookLoan = daoBookLoan;
        this.createTable();  
    }

    public void createTable() throws SQLException, ClassNotFoundException {
        final Connection conn = MySQLConn.getConnection();

        if (conn == null) {
            System.err.println("Connection with database failed :(");
            return;
        }

        String sql = "CREATE TABLE IF NOT EXISTS LOAN ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "borrowerName VARCHAR(100) NOT NULL, "
                + "loanDate DATE NOT NULL, "
                + "returnDate DATE,"
                + "returned BOOLEAN);";


        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
            System.out.println("Loan table created!");
        
            daoBookLoan.createTable(); 
            
        } catch (SQLException e) {
            System.err.println("Error while creating loan table: " + e.getMessage());
            throw e;
        }
    }
    
    public List<Loan> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT " +
                     "l.id AS loanId, " +
                     "l.borrowerName, " +
                     "l.loanDate, " +
                     "l.returnDate, " +
                     "l.returned, " +
                     "b.title AS bookTitle, " +
                     "bl.amount AS bookAmount " +
                     "FROM LOAN l " +
                     "LEFT JOIN BOOK_LOAN bl ON l.id = bl.loanId " +
                     "LEFT JOIN BOOK b ON bl.bookId = b.id " +
                     "ORDER BY l.id";

        List<Loan> loans = new ArrayList<>();
        Map<Integer, Loan> loanMap = new HashMap<>();

        try (Connection conn = MySQLConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int loanId = rs.getInt("loanId");

                Loan loan = loanMap.get(loanId);
                if (loan == null) {
                    loan = new Loan();
                    loan.setId(loanId);
                    loan.setBorrowerName(rs.getString("borrowerName"));
                    Date loanDate = rs.getDate("loanDate");
                    loan.setLoanDate(loanDate != null ? loanDate.toLocalDate() : null);
                    Date returnDate = rs.getDate("returnDate");
                    loan.setReturnDate(returnDate != null ? returnDate.toLocalDate() : null);
                    loan.setReturned(rs.getBoolean("returned"));
                    loan.setBookTitles(new ArrayList<>());
                    
                    loans.add(loan);
                    loanMap.put(loanId, loan);
                }

                String bookTitle = rs.getString("bookTitle");
                int bookAmount = rs.getInt("bookAmount");
                if (bookTitle != null) {
                    loan.getBookTitles().add(bookTitle + " (Quantity: " + bookAmount + ")");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving all loans: " + e.getMessage());
            throw e;
        }

        return loans;
    }

    public Loan getById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM LOAN WHERE id = ?";
        Loan loanDTO = null;

        try (Connection conn = MySQLConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	loanDTO = new Loan();
                    loanDTO.setId(rs.getInt("id"));
                    loanDTO.setBorrowerName(rs.getString("borrowerName"));
                    Date loanDate = rs.getDate("loanDate");
                    if (loanDate != null) {
                        loanDTO.setLoanDate(loanDate.toLocalDate());
                    }
                    Date returnDate = rs.getDate("returnDate");
                    if (returnDate != null) {
                    	loanDTO.setReturnDate(returnDate.toLocalDate());
                    }
                    loanDTO.setReturned(rs.getBoolean("returned"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error while searching a loan by ID: " + e.getMessage());
            throw e;
        }
        return loanDTO;
    }    
    
    public int create(Loan loan) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO LOAN (borrowerName, loanDate, returnDate) VALUES (?, ?, ?)";
        try (Connection conn = MySQLConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, loan.getBorrowerName());
            ps.setDate(2, java.sql.Date.valueOf(loan.getLoanDate()));
            ps.setDate(3, java.sql.Date.valueOf(loan.getReturnDate()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while creating loan: " + e.getMessage());
            throw e;
        }
        throw new SQLException("Creating loan failed, no ID obtained.");
    }

    

    public Loan getLoanWithBookDetails(int loanId) throws SQLException, ClassNotFoundException {
	    String loanSql = "SELECT l.id, l.borrowerName, l.loanDate, l.returnDate, l.returned " +
	                     "FROM LOAN l WHERE l.id = ?";
	    String bookDetailsSql = "SELECT b.title, bl.amount FROM BOOK_LOAN bl " +
	                            "JOIN BOOK b ON bl.bookId = b.id WHERE bl.loanId = ?";
	    
	    Loan loan = null;

	    try (Connection conn = MySQLConn.getConnection();
	         PreparedStatement loanStmt = conn.prepareStatement(loanSql);
	         PreparedStatement bookStmt = conn.prepareStatement(bookDetailsSql)) {

	        loanStmt.setInt(1, loanId);
	        try (ResultSet loanRs = loanStmt.executeQuery()) {
	            if (loanRs.next()) {
	                loan = new Loan();
	                loan.setId(loanRs.getInt("id"));
	                loan.setBorrowerName(loanRs.getString("borrowerName"));
	                Date loanDate = loanRs.getDate("loanDate");
	                loan.setLoanDate(loanDate != null ? loanDate.toLocalDate() : null);
	                Date returnDate = loanRs.getDate("returnDate");
	                loan.setReturnDate(returnDate != null ? returnDate.toLocalDate() : null);
	                loan.setReturned(loanRs.getBoolean("returned"));
	            }
	        }

	        if (loan != null) {
	            bookStmt.setInt(1, loanId);
	            try (ResultSet bookRs = bookStmt.executeQuery()) {
	                List<String> bookDetails = new ArrayList<>();
	                while (bookRs.next()) {
	                    String title = bookRs.getString("title");
	                    int amount = bookRs.getInt("amount");
	                    bookDetails.add(title + " (Quantity: " + amount + ")");
	                }
	                loan.setBookTitles(bookDetails);
	            }
	        }
	    }
	    return loan;
	}
    
    
    public void returnLoan(int loanId) throws SQLException, ClassNotFoundException {
        String updateLoanSql = "UPDATE LOAN SET returned = true WHERE id = ?";
        String getBookLoanSql = "SELECT bookId, amount FROM BOOK_LOAN WHERE loanId = ?";
        String updateBookStockSql = "UPDATE BOOK SET copiesAvailable = copiesAvailable + ? WHERE id = ?";

        try (Connection conn = MySQLConn.getConnection();
             PreparedStatement updateLoanStmt = conn.prepareStatement(updateLoanSql);
             PreparedStatement getBookLoanStmt = conn.prepareStatement(getBookLoanSql);
             PreparedStatement updateBookStockStmt = conn.prepareStatement(updateBookStockSql)) {

            updateLoanStmt.setInt(1, loanId);
            int rowsUpdated = updateLoanStmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("No loan found with ID: " + loanId);
            }

            getBookLoanStmt.setInt(1, loanId);
            try (ResultSet rs = getBookLoanStmt.executeQuery()) {
                while (rs.next()) {
                    int bookId = rs.getInt("bookId");
                    int amount = rs.getInt("amount");

                    updateBookStockStmt.setInt(1, amount);
                    updateBookStockStmt.setInt(2, bookId);
                    updateBookStockStmt.executeUpdate();
                }
            }
        }
    }
    
    public boolean delete(Loan loanDTO) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM LOAN WHERE id = ?";
        try (Connection conn = MySQLConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, loanDTO.getId());
            int rowsAffected = ps.executeUpdate(); 
            return rowsAffected > 0; 
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error when deleting loan: " + e.getMessage());
            throw e;
        }
    }


}
