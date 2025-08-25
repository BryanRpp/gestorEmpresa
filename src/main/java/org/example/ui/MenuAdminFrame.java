package org.example.ui;

import org.example.model.Produto;
import org.example.model.Usuario;
import org.example.service.GestorProdutos;
import org.example.service.GestorUsuarios;

import javax.swing.*;
import java.awt.*;

public class MenuAdminFrame extends JFrame {
    private final Usuario atual;
    private final GestorUsuarios gestorUsuarios;
    private final GestorProdutos gestorProdutos;

    public MenuAdminFrame(Usuario atual, GestorUsuarios gu, GestorProdutos gp) {
        this.atual = atual;
        this.gestorUsuarios = gu;
        this.gestorProdutos = gp;

        setTitle("Admin - " + atual.getNome());
        setSize(520, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        var panel = new JPanel(new GridLayout(9,1,8,8));

        var btnFuncionarios = new JButton("Ver funcionários");
        var btnFaturamento = new JButton("Ver total de faturamento");
        var btnDespesas = new JButton("Ver despesas");
        var btnAdicionar = new JButton("Adicionar produto");
        var btnRemover = new JButton("Remover produto");
        var btnConsultar = new JButton("Consultar produto");
        var btnListar = new JButton("Listar todos os produtos");
        var btnPedido = new JButton("Fazer pedido de produtos");
        var btnSair = new JButton("Sair");

        panel.add(btnFuncionarios);
        panel.add(btnFaturamento);
        panel.add(btnDespesas);
        panel.add(btnAdicionar);
        panel.add(btnRemover);
        panel.add(btnConsultar);
        panel.add(btnListar);
        panel.add(btnPedido);
        panel.add(btnSair);
        add(panel);

        btnFuncionarios.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Funcionários:\n");
            for (var u : gestorUsuarios.listar()) sb.append(u).append('\n');
            JTextArea ta = new JTextArea(sb.toString(), 20, 40);
            ta.setEditable(false);
            JOptionPane.showMessageDialog(this, new JScrollPane(ta));
        });

        btnFaturamento.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Faturamento total: R$ " +
                        String.format("%.2f", gestorProdutos.getFaturamento()))
        );

        btnDespesas.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Despesas totais: R$ " +
                        String.format("%.2f", gestorProdutos.getDespesas()))
        );

        btnAdicionar.addActionListener(e -> {
            try {
                String nome = JOptionPane.showInputDialog(this, "Nome do produto:");
                double preco = Double.parseDouble(JOptionPane.showInputDialog(this, "Preço:"));
                int qtd = Integer.parseInt(JOptionPane.showInputDialog(this, "Quantidade:"));
                boolean ok = gestorProdutos.adicionarProduto(nome, preco, qtd);
                JOptionPane.showMessageDialog(this, ok ? "Produto adicionado/estoque atualizado." : "Erro ao adicionar.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Entrada inválida.");
            }
        });

        btnRemover.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome do produto para remover:");
            boolean ok = gestorProdutos.removerProduto(nome);
            JOptionPane.showMessageDialog(this, ok ? "Produto removido." : "Produto não encontrado.");
        });

        btnConsultar.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome do produto:");
            Produto p = gestorProdutos.consultarProduto(nome);
            JOptionPane.showMessageDialog(this, p != null ? p.toString() : "Produto não encontrado.");
        });

        btnListar.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Produtos em estoque:\n");
            for (Produto p : gestorProdutos.listarProdutos()) sb.append(p).append('\n');
            JTextArea ta = new JTextArea(sb.toString(), 15, 40);
            ta.setEditable(false);
            JOptionPane.showMessageDialog(this, new JScrollPane(ta));
        });

        btnPedido.addActionListener(e -> {
            try {
                String nome = JOptionPane.showInputDialog(this, "Produto a repor:");
                int qtd = Integer.parseInt(JOptionPane.showInputDialog(this, "Quantidade:"));
                boolean ok = gestorProdutos.fazerPedido(nome, qtd);
                JOptionPane.showMessageDialog(this, ok ? "Pedido registrado e estoque atualizado." : "Erro ao registrar pedido.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Entrada inválida.");
            }
        });

        btnSair.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }
}
