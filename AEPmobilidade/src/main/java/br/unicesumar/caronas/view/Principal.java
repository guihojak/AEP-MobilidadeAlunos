package br.unicesumar.caronas.view;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Principal {
    public static void main(String[] args) {
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");

        FlatLightLaf.setup();
        SwingUtilities.invokeLater(PainelPrincipalView::new);
    }
}
