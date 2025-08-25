package org.example.service;

import org.example.dao.UsuarioDAO;
import org.example.model.Usuario;

import java.util.List;

public class GestorUsuarios {
    private final UsuarioDAO dao = new UsuarioDAO();

    public GestorUsuarios() {
        dao.criarAdminSeVazio();
    }

    public boolean cadastrarUsuario(Usuario u) {
        return dao.cadastrar(u);
    }

    public Usuario login(String email, String senha) {
        return dao.login(email, senha);
    }

    public List<Usuario> listar() {
        return dao.listar();
    }
}

