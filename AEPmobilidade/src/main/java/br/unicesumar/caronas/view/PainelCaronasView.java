package br.unicesumar.caronas.view;

import br.unicesumar.caronas.model.Usuario;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class PainelCaronasView extends JPanel {

    private final Usuario usuario;
    private final JTable tabelaCaronas;
    private final DefaultTableModel modeloTabela;

    public PainelCaronasView(Usuario usuario) {
        this.usuario = usuario;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Título
        JLabel titulo = new JLabel("Caronas Disponíveis", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        // Tabela com caronas simuladas (aqui será preenchida dinamicamente depois)
        String[] colunas = {"Origem", "Destino", "Data/Hora", "Vagas"};
        Object[][] dadosIniciais = {
                {"Zona Sul", "Campus UniCesumar", "2025-11-09 08:00", 3},
                {"Terminal Central", "Campus Zona 7", "2025-11-10 09:00", 2}
        };

        modeloTabela = new DefaultTableModel(dadosIniciais, colunas);
        tabelaCaronas = new JTable(modeloTabela);
        tabelaCaronas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabelaCaronas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabelaCaronas.setRowHeight(24);

        JScrollPane scroll = new JScrollPane(tabelaCaronas);
        add(scroll, BorderLayout.CENTER);

        // Botão para criar nova carona
        JButton btnNovaCarona = new JButton("➕ Criar Nova Carona");
        btnNovaCarona.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnNovaCarona.setBackground(new Color(0, 120, 215));
        btnNovaCarona.setForeground(Color.WHITE);
        btnNovaCarona.setFocusPainted(false);
        btnNovaCarona.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnNovaCarona.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Chama o diálogo de nova carona e, ao salvar, atualiza automaticamente a tabela
        btnNovaCarona.addActionListener(e -> {
            new NovaCaronaDialog(usuario, v -> atualizarCaronas()).setVisible(true);
        });

        add(btnNovaCarona, BorderLayout.SOUTH);
    }

    /**
     * Atualiza a lista de caronas disponíveis.
     * Em uma integração real, essa função faria uma consulta no banco via DAO.
     */
    private void atualizarCaronas() {
        modeloTabela.setRowCount(0); // limpa a tabela

        // Simulação de recarregamento (posteriormente puxaremos do banco)
        Object[][] novasCaronas = {
                {"Zona Sul", "Campus UniCesumar", "2025-11-10 08:00", 3},
                {"Residencial Zona 3", "Campus UniCesumar", "2025-11-11 07:30", 2},
                {"Terminal Central", "Zona 7 - Campus", "2025-11-11 18:00", 4}
        };

        for (Object[] carona : novasCaronas) {
            modeloTabela.addRow(carona);
        }

        revalidate();
        repaint();
    }
}
