package org.example.ui;

import org.example.dao.AuditoriaDAO;
import org.example.model.Usuario;
import org.example.service.GestorUsuarios;

import javax.swing.*;
import java.awt.*;

public class CadastroUsuarioDialog extends JDialog {
    private boolean salvo = false;

    public CadastroUsuarioDialog(Window owner, GestorUsuarios gestorUsuarios, int adminId) {
        super(owner, "Cadastrar Usuário", ModalityType.APPLICATION_MODAL);
        setSize(420, 280);
        setLocationRelativeTo(owner);
        setLayout(new GridBagLayout());

        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField tfNome = new JTextField(20);
        JTextField tfEmail = new JTextField(20);
        JPasswordField pfSenha = new JPasswordField(20);
        JComboBox<String> cbNivel = new JComboBox<>(new String[]{"Admin","Estoquista","Consulta"});
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; add(tfNome, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; add(tfEmail, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1; add(pfSenha, gbc);
        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Nível:"), gbc);
        gbc.gridx = 1; add(cbNivel, gbc);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.add(btnSalvar); botoes.add(btnCancelar);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; add(botoes, gbc);

        btnSalvar.addActionListener(e -> {
            String nome = tfNome.getText().trim();
            String email = tfEmail.getText().trim();
            String senha = new String(pfSenha.getPassword()).trim();
            String nivel = cbNivel.getSelectedItem().toString();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }

            Usuario u = new Usuario(null, nome, email, senha, nivel);
            boolean ok = gestorUsuarios.cadastrarUsuario(u);
            if (ok) {
                // registra auditoria
                new AuditoriaDAO().registrar(adminId, "Cadastrou usuário: " + email + " (nível " + nivel + ")");
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
                salvo = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar (email pode já estar em uso).");
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    public boolean foiSalvo() { return salvo; }
}
