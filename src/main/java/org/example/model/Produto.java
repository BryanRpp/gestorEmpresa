package org.example.model;

public class Produto {
    private int id;
    private String nome;
    private String categoria;
    private String marca;
    private String unidadeMedida;
    private double precoCusto;
    private double precoVenda;
    private double margem;
    private int estoqueAtual;
    private int estoqueMinimo;
    private int estoqueMaximo;
    private int pontoReposicao;

    public Produto(int id, String nome, String categoria, String marca, String unidadeMedida, double precoCusto, double precoVenda, double margem, int estoqueAtual, int estoqueMinimo, int estoqueMaximo, int pontoReposicao) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.marca = marca;
        this.unidadeMedida = unidadeMedida;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.margem = margem;
        this.estoqueAtual = estoqueAtual;
        this.estoqueMinimo = estoqueMinimo;
        this.estoqueMaximo = estoqueMaximo;
        this.pontoReposicao = pontoReposicao;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCategoria() { return categoria; }
    public String getMarca() { return marca; }
    public String getUnidadeMedida() { return unidadeMedida; }
    public double getPrecoCusto() { return precoCusto; }
    public double getPrecoVenda() { return precoVenda; }
    public double getMargem() { return margem; }
    public int getEstoqueAtual() { return estoqueAtual; }
    public int getEstoqueMinimo() { return estoqueMinimo; }
    public int getEstoqueMaximo() { return estoqueMaximo; }
    public int getPontoReposicao() { return pontoReposicao; }
}
