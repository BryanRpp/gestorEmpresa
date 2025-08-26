package org.example;

import org.example.service.GestorProdutos;
import org.example.service.GestorMovimentacoes;
import org.example.service.GestorUsuarios;
import org.example.ui.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestorUsuarios gestorUsuarios = new GestorUsuarios();
            GestorProdutos gestorProdutos = new GestorProdutos();
            GestorMovimentacoes gestorMovimentacoes = new GestorMovimentacoes(gestorProdutos);

            new LoginFrame(gestorUsuarios, gestorProdutos, gestorMovimentacoes).setVisible(true);
        });
    }
}
