package br.unicesumar.caronas.view;

import br.unicesumar.caronas.dao.CaronaDAO;
import br.unicesumar.caronas.model.Carona;
import br.unicesumar.caronas.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class NovaCaronaDialog extends JDialog {

    private final JTextField txtOrigem;
    private final JTextField txtDestino;
    private final JTextField txtDataHora;
    private final JSpinner spnVagas;
    private final Usuario usuario;
    private final Consumer<Void> onSuccess;

    public NovaCaronaDialog(Usuario usuario, Consumer<Void> onSuccess) {
        this.usuario = usuario;
        this.onSuccess = onSuccess;

        setTitle("Criar Nova Carona");
        setModal(true);
        setSize(420, 330);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(15, 15));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Painel principal
        JPanel painel = new JPanel(new GridLayout(5, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Campos
        txtOrigem = new JTextField();
        txtOrigem.setBorder(BorderFactory.createTitledBorder("Origem"));
        painel.add(txtOrigem);

        txtDestino = new JTextField();
        txtDestino.setBorder(BorderFactory.createTitledBorder("Destino"));
        painel.add(txtDestino);

        txtDataHora = new JTextField();
        txtDataHora.setBorder(BorderFactory.createTitledBorder("Data e Hora (AAAA-MM-DD HH:MM)"));
        painel.add(txtDataHora);

        spnVagas = new JSpinner(new SpinnerNumberModel(1, 1, 6, 1));
        JPanel painelVagas = new JPanel(new BorderLayout());
        painelVagas.setBorder(BorderFactory.createTitledBorder("Número de vagas"));
        painelVagas.add(spnVagas, BorderLayout.CENTER);
        painel.add(painelVagas);

        add(painel, BorderLayout.CENTER);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton btnSalvar = new JButton("Salvar Carona");
        btnSalvar.setBackground(new Color(0, 120, 215));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.addActionListener(e -> salvarCarona());

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void salvarCarona() {
        try {
            String origem = txtOrigem.getText().trim();
            String destino = txtDestino.getText().trim();
            String dataHoraTexto = txtDataHora.getText().trim();
            int vagas = (int) spnVagas.getValue();

            if (origem.isEmpty() || destino.isEmpty() || dataHoraTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDateTime dataHora;
            try {
                dataHora = LocalDateTime.parse(dataHoraTexto.replace(" ", "T"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Formato de data/hora inválido. Use AAAA-MM-DD HH:MM.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Carona carona = new Carona();
            carona.setOrigem(origem);
            carona.setDestino(destino);
            carona.setDataHora(dataHora);
            carona.setVagas(vagas);
            carona.setIdUsuario(usuario.getId());

            CaronaDAO dao = new CaronaDAO();
            boolean sucesso = dao.inserir(carona);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Carona criada com sucesso!");
                if (onSuccess != null) onSuccess.accept(null); // Atualiza o painel principal
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar carona no banco.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao criar carona: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
