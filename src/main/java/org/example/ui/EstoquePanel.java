package org.example.ui;

import org.example.model.Produto;
import org.example.service.GestorProdutos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class EstoquePanel extends JPanel {
    private final GestorProdutos gp;
    private final JTable tAlertas;
    private final JTable tExcesso;

    public EstoquePanel(GestorProdutos gp) {
        this.gp = gp;
        setLayout(new GridLayout(2,1));

        tAlertas = new JTable(new DefaultTableModel(new String[]{"ID","Produto","Estoque","Min/Rep"},0));
        tExcesso = new JTable(new DefaultTableModel(new String[]{"ID","Produto","Estoque","Máximo"},0));

        JPanel p1 = new JPanel(new BorderLayout());
        p1.add(new JLabel("⚠️ Produtos com necessidade de reposição"), BorderLayout.NORTH);
        p1.add(new JScrollPane(tAlertas), BorderLayout.CENTER);

        JPanel p2 = new JPanel(new BorderLayout());
        p2.add(new JLabel("📦 Produtos com excesso de estoque"), BorderLayout.NORTH);
        p2.add(new JScrollPane(tExcesso), BorderLayout.CENTER);

        add(p1); add(p2);
        carregar();
    }

    private void carregar() {
        try {
            DefaultTableModel m1 = (DefaultTableModel) tAlertas.getModel();
            m1.setRowCount(0);
            for (Produto p : gp.alertasReposicao()) {
                m1.addRow(new Object[]{p.getId(), p.getNome(), p.getEstoqueAtual(), Math.max(p.getEstoqueMinimo(), p.getPontoReposicao())});
            }
            DefaultTableModel m2 = (DefaultTableModel) tExcesso.getModel();
            m2.setRowCount(0);
            for (Produto p : gp.excessoEstoque()) {
                m2.addRow(new Object[]{p.getId(), p.getNome(), p.getEstoqueAtual(), p.getEstoqueMaximo()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
}
