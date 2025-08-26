package org.example.dao;

import org.example.db.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimentacaoDAO {

    /** Registra movimentação e atualiza estoque em transação. */
    public void registrarMovimentacao(int produtoId, String tipo, int quantidade, String motivo, int usuarioId) throws SQLException {
        String insertMov = "INSERT INTO movimentacoes (produto_id,tipo,quantidade,motivo,usuario_id) VALUES (?,?,?,?,?)";
        String updEstoque = ( "Entrada".equalsIgnoreCase(tipo) ?
                "UPDATE produtos SET estoque_atual = estoque_atual + ? WHERE id=?"
                : "UPDATE produtos SET estoque_atual = GREATEST(0, estoque_atual - ?) WHERE id=?");

        try (Connection c = Conexao.obter()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps1 = c.prepareStatement(insertMov);
                 PreparedStatement ps2 = c.prepareStatement(updEstoque)) {

                ps1.setInt(1, produtoId);
                ps1.setString(2, tipo);
                ps1.setInt(3, quantidade);
                ps1.setString(4, motivo);
                ps1.setInt(5, usuarioId);
                ps1.executeUpdate();

                ps2.setInt(1, quantidade);
                ps2.setInt(2, produtoId);
                ps2.executeUpdate();

                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    public List<String> listarTextoPorPeriodo(String inicioISO, String fimISO) throws SQLException {
        String sql = "SELECT m.id, p.nome, m.tipo, m.quantidade, m.motivo, m.data " +
                "FROM movimentacoes m JOIN produtos p ON p.id = m.produto_id " +
                "WHERE m.data BETWEEN ? AND ? ORDER BY m.data DESC";
        List<String> out = new ArrayList<>();
        try (Connection c = Conexao.obter();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, inicioISO);
            ps.setString(2, fimISO);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.add(String.format("#%d - %s | %s %d | %s | %s",
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("tipo"),
                        rs.getInt("quantidade"),
                        rs.getString("motivo"),
                        rs.getTimestamp("data").toString()));
            }
        }
        return out;
    }
}
