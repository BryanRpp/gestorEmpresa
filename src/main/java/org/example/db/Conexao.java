package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/erp_estoque?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Balão22!";

    public static Connection obter() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // garante driver no classpath
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado!", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
