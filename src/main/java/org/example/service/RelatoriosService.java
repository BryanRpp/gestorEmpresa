package org.example.service;

import org.example.dao.MovimentacaoDAO;
import org.example.model.Produto;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RelatoriosService {
    private final GestorProdutos gestorProdutos;
    private final MovimentacaoDAO movDAO = new MovimentacaoDAO();

    public RelatoriosService(GestorProdutos gestorProdutos) {
        this.gestorProdutos = gestorProdutos;
    }

    public List<Produto> produtosParados() throws SQLException {
        // Critério simples: estoque alto e não em alerta de reposição (poderia olhar movimentações)
        return gestorProdutos.excessoEstoque();
    }

    public List<String> movimentacoesPeriodo(LocalDate ini, LocalDate fim) throws SQLException {
        return movDAO.listarTextoPorPeriodo(ini.atStartOfDay().toString(), fim.plusDays(1).atStartOfDay().toString());
    }
}
