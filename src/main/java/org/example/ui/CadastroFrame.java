package org.example.ui;

import org.example.model.Usuario;
import org.example.service.GestorUsuarios;

import javax.swing.*;
import java.awt.*;

public class CadastroFrame extends JFrame {
    private final GestorUsuarios gestorUsuarios;

    public CadastroFrame(GestorUsuarios gestorUsuarios) {
        this.gestorUsuarios = gestorUsuarios;

        setTitle("Cadastro de Usuário");
        setSize(420, 280);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        var panel = new JPanel(new GridLayout(6, 2, 8, 8));
        var txtNome = new JTextField();
        var txtTelefone = new JTextField();
        var txtEmail = new JTextField();
        var txtSenha = new JPasswordField();
        var cbNivel = new JComboBox<>(new String[]{"Admin", "Vendas", "Compras"});
        var btnSalvar = new JButton("Salvar");

        panel.add(new JLabel("Nome:")); panel.add(txtNome);
        panel.add(new JLabel("Telefone:")); panel.add(txtTelefone);
        panel.add(new JLabel("Email:")); panel.add(txtEmail);
        panel.add(new JLabel("Senha:")); panel.add(txtSenha);
        panel.add(new JLabel("Nível:")); panel.add(cbNivel);
        panel.add(new JLabel()); panel.add(btnSalvar);

        add(panel);

        btnSalvar.addActionListener(e -> {
            try {
                Usuario u = new Usuario(
                        txtNome.getText().trim(),
                        txtTelefone.getText().trim(),
                        txtEmail.getText().trim(),
                        new String(txtSenha.getPassword()),
                        cbNivel.getSelectedItem().toString()
                );
                boolean ok = gestorUsuarios.cadastrarUsuario(u);
                JOptionPane.showMessageDialog(this, ok ? "Usuário cadastrado!" : "Erro ao cadastrar.");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });
    }
}
