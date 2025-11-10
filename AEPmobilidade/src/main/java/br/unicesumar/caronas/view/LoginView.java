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

    // Cores e Branding
    private static final Color PRIMARY_BLUE = new Color(0, 120, 212);
    private static final String APP_NAME = "UniCarona"; // Seu nome de app
    private static final int INPUT_HEIGHT = 45; // Altura forçada do campo (aumentada um pouco)

    public LoginView() {
        super(APP_NAME + " - Login");
        usuarioController = new UsuarioController();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel painel = new JPanel(new GridLayout(6, 1, 15, 15));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        painel.setBackground(Color.WHITE);

        // 1. Branding
        JLabel lblAppName = new JLabel("🚗 " + APP_NAME, SwingConstants.CENTER);
        lblAppName.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblAppName.setForeground(PRIMARY_BLUE.darker().darker());
        painel.add(lblAppName);

        // 2. Título da Seção
        JLabel lblTitulo = new JLabel("Acesse sua conta", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblTitulo.setForeground(Color.DARK_GRAY);
        painel.add(lblTitulo);

        // 3. Campo E-mail (Fonte reduzida para 14)
        txtEmail = new JTextField();
        painel.add(criarInputComIcone(txtEmail, "📧 E-mail institucional", INPUT_HEIGHT));

        // 4. Campo Senha (Fonte reduzida para 14)
        txtSenha = new JPasswordField();
        painel.add(criarInputComIcone(txtSenha, "🔒 Senha", INPUT_HEIGHT));

        // 5. Botão Entrar (Destaque Azul)
        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnEntrar.setBackground(PRIMARY_BLUE);
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.addActionListener(this::autenticarUsuario);
        btnEntrar.setPreferredSize(new Dimension(100, 45));
        painel.add(btnEntrar);

        // 6. Botão Cadastrar (Secundário/Link)
        JButton btnCadastrar = new JButton("Criar nova conta");
        btnCadastrar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCadastrar.setForeground(PRIMARY_BLUE.darker());
        btnCadastrar.setContentAreaFilled(false);
        btnCadastrar.setBorderPainted(false);
        btnCadastrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCadastrar.addActionListener(e -> {
            new CadastroUsuarioView().setVisible(true);
            dispose();
        });
        painel.add(btnCadastrar);

        add(painel, BorderLayout.CENTER);
    }

    /**
     * Helper: Customiza e força uma altura em JTextField/JPasswordField.
     */
    private JComponent criarInputComIcone(JComponent input, String titulo, int altura) {
        input.setPreferredSize(new Dimension(input.getPreferredSize().width, altura));
        // ⚠️ Ajuste principal: Fonte menor para caber melhor
        input.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        input.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                titulo,
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP
        ));

        return input;
    }


    private void autenticarUsuario(ActionEvent e) {
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());

        Usuario usuario = usuarioController.autenticar(email, senha);
        if (usuario != null) {
            // Refinando a mensagem de sucesso do login
            JOptionPane.showMessageDialog(this,
                    "✅ Login realizado com sucesso! Bem-vindo(a), " + usuario.getNome() + "!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            new PainelPrincipalView(usuario).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "❌ E-mail ou senha inválidos. Por favor, tente novamente.",
                    "Erro de Autenticação",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}