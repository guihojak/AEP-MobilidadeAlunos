package br.unicesumar.caronas.dao;

import br.unicesumar.caronas.model.Carona;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia as operações da tabela caronas.
 */
public class CaronaDAO {

    public boolean inserir(Carona c) {
        String sql = "INSERT INTO caronas (motorista_id, origem, destino, data_hora, vagas, descricao) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, c.getMotoristaId());
            ps.setString(2, c.getOrigem());
            ps.setString(3, c.getDestino());
            ps.setTimestamp(4, Timestamp.valueOf(c.getDataHora()));
            ps.setInt(5, c.getVagas());
            ps.setString(6, c.getDescricao());

            int linhas = ps.executeUpdate();
            if (linhas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) c.setId(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir carona: " + e.getMessage());
        }
        return false;
    }

    public List<Carona> listarProximas() {
        List<Carona> lista = new ArrayList<>();
        String sql = "SELECT * FROM caronas WHERE data_hora >= NOW() ORDER BY data_hora ASC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Carona c = new Carona();
                c.setId(rs.getInt("id"));
                c.setMotoristaId(rs.getInt("motorista_id"));
                c.setOrigem(rs.getString("origem"));
                c.setDestino(rs.getString("destino"));
                c.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                c.setVagas(rs.getInt("vagas"));
                c.setDescricao(rs.getString("descricao"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar caronas: " + e.getMessage());
        }
        return lista;
    }
}
