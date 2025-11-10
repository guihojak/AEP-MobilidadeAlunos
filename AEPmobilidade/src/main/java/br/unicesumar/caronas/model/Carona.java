package br.unicesumar.caronas.model;

import java.time.LocalDateTime;

public class Carona {
    private int id;
    private String origem;
    private String destino;
    private LocalDateTime dataHora;
    private int vagas;
    private int idUsuario; // ✅ novo campo: quem criou a carona

    // --- Getters e Setters ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) { // ✅ método faltante
        this.idUsuario = idUsuario;
    }

    // --- Representação textual (para logs/debug) ---
    @Override
    public String toString() {
        return String.format("Carona[%s -> %s, %s, Vagas: %d, Usuário: %d]",
                origem, destino, dataHora, vagas, idUsuario);
    }
}
