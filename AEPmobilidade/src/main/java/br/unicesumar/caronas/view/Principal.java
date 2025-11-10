package br.unicesumar.caronas.view;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Principal {
    public static void main(String[] args) {
        // força renderização JavaFX em software (evita artefatos gráficos)
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");

        // Inicializa o toolkit JavaFX uma vez (bloqueante até pronto)
        JavaFXInitializer.init();

        // Tema e abertura da tela de login no EDT
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
