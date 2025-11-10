package br.unicesumar.caronas.model;

import java.time.LocalDateTime;

/**
 * Representa uma carona publicada por um usuário.
 */
public class Carona {
    private int id;
    private int motoristaId;
    private String origem;
    private String destino;
    private LocalDateTime dataHora;
    private int vagas;
    private String descricao;

    public Carona() {}

    public Carona(int motoristaId, String origem, String destino, LocalDateTime dataHora, int vagas, String descricao) {
        this.motoristaId = motoristaId;
        this.origem = origem;
        this.destino = destino;
        this.dataHora = dataHora;
        this.vagas = vagas;
        this.descricao = descricao;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getMotoristaId() { return motoristaId; }
    public void setMotoristaId(int motoristaId) { this.motoristaId = motoristaId; }
    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }
    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public int getVagas() { return vagas; }
    public void setVagas(int vagas) { this.vagas = vagas; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
