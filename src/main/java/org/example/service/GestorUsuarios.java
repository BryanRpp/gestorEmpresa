package org.example.service;

import org.example.dao.UsuarioDAO;
import org.example.model.Usuario;

public class GestorUsuarios {
    private final UsuarioDAO usuarioDAO;

    public GestorUsuarios() {
        this.usuarioDAO = new UsuarioDAO();
        criarAdminSeNaoExistir();
    }

    public Usuario login(String email, String senha) {
        return usuarioDAO.buscarPorEmailSenha(email, senha);
    }

    public void cadastrarUsuario(String nome, String telefone, String email, String senha, String nivel) {
        Usuario usuario = new Usuario(nome, telefone, email, senha, nivel);
        usuarioDAO.inserir(usuario);
    }

    private void criarAdminSeNaoExistir() {
        if (usuarioDAO.contarUsuarios() == 0) {
            Usuario admin = new Usuario("Administrador", "0000", "admin@erp.com", "admin", "Admin");
            usuarioDAO.inserir(admin);
        }
    }
}
