package org.example.ui;

import org.example.model.Usuario;
import org.example.service.GestorMovimentacoes;
import org.example.service.GestorProdutos;
import org.example.service.GestorUsuarios;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final GestorUsuarios gestorUsuarios;
    private final GestorProdutos gestorProdutos;
    private final GestorMovimentacoes gestorMovimentacoes;

    public LoginFrame(GestorUsuarios gu, GestorProdutos gp, GestorMovimentacoes gm) {
        super("ERP Estoque - Login");
        this.gestorUsuarios = gu;
        this.gestorProdutos = gp;
        this.gestorMovimentacoes = gm;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 220);
        setLocationRelativeTo(null);

        var panel = new JPanel(new GridBagLayout());
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        var tfEmail = new JTextField(20);
        var pfSenha = new JPasswordField(20);
        var btn = new JButton("Entrar");

        gbc.gridx=0; gbc.gridy=0; panel.add(new JLabel("Email:"), gbc);
        gbc.gridx=1; panel.add(tfEmail, gbc);
        gbc.gridx=0; gbc.gridy=1; panel.add(new JLabel("Senha:"), gbc);
        gbc.gridx=1; panel.add(pfSenha, gbc);
        gbc.gridwidth=2; gbc.gridx=0; gbc.gridy=2; panel.add(btn, gbc);

        btn.addActionListener(e -> {
            String email = tfEmail.getText().trim();
            String senha = new String(pfSenha.getPassword());
            Usuario u = gestorUsuarios.login(email, senha);
            if (u == null) {
                JOptionPane.showMessageDialog(this, "Credenciais inválidas");
                return;
            }
            new MainFrame(u, gestorProdutos, gestorMovimentacoes).setVisible(true);
            dispose();
        });

        setContentPane(panel);
    }
}
