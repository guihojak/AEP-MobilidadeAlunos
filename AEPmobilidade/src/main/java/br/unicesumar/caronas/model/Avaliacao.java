package br.unicesumar.caronas.model;

import java.time.LocalDateTime;

/**
 * Representa a avaliação feita por um usuário após uma carona.
 */
public class Avaliacao {
    private int id;
    private int caronaId;
    private int avaliadorId;
    private int nota;
    private String comentario;
    private LocalDateTime createdAt;

    public Avaliacao() {}

    public Avaliacao(int caronaId, int avaliadorId, int nota, String comentario) {
        this.caronaId = caronaId;
        this.avaliadorId = avaliadorId;
        this.nota = nota;
        this.comentario = comentario;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCaronaId() { return caronaId; }
    public void setCaronaId(int caronaId) { this.caronaId = caronaId; }
    public int getAvaliadorId() { return avaliadorId; }
    public void setAvaliadorId(int avaliadorId) { this.avaliadorId = avaliadorId; }
    public int getNota() { return nota; }
    public void setNota(int nota) { this.nota = nota; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
