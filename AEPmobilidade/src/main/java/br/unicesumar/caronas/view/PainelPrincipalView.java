package br.unicesumar.caronas.view;

import br.unicesumar.caronas.model.Usuario;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;

public class PainelPrincipalView extends JFrame {

    private final Usuario usuarioLogado;
    private JTabbedPane tabbedPane;

    private static final Color PRIMARY_BLUE = new Color(0, 120, 212);

    public PainelPrincipalView(Usuario usuario) {
        this.usuarioLogado = usuario;

        // Configura o Look and Feel
        try {
            FlatIntelliJLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf: " + ex);
        }

        setTitle("UniCarona - Painel Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null); // Centraliza a janela

        initComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        // Estilo das abas
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tabbedPane.setForeground(PRIMARY_BLUE.darker());
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        // 1. Aba de Caronas (Tela Principal)
        PainelCaronasView caronasView = new PainelCaronasView(usuarioLogado, this);
        tabbedPane.addTab("  Explorar Caronas  ", caronasView);

        // 2. Aba de Veículos (NOVA INTEGRAÇÃO)
        PainelVeiculosView veiculosView = new PainelVeiculosView(usuarioLogado);
        tabbedPane.addTab("  Meus Veículos  ", veiculosView);

        // 3. Aba de Perfil (Tela de Detalhes do Usuário)
        PainelPerfilView perfilView = new PainelPerfilView(usuarioLogado);
        tabbedPane.addTab("  Meu Perfil  ", perfilView);

        this.add(tabbedPane, BorderLayout.CENTER);
    }

    // Método auxiliar para exibir a rota no mapa (usado pelo PainelCaronasView)
    public void exibirRotaNoMapa(String origem, String destino) {
        // Implementação simulada
        JOptionPane.showMessageDialog(this,
                String.format("Exibindo rota de:\nOrigem: %s\nDestino: %s", origem, destino),
                "Visualização de Rota", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método principal para iniciar a aplicação (opcional, se não estiver em Main.java)
    /*
    public static void main(String[] args) {
        // Exemplo: Criar um usuário de teste
        Usuario u = new Usuario(1, "Ana Carolina", "ana.carolina@unicesumar.edu.br", "hashed_pass", "998877", "(44) 99999-0000");

        EventQueue.invokeLater(() -> {
            new PainelPrincipalView(u).setVisible(true);
        });
    }
    */

    // =================================================================
    // 👤 INNER CLASS: Painel Perfil (Corrigido para Soft Design)
    // =================================================================

    private static class PainelPerfilView extends JPanel {
        private static final Color PRIMARY_BLUE = new Color(0, 120, 212);
        private static final Color BACKGROUND_GRAY = new Color(240, 240, 240);
        private final Usuario usuario;

        public PainelPerfilView(Usuario usuario) {
            this.usuario = usuario;
            setLayout(new BorderLayout(0, 0));
            setBackground(BACKGROUND_GRAY);
            setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

            JLabel titulo = new JLabel("Minha Conta 👤", SwingConstants.LEFT);
            titulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
            titulo.setForeground(PRIMARY_BLUE.darker());
            titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
            add(titulo, BorderLayout.NORTH);

            JPanel painelCentralizado = new JPanel(new GridBagLayout());
            painelCentralizado.setBackground(BACKGROUND_GRAY);

            JPanel containerCards = new JPanel();
            containerCards.setLayout(new BoxLayout(containerCards, BoxLayout.Y_AXIS));
            containerCards.setBackground(BACKGROUND_GRAY);
            containerCards.add(Box.createVerticalStrut(20));

            containerCards.add(criarCardDetalhes(usuario));
            containerCards.add(Box.createVerticalStrut(30));
            containerCards.add(criarCardAcoes());

            painelCentralizado.add(containerCards);

            add(painelCentralizado, BorderLayout.CENTER);
        }

        private JPanel criarCardDetalhes(Usuario usuario) {
            JPanel card = new JPanel(new BorderLayout(30, 30));
            card.setPreferredSize(new Dimension(550, 350));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
            card.setAlignmentX(Component.CENTER_ALIGNMENT);

            // ⚠️ CORREÇÃO: Aplica apenas o arredondamento (arc)
            card.putClientProperty(FlatClientProperties.STYLE, "arc: 15");

            // --- Ícone e Nome (NORTH) ---
            JPanel painelTopo = new JPanel(new BorderLayout(20, 0));
            painelTopo.setBackground(Color.WHITE);
            painelTopo.add(criarIconePerfil(), BorderLayout.WEST);

            JLabel lblNome = new JLabel("<html><b style='font-size: 20pt;'>"+ usuario.getNome() +"</b><br>"+ usuario.getEmail() +"</html>");
            lblNome.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            painelTopo.add(lblNome, BorderLayout.CENTER);

            card.add(painelTopo, BorderLayout.NORTH);

            // --- Painel de Dados (CENTER) ---
            JPanel painelDados = new JPanel(new GridLayout(3, 1, 10, 15));
            painelDados.setBackground(Color.WHITE);
            painelDados.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

            painelDados.add(criarDetalhe("📱", "Telefone:", usuario.getTelefone() != null ? usuario.getTelefone() : "Não Cadastrado"));
            painelDados.add(criarDetalhe("🎓", "Matrícula:", usuario.getMatricula()));
            painelDados.add(criarDetalhe("⭐️", "Reputação:", "5.0/5.0"));

            card.add(painelDados, BorderLayout.CENTER);

            return card;
        }

        private JPanel criarCardAcoes() {
            JPanel card = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
            card.setPreferredSize(new Dimension(550, 100));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            card.setAlignmentX(Component.CENTER_ALIGNMENT);

            // ⚠️ CORREÇÃO: Aplica apenas o arredondamento (arc)
            card.putClientProperty(FlatClientProperties.STYLE, "arc: 15");

            // Botão Gerenciar Veículos - Leva para a aba Veículos
            JButton btnVeiculos = new JButton("Gerenciar Veículos");
            btnVeiculos.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnVeiculos.setBackground(PRIMARY_BLUE.darker());
            btnVeiculos.setForeground(Color.WHITE);
            btnVeiculos.setFocusPainted(false);
            btnVeiculos.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
            btnVeiculos.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

            btnVeiculos.addActionListener(e -> {
                // Lógica para mudar para a aba de Veículos (se PainelPrincipalView fosse acessível)
                // Como é static inner class, precisaria de uma referência ou método público na Main View.
                JOptionPane.showMessageDialog(null, "Acesse a aba 'Meus Veículos' para gerenciar.", "Navegação", JOptionPane.INFORMATION_MESSAGE);
            });

            // Botão Editar Perfil
            JButton btnEditar = new JButton("Editar Perfil");
            btnEditar.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnEditar.setBackground(new Color(100, 100, 100));
            btnEditar.setForeground(Color.WHITE);
            btnEditar.setFocusPainted(false);
            btnEditar.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
            btnEditar.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

            card.add(btnVeiculos);
            card.add(btnEditar);

            return card;
        }

        private JPanel criarDetalhe(String icone, String titulo, String valor) {
            JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
            painel.setBackground(Color.WHITE);
            painel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel lblIcone = new JLabel(icone);
            lblIcone.setFont(new Font("Segoe UI", Font.PLAIN, 20));

            JLabel lblDetalhe = new JLabel(String.format("<html><b style='color: #555;'>%s</b> %s</html>", titulo, valor));
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
                    return new Dimension(80, 80);
                }
            };
        }
    }
}