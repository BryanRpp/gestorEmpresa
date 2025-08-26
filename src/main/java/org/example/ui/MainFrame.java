package org.example.ui;

import org.example.model.Usuario;
import org.example.service.GestorMovimentacoes;
import org.example.service.GestorProdutos;
import org.example.service.GestorUsuarios;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(Usuario usuario, GestorUsuarios gu, GestorProdutos gp, GestorMovimentacoes gm) {
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
                // pode tudo exceto gerenciar usuários (não implementado aqui)
            }
            case "Admin" -> { /* tudo liberado */ }
        }

        setContentPane(tabs);

        // Se for Admin, perguntar se quer cadastrar outro usuário agora
        if ("Admin".equalsIgnoreCase(usuario.getNivel())) {
            SwingUtilities.invokeLater(() -> {
                int op = JOptionPane.showConfirmDialog(this,
                        "Deseja cadastrar um novo usuário agora?",
                        "Cadastrar usuário",
                        JOptionPane.YES_NO_OPTION);
                if (op == JOptionPane.YES_OPTION) {
                    CadastroUsuarioDialog dlg = new CadastroUsuarioDialog(this, gu, usuario.getId());
                    dlg.setVisible(true);
                }
            });
        }
    }
}
