package br.unicesumar.caronas.view;

import javafx.embed.swing.JFXPanel;

/**
 * Classe auxiliar para garantir que o toolkit do JavaFX seja inicializado
 * apenas uma vez na thread AWT (Swing).
 */
public class JavaFXInitializer {

    private static boolean initialized = false;

    public static void init() {
        if (!initialized) {
            // Inicializa o JavaFX toolkit na thread AWT (se não estiver na thread AWT, ele faz o agendamento correto)
            new JFXPanel();
            initialized = true;
        }
    }
}