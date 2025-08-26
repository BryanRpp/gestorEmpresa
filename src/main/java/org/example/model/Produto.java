package org.example.model;

public class Produto {
    private Integer id;
    private String nome;
    private String categoria;
    private String marca;
    private String unidadeMedida;
    private double precoCusto;
    private double precoVenda;
    private int estoqueAtual;
    private int estoqueMinimo;
    private int estoqueMaximo;
    private int pontoReposicao;

    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public String getCategoria() { return categoria; }
    public String getMarca() { return marca; }
    public String getUnidadeMedida() { return unidadeMedida; }
    public double getPrecoCusto() { return precoCusto; }
    public double getPrecoVenda() { return precoVenda; }
    public int getEstoqueAtual() { return estoqueAtual; }
    public int getEstoqueMinimo() { return estoqueMinimo; }
    public int getEstoqueMaximo() { return estoqueMaximo; }
    public int getPontoReposicao() { return pontoReposicao; }

    public void setId(Integer id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }
    public void setPrecoCusto(double precoCusto) { this.precoCusto = precoCusto; }
    public void setPrecoVenda(double precoVenda) { this.precoVenda = precoVenda; }
    public void setEstoqueAtual(int estoqueAtual) { this.estoqueAtual = estoqueAtual; }
    public void setEstoqueMinimo(int estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }
    public void setEstoqueMaximo(int estoqueMaximo) { this.estoqueMaximo = estoqueMaximo; }
    public void setPontoReposicao(int pontoReposicao) { this.pontoReposicao = pontoReposicao; }

    @Override
    public String toString() {
        return id + " - " + nome + " (" + estoqueAtual + " " + unidadeMedida + ")";
    }
}
