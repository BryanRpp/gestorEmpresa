package org.example.dao;

import org.example.db.Conexao;
import org.example.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void inserir(Produto p) throws SQLException {
        String sql = "INSERT INTO produtos (nome,categoria,marca,unidade_medida,preco_custo,preco_venda,estoque_atual,estoque_minimo,estoque_maximo,ponto_reposicao) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = Conexao.obter();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCategoria());
            ps.setString(3, p.getMarca());
            ps.setString(4, p.getUnidadeMedida());
            ps.setDouble(5, p.getPrecoCusto());
            ps.setDouble(6, p.getPrecoVenda());
            ps.setInt(7, p.getEstoqueAtual());
            ps.setInt(8, p.getEstoqueMinimo());
            ps.setInt(9, p.getEstoqueMaximo());
            ps.setInt(10, p.getPontoReposicao());
            ps.executeUpdate();
        }
    }

    public void atualizar(Produto p) throws SQLException {
        String sql = "UPDATE produtos SET nome=?,categoria=?,marca=?,unidade_medida=?,preco_custo=?,preco_venda=?,estoque_atual=?,estoque_minimo=?,estoque_maximo=?,ponto_reposicao=? WHERE id=?";
        try (Connection c = Conexao.obter();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCategoria());
            ps.setString(3, p.getMarca());
            ps.setString(4, p.getUnidadeMedida());
            ps.setDouble(5, p.getPrecoCusto());
            ps.setDouble(6, p.getPrecoVenda());
            ps.setInt(7, p.getEstoqueAtual());
            ps.setInt(8, p.getEstoqueMinimo());
            ps.setInt(9, p.getEstoqueMaximo());
            ps.setInt(10, p.getPontoReposicao());
            ps.setInt(11, p.getId());
            ps.executeUpdate();
        }
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM produtos WHERE id=?";
        try (Connection c = Conexao.obter();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Produto buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM produtos WHERE id=?";
        try (Connection c = Conexao.obter();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public List<Produto> listarTodos() throws SQLException {
        String sql = "SELECT * FROM produtos ORDER BY nome";
        try (Connection c = Conexao.obter();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Produto> lista = new ArrayList<>();
            while (rs.next()) lista.add(map(rs));
            return lista;
        }
    }

    public List<Produto> abaixoDoMinimoOuReposicao() throws SQLException {
        String sql = "SELECT * FROM produtos WHERE estoque_atual <= GREATEST(estoque_minimo, ponto_reposicao)";
        try (Connection c = Conexao.obter();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Produto> lista = new ArrayList<>();
            while (rs.next()) lista.add(map(rs));
            return lista;
        }
    }

    public List<Produto> acimaDoMaximo() throws SQLException {
        String sql = "SELECT * FROM produtos WHERE estoque_atual >= estoque_maximo AND estoque_maximo > 0";
        try (Connection c = Conexao.obter();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Produto> lista = new ArrayList<>();
            while (rs.next()) lista.add(map(rs));
            return lista;
        }
    }

    private Produto map(ResultSet rs) throws SQLException {
        Produto p = new Produto();
        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setCategoria(rs.getString("categoria"));
        p.setMarca(rs.getString("marca"));
        p.setUnidadeMedida(rs.getString("unidade_medida"));
        p.setPrecoCusto(rs.getDouble("preco_custo"));
        p.setPrecoVenda(rs.getDouble("preco_venda"));
        p.setEstoqueAtual(rs.getInt("estoque_atual"));
        p.setEstoqueMinimo(rs.getInt("estoque_minimo"));
        p.setEstoqueMaximo(rs.getInt("estoque_maximo"));
        p.setPontoReposicao(rs.getInt("ponto_reposicao"));
        return p;
    }
}
