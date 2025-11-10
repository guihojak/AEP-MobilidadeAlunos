package br.unicesumar.caronas.dao;

import br.unicesumar.caronas.model.Veiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    private Connection getConnection() throws SQLException {
        // ⚠️ SUBSTITUA PELA SUA LÓGICA DE CONEXÃO REAL
        throw new UnsupportedOperationException("Método de conexão não implementado.");
    }

    // --- C - CREATE ---
    public void salvar(Veiculo veiculo) {
        // SQL ajustado: apenas modelo, placa e capacidade
        String sql = "INSERT INTO veiculo (id_usuario, modelo, placa, capacidade) VALUES (?, ?, ?, ?)";

        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, veiculo.getIdUsuario());
            stmt.setString(2, veiculo.getModelo());
            stmt.setString(3, veiculo.getPlaca());
            stmt.setInt(4, veiculo.getCapacidade()); // Capacidade

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    veiculo.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- R - READ ---
    public List<Veiculo> listarPorUsuario(int idUsuario) {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM veiculo WHERE id_usuario = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Veiculo v = new Veiculo();
                    v.setId(rs.getInt("id"));
                    v.setIdUsuario(rs.getInt("id_usuario"));
                    v.setModelo(rs.getString("modelo"));
                    v.setPlaca(rs.getString("placa"));
                    v.setCapacidade(rs.getInt("capacidade")); // Capacidade
                    veiculos.add(v);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return veiculos;
    }

    // --- U - UPDATE ---
    public void atualizar(Veiculo veiculo) {
        // SQL ajustado: apenas modelo, placa e capacidade
        String sql = "UPDATE veiculo SET modelo=?, placa=?, capacidade=? WHERE id=? AND id_usuario=?";

        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, veiculo.getModelo());
            stmt.setString(2, veiculo.getPlaca());
            stmt.setInt(3, veiculo.getCapacidade()); // Capacidade
            stmt.setInt(4, veiculo.getId());
            stmt.setInt(5, veiculo.getIdUsuario());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- D - DELETE ---
    public void deletar(int idVeiculo) {
        String sql = "DELETE FROM veiculo WHERE id=?";

        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVeiculo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}