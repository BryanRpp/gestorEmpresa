package org.example.ui;

import org.example.model.Produto;
import org.example.service.GestorProdutos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class RelatoriosPanel extends JPanel {
    private final GestorProdutos gp;
    private final JTable tabela;

    public RelatoriosPanel(GestorProdutos gp) {
        this.gp = gp;
        setLayout(new BorderLayout());

        tabela = new JTable(new DefaultTableModel(new String[]{
                "ID","Produto","Estoque","Estoque Mín.","Estoque Máx.","Reposição","Status"
        },0));

        JButton btnParados = new JButton("Produtos Parados / Excesso");
        JButton btnEstoqueAtual = new JButton("Estoque Atual");

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(btnParados); top.add(btnEstoqueAtual);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnParados.addActionListener(e -> carregarParados());
        btnEstoqueAtual.addActionListener(e -> carregarAtual());
    }

    private void carregarParados() {
        try {
            DefaultTableModel m = (DefaultTableModel) tabela.getModel();
            m.setRowCount(0);
            for (Produto p : gp.excessoEstoque()) {
                m.addRow(new Object[]{p.getId(), p.getNome(), p.getEstoqueAtual(), p.getEstoqueMinimo(), p.getEstoqueMaximo(), p.getPontoReposicao(), "Excesso"});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void carregarAtual() {
        try {
            DefaultTableModel m = (DefaultTableModel) tabela.getModel();
            m.setRowCount(0);
            for (Produto p : gp.listar()) {
                String status = p.getEstoqueAtual() <= Math.max(p.getEstoqueMinimo(), p.getPontoReposicao())
                        ? "Reposição" : (p.getEstoqueMaximo() > 0 && p.getEstoqueAtual() >= p.getEstoqueMaximo() ? "Excesso" : "OK");
                m.addRow(new Object[]{p.getId(), p.getNome(), p.getEstoqueAtual(), p.getEstoqueMinimo(), p.getEstoqueMaximo(), p.getPontoReposicao(), status});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
}
