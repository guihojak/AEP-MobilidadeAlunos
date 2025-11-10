package br.unicesumar.caronas.view;

import br.unicesumar.caronas.controller.UsuarioController;
import br.unicesumar.caronas.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginView extends JFrame {

    private final JTextField txtEmail;
    private final JPasswordField txtSenha;
    private final UsuarioController usuarioController;

    public LoginView() {
        super("Caronas Acadêmicas - Login");
        usuarioController = new UsuarioController();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 280);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel painel = new JPanel(new GridLayout(5, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel lblTitulo = new JLabel("Acesse sua conta", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        painel.add(lblTitulo);

        txtEmail = new JTextField();
        txtEmail.setBorder(BorderFactory.createTitledBorder("E-mail institucional"));
        painel.add(txtEmail);

        txtSenha = new JPasswordField();
        txtSenha.setBorder(BorderFactory.createTitledBorder("Senha"));
        painel.add(txtSenha);

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.addActionListener(this::autenticarUsuario);
        painel.add(btnEntrar);

        JButton btnCadastrar = new JButton("Criar nova conta");
        btnCadastrar.addActionListener(e -> {
            new CadastroUsuarioView().setVisible(true);
            dispose();
        });
        painel.add(btnCadastrar);

        add(painel, BorderLayout.CENTER);
    }

    private void autenticarUsuario(ActionEvent e) {
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());

        Usuario usuario = usuarioController.autenticar(email, senha);
        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Bem-vindo, " + usuario.getNome() + "!");
            new PainelPrincipalView(usuario).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "E-mail ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
