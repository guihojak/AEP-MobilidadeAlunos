package br.unicesumar.caronas.view;

import br.unicesumar.caronas.controller.VeiculoController;
import br.unicesumar.caronas.model.Usuario;
import br.unicesumar.caronas.model.Veiculo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Diálogo para cadastrar ou editar um Veículo.
 */
public class VeiculoDialog extends JDialog {

    private final VeiculoController veiculoController;
    private final Usuario usuarioLogado;
    private final Veiculo veiculoOriginal; // null para novo veículo

    // Componentes de UI
    private JTextField modeloField;
    private JTextField placaField;
    private JTextField capacidadeField;

    // Referência para o PainelVeiculosView (para forçar a atualização da lista)
    private final PainelVeiculosView pai;

    /**
     * Construtor para Adição ou Edição de Veículo.
     */
    public VeiculoDialog(PainelVeiculosView pai, Veiculo veiculo, Usuario usuario) {
        // Obter o frame pai para tornar o diálogo modal em relação à janela principal
        super((Frame) SwingUtilities.getWindowAncestor(pai), true);

        this.pai = pai;
        this.veiculoController = new VeiculoController();
        this.usuarioLogado = usuario;
        this.veiculoOriginal = veiculo;

        setTitle(veiculo == null ? "Cadastrar Novo Veículo ➕" : "Editar Veículo ✏️");
        inicializarComponentes();

        if (veiculo != null) {
            preencherCampos(veiculo);
        }
    }

    private void inicializarComponentes() {
        // Configurações do Diálogo
        setSize(400, 250);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(getParent());

        // Painel de Formulário (Centro)
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        modeloField = new JTextField();
        placaField = new JTextField();
        capacidadeField = new JTextField();
        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(this::salvarVeiculo);

        formPanel.add(new JLabel("Modelo:"));
        formPanel.add(modeloField);
        formPanel.add(new JLabel("Placa:"));
        formPanel.add(placaField);
        formPanel.add(new JLabel("Capacidade (Lugares):"));
        formPanel.add(capacidadeField);
        formPanel.add(new JLabel(""));
        formPanel.add(salvarButton);

        add(formPanel, BorderLayout.CENTER);
    }

    private void preencherCampos(Veiculo veiculo) {
        modeloField.setText(veiculo.getModelo());
        placaField.setText(veiculo.getPlaca());
        capacidadeField.setText(String.valueOf(veiculo.getCapacidade()));
    }

    /**
     * Método que realiza a validação e o salvamento/edição do veículo.
     */
    private void salvarVeiculo(ActionEvent e) {
        String modelo = modeloField.getText().trim();
        String placa = placaField.getText().trim();
        String capacidadeStr = capacidadeField.getText().trim();

        if (modelo.isEmpty() || placa.isEmpty() || capacidadeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int capacidade;
        try {
            capacidade = Integer.parseInt(capacidadeStr);
            if (capacidade <= 0) {
                JOptionPane.showMessageDialog(this, "A capacidade deve ser maior que zero.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "A capacidade deve ser um número inteiro válido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Veiculo veiculo;
        if (veiculoOriginal == null) {
            // ✅ CRIAÇÃO: Ordem dos argumentos: (id, idUsuario, modelo, placa, capacidade)
            veiculo = new Veiculo(
                    0,
                    usuarioLogado.getId(),
                    modelo,
                    placa,
                    capacidade
            );
        } else {
            // EDIÇÃO: Usa os setters
            veiculo = veiculoOriginal;
            veiculo.setModelo(modelo);
            veiculo.setPlaca(placa);
            veiculo.setCapacidade(capacidade);
            // id e idUsuario permanecem
        }

        try {
            // 🚨 MÉTODO CORRIGIDO: Usa salvarOuAtualizar
            veiculoController.salvarOuAtualizar(veiculo);

            JOptionPane.showMessageDialog(this, "Veículo salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            // 💡 CHAMA O MÉTODO PÚBLICO para atualizar a lista no painel principal
            pai.atualizarListaVeiculos();

            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o veículo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}