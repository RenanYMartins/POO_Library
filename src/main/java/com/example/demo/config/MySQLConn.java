package com.example.demo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConn {
    private static Connection conn = null;
    private static final String USER = "root";
    private static final String PASSWORD = "admin";
    private static final String URL = "jdbc:mysql://localhost:3306/library";

    private static void connect() throws ClassNotFoundException, SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão realizada com sucesso!");
            } catch (SQLException e) {
                System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
                throw e;
            }
        }
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        connect();
        return conn;
    }
}
