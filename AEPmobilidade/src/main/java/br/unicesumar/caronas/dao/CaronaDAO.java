package br.unicesumar.caronas.dao;

import br.unicesumar.caronas.model.Carona;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CaronaDAO {

    public boolean inserir(Carona c) {
        String sql = "INSERT INTO caronas (id_usuario, origem, destino, data_hora, vagas) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, c.getIdUsuario()); // ✅ corrigido (antes era getMotoristaId)
            ps.setString(2, c.getOrigem());
            ps.setString(3, c.getDestino());
            ps.setTimestamp(4, Timestamp.valueOf(c.getDataHora()));
            ps.setInt(5, c.getVagas());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        c.setId(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<Carona> listarProximas() {
        List<Carona> res = new ArrayList<>();
        String sql = "SELECT * FROM caronas WHERE data_hora >= NOW() ORDER BY data_hora ASC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Carona c = new Carona();
                c.setId(rs.getInt("id"));
                c.setIdUsuario(rs.getInt("id_usuario"));
                c.setOrigem(rs.getString("origem"));
                c.setDestino(rs.getString("destino"));
                c.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                c.setVagas(rs.getInt("vagas"));
                res.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }
}
