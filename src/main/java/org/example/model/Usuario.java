package org.example.model;

public class Usuario {
    private int id;
    private String nome;
    private String telefone;
    private String email;
    private String senha;
    private String nivel; // Admin, Compras, Vendas

    public Usuario(int id, String nome, String telefone, String email, String senha, String nivel) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.nivel = nivel;
    }

    public Usuario(String nome, String telefone, String email, String senha, String nivel) {
        this(0, nome, telefone, email, senha, nivel);
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public String getNivel() { return nivel; }

    @Override
    public String toString() {
        return "Nome: " + nome + " | Telefone: " + telefone + " | Email: " + email + " | Nível: " + nivel;
    }
}
