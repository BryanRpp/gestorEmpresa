package org.example.ui;

import org.example.model.Usuario;
import org.example.service.GestorProdutos;
import org.example.service.GestorUsuarios;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final GestorUsuarios gestorUsuarios = new GestorUsuarios();
    private final GestorProdutos gestorProdutos = new GestorProdutos();

    public LoginFrame() {
        setTitle("Login");
        setSize(360, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        var panel = new JPanel(new GridLayout(4, 2, 8, 8));
        var txtEmail = new JTextField();
        var txtSenha = new JPasswordField();
        var btnEntrar = new JButton("Entrar");
        var btnCadastrar = new JButton("Cadastrar");

        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Senha:"));
        panel.add(txtSenha);
        panel.add(btnEntrar);
        panel.add(btnCadastrar);

        add(panel, BorderLayout.CENTER);

        btnEntrar.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String senha = new String(txtSenha.getPassword());
            Usuario u = gestorUsuarios.login(email, senha);
            if (u == null) {
                JOptionPane.showMessageDialog(this, "Email ou senha inválidos!");
                return;
            }
            dispose();
            switch (u.getNivel()) {
                case "Admin" -> new MenuAdminFrame(u, gestorUsuarios, gestorProdutos).setVisible(true);
                case "Vendas" -> new MenuVendasFrame(u, gestorProdutos).setVisible(true);
                case "Compras" -> new MenuComprasFrame(u, gestorProdutos).setVisible(true);
                default -> JOptionPane.showMessageDialog(this, "Nível desconhecido: " + u.getNivel());
            }
        });

        btnCadastrar.addActionListener(e -> new CadastroFrame(gestorUsuarios).setVisible(true));
    }
}

