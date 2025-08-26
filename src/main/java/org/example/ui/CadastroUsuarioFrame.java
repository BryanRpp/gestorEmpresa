package org.example.ui;

import org.example.service.GestorUsuarios;

import javax.swing.*;
import java.awt.*;

public class CadastroUsuarioFrame extends JFrame {
    private final GestorUsuarios gestorUsuarios;

    public CadastroUsuarioFrame(GestorUsuarios gestorUsuarios) {
        this.gestorUsuarios = gestorUsuarios;

        setTitle("Cadastro de Usuário");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        JTextField txtNome = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtTelefone = new JTextField();
        JPasswordField txtSenha = new JPasswordField();

        String[] niveis = {"Admin", "Compras", "Vendas"};
        JComboBox<String> cbNivel = new JComboBox<>(niveis);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            gestorUsuarios.cadastrarUsuario(
                    txtNome.getText(),
                    txtTelefone.getText(),
                    txtEmail.getText(),
                    new String(txtSenha.getPassword()),
                    (String) cbNivel.getSelectedItem()
            );
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            dispose();
        });

        panel.add(new JLabel("Nome:"));
        panel.add(txtNome);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Telefone:"));
        panel.add(txtTelefone);
        panel.add(new JLabel("Senha:"));
        panel.add(txtSenha);
        panel.add(new JLabel("Nível:"));
        panel.add(cbNivel);
        panel.add(new JLabel(""));
        panel.add(btnSalvar);

        add(panel);
    }
}
