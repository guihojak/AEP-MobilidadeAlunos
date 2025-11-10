package br.unicesumar.caronas.controller;

import br.unicesumar.caronas.dao.UsuarioDAO;
import br.unicesumar.caronas.model.Usuario;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario autenticar(String email, String senha) {
        return usuarioDAO.autenticar(email, senha);
    }

    public boolean cadastrar(String nome, String email, String senha) {
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            return false;
        }
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        return usuarioDAO.inserir(usuario);
    }

    public String getNomeUsuario(int id) {
        return usuarioDAO.getNomeUsuario(id);
    }
}