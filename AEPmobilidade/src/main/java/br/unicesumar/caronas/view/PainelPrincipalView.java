package br.unicesumar.caronas.view;

import br.unicesumar.caronas.model.Usuario;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;

public class PainelPrincipalView extends JFrame {

    private final Usuario usuarioLogado;
    private final CardLayout cardLayout;
    private final JPanel painelConteudo;
    private final MapaRealView mapaRealView;

    // Cores e Estilos
    private static final Color PRIMARY_BLUE = new Color(0, 120, 212);
    private static final Color HOVER_COLOR = new Color(220, 220, 220);

    public PainelPrincipalView(Usuario usuario) {
        this.usuarioLogado = usuario;

        // 1. Aplica o tema moderno (FlatLaf)
        try {
            FlatIntelliJLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }

        setTitle("UniCarona - Bem-vindo, " + usuario.getNome());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ⚠️ REMOVIDA A TELA CHEIA. Definindo um tamanho fixo e centralizado.
        setSize(1200, 800);
        setLocationRelativeTo(null); // Centraliza a janela

        // 2. Instancia Layout e Paineis
        this.cardLayout = new CardLayout();
        this.painelConteudo = new JPanel(cardLayout);

        this.mapaRealView = new MapaRealView();

        // 3. Cria e adiciona os paineis
        JPanel painelCaronas = new PainelCaronasView(usuario, this);
        JPanel painelPerfil = new PainelPerfilView(usuario);

        painelConteudo.add(painelCaronas, "Caronas");
        painelConteudo.add(mapaRealView, "Mapa");
        painelConteudo.add(painelPerfil, "Perfil");

        // 4. Cria a barra lateral de Navegação customizada
        JPanel painelMenu = criarMenuLateral();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelMenu, painelConteudo);
        splitPane.setDividerLocation(200); // Largura fixa da barra lateral (melhor que 180)
        splitPane.setDividerSize(1); // Deixa uma linha fina para separar
        splitPane.setEnabled(false); // Impede que o usuário redimensione o split

        getContentPane().add(splitPane);

        // 5. Garante que a primeira tela seja Caronas
        cardLayout.show(painelConteudo, "Caronas");

        setVisible(true);
    }

    // Método para exibir rota no mapa (mantido da integração anterior)
    public void exibirRotaNoMapa(String origem, String destino) {
        cardLayout.show(painelConteudo, "Mapa");
        mapaRealView.mostrarCarona(origem, destino);
    }

    // ⚠️ NOVO MÉTODO: Cria uma barra lateral com botões customizados (mais clean que o JTabbedPane)
    private JPanel criarMenuLateral() {
        JPanel painelMenu = new JPanel(new BorderLayout());
        painelMenu.setBackground(Color.WHITE);

        // Título/Logo
        JLabel lblLogo = new JLabel("UniCarona 🚗", SwingConstants.CENTER);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblLogo.setForeground(PRIMARY_BLUE.darker());
        lblLogo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        painelMenu.add(lblLogo, BorderLayout.NORTH);

        // Painel de Botões (Centro)
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));
        painelBotoes.setBackground(Color.WHITE);

        // Cria os botões de navegação
        JButton btnCaronas = criarBotaoMenu("🚗 Caronas", "Caronas");
        JButton btnMapa = criarBotaoMenu("🗺️ Mapa", "Mapa");
        JButton btnPerfil = criarBotaoMenu("👤 Perfil", "Perfil");

        painelBotoes.add(btnCaronas);
        painelBotoes.add(btnMapa);
        painelBotoes.add(btnPerfil);

        // Padding para centralizar verticalmente os botões
        painelBotoes.add(Box.createVerticalGlue());

        painelMenu.add(painelBotoes, BorderLayout.CENTER);

        // Botão de Sair (Rodapé)
        JButton btnSair = new JButton("Sair (Logout)");
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSair.setBackground(Color.RED);
        btnSair.setForeground(Color.WHITE);
        btnSair.setFocusPainted(false);
        btnSair.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnSair.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });

        JPanel painelRodape = new JPanel(new BorderLayout());
        painelRodape.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        painelRodape.setBackground(Color.WHITE);
        painelRodape.add(btnSair, BorderLayout.NORTH); // Coloca o botão no topo do rodapé

        painelMenu.add(painelRodape, BorderLayout.SOUTH);

        return painelMenu;
    }

    // Helper para criar botões customizados com ação de navegação
    private JButton criarBotaoMenu(String texto, String cardName) {
        JButton button = new JButton(texto);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 50));
        button.setPreferredSize(new Dimension(180, 50));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Estilo de hover simples
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(HOVER_COLOR);
                button.setOpaque(true);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
                button.setOpaque(false);
            }
        });

        // Ação de navegação
        button.addActionListener(e -> cardLayout.show(painelConteudo, cardName));
        return button;
    }

    // 🔹 Painel de Perfil (Manter essa inner class)
    private static class PainelPerfilView extends JPanel {
        private static final Color PRIMARY_BLUE = new Color(0, 120, 212);
        private static final Color BACKGROUND_GRAY = new Color(245, 245, 245);

        public PainelPerfilView(Usuario usuario) {
            setLayout(new BorderLayout(0, 0));
            setBackground(BACKGROUND_GRAY);
            setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

            // 1. Título Geral
            JLabel titulo = new JLabel("Minha Conta", SwingConstants.LEFT);
            titulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
            titulo.setForeground(PRIMARY_BLUE.darker());
            titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
            add(titulo, BorderLayout.NORTH);

            // 2. Painel Central para centralizar o CARD do Perfil
            JPanel painelCentralizado = new JPanel(new GridBagLayout());
            painelCentralizado.setBackground(BACKGROUND_GRAY);

            JPanel painelCard = criarCardPerfil(usuario);
            painelCentralizado.add(painelCard);

            add(painelCentralizado, BorderLayout.CENTER);
        }

        // Novo método para criar o "Card" do Perfil
        private JPanel criarCardPerfil(Usuario usuario) {
            // Card principal com sombra e cantos arredondados (Fluent/Soft Design)
            JPanel card = new JPanel(new BorderLayout(30, 30));
            card.setPreferredSize(new Dimension(500, 550)); // Tamanho fixo para destaque
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

            // Aplica o estilo de "Card" (sombra e cantos arredondados)
            card.putClientProperty(FlatClientProperties.STYLE,
                    "arc: 15;" +
                            "shadowWidth: 10;" +
                            "shadowOpacity: 0.15;"
            );

            // --- Título do Card ---
            JLabel tituloCard = new JLabel("Detalhes Pessoais", SwingConstants.CENTER);
            tituloCard.setFont(new Font("Segoe UI", Font.BOLD, 22));
            tituloCard.setForeground(PRIMARY_BLUE);
            card.add(tituloCard, BorderLayout.NORTH);

            // --- Painel de Dados (FlowLayout para centralizar itens verticais) ---
            JPanel painelDados = new JPanel();
            painelDados.setLayout(new BoxLayout(painelDados, BoxLayout.Y_AXIS));
            painelDados.setBackground(Color.WHITE);
            painelDados.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

            // Foto/Ícone
            JPanel painelFoto = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelFoto.setBackground(Color.WHITE);
            painelFoto.add(criarIconePerfil());
            painelDados.add(painelFoto);

            painelDados.add(Box.createVerticalStrut(25)); // Espaçamento

            // Nome e Email (Alinhados à Esquerda, mas centrados no card)
            painelDados.add(criarDetalhe("👤", "Nome:", usuario.getNome()));
            painelDados.add(Box.createVerticalStrut(15));
            painelDados.add(criarDetalhe("📧", "E-mail:", usuario.getEmail()));
            painelDados.add(Box.createVerticalStrut(15));
            painelDados.add(criarDetalhe("⭐️", "Reputação:", "5.0/5.0"));

            painelDados.add(Box.createVerticalStrut(40));

            // Botão Ação
            JButton btnVeiculos = new JButton("Gerenciar Veículos");
            btnVeiculos.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnVeiculos.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btnVeiculos.setBackground(PRIMARY_BLUE.darker());
            btnVeiculos.setForeground(Color.WHITE);
            btnVeiculos.setFocusPainted(false);
            btnVeiculos.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
            btnVeiculos.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

            painelDados.add(btnVeiculos);

            card.add(painelDados, BorderLayout.CENTER);

            return card;
        }

        private JPanel criarDetalhe(String icone, String titulo, String valor) {
            JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
            painel.setBackground(Color.WHITE);
            painel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel lblIcone = new JLabel(icone);
            lblIcone.setFont(new Font("Segoe UI", Font.PLAIN, 20));

            JLabel lblDetalhe = new JLabel(String.format("<html><b>%s</b> %s</html>", titulo, valor));
            lblDetalhe.setFont(new Font("Segoe UI", Font.PLAIN, 18));

            painel.add(lblIcone);
            painel.add(lblDetalhe);
            return painel;
        }

        private JPanel criarIconePerfil() {
            return new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    int size = Math.min(getWidth(), getHeight());
                    g2d.setColor(PRIMARY_BLUE.brighter());
                    g2d.fillOval(0, 0, size, size);

                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("Segoe UI", Font.BOLD, 40));
                    g2d.drawString("👤", size / 2 - 20, size / 2 + 15);
                }
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(100, 100);
                }
            };
        }
    }

        // Helper para criar labels com ícone (usando Unicode)
        private JPanel criarDetalhe(String icone, String titulo, String valor) {
            JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
            painel.setBackground(Color.WHITE);
            JLabel lblIcone = new JLabel(icone);
            lblIcone.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            JLabel lblDetalhe = new JLabel(String.format("<html><b>%s</b> %s</html>", titulo, valor));
            lblDetalhe.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            painel.add(lblIcone);
            painel.add(lblDetalhe);
            return painel;
        }
    }
