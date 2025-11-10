package br.unicesumar.caronas.view;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class MapaRealView extends JPanel {

    private final JFXPanel fxPanel;
    private String origem = "UniCesumar Maringá";
    private String destino = "Casa do Motorista";

    // Armazena a referência do WebView para atualizações mais rápidas
    private final AtomicReference<WebEngine> webEngineRef = new AtomicReference<>();

    public MapaRealView() {
        setLayout(new BorderLayout());
        fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.CENTER);

        // Inicializa o JavaFX toolkit e carrega o mapa inicial
        JavaFXInitializer.init();
        iniciarMapa();
    }

    /**
     * Ponto de entrada para atualizar o mapa com a carona selecionada.
     */
    public void mostrarCarona(String origem, String destino) {
        atualizarRota(origem, destino);
    }

    private void atualizarRota(String novaOrigem, String novoDestino) {
        this.origem = novaOrigem;
        this.destino = novoDestino;

        if (webEngineRef.get() != null) {
            // Se o mapa já está carregado, apenas atualiza o conteúdo HTML
            String novoHtml = gerarHTMLMapa();
            Platform.runLater(() -> webEngineRef.get().loadContent(novoHtml));
        } else {
            // Se ainda não carregou, inicia o processo completo
            iniciarMapa();
        }
    }

    private void iniciarMapa() {
        Platform.runLater(() -> {
            WebView webView = new WebView();
            WebEngine engine = webView.getEngine();

            // Armazena a referência para permitir atualizações dinâmicas
            webEngineRef.set(engine);

            String htmlMapa = gerarHTMLMapa();
            engine.loadContent(htmlMapa);
            fxPanel.setScene(new Scene(webView));
        });
    }

    private String gerarHTMLMapa() {
        // Coordenadas simuladas (Centro de Maringá)
        double latMaringa = -23.4205;
        double lonMaringa = -51.9331;

        // Simulação de pontos de Origem/Destino (simples desvio do centro)
        // Em um sistema real, você usaria uma API de Geocoding (ex: Nominatim)
        double latOrigem = latMaringa - 0.03;
        double lonOrigem = lonMaringa - 0.05;
        double latDestino = latMaringa + 0.02;
        double lonDestino = lonMaringa + 0.02;

        String script = String.format("""
            var map = L.map('map').setView([%f, %f], 13);
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                maxZoom: 19,
                attribution: '© OpenStreetMap contributors'
            }).addTo(map);

            // Marcador de Origem
            L.marker([%f, %f]).addTo(map)
                .bindPopup('<b>Partida:</b> %s')
                .openPopup();

            // Marcador de Destino
            L.marker([%f, %f]).addTo(map)
                .bindPopup('<b>Chegada:</b> %s')
                .openPopup();
                
            // Simula a rota com Polyline (linha reta)
            var latlngs = [[%f, %f], [%f, %f]];
            var polyline = L.polyline(latlngs, {color: '#0078D4', weight: 5}).addTo(map);
            
            // Ajusta o mapa para mostrar ambos os marcadores
            map.fitBounds(polyline.getBounds().pad(0.5)); // padding para melhor visualização
        """, latMaringa, lonMaringa,
                latOrigem, lonOrigem, this.origem,
                latDestino, lonDestino, this.destino,
                latOrigem, lonOrigem, latDestino, lonDestino
        );

        // HTML Completo
        return """
        <!DOCTYPE html>
        <html lang='pt-br'>
        <head>
          <meta charset='utf-8'/>
          <meta name='viewport' content='width=device-width, initial-scale=1.0'>
          <link rel='stylesheet' href='https://unpkg.com/leaflet@1.9.4/dist/leaflet.css'/>
          <script src='https://unpkg.com/leaflet@1.9.4/dist/leaflet.js'></script>
          <style>html, body {height:100%%; margin:0;} #map {height:100%%; width:100%%;}</style>
        </head>
        <body>
          <div id='map'></div>
          <script>
            %s
          </script>
        </body>
        </html>
        """.formatted(script);
    }
}