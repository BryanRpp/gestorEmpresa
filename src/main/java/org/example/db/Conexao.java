package org.example.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try {
            Properties p = new Properties();
            InputStream is = Conexao.class.getClassLoader().getResourceAsStream("application.properties");
            if (is == null) throw new RuntimeException("application.properties não encontrado");
            p.load(is);
            URL = p.getProperty("db.url");
            USER = p.getProperty("db.user");
            PASSWORD = p.getProperty("db.password");
        } catch (Exception e) {
            throw new RuntimeException("Erro carregando configuração de banco: " + e.getMessage(), e);
        }
    }

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
