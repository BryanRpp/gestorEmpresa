package org.example.ui;

import org.example.model.Usuario;
import org.example.service.GestorUsuarios;

import javax.swing.*;
import java.awt.*;

public class MenuAdminFrame extends JFrame {
    private final Usuario usuario;
    private final GestorUsuarios gestorUsuarios;

    public MenuAdminFrame(Usuario usuario, GestorUsuarios gestorUsuarios) {
        this.usuario = usuario;
        this.gestorUsuarios = gestorUsuarios;

        setTitle("Menu Admin - ERP Estoque");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));

        JLabel lblBemVindo = new JLabel("Bem-vindo, " + usuario.getNome(), SwingConstants.CENTER);
        JButton btnCadastrarUsuario = new JButton("Cadastrar Novo Usuário");
        JButton btnSair = new JButton("Sair");

        btnCadastrarUsuario.addActionListener(e -> {
            new CadastroUsuarioFrame(gestorUsuarios).setVisible(true);
        });

        btnSair.addActionListener(e -> {
            dispose();
            new LoginFrame(gestorUsuarios).setVisible(true);
        });

        panel.add(lblBemVindo);
        panel.add(btnCadastrarUsuario);
        panel.add(btnSair);

        add(panel);
    }
}
