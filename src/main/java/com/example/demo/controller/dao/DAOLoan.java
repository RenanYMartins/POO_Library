package com.example.demo.controller.dao;

import com.example.demo.controller.model.Book;
import com.example.demo.controller.model.Loan;
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

@Component
public class DAOLoan {
	public DAOLoan() throws SQLException, ClassNotFoundException {
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
                + "returned TINYINT(1) NOT NULL DEFAULT 0);";


        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
            System.out.println("Loan table created!");
        } catch (SQLException e) {
            System.err.println("Error while creating loan table: " + e.getMessage());
            throw e;
        }
    }
    
    
    public List<Loan> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM LOAN";
        List<Loan> loans = new ArrayList<>();

        try (final Connection conn = MySQLConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Loan loanDTO = new Loan();
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
                loans.add(loanDTO);
            }

            loans.forEach(loan -> {
                System.out.println("Loan ID: " + loan.getId());
                System.out.println("Borrower Name : " + loan.getBorrowerName());
                System.out.println("Loan Date: " + loan.getLoanDate());
                System.out.println("Return Date: " + loan.getReturnDate());
                System.out.println("Empréstimo ativo: " + loan.getIsReturned());
            });

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error while searching the loans: " + e.getMessage());
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
    
    
    public void create(Loan loan) throws SQLException, ClassNotFoundException {
        Connection conn = MySQLConn.getConnection();

        if (conn == null) {
            System.err.println("Connection with database failed :(");
            return;
        }

        String sql = "INSERT INTO LOAN (borrowerName, loanDate, returnDate) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, loan.getBorrowerName());
            ps.setDate(2, java.sql.Date.valueOf(loan.getLoanDate())); 
            ps.setDate(3, java.sql.Date.valueOf(loan.getReturnDate()));
            
            ps.execute();
            System.out.println("Loan created with success!");
        } catch (SQLException e) {
            System.err.println("Error while creating loan: " + e.getMessage());
            throw e;
        }
    }
    
    
//    public void update(Loan loanDTO) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE LOAN SET borrowerName = ?, copiesAvailable = ? WHERE id = ?";
//
//        try (Connection conn = MySQLConn.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setString(1, bookDTO.getTitle());
//            ps.setInt(2, bookDTO.getCopiesAvailable());
//            ps.setInt(3, bookDTO.getId());
//
//            int search = ps.executeUpdate();
//
//            if (search > 0) {
//                System.out.println("Livro atualizado com sucesso!");
//            } else {
//                System.out.println("Livro nao encontrado.");
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            System.err.println("Erro ao atualizar livro: " + e.getMessage());
//            throw e;
//        }
//    }
}
