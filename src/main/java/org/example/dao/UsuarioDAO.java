package org.example.dao;

import org.example.db.Conexao;
import org.example.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void criarAdminSeVazio() {
        String countSql = "SELECT COUNT(*) FROM usuarios";
        try (Connection c = Conexao.conectar();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(countSql)) {
            if (rs.next() && rs.getInt(1) == 0) {
                cadastrar(new Usuario("admin","0000-0000","admin@sistema.com","1234","Admin"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean cadastrar(Usuario u) {
        String sql = "INSERT INTO usuarios (nome, telefone, email, senha, nivel) VALUES (?,?,?,?,?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getTelefone());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getSenha());
            ps.setString(5, u.getNivel());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario login(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email=? AND senha=?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getString("nivel")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY nome";
        try (Connection c = Conexao.conectar();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("nivel")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
