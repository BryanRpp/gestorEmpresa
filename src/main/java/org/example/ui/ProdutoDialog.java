package org.example.ui;

import org.example.model.Produto;
import org.example.service.GestorProdutos;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ProdutoDialog extends JDialog {
    private boolean salvo = false;

    public ProdutoDialog(Window owner, Produto produto, GestorProdutos gp) {
        super(owner, "Produto", ModalityType.APPLICATION_MODAL);
        setSize(480, 420);
        setLocationRelativeTo(owner);
        setLayout(new GridBagLayout());
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField tfNome = new JTextField();
        JTextField tfCategoria = new JTextField();
        JTextField tfMarca = new JTextField();
        JTextField tfUM = new JTextField();
        JTextField tfCusto = new JTextField();
        JTextField tfVenda = new JTextField();
        JTextField tfEstoque = new JTextField("0");
        JTextField tfMin = new JTextField("0");
        JTextField tfMax = new JTextField("0");
        JTextField tfRep = new JTextField("0");

        Integer id = null;
        if (produto != null) {
            id = produto.getId();
            tfNome.setText(produto.getNome());
            tfCategoria.setText(produto.getCategoria());
            tfMarca.setText(produto.getMarca());
            tfUM.setText(produto.getUnidadeMedida());
            tfCusto.setText(String.valueOf(produto.getPrecoCusto()));
            tfVenda.setText(String.valueOf(produto.getPrecoVenda()));
            tfEstoque.setText(String.valueOf(produto.getEstoqueAtual()));
            tfMin.setText(String.valueOf(produto.getEstoqueMinimo()));
            tfMax.setText(String.valueOf(produto.getEstoqueMaximo()));
            tfRep.setText(String.valueOf(produto.getPontoReposicao()));
        }

        int y=0;
        add(new JLabel("Nome:"), gbc(0,y)); add(tfNome, gbc(1,y++));
        add(new JLabel("Categoria:"), gbc(0,y)); add(tfCategoria, gbc(1,y++));
        add(new JLabel("Marca:"), gbc(0,y)); add(tfMarca, gbc(1,y++));
        add(new JLabel("Unid. Medida:"), gbc(0,y)); add(tfUM, gbc(1,y++));
        add(new JLabel("Preço Custo:"), gbc(0,y)); add(tfCusto, gbc(1,y++));
        add(new JLabel("Preço Venda:"), gbc(0,y)); add(tfVenda, gbc(1,y++));
        add(new JLabel("Estoque Atual:"), gbc(0,y)); add(tfEstoque, gbc(1,y++));
        add(new JLabel("Estoque Mínimo:"), gbc(0,y)); add(tfMin, gbc(1,y++));
        add(new JLabel("Estoque Máximo:"), gbc(0,y)); add(tfMax, gbc(1,y++));
        add(new JLabel("Ponto Reposição:"), gbc(0,y)); add(tfRep, gbc(1,y++));

        JButton salvar = new JButton("Salvar");
        add(salvar, gbc(0,y,2));

        Integer finalId = id;
        salvar.addActionListener(e -> {
            try {
                Produto p = new Produto();
                p.setId(finalId);
                p.setNome(tfNome.getText().trim());
                p.setCategoria(tfCategoria.getText().trim());
                p.setMarca(tfMarca.getText().trim());
                p.setUnidadeMedida(tfUM.getText().trim());
                p.setPrecoCusto(Double.parseDouble(tfCusto.getText().trim()));
                p.setPrecoVenda(Double.parseDouble(tfVenda.getText().trim()));
                p.setEstoqueAtual(Integer.parseInt(tfEstoque.getText().trim()));
                p.setEstoqueMinimo(Integer.parseInt(tfMin.getText().trim()));
                p.setEstoqueMaximo(Integer.parseInt(tfMax.getText().trim()));
                p.setPontoReposicao(Integer.parseInt(tfRep.getText().trim()));

                gp.salvar(p);
                salvo = true;
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });
    }

    private GridBagConstraints gbc(int x, int y) { return gbc(x,y,1); }
    private GridBagConstraints gbc(int x, int y, int w) {
        var g = new GridBagConstraints();
        g.insets = new Insets(6,6,6,6);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = x; g.gridy = y; g.gridwidth = w;
        return g;
    }

    public boolean foiSalvo() { return salvo; }
}
