package org.example.ui;

import org.example.model.Produto;
import org.example.model.Usuario;
import org.example.service.GestorProdutos;

import javax.swing.*;
import java.awt.*;

public class MenuVendasFrame extends JFrame {
    private final Usuario atual;
    private final GestorProdutos gestorProdutos;

    public MenuVendasFrame(Usuario atual, GestorProdutos gp) {
        this.atual = atual;
        this.gestorProdutos = gp;

        setTitle("Vendas - " + atual.getNome());
        setSize(480, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        var panel = new JPanel(new GridLayout(5,1,8,8));
        var btnRegistrar = new JButton("Registrar produtos vendidos");
        var btnValores = new JButton("Informe de valores (desconto opcional)");
        var btnConsultar = new JButton("Consultar produto");
        var btnListar = new JButton("Listar todos os produtos");
        var btnSair = new JButton("Sair");

        panel.add(btnRegistrar);
        panel.add(btnValores);
        panel.add(btnConsultar);
        panel.add(btnListar);
        panel.add(btnSair);
        add(panel);

        btnRegistrar.addActionListener(e -> {
            try {
                String nome = JOptionPane.showInputDialog(this, "Nome do produto:");
                int qtd = Integer.parseInt(JOptionPane.showInputDialog(this, "Quantidade vendida:"));
                double total = gestorProdutos.registrarVenda(nome, qtd, false);
                JOptionPane.showMessageDialog(this, "Venda registrada. Total: R$ " + String.format("%.2f", total));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnValores.addActionListener(e -> {
            try {
                String nome = JOptionPane.showInputDialog(this, "Nome do produto:");
                int qtd = Integer.parseInt(JOptionPane.showInputDialog(this, "Quantidade vendida:"));
                int aplicar = JOptionPane.showConfirmDialog(this, "Aplicar 10% de desconto?", "Desconto", JOptionPane.YES_NO_OPTION);
                boolean desconto = (aplicar == JOptionPane.YES_OPTION);
                double total = gestorProdutos.registrarVenda(nome, qtd, desconto);
                JOptionPane.showMessageDialog(this, "Venda registrada. Total: R$ " + String.format("%.2f", total));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnConsultar.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome do produto:");
            Produto p = gestorProdutos.consultarProduto(nome);
            JOptionPane.showMessageDialog(this, p != null ? p.toString() : "Produto não encontrado.");
        });

        btnListar.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Produtos:\n");
            for (Produto p : gestorProdutos.listarProdutos()) sb.append(p).append('\n');
            JTextArea ta = new JTextArea(sb.toString(), 15, 40);
            ta.setEditable(false);
            JOptionPane.showMessageDialog(this, new JScrollPane(ta));
        });

        btnSair.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }
}

