package org.example.service;

import org.example.dao.CompraDAO;
import org.example.dao.ProdutoDAO;
import org.example.dao.VendaDAO;
import org.example.model.Produto;

import java.sql.SQLException;
import java.util.List;

public class GestorProdutos {
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final VendaDAO vendaDAO = new VendaDAO();
    private final CompraDAO compraDAO = new CompraDAO();

    public boolean adicionarProduto(String nome, double preco, int quantidade) {
        boolean ok = produtoDAO.adicionarOuAtualizarEstoque(nome, preco, quantidade);
        Produto p = produtoDAO.buscarPorNome(nome);
        if (ok && p != null) compraDAO.registrarCompra(p.getId(), quantidade, preco);
        return ok;
    }

    public boolean removerProduto(String nome) {
        return produtoDAO.removerPorNome(nome);
    }

    public Produto consultarProduto(String nome) {
        return produtoDAO.buscarPorNome(nome);
    }

    public List<Produto> listarProdutos() {
        return produtoDAO.listar();
    }

    public double registrarVenda(String nomeProduto, int quantidade, boolean desconto) throws SQLException {
        Produto p = produtoDAO.buscarPorNome(nomeProduto);
        if (p == null) throw new IllegalArgumentException("Produto não encontrado");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade inválida");

        boolean retirou = produtoDAO.retirarDoEstoque(p.getId(), quantidade);
        if (!retirou) throw new IllegalStateException("Estoque insuficiente");

        vendaDAO.registrarVenda(p.getId(), quantidade, p.getPreco(), desconto);
        return p.getPreco() * quantidade * (desconto ? 0.9 : 1.0);
    }

    public boolean fazerPedido(String nomeProduto, int quantidade) {
        Produto p = produtoDAO.buscarPorNome(nomeProduto);
        if (p == null) throw new IllegalArgumentException("Produto não encontrado");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade inválida");
        boolean ok = produtoDAO.atualizarEstoque(p.getId(), p.getQuantidade() + quantidade, p.getPreco());
        if (ok) compraDAO.registrarCompra(p.getId(), quantidade, p.getPreco());
        return ok;
    }

    public double getFaturamento() { return vendaDAO.totalFaturado(); }
    public double getDespesas() { return compraDAO.totalDespesas(); }
}

