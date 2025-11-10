package br.unicesumar.caronas.view;

import br.unicesumar.caronas.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class PainelCaronasView extends JPanel {

    private final Usuario usuario;

    public PainelCaronasView(Usuario usuario) {
        this.usuario = usuario;
        setLayout(new BorderLayout());
        JLabel titulo = new JLabel("Caronas Disponíveis", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        String[] colunas = {"Origem", "Destino", "Data/Hora", "Vagas"};
        Object[][] dados = {
                {"Zona Sul", "Campus UniCesumar", "2025-11-09 08:00", 3},
                {"Terminal Central", "Campus Zona 7", "2025-11-10 09:00", 2}
        };

        JTable tabela = new JTable(dados, colunas);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JButton btnNovaCarona = new JButton("➕ Criar Nova Carona");
        btnNovaCarona.addActionListener(e -> new NovaCaronaDialog(usuario).setVisible(true));
        add(btnNovaCarona, BorderLayout.SOUTH);
    }
}
