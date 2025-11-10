package br.unicesumar.caronas.model;

/**
 * Armazena informações do veículo de um motorista.
 */
public class Veiculo {
    private int id;
    private int usuarioId;
    private String modelo;
    private String placa;
    private int capacidade;

    public Veiculo() {}

    public Veiculo(int usuarioId, String modelo, String placa, int capacidade) {
        this.usuarioId = usuarioId;
        this.modelo = modelo;
        this.placa = placa;
        this.capacidade = capacidade;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public int getCapacidade() { return capacidade; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }
}
