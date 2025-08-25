package org.example.dao;

import org.example.db.Conexao;
import org.example.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public boolean adicionarOuAtualizarEstoque(String nome, double preco, int quantidade) {
        Produto existente = buscarPorNome(nome);
        if (existente == null) {
            return adicionar(new Produto(nome, preco, quantidade));
        } else {
            return atualizarEstoque(existente.getId(), existente.getQuantidade() + quantidade, preco);
        }
    }

    public boolean adicionar(Produto p) {
        String sql = "INSERT INTO produtos (nome, preco, quantidade) VALUES (?,?,?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setDouble(2, p.getPreco());
            ps.setInt(3, p.getQuantidade());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarEstoque(int id, int novaQtd, double novoPreco) {
        String sql = "UPDATE produtos SET quantidade=?, preco=? WHERE id=?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, novaQtd);
            ps.setDouble(2, novoPreco);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean retirarDoEstoque(int id, int qtd) throws SQLException {
        String select = "SELECT quantidade FROM produtos WHERE id=?";
        String update = "UPDATE produtos SET quantidade = quantidade - ? WHERE id=? AND quantidade >= ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement psSel = c.prepareStatement(select);
             PreparedStatement psUpd = c.prepareStatement(update)) {
            psSel.setInt(1, id);
            try (ResultSet rs = psSel.executeQuery()) {
                if (rs.next()) {
                    int atual = rs.getInt(1);
                    if (atual < qtd) return false;
                } else {
                    return false;
                }
            }
            psUpd.setInt(1, qtd);
            psUpd.setInt(2, id);
            psUpd.setInt(3, qtd);
            return psUpd.executeUpdate() > 0;
        }
    }

    public boolean removerPorNome(String nome) {
        String sql = "DELETE FROM produtos WHERE nome=?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nome);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Produto buscarPorNome(String nome) {
        String sql = "SELECT * FROM produtos WHERE nome=?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getDouble("preco"),
                            rs.getInt("quantidade")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM produtos WHERE id=?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getDouble("preco"),
                            rs.getInt("quantidade")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos ORDER BY nome";
        try (Connection c = Conexao.conectar();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

