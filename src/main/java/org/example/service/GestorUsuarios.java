package org.example.service;

import org.example.dao.UsuarioDAO;
import org.example.model.Usuario;

public class GestorUsuarios {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public GestorUsuarios() {
        usuarioDAO.criarAdminSeVazio();
    }

    public Usuario login(String email, String senha) {
        return usuarioDAO.autenticar(email, senha);
    }
}
