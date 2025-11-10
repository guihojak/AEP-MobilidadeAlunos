package br.unicesumar.caronas.view;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;

/**
 * Painel Swing que exibe um mapa real interativo (OpenStreetMap via Leaflet)
 * totalmente funcional e inicializado de forma segura.
 */
public class MapaRealView extends JPanel {
    private final JFXPanel jfxPanel = new JFXPanel();
    private WebEngine webEngine;

    public MapaRealView() {
        setLayout(new BorderLayout());
        add(jfxPanel, BorderLayout.CENTER);

        inicializarMapa();
    }
    private void inicializarMapa() {
        try {
            if (Platform.isImplicitExit()) {
                // Já inicializado
                carregarMapa();
            } else {
                Platform.startup(() -> carregarMapa());
            }
        } catch (IllegalStateException e) {
            // Toolkit já ativo → apenas recarrega o mapa
            Platform.runLater(this::carregarMapa);
        }
    }

    private void carregarMapa() {
        WebView webView = new WebView();
        webEngine = webView.getEngine();

        String html = """
            <!DOCTYPE html>
            <html>
            <head>
              <meta charset="utf-8" />
              <meta name="viewport" content="width=device-width, initial-scale=1.0">
              <link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />
              <script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
              <style>
                html, body, #map { height: 100%; margin: 0; padding: 0; }
              </style>
            </head>
            <body>
              <div id="map"></div>
              <script>
                var map = L.map('map').setView([-23.420999, -51.933056], 13);
                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                  maxZoom: 19,
                  attribution: '© OpenStreetMap contributors'
                }).addTo(map);
                L.marker([-23.420999, -51.933056])
                  .addTo(map)
                  .bindPopup('Campus UniCesumar - Maringá')
                  .openPopup();
              </script>
            </body>
            </html>
            """;

        webEngine.loadContent(html);
        jfxPanel.setScene(new Scene(webView));
    }


    public void adicionarMarcador(double lat, double lon, String descricao) {
        Platform.runLater(() -> {
            if (webEngine != null) {
                webEngine.executeScript(String.format(
                        "L.marker([%f, %f]).addTo(map).bindPopup('%s').openPopup();",
                        lat, lon, descricao
                ));
            }
        });
    }
}
