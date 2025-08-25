package org.example.dao;

import org.example.db.Conexao;

import java.sql.*;
import java.time.LocalDateTime;

public class VendaDAO {

    public boolean registrarVenda(int produtoId, int quantidade, double precoUnitario, boolean desconto) {
        String sql = "INSERT INTO vendas (produto_id, quantidade, preco_unitario, desconto, data_hora) VALUES (?,?,?,?,?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, produtoId);
            ps.setInt(2, quantidade);
            ps.setDouble(3, precoUnitario);
            ps.setBoolean(4, desconto);
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double totalFaturado() {
        String sql = "SELECT SUM(quantidade * preco_unitario * (CASE WHEN desconto THEN 0.9 ELSE 1 END)) FROM vendas";
        try (Connection c = Conexao.conectar();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
