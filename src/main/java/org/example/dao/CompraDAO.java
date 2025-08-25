package org.example.dao;

import org.example.db.Conexao;

import java.sql.*;
import java.time.LocalDateTime;

public class CompraDAO {

    public boolean registrarCompra(int produtoId, int quantidade, double precoUnitario) {
        String sql = "INSERT INTO compras (produto_id, quantidade, preco_unitario, data_hora) VALUES (?,?,?,?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, produtoId);
            ps.setInt(2, quantidade);
            ps.setDouble(3, precoUnitario);
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double totalDespesas() {
        String sql = "SELECT SUM(quantidade * preco_unitario) FROM compras";
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

