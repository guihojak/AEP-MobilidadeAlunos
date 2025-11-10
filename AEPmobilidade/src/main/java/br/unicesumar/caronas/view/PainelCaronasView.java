package br.unicesumar.caronas.view;

import br.unicesumar.caronas.controller.CaronaController;
import br.unicesumar.caronas.controller.UsuarioController;
import br.unicesumar.caronas.model.Carona;
import br.unicesumar.caronas.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.formdev.flatlaf.FlatClientProperties;

public class PainelCaronasView extends JPanel {

    private final Usuario usuario;
    private JTable tabelaCaronas;
    private DefaultTableModel modeloTabela;

    private final CaronaController caronaController;
    private final UsuarioController usuarioController;
    private final PainelPrincipalView principalView;

    private static final Color PRIMARY_BLUE = new Color(0, 120, 212);
    private static final Color BACKGROUND_GRAY = new Color(240, 240, 240);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public PainelCaronasView(Usuario usuario, PainelPrincipalView principalView) {
        this.usuario = usuario;
        this.caronaController = new CaronaController();
        this.usuarioController = new UsuarioController();
        this.principalView = principalView;

        setLayout(new BorderLayout(30, 30));

        // Remoção da borda LineBorder rígida para um visual mais limpo (apenas padding)
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        setBackground(BACKGROUND_GRAY);

        // 1. Área de Título e Filtro (NORTH)
        add(criarPainelTopo(), BorderLayout.NORTH);

        // 2. Painel Central (Tabela e Botões de Ação)
        JPanel painelCentral = new JPanel(new BorderLayout(0, 30));
        painelCentral.setBackground(BACKGROUND_GRAY);

        JComponent painelTabelaCard = criarPainelTabelaCard();
        painelCentral.add(painelTabelaCard, BorderLayout.CENTER);

        JPanel painelBotoes = criarPainelBotoes();
        painelCentral.add(painelBotoes, BorderLayout.SOUTH);

        add(painelCentral, BorderLayout.CENTER);

        atualizarCaronas();
    }

    /**
     * Cria a área de topo: Título principal + Barra de busca rápida.
     */
    private JPanel criarPainelTopo() {
        JPanel painelTopo = new JPanel(new BorderLayout(0, 15));
        painelTopo.setBackground(BACKGROUND_GRAY);

        // Título Principal
        JLabel titulo = new JLabel("Explorar Caronas 🌍", SwingConstants.LEFT);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titulo.setForeground(PRIMARY_BLUE.darker());
        painelTopo.add(titulo, BorderLayout.NORTH);

        // Barra de Busca (Simulando um input de site)
        JTextField txtBusca = new JTextField("Buscar por origem ou destino...");
        txtBusca.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtBusca.setForeground(Color.GRAY.darker());
        txtBusca.setPreferredSize(new Dimension(400, 45));

        // ⚠️ Correção ARC: Usa string literal para arredondamento (compatibilidade máxima)
        txtBusca.putClientProperty("TextComponent.arc", 10);
        txtBusca.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelBusca.setBackground(BACKGROUND_GRAY);
        painelBusca.add(txtBusca);

        painelTopo.add(painelBusca, BorderLayout.CENTER);

        return painelTopo;
    }

    /**
     * Cria o painel da tabela encapsulado em um "Card" com arredondamento.
     */
    private JComponent criarPainelTabelaCard() {
        String[] colunas = {"Origem", "Destino", "Data/Hora", "Vagas", "Motorista"};

        modeloTabela = new DefaultTableModel(null, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaCaronas = new JTable(modeloTabela);
        tabelaCaronas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaCaronas.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tabelaCaronas.setRowHeight(40);
        tabelaCaronas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));

        // Estilo de tabela limpo e suave
        tabelaCaronas.setShowGrid(false);
        tabelaCaronas.setIntercellSpacing(new Dimension(0, 1));
        tabelaCaronas.getTableHeader().setBackground(new Color(230, 230, 230));
        tabelaCaronas.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        tabelaCaronas.setBackground(Color.WHITE);
        tabelaCaronas.setSelectionBackground(PRIMARY_BLUE.brighter());
        tabelaCaronas.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(tabelaCaronas);
        scrollPane.setBackground(Color.WHITE);

        // Borda branca para 'padding' visual
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));

        // ⚠️ ATENÇÃO: Apenas 'arc' e 'borderWidth' são mantidos. Sombras removidas!
        scrollPane.putClientProperty(FlatClientProperties.STYLE,
                "arc: 12;" +
                        "borderWidth: 0"
        );

        // Adição do MouseListener (Funcionalidade)
        tabelaCaronas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int linhaSelecionada = tabelaCaronas.getSelectedRow();
                    if (linhaSelecionada != -1) {
                        String origem = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
                        String destino = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
                        principalView.exibirRotaNoMapa(origem, destino);
                    }
                }
            }
        });

        return scrollPane;
    }

    /**
     * Cria e configura o painel de botões de ação (Rodapé).
     */
    private JPanel criarPainelBotoes() {
        Color PRIMARY_ACTION_COLOR = PRIMARY_BLUE;
        Color SECONDARY_ACTION_COLOR = new Color(100, 100, 100);

        // Botão Nova Carona
        JButton btnNovaCarona = new JButton("➕ Criar Nova Carona");
        btnNovaCarona.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnNovaCarona.setBackground(PRIMARY_ACTION_COLOR);
        btnNovaCarona.setForeground(Color.WHITE);
        btnNovaCarona.setFocusPainted(false);
        btnNovaCarona.setBorder(BorderFactory.createEmptyBorder(14, 25, 14, 25));
        btnNovaCarona.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnNovaCarona.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

        btnNovaCarona.addActionListener(e -> {
            new NovaCaronaDialog(usuario, v -> atualizarCaronas()).setVisible(true);
        });

        // Botão Atualizar
        JButton btnAtualizar = new JButton("🔄 Atualizar Lista");
        btnAtualizar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnAtualizar.setBackground(SECONDARY_ACTION_COLOR);
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.setBorder(BorderFactory.createEmptyBorder(14, 25, 14, 25));
        btnAtualizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAtualizar.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

        btnAtualizar.addActionListener(e -> atualizarCaronas());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnNovaCarona);
        painelBotoes.setBackground(BACKGROUND_GRAY);

        return painelBotoes;
    }

    private void atualizarCaronas() {
        modeloTabela.setRowCount(0);

        List<Carona> caronas = caronaController.listarProximas();

        for (Carona c : caronas) {
            String motoristaNome = usuarioController.getNomeUsuario(c.getIdUsuario());

            modeloTabela.addRow(new Object[]{
                    c.getOrigem(),
                    c.getDestino(),
                    c.getDataHora().format(FORMATTER),
                    c.getVagas(),
                    motoristaNome
            });
        }
    }
}