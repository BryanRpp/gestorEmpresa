package org.example.dao;

import org.example.db.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuditoriaDAO {
    public void registrar(int usuarioId, String acao) {
        String sql = "INSERT INTO auditoria (usuario_id, acao) VALUES (?,?)";
        try (Connection c = Conexao.obter();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setString(2, acao);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
