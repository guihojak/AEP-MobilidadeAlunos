package br.unicesumar.caronas.controller;

import br.unicesumar.caronas.dao.CaronaDAO;
import br.unicesumar.caronas.model.Carona;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CaronaController {
    private final CaronaDAO dao = new CaronaDAO();
    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public boolean criarCarona(Carona c) {
        return dao.inserir(c);
    }

    public List<Carona> listarProximas() {
        return dao.listarProximas();
    }

    /**
     * Geocode simples via Nominatim. Retorna Optional<double[]> {lat, lon}
     */
    public Optional<double[]> geocode(String query) {
        try {
            String q = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String url = "https://nominatim.openstreetmap.org/search?q=" + q + "&format=json&limit=1";
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "AEP-mobilidade/1.0 (meuemail@example.com)")
                    .GET().build();
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            JsonNode root = mapper.readTree(resp.body());
            if (root.isArray() && root.size() > 0) {
                JsonNode item = root.get(0);
                double lat = item.get("lat").asDouble();
                double lon = item.get("lon").asDouble();
                return Optional.of(new double[]{lat, lon});
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }
}
