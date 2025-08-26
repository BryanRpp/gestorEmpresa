package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/erp_estoque?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root"; // coloque seu usuário do MySQL
    private static final String PASSWORD = "Balão22!"; // coloque sua senha do MySQL

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // carrega o driver do MySQL
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC do MySQL não encontrado!", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados!", e);
        }
    }
}
