package br.unicesumar.caronas.view;

import br.unicesumar.caronas.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class PainelPrincipalView extends JFrame {

    private final Usuario usuario;
    private final JPanel painelConteudo;
    private final CardLayout cardLayout;

    public PainelPrincipalView(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Caronas Acadêmicas - Plataforma de Mobilidade Universitária");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(true);

        // 🔹 Painel lateral de navegação
        JPanel painelLateral = criarPainelLateral();

        // 🔹 Painel central com conteúdo dinâmico (CardLayout)
        cardLayout = new CardLayout();
        painelConteudo = new JPanel(cardLayout);

        // Telas registradas
        painelConteudo.add(new MapaRealView(), "Mapa");
        painelConteudo.add(new PainelCaronasView(usuario), "Caronas");
        painelConteudo.add(new PainelPerfilView(usuario), "Perfil");

        add(painelLateral, BorderLayout.WEST);
        add(painelConteudo, BorderLayout.CENTER);

        // Exibir tela inicial
        cardLayout.show(painelConteudo, "Mapa");
    }

    private JPanel criarPainelLateral() {
        JPanel painelLateral = new JPanel();
        painelLateral.setBackground(new Color(25, 25, 35));
        painelLateral.setLayout(new BoxLayout(painelLateral, BoxLayout.Y_AXIS));
        painelLateral.setPreferredSize(new Dimension(220, getHeight()));

        JLabel lblTitulo = new JLabel("<html><center><b>CARONAS<br>ACADÊMICAS</b></center></html>");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 40, 0));
        painelLateral.add(lblTitulo);

        // Botões de navegação
        painelLateral.add(criarBotaoMenu("📍  Mapa de Rotas", "Mapa"));
        painelLateral.add(criarBotaoMenu("🚗  Minhas Caronas", "Caronas"));
        painelLateral.add(criarBotaoMenu("👤  Meu Perfil", "Perfil"));

        painelLateral.add(Box.createVerticalGlue());

        JButton btnSair = new JButton("⏻  Sair");
        btnSair.setBackground(new Color(220, 53, 69));
        btnSair.setForeground(Color.WHITE);
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSair.setFocusPainted(false);
        btnSair.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSair.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSair.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });
        painelLateral.add(btnSair);

        return painelLateral;
    }

    private JButton criarBotaoMenu(String texto, String tela) {
        JButton botao = new JButton(texto);
        botao.setMaximumSize(new Dimension(180, 45));
        botao.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        botao.setBackground(new Color(45, 45, 55));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        botao.addActionListener(e -> cardLayout.show(painelConteudo, tela));

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(new Color(70, 70, 90));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(new Color(45, 45, 55));
            }
        });

        return botao;
    }

    // 🔹 Painel do Mapa (simplificado para a entrega)
    private static class PainelMapaView extends JPanel {
        public PainelMapaView() {
            setLayout(new BorderLayout());
            JLabel label = new JLabel("🗺️  Mapa de Rotas (em desenvolvimento)", SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 20));
            label.setForeground(new Color(60, 60, 60));
            add(label, BorderLayout.CENTER);
        }
    }

    // 🔹 Painel de Perfil (dados do usuário)
    private static class PainelPerfilView extends JPanel {
        public PainelPerfilView(Usuario usuario) {
            setLayout(new GridLayout(4, 1, 10, 10));
            setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));

            JLabel titulo = new JLabel("Meu Perfil", SwingConstants.CENTER);
            titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
            add(titulo);

            JLabel nome = new JLabel("👤 Nome: " + usuario.getNome());
            nome.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            add(nome);

            JLabel email = new JLabel("📧 E-mail: " + usuario.getEmail());
            email.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            add(email);

            JLabel aviso = new JLabel("<html><center>Mais recursos de perfil estarão disponíveis<br>na próxima entrega.</center></html>", SwingConstants.CENTER);
            aviso.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            aviso.setForeground(new Color(100, 100, 100));
            add(aviso);
        }
    }
}
