package org.example.dao;

import org.example.db.Conexao;
import org.example.model.Usuario;

import java.sql.*;

public class UsuarioDAO {

    public Usuario autenticar(String email, String senha) {
        String sql = "SELECT id,nome,email,senha,nivel FROM usuarios WHERE email=? AND senha=?";
        try (Connection c = Conexao.obter();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("nivel")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Garante que há um Admin; usa o mesmo do schema, mas deixa idempotente. */
    public void criarAdminSeVazio() {
        String count = "SELECT COUNT(*) FROM usuarios";
        String insert = "INSERT INTO usuarios(nome,email,senha,nivel) VALUES('Administrador','admin@sistema.com','admin123','Admin')";
        try (Connection c = Conexao.obter();
             Statement st = c.createStatement()) {
            ResultSet rs = st.executeQuery(count);
            rs.next();
            if (rs.getInt(1) == 0) {
                st.executeUpdate(insert);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
