package com.example.demo.controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.example.demo.config.MySQLConn;

@Component
public class DAOBookLoan {
	public DAOBookLoan() throws SQLException, ClassNotFoundException { }
	
	public void createTable() throws SQLException, ClassNotFoundException {
	    final Connection conn = MySQLConn.getConnection();

	    if (conn == null) {
	        System.err.println("Connection with database failed :(");
	        return;
	    }

	    String sql = "CREATE TABLE IF NOT EXISTS BOOK_LOAN ("
	            + "id INT AUTO_INCREMENT PRIMARY KEY, "
	            + "bookId INT NOT NULL, "
	            + "loanId INT NOT NULL, "
	            + "amount INT NOT NULL,"
	            + "FOREIGN KEY (bookId) REFERENCES BOOK(id) ON DELETE CASCADE, "
	            + "FOREIGN KEY (loanId) REFERENCES LOAN(id) ON DELETE CASCADE);";

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.executeUpdate();
	        System.out.println("BookLoan table created!");
	    } catch (SQLException e) {
	        System.err.println("Error while creating BookLoan table: " + e.getMessage());
	        throw e;
	    }
	}
	
	public void createBookLoan(int loanId, int bookId, int amount) throws SQLException, ClassNotFoundException {
	    String sql = "INSERT INTO BOOK_LOAN (loanId, bookId, amount) VALUES (?, ?, ?)";
	    try (Connection conn = MySQLConn.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, loanId);
	        ps.setInt(2, bookId);
	        ps.setInt(3, amount);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        System.err.println("Error while inserting into BOOK_LOAN: " + e.getMessage());
	        throw e;
	    }
	}
	
	public boolean existsLoanWithBookId(int id) throws SQLException, ClassNotFoundException {
	    String sql = "SELECT COUNT(*) FROM BOOK_LOAN WHERE bookId = ?";
	    boolean exists = false;

	    try (Connection conn = MySQLConn.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, id);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                exists = rs.getInt(1) > 0; 
	            }
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        System.err.println("Error while checking book loan existence: " + e.getMessage());
	        throw e;
	    }

	    return exists;
	}

}
