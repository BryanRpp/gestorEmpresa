package org.example.model;

import java.time.LocalDateTime;

public class Movimentacao {
    private Integer id;
    private Integer produtoId;
    private String tipo; // Entrada | Saida
    private int quantidade;
    private String motivo; // Compra, Ajuste, Perda, ConsumoInterno
    private Integer usuarioId;
    private LocalDateTime data;

    public Integer getId() { return id; }
    public Integer getProdutoId() { return produtoId; }
    public String getTipo() { return tipo; }
    public int getQuantidade() { return quantidade; }
    public String getMotivo() { return motivo; }
    public Integer getUsuarioId() { return usuarioId; }
    public LocalDateTime getData() { return data; }

    public void setId(Integer id) { this.id = id; }
    public void setProdutoId(Integer produtoId) { this.produtoId = produtoId; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    public void setData(LocalDateTime data) { this.data = data; }
}
