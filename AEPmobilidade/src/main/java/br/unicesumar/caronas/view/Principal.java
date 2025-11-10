package br.unicesumar.caronas.view;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Principal {
    public static void main(String[] args) {
        JavaFXInitializer.init();
        // Tema e abertura da tela de login no EDT
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}