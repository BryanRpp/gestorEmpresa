package org.example.dao;

import org.example.db.Conexao;
import org.example.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void salvar(Produto produto) {
        String sql = "INSERT INTO produtos (nome, categoria, marca, unidade_medida, preco_custo, preco_venda, margem, estoque_atual, estoque_minimo, estoque_maximo, ponto_reposicao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getCategoria());
            stmt.setString(3, produto.getMarca());
            stmt.setString(4, produto.getUnidadeMedida());
            stmt.setDouble(5, produto.getPrecoCusto());
            stmt.setDouble(6, produto.getPrecoVenda());
            stmt.setDouble(7, produto.getMargem());
            stmt.setInt(8, produto.getEstoqueAtual());
            stmt.setInt(9, produto.getEstoqueMinimo());
            stmt.setInt(10, produto.getEstoqueMaximo());
            stmt.setInt(11, produto.getPontoReposicao());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto", e);
        }
    }

    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produto p = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("categoria"),
                        rs.getString("marca"),
                        rs.getString("unidade_medida"),
                        rs.getDouble("preco_custo"),
                        rs.getDouble("preco_venda"),
                        rs.getDouble("margem"),
                        rs.getInt("estoque_atual"),
                        rs.getInt("estoque_minimo"),
                        rs.getInt("estoque_maximo"),
                        rs.getInt("ponto_reposicao")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos", e);
        }
        return lista;
    }
}
