package org.example.ui;

import org.example.model.Produto;
import org.example.model.Usuario;
import org.example.service.GestorProdutos;

import javax.swing.*;
import java.awt.*;

public class MenuComprasFrame extends JFrame {
    private final Usuario atual;
    private final GestorProdutos gestorProdutos;

    public MenuComprasFrame(Usuario atual, GestorProdutos gp) {
        this.atual = atual;
        this.gestorProdutos = gp;

        setTitle("Compras - " + atual.getNome());
        setSize(480, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        var panel = new JPanel(new GridLayout(5,1,8,8));
        var btnAdicionar = new JButton("Adicionar produto");
        var btnRemover = new JButton("Remover produto");
        var btnConsultar = new JButton("Consultar produto");
        var btnListar = new JButton("Listar todos os produtos");
        var btnSair = new JButton("Sair");

        panel.add(btnAdicionar);
        panel.add(btnRemover);
        panel.add(btnConsultar);
        panel.add(btnListar);
        panel.add(btnSair);
        add(panel);

        btnAdicionar.addActionListener(e -> {
            try {
                String nome = JOptionPane.showInputDialog(this, "Nome do produto:");
                double preco = Double.parseDouble(JOptionPane.showInputDialog(this, "Preço unitário:"));
                int qtd = Integer.parseInt(JOptionPane.showInputDialog(this, "Quantidade:"));
                boolean ok = gestorProdutos.adicionarProduto(nome, preco, qtd);
                JOptionPane.showMessageDialog(this, ok ? "Produto adicionado." : "Erro ao adicionar.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Entrada inválida.");
            }
        });

        btnRemover.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome do produto a remover:");
            boolean ok = gestorProdutos.removerProduto(nome);
            JOptionPane.showMessageDialog(this, ok ? "Produto removido." : "Produto não encontrado.");
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
