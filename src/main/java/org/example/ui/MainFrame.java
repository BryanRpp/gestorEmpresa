package org.example.ui;

import org.example.model.Usuario;
import org.example.service.GestorMovimentacoes;
import org.example.service.GestorProdutos;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(Usuario usuario, GestorProdutos gp, GestorMovimentacoes gm) {
        super("ERP Estoque - Usuário: " + usuario.getNome() + " [" + usuario.getNivel() + "]");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        ProdutosPanel produtos = new ProdutosPanel(usuario, gp);
        MovimentacoesPanel movs = new MovimentacoesPanel(usuario, gp, gm);
        EstoquePanel estoque = new EstoquePanel(gp);
        RelatoriosPanel relatorios = new RelatoriosPanel(gp);

        tabs.add("Produtos", produtos);
        tabs.add("Movimentações", movs);
        tabs.add("Estoque", estoque);
        tabs.add("Relatórios", relatorios);

        // Permissões
        switch (usuario.getNivel()) {
            case "Consulta" -> {
                movs.setEnabled(false);
                produtos.setSomenteLeitura(true);
            }
            case "Estoquista" -> {
                // pode tudo exceto configurar usuários (inexistente aqui)
            }
            case "Admin" -> { /* tudo liberado */ }
        }

        setContentPane(tabs);
    }
}
