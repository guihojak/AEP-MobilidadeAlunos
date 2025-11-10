package br.unicesumar.caronas.view;

import br.unicesumar.caronas.controller.UsuarioController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CadastroUsuarioView extends JFrame {

    private final JTextField txtNome;
    private final JTextField txtEmail;
    private final JPasswordField txtSenha;
    private final UsuarioController usuarioController;

    public CadastroUsuarioView() {
        super("Cadastrar novo usuário");
        usuarioController = new UsuarioController();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 320);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));
        setResizable(false);

        txtNome = new JTextField();
        txtNome.setBorder(BorderFactory.createTitledBorder("Nome completo"));
        add(txtNome);

        txtEmail = new JTextField();
        txtEmail.setBorder(BorderFactory.createTitledBorder("E-mail institucional"));
        add(txtEmail);

        txtSenha = new JPasswordField();
        txtSenha.setBorder(BorderFactory.createTitledBorder("Senha"));
        add(txtSenha);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(this::cadastrarUsuario);
        add(btnCadastrar);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
        add(btnVoltar);
    }

    private void cadastrarUsuario(ActionEvent e) {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());

        boolean sucesso = usuarioController.cadastrar(nome, email, senha);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            new LoginView().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar. Verifique os dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
