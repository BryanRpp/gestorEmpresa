package org.example.service;

import org.example.dao.ProdutoDAO;
import org.example.model.Produto;

import java.sql.SQLException;
import java.util.List;

public class GestorProdutos {
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    public void salvar(Produto p) throws SQLException {
        if (p.getId() == null) produtoDAO.inserir(p);
        else produtoDAO.atualizar(p);
    }

    public void remover(int id) throws SQLException {
        produtoDAO.remover(id);
    }

    public Produto buscar(int id) throws SQLException {
        return produtoDAO.buscarPorId(id);
    }

    public List<Produto> listar() throws SQLException {
        return produtoDAO.listarTodos();
    }

    public List<Produto> alertasReposicao() throws SQLException {
        return produtoDAO.abaixoDoMinimoOuReposicao();
    }

    public List<Produto> excessoEstoque() throws SQLException {
        return produtoDAO.acimaDoMaximo();
    }
}
