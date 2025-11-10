package br.unicesumar.caronas.view;

import br.unicesumar.caronas.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class NovaCaronaDialog extends JDialog {

    public NovaCaronaDialog(Usuario usuario) {
        setTitle("Nova Carona - " + usuario.getNome());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new GridLayout(5, 1, 10, 10));
        setResizable(false);

        JTextField origem = new JTextField();
        origem.setBorder(BorderFactory.createTitledBorder("Origem"));

        JTextField destino = new JTextField();
        destino.setBorder(BorderFactory.createTitledBorder("Destino"));

        JTextField dataHora = new JTextField();
        dataHora.setBorder(BorderFactory.createTitledBorder("Data/Hora (AAAA-MM-DD HH:MM)"));

        JTextField vagas = new JTextField();
        vagas.setBorder(BorderFactory.createTitledBorder("Vagas"));

        JButton btnSalvar = new JButton("Salvar Carona");
        btnSalvar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Carona criada!\nDe " + origem.getText() + " → " + destino.getText(),
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        add(origem);
        add(destino);
        add(dataHora);
        add(vagas);
        add(btnSalvar);
    }
}
