package org.example.ui;

import org.example.model.Usuario;
import org.example.service.GestorUsuarios;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final GestorUsuarios gestorUsuarios;

    public LoginFrame(GestorUsuarios gestorUsuarios) {
        this.gestorUsuarios = gestorUsuarios;

        setTitle("Login - ERP Estoque");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        JTextField txtEmail = new JTextField();
        JPasswordField txtSenha = new JPasswordField();
        JButton btnLogin = new JButton("Entrar");

        btnLogin.addActionListener(e -> {
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());
            Usuario u = gestorUsuarios.login(email, senha);
            if (u != null) {
                JOptionPane.showMessageDialog(this, "Bem-vindo, " + u.getNome());
                switch (u.getNivel()) {
                    case "Admin" -> {
                        new MenuAdminFrame(u, gestorUsuarios).setVisible(true);
                        dispose();
                    }
                    default -> JOptionPane.showMessageDialog(this, "Nível de acesso não implementado.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos");
            }
        });

        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Senha:"));
        panel.add(txtSenha);
        panel.add(new JLabel(""));
        panel.add(btnLogin);

        add(panel);
    }
}
