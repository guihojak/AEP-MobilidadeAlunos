package br.unicesumar.caronas.view;

import br.unicesumar.caronas.controller.VeiculoController;
import br.unicesumar.caronas.model.Usuario;
import br.unicesumar.caronas.model.Veiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import com.formdev.flatlaf.FlatClientProperties;

public class PainelVeiculosView extends JPanel {

    private final Usuario usuario;
    private final VeiculoController veiculoController;

    private JTable tabelaVeiculos;
    private DefaultTableModel modeloTabela;

    private static final Color PRIMARY_BLUE = new Color(0, 120, 212);
    private static final Color BACKGROUND_GRAY = new Color(240, 240, 240);

    public PainelVeiculosView(Usuario usuario) {
        this.usuario = usuario;
        this.veiculoController = new VeiculoController();

        setLayout(new BorderLayout(30, 30));
        setBackground(BACKGROUND_GRAY);
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        add(criarPainelTopo(), BorderLayout.NORTH);

        JPanel painelCentral = new JPanel(new BorderLayout(0, 30));
        painelCentral.setBackground(BACKGROUND_GRAY);

        JComponent painelTabelaCard = criarPainelTabelaCard();
        painelCentral.add(painelTabelaCard, BorderLayout.CENTER);

        painelCentral.add(criarPainelBotoes(), BorderLayout.SOUTH);

        add(painelCentral, BorderLayout.CENTER);

        atualizarListaVeiculos();
    }

    private JPanel criarPainelTopo() {
        JPanel painelTopo = new JPanel(new BorderLayout(0, 15));
        painelTopo.setBackground(BACKGROUND_GRAY);

        JLabel titulo = new JLabel("Meus Veículos Cadastrados 🚗", SwingConstants.LEFT);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titulo.setForeground(PRIMARY_BLUE.darker());
        painelTopo.add(titulo, BorderLayout.NORTH);

        JLabel subtitulo = new JLabel("Gerencie os veículos que você utiliza para oferecer caronas.");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitulo.setForeground(Color.GRAY.darker());
        painelTopo.add(subtitulo, BorderLayout.CENTER);

        return painelTopo;
    }

    private JComponent criarPainelTabelaCard() {
        String[] colunas = {"ID", "Modelo", "Placa", "Capacidade"};

        modeloTabela = new DefaultTableModel(null, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 3 ? Integer.class : String.class;
            }
        };
        tabelaVeiculos = new JTable(modeloTabela);
        // ... (Estilização da Tabela omitida por brevidade, mas está correta)
        tabelaVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaVeiculos.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tabelaVeiculos.setRowHeight(40);
        tabelaVeiculos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        tabelaVeiculos.setShowGrid(false);
        tabelaVeiculos.setIntercellSpacing(new Dimension(0, 1));
        tabelaVeiculos.getTableHeader().setBackground(new Color(230, 230, 230));
        tabelaVeiculos.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        tabelaVeiculos.setBackground(Color.WHITE);
        tabelaVeiculos.setSelectionBackground(PRIMARY_BLUE.brighter());
        tabelaVeiculos.setSelectionForeground(Color.BLACK);

        // Esconder a coluna ID
        tabelaVeiculos.getColumnModel().getColumn(0).setMinWidth(0);
        tabelaVeiculos.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelaVeiculos.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane scrollPane = new JScrollPane(tabelaVeiculos);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));

        scrollPane.putClientProperty(FlatClientProperties.STYLE,
                "arc: 12;" +
                        "borderWidth: 0"
        );

        return scrollPane;
    }

    private JPanel criarPainelBotoes() {
        Color PRIMARY_ACTION_COLOR = PRIMARY_BLUE;
        Color DANGER_COLOR = new Color(212, 0, 0);

        JButton btnNovo = criarBotaoAcao("➕ Adicionar Novo", PRIMARY_ACTION_COLOR);
        // 💡 CHAMADA FINAL: Novo Veiculo
        btnNovo.addActionListener(e -> adicionarNovoVeiculo());

        JButton btnEditar = criarBotaoAcao("✏️ Editar Selecionado", new Color(100, 100, 100));
        // 💡 CHAMADA FINAL: Editar Veiculo
        btnEditar.addActionListener(e -> editarVeiculoSelecionado());

        JButton btnExcluir = criarBotaoAcao("🗑️ Excluir Selecionado", DANGER_COLOR);
        btnExcluir.addActionListener(e -> excluirVeiculoSelecionado());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        painelBotoes.add(btnNovo);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.setBackground(BACKGROUND_GRAY);

        return painelBotoes;
    }

    private JButton criarBotaoAcao(String texto, Color corFundo) {
        JButton button = new JButton(texto);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBackground(corFundo);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        return button;
    }

    // --- Métodos de Lógica ---

    // 🚨 MÉTODO AGORA É PÚBLICO para ser chamado pelo VeiculoDialog
    public void atualizarListaVeiculos() {
        modeloTabela.setRowCount(0);

        try {
            List<Veiculo> veiculos = veiculoController.listarVeiculosPorUsuario(usuario.getId());

            for (Veiculo v : veiculos) {
                modeloTabela.addRow(new Object[]{
                        v.getId(),
                        v.getModelo(),
                        v.getPlaca(),
                        v.getCapacidade()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar veículos: " + e.getMessage(),
                    "Erro de Leitura", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarNovoVeiculo() {
        // 💡 CHAMA O DIÁLOGO para adicionar (passa 'null' como Veiculo)
        new VeiculoDialog(this, null, usuario).setVisible(true);
    }

    private void editarVeiculoSelecionado() {
        int linha = tabelaVeiculos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para editar.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 1. Coleta os dados básicos da linha (ID, Modelo, Placa, Capacidade)
        int idVeiculo = (int) modeloTabela.getValueAt(linha, 0);
        String modelo = (String) modeloTabela.getValueAt(linha, 1);
        String placa = (String) modeloTabela.getValueAt(linha, 2);
        int capacidade = (int) modeloTabela.getValueAt(linha, 3);

        // 2. Cria um objeto Veiculo temporário para edição
        // Passamos o ID e ID do Usuário para que o UPDATE funcione corretamente
        Veiculo veiculoParaEdicao = new Veiculo(
                idVeiculo,
                usuario.getId(), // idUsuario
                modelo,
                placa,
                capacidade
        );

        // 3. 💡 CHAMA O DIÁLOGO para edição (passa o Veiculo a ser editado)
        new VeiculoDialog(this, veiculoParaEdicao, usuario).setVisible(true);
    }

    private void excluirVeiculoSelecionado() {
        int linha = tabelaVeiculos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para excluir.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o veículo selecionado?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                int idVeiculo = (int) modeloTabela.getValueAt(linha, 0);
                veiculoController.deletarVeiculo(idVeiculo);
                atualizarListaVeiculos();
                JOptionPane.showMessageDialog(this, "Veículo excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir veículo: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}