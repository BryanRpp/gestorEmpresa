package org.example.model;

public class Usuario {
    private int id;
    private String nome;
    private String login;
    private String senha;
    private String papel; // Admin ou Funcionario

    public Usuario(int id, String nome, String login, String senha, String papel) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.papel = papel;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public String getPapel() { return papel; }
}
