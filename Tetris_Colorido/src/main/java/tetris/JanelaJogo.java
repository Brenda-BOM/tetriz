package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class JanelaJogo extends JFrame {
    private final PainelJogo painelJogo;
    private final JLabel movimentosLabel;
    private final JLabel dificuldadeLabel;
    private final JLabel linhasLabel;

    public JanelaJogo() {
        setTitle("Tetris Colorido");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(24, 20, 52));
        setLayout(new BorderLayout(12, 12));

        JLabel titulo = new JLabel("TETRIS COLORIDO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(255, 221, 89));
        titulo.setBorder(BorderFactory.createEmptyBorder(14, 8, 4, 8));
        add(titulo, BorderLayout.NORTH);

        painelJogo = new PainelJogo(this);
        add(painelJogo, BorderLayout.CENTER);

        JPanel lateral = new JPanel();
        lateral.setPreferredSize(new Dimension(190, 0));
        lateral.setBackground(new Color(37, 31, 75));
        lateral.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        lateral.setLayout(new GridLayout(0, 1, 8, 8));

        movimentosLabel = criarInfo("Movimentos: 0");
        dificuldadeLabel = criarInfo("Dificuldade: -");
        linhasLabel = criarInfo("Linhas: 0");
        lateral.add(movimentosLabel);
        lateral.add(dificuldadeLabel);
        lateral.add(linhasLabel);

        JLabel controles = criarInfo("<html><center>CONTROLES<br><br>← → mover<br>↑ girar<br>↓ descer<br>ESPAÇO: queda<br><br>Também use os<br>botões abaixo.</center></html>");
        lateral.add(controles);

        JButton esquerda = criarBotao("◀ Esquerda");
        esquerda.addActionListener(e -> painelJogo.mover(-1));
        JButton direita = criarBotao("Direita ▶");
        direita.addActionListener(e -> painelJogo.mover(1));
        JButton girar = criarBotao("↻ Girar");
        girar.addActionListener(e -> painelJogo.girar());
        JButton descer = criarBotao("▼ Descer");
        descer.addActionListener(e -> painelJogo.descerUmaLinha());
        JButton queda = criarBotao("⤓ Queda rápida");
        queda.addActionListener(e -> painelJogo.quedaRapida());
        JButton reiniciar = criarBotao("⟳ Recomeçar");
        reiniciar.addActionListener(e -> painelJogo.recomecar());

        lateral.add(esquerda);
        lateral.add(direita);
        lateral.add(girar);
        lateral.add(descer);
        lateral.add(queda);
        lateral.add(reiniciar);
        add(lateral, BorderLayout.EAST);

        painelJogo.iniciarNovaPartida();
        pack();
        setLocationRelativeTo(null);
    }

    private JLabel criarInfo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setOpaque(true);
        label.setBackground(new Color(65, 52, 115));
        label.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        return label;
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 13));
        botao.setForeground(new Color(35, 25, 65));
        botao.setBackground(new Color(255, 190, 92));
        botao.setFocusPainted(false);
        return botao;
    }

    public void atualizarInformacoes(int movimentos, int linhas, String dificuldade) {
        movimentosLabel.setText("Movimentos: " + movimentos);
        linhasLabel.setText("Linhas: " + linhas);
        dificuldadeLabel.setText("Dificuldade: " + dificuldade);
    }
}
