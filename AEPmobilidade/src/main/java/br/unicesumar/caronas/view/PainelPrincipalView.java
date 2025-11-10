package br.unicesumar.caronas.view;

import br.unicesumar.caronas.model.Usuario;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

/**
 * Painel principal do sistema de caronas.
 * Agora suporta receber o usuário logado e exibir seu nome.
 */
public class PainelPrincipalView extends JFrame {
    private final MapaRealView mapaView = new MapaRealView();
    private Usuario usuario;

    // 🔹 Construtor sem parâmetros (caso queira abrir direto)
    public PainelPrincipalView() {
        this(null);
    }

    // 🔹 Construtor com o usuário logado
    public PainelPrincipalView(Usuario usuario) {
        super("Caronas Acadêmicas - Painel Principal");
        this.usuario = usuario;
        configurarLayout();
    }

    private void configurarLayout() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // === BARRA SUPERIOR ===
        JPanel barraTopo = new JPanel(new BorderLayout());
        barraTopo.setBackground(new Color(240, 240, 240));
        barraTopo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel();
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        if (usuario != null)
            titulo.setText("Caronas Acadêmicas - Bem-vindo, " + usuario.getNome());
        else
            titulo.setText("Caronas Acadêmicas");

        barraTopo.add(titulo, BorderLayout.WEST);

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> System.exit(0));
        barraTopo.add(btnSair, BorderLayout.EAST);

        // === PAINEL LATERAL ===
        JPanel painelLateral = new JPanel();
        painelLateral.setLayout(new BoxLayout(painelLateral, BoxLayout.Y_AXIS));
        painelLateral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnNovaCarona = new JButton("Criar Carona");
        JButton btnAtualizarMapa = new JButton("Atualizar Mapa");

        btnNovaCarona.addActionListener(e ->
                mapaView.adicionarMarcador(-23.43, -51.94, "Nova Carona adicionada"));

        painelLateral.add(btnNovaCarona);
        painelLateral.add(Box.createVerticalStrut(10));
        painelLateral.add(btnAtualizarMapa);

        // === MAPA CENTRAL ===
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.add(mapaView, BorderLayout.CENTER);

        // === MONTAGEM FINAL ===
        add(barraTopo, BorderLayout.NORTH);
        add(painelLateral, BorderLayout.WEST);
        add(painelCentral, BorderLayout.CENTER);

        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 🔹 Execução direta (para testes)
    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(PainelPrincipalView::new);
    }
}
