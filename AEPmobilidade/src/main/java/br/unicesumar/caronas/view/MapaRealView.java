package br.unicesumar.caronas.view;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;

public class MapaRealView extends JPanel {

    private final JFXPanel fxPanel = new JFXPanel();
    private WebEngine webEngine;

    public MapaRealView() {
        setLayout(new BorderLayout());
        add(fxPanel, BorderLayout.CENTER);

        // inicia o JavaFX no contexto correto
        Platform.runLater(this::inicializarMapa);
    }

    private void inicializarMapa() {
        WebView webView = new WebView();
        webEngine = webView.getEngine();

        Scene scene = new Scene(webView);
        fxPanel.setScene(scene);

        // carrega o conteúdo HTML do mapa
        Platform.runLater(() -> webEngine.loadContent(gerarHTMLMapa()));
    }

    private String gerarHTMLMapa() {
        return """
            <!DOCTYPE html>
            <html lang="pt-br">
            <head>
              <meta charset="utf-8" />
              <meta name="viewport" content="width=device-width, initial-scale=1.0">
              <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" crossorigin=""/>
              <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js" crossorigin=""></script>
              <style>
                html, body { height: 100%%; margin: 0; }
                #map { height: 100%%; width: 100%%; background: #f0f0f0; }
              </style>
            </head>
            <body>
              <div id="map"></div>
              <script>
                // cria o mapa centrado em Maringá
                var map = L.map('map').setView([-23.4205, -51.9331], 14);

                // adiciona camada de tiles
                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                    attribution: '&copy; OpenStreetMap colaboradores',
                    maxZoom: 19
                }).addTo(map);

                // adiciona marcador
                L.marker([-23.4205, -51.9331])
                    .addTo(map)
                    .bindPopup('Campus UniCesumar - Maringá')
                    .openPopup();
              </script>
            </body>
            </html>
        """;
    }
}
