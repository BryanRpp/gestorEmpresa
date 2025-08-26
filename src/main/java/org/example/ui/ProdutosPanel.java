package org.example.ui;

import org.example.model.Produto;
import org.example.model.Usuario;
import org.example.service.GestorProdutos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class ProdutosPanel extends JPanel {
    private final GestorProdutos gp;
    private final JTable tabela;
    private boolean somenteLeitura = false;

    public ProdutosPanel(Usuario u, GestorProdutos gp) {
        this.gp = gp;
        setLayout(new BorderLayout());

        String[] cols = {"ID","Nome","Categoria","Marca","UM","Custo","Venda","Estoque","Min","Max","Reposição"};
        DefaultTableModel model = new DefaultTableModel(cols,0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(model);

        JButton btnNovo = new JButton("Novo");
        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");
        JButton btnRecarregar = new JButton("Recarregar");

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(btnNovo); top.add(btnEditar); top.add(btnRemover); top.add(btnRecarregar);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnNovo.addActionListener(e -> abrirDialog(null));
        btnEditar.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row < 0) return;
            Produto p = linhaParaProduto(model, row);
            abrirDialog(p);
        });
        btnRemover.addActionListener(e -> {
            if (somenteLeitura) return;
            int row = tabela.getSelectedRow();
            if (row < 0) return;
            int id = Integer.parseInt(model.getValueAt(row,0).toString());
            try {
                gp.remover(id);
                carregar();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });
        btnRecarregar.addActionListener(e -> carregar());

        if (u.getNivel().equals("Consulta")) {
            btnNovo.setEnabled(false);
            btnEditar.setEnabled(false);
            btnRemover.setEnabled(false);
        }

        carregar();
    }

    public void setSomenteLeitura(boolean v) { this.somenteLeitura = v; }

    private Produto linhaParaProduto(DefaultTableModel m, int row) {
        Produto p = new Produto();
        p.setId(Integer.parseInt(m.getValueAt(row,0).toString()));
        p.setNome(m.getValueAt(row,1).toString());
        p.setCategoria(m.getValueAt(row,2).toString());
        p.setMarca(m.getValueAt(row,3).toString());
        p.setUnidadeMedida(m.getValueAt(row,4).toString());
        p.setPrecoCusto(Double.parseDouble(m.getValueAt(row,5).toString()));
        p.setPrecoVenda(Double.parseDouble(m.getValueAt(row,6).toString()));
        p.setEstoqueAtual(Integer.parseInt(m.getValueAt(row,7).toString()));
        p.setEstoqueMinimo(Integer.parseInt(m.getValueAt(row,8).toString()));
        p.setEstoqueMaximo(Integer.parseInt(m.getValueAt(row,9).toString()));
        p.setPontoReposicao(Integer.parseInt(m.getValueAt(row,10).toString()));
        return p;
    }

    private void abrirDialog(Produto p) {
        if (somenteLeitura) return;
        ProdutoDialog dlg = new ProdutoDialog(SwingUtilities.getWindowAncestor(this), p, gp);
        dlg.setVisible(true);
        if (dlg.foiSalvo()) carregar();
    }

    private void carregar() {
        DefaultTableModel m = (DefaultTableModel) tabela.getModel();
        m.setRowCount(0);
        try {
            for (Produto p : gp.listar()) {
                m.addRow(new Object[]{
                        p.getId(), p.getNome(), p.getCategoria(), p.getMarca(), p.getUnidadeMedida(),
                        p.getPrecoCusto(), p.getPrecoVenda(), p.getEstoqueAtual(),
                        p.getEstoqueMinimo(), p.getEstoqueMaximo(), p.getPontoReposicao()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
}
