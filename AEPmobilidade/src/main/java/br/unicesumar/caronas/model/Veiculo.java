package br.unicesumar.caronas.model;

public class Veiculo {

    private int id;
    private int idUsuario;
    private String modelo;
    private String placa;
    private int capacidade; // ⚠️ NOVO CAMPO: capacidade

    // Construtor Vazio
    public Veiculo() {
    }

    // Construtor Completo
    public Veiculo(int id, int idUsuario, String modelo, String placa, int capacidade) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.modelo = modelo;
        this.placa = placa;
        this.capacidade = capacidade;
    }

    // --- Getters e Setters ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    // Getter e Setter para a nova capacidade
    public int getCapacidade() { return capacidade; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }
}