package org.example.ui;

import org.example.model.Produto;
import org.example.model.Usuario;
import org.example.service.GestorMovimentacoes;
import org.example.service.GestorProdutos;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MovimentacoesPanel extends JPanel {
    private final GestorProdutos gp;
    private final GestorMovimentacoes gm;
    private final Usuario usuario;

    public MovimentacoesPanel(Usuario usuario, GestorProdutos gp, GestorMovimentacoes gm) {
        this.usuario = usuario;
        this.gp = gp;
        this.gm = gm;

        setLayout(new GridBagLayout());
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<Produto> cbProduto = new JComboBox<>();
        JTextField tfQtd = new JTextField(10);
        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Entrada","Saida"});
        JComboBox<String> cbMotivo = new JComboBox<>(new String[]{"Compra","Ajuste","Perda","ConsumoInterno"});
        JButton btn = new JButton("Registrar");

        add(new JLabel("Produto:"), gbc(0,0)); add(cbProduto, gbc(1,0));
        add(new JLabel("Quantidade:"), gbc(0,1)); add(tfQtd, gbc(1,1));
        add(new JLabel("Tipo:"), gbc(0,2)); add(cbTipo, gbc(1,2));
        add(new JLabel("Motivo:"), gbc(0,3)); add(cbMotivo, gbc(1,3));
        add(btn, gbc(0,4,2));

        carregarProdutos(cbProduto);

        // Permissões
        if (usuario.getNivel().equals("Consulta")) {
            btn.setEnabled(false);
        }

        btn.addActionListener(e -> {
            try {
                Produto p = (Produto) cbProduto.getSelectedItem();
                if (p == null) return;
                int qtd = Integer.parseInt(tfQtd.getText().trim());
                String tipo = cbTipo.getSelectedItem().toString();
                String motivo = cbMotivo.getSelectedItem().toString();

                if ("Entrada".equals(tipo)) {
                    gm.entrada(p.getId(), qtd, usuario.getId());
                } else {
                    gm.saida(p.getId(), qtd, motivo, usuario.getId());
                }
                JOptionPane.showMessageDialog(this, "Movimentação registrada!");
                carregarProdutos(cbProduto); // atualiza descrições com estoque
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });
    }

    private void carregarProdutos(JComboBox<Produto> cb) {
        cb.removeAllItems();
        try {
            for (Produto p : gp.listar()) cb.addItem(p);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private GridBagConstraints gbc(int x, int y) { return gbc(x,y,1); }
    private GridBagConstraints gbc(int x, int y, int w) {
        var g = new GridBagConstraints();
        g.insets = new Insets(6,6,6,6);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = x; g.gridy = y; g.gridwidth = w;
        return g;
    }
}
