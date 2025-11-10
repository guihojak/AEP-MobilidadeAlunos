package br.unicesumar.caronas.view;

import br.unicesumar.caronas.controller.CaronaController;
import br.unicesumar.caronas.model.Carona;
import br.unicesumar.caronas.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.function.Consumer;

public class NovaCaronaDialog extends JDialog {

    private final JTextField txtOrigem;
    private final JTextField txtDestino;
    private final JTextField txtDataHora;
    private final JSpinner spnVagas;
    private final Usuario usuario;
    private final Consumer<Void> onSuccess;
    private final CaronaController caronaController; // Adicionado o Controller

    private static final Color PRIMARY_BLUE = new Color(0, 120, 212);
    private static final int INPUT_HEIGHT = 45; // Consistente com Login/Cadastro

    public NovaCaronaDialog(Usuario usuario, Consumer<Void> onSuccess) {
        this.usuario = usuario;
        this.onSuccess = onSuccess;
        this.caronaController = new CaronaController(); // Inicializa o Controller

        setTitle("🚗 Oferecer Carona");
        setModal(true);
        setSize(450, 430); // Tamanho ajustado para caber o novo layout
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Painel principal
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        painel.setBackground(Color.WHITE);

        // 1. Título do Modal
        JLabel lblTitulo = new JLabel("Crie sua Oferta de Carona", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(PRIMARY_BLUE.darker());
        painel.add(lblTitulo, BorderLayout.NORTH);

        // 2. Painel de campos
        JPanel painelCampos = new JPanel(new GridLayout(4, 1, 15, 15));

        // Campo 1: Origem
        txtOrigem = new JTextField();
        painelCampos.add(criarInputCustomizado(txtOrigem, "📍 Ponto de Partida", INPUT_HEIGHT));

        // Campo 2: Destino
        txtDestino = new JTextField();
        painelCampos.add(criarInputCustomizado(txtDestino, "🏁 Destino (Ex: UniCesumar)", INPUT_HEIGHT));

        // Campo 3: Data/Hora
        txtDataHora = new JTextField();
        painelCampos.add(criarInputCustomizado(txtDataHora, "⏰ Data/Hora (Formato: YYYY-MM-DD HH:MM)", INPUT_HEIGHT));

        // Campo 4: Vagas
        spnVagas = new JSpinner(new SpinnerNumberModel(1, 1, 6, 1));
        spnVagas.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Ajusta a fonte do spinner
        JPanel painelVagas = new JPanel(new BorderLayout(10, 0));
        painelVagas.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                "👥 Vagas disponíveis",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP
        ));
        painelVagas.add(spnVagas, BorderLayout.WEST);
        painelVagas.setBackground(Color.WHITE);
        painelCampos.add(painelVagas);

        painel.add(painelCampos, BorderLayout.CENTER);

        // 3. Botão de Ação (Rodapé)
        JButton btnCriar = new JButton("Confirmar Carona");
        btnCriar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnCriar.setBackground(PRIMARY_BLUE);
        btnCriar.setForeground(Color.WHITE);
        btnCriar.setFocusPainted(false);
        btnCriar.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnCriar.addActionListener(e -> criarCarona());

        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        painelRodape.setBackground(Color.WHITE);
        painelRodape.add(btnCriar);

        painel.add(painelRodape, BorderLayout.SOUTH);

        add(painel, BorderLayout.CENTER);
    }

    /**
     * Helper para customizar a aparência dos JTextFields, garantindo consistência.
     */
    private JComponent criarInputCustomizado(JTextField input, String titulo, int altura) {
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

    private void criarCarona() {
        String origem = txtOrigem.getText().trim();
        String destino = txtDestino.getText().trim();
        String dataHoraTexto = txtDataHora.getText().trim();
        int vagas = (int) spnVagas.getValue();

        if (origem.isEmpty() || destino.isEmpty() || dataHoraTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Todos os campos de origem, destino e data/hora devem ser preenchidos.",
                    "Aviso: Campos Vazios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDateTime dataHora;
        try {
            // Usa o replace para aceitar tanto espaço quanto 'T' no formato ISO
            dataHora = LocalDateTime.parse(dataHoraTexto.replace(" ", "T"));
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "❌ Formato de data/hora inválido. Use o padrão: YYYY-MM-DD HH:MM (Ex: 2025-12-31 15:30).",
                    "Erro de Formato",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 1. Cria o objeto Carona
        Carona carona = new Carona();
        carona.setOrigem(origem);
        carona.setDestino(destino);
        carona.setDataHora(dataHora);
        carona.setVagas(vagas);
        carona.setIdUsuario(usuario.getId());

        // 2. Chama o Controller (Centralizando a lógica de negócio)
        boolean sucesso = caronaController.criarCarona(carona);

        if (sucesso) {
            // Mensagem de sucesso refinada
            JOptionPane.showMessageDialog(this,
                    "✨ Sua carona de " + origem + " para " + destino + " foi publicada com sucesso!",
                    "Carona Criada",
                    JOptionPane.INFORMATION_MESSAGE);

            if (onSuccess != null) onSuccess.accept(null); // Atualiza o painel principal (tabela)
            dispose();
        } else {
            // Mensagem de erro refinada
            JOptionPane.showMessageDialog(this,
                    "❌ Não foi possível salvar a carona no banco de dados. Tente novamente.",
                    "Erro no Banco de Dados",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}