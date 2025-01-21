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
    public DAOBook() {}

    public void createTable() throws SQLException, ClassNotFoundException {
        final Connection conn = MySQLConn.getConnection();

        if (conn == null) {
            System.err.println("Conexão com o banco de dados não foi estabelecida.");
            return;
        }

        String sql = "CREATE TABLE IF NOT EXISTS BOOK ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "title VARCHAR(255) NOT NULL, "
                + "copiesAvailable INT NOT NULL)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
            System.out.println("Tabela de livros criada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao criar a tabela: " + e.getMessage());
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
                System.out.println("Livro ID: " + book.getId());
                System.out.println("Título: " + book.getTitle());
                System.out.println("Quantidade: " + book.getCopiesAvailable());
            });

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao buscar livros: " + e.getMessage());
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
            System.err.println("Erro ao buscar livro por ID: " + e.getMessage());
            throw e;
        }

        return bookDTO;
    }

    public void create(Book book) throws SQLException, ClassNotFoundException {
        Connection conn = MySQLConn.getConnection();

        if (conn == null)
        {
            System.err.println("Conexão com o banco de dados não foi estabelecida.");
            return;
        }

        String sql = "INSERT INTO BOOK (title, copiesAvailable) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getCopiesAvailable());
            ps.execute();
            System.out.println("Livro cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir livro: " + e.getMessage());
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
                System.out.println("Livro atualizado com sucesso!");
            } else {
                System.out.println("Livro nao encontrado.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao atualizar livro: " + e.getMessage());
            throw e;
        }
    }
    
    public void delete(Book bookDTO) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM BOOK WHERE id = ?";
        try (Connection conn = MySQLConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookDTO.getId());
            ps.execute();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao deletar livro: " + e.getMessage());
            throw e;
        }
    }
}
