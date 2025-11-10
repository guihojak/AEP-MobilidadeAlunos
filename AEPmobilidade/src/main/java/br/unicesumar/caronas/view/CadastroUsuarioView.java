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

    private static final Color PRIMARY_BLUE = new Color(0, 120, 212);
    private static final String APP_NAME = "UniCarona";
    private static final int INPUT_HEIGHT = 45;

    public CadastroUsuarioView() {
        super(APP_NAME + " - Cadastro");
        usuarioController = new UsuarioController();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 480);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(7, 1, 15, 15));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        painel.setBackground(Color.WHITE);

        // 1. Branding
        JLabel lblAppName = new JLabel("🚗 " + APP_NAME, SwingConstants.CENTER);
        lblAppName.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblAppName.setForeground(PRIMARY_BLUE.darker().darker());
        painel.add(lblAppName);

        // 2. Título da Seção
        JLabel lblTitulo = new JLabel("Criar Nova Conta", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblTitulo.setForeground(Color.DARK_GRAY);
        painel.add(lblTitulo);

        // 3. Campo Nome (Fonte reduzida para 14)
        txtNome = new JTextField();
        painel.add(criarInputComIcone(txtNome, "👤 Nome completo", INPUT_HEIGHT));

        // 4. Campo E-mail (Fonte reduzida para 14)
        txtEmail = new JTextField();
        painel.add(criarInputComIcone(txtEmail, "📧 E-mail institucional", INPUT_HEIGHT));

        // 5. Campo Senha (Fonte reduzida para 14)
        txtSenha = new JPasswordField();
        painel.add(criarInputComIcone(txtSenha, "🔒 Senha", INPUT_HEIGHT));

        // 6. Botão Cadastrar (Destaque Azul)
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnCadastrar.setBackground(PRIMARY_BLUE);
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.addActionListener(this::cadastrarUsuario);
        painel.add(btnCadastrar);

        // 7. Botão Voltar (Secundário/Link)
        JButton btnVoltar = new JButton("Voltar ao Login");
        btnVoltar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnVoltar.setForeground(PRIMARY_BLUE.darker());
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
        painel.add(btnVoltar);

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

    private void cadastrarUsuario(ActionEvent e) {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());

        boolean sucesso = usuarioController.cadastrar(nome, email, senha);

        if (sucesso) {
            // Refinando a mensagem de sucesso do cadastro
            JOptionPane.showMessageDialog(this,
                    "🎉 Conta criada com sucesso! Você já pode fazer login.",
                    "Cadastro Concluído",
                    JOptionPane.INFORMATION_MESSAGE);
            new LoginView().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "❌ Erro ao cadastrar. Verifique se o e-mail é válido ou se já está em uso.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}