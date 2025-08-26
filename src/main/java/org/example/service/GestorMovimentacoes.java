package org.example.service;

import org.example.dao.AuditoriaDAO;
import org.example.dao.MovimentacaoDAO;

import java.sql.SQLException;

public class GestorMovimentacoes {
    private final MovimentacaoDAO movDAO = new MovimentacaoDAO();
    private final AuditoriaDAO audDAO = new AuditoriaDAO();
    private final GestorProdutos gestorProdutos;

    public GestorMovimentacoes(GestorProdutos gestorProdutos) {
        this.gestorProdutos = gestorProdutos;
    }

    public void entrada(int produtoId, int qtd, int usuarioId) throws SQLException {
        movDAO.registrarMovimentacao(produtoId, "Entrada", qtd, "Compra", usuarioId);
        audDAO.registrar(usuarioId, "Entrada de " + qtd + " no produto ID " + produtoId);
    }

    public void saida(int produtoId, int qtd, String motivo, int usuarioId) throws SQLException {
        movDAO.registrarMovimentacao(produtoId, "Saida", qtd, motivo, usuarioId);
        audDAO.registrar(usuarioId, "Saída (" + motivo + ") de " + qtd + " no produto ID " + produtoId);
    }
}
