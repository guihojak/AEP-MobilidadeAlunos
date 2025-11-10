package br.unicesumar.caronas.dao;

import br.unicesumar.caronas.model.Usuario;
import org.mindrot.jbcrypt.BCrypt; // ⚠️ NOVA IMPORTAÇÃO
import java.sql.*;

public class UsuarioDAO {

    public boolean inserir(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, email, senha_hash) VALUES (?, ?, ?)";

        // ⚠️ Hashing da senha com BCrypt
        String senhaHashed = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, senhaHashed); // Salva o hash

            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuario.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Usuario autenticar(String email, String senhaPura) {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        Usuario usuario = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    String senhaHashed = rs.getString("senha_hash");

                    // ⚠️ Verificação da senha com BCrypt
                    if (BCrypt.checkpw(senhaPura, senhaHashed)) {
                        return usuario;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Busca o nome do usuário pelo ID (usado no PainelCaronasView).
     */
    public String getNomeUsuario(int id) {
        String sql = "SELECT nome FROM usuarios WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nome");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar nome do usuário: " + e.getMessage());
        }
        return "Desconhecido";
    }
}