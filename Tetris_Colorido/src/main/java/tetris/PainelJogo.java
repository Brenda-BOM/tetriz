package tetris;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PainelJogo extends JPanel {
    private static final int TAMANHO = 28;
    private final JanelaJogo janela;
    private final Tabuleiro tabuleiro = new Tabuleiro();
    private final Random aleatorio = new Random();
    private final Timer temporizador;
    private Peca pecaAtual;
    private int movimentos;
    private int linhas;
    private int velocidade;
    private String dificuldade;
    private boolean fimDeJogo;

    private final int[][][] formas = {
        {{1,1,1,1}},
        {{1,1},{1,1}},
        {{0,1,0},{1,1,1}},
        {{1,0,0},{1,1,1}},
        {{0,0,1},{1,1,1}},
        {{1,1,0},{0,1,1}},
        {{0,1,1},{1,1,0}}
    };
    private final Color[] cores = {
        new Color(0, 220, 255), new Color(255, 218, 60),
        new Color(190, 90, 255), new Color(255, 130, 65),
        new Color(70, 225, 130), new Color(255, 80, 130),
        new Color(100, 145, 255)
    };

    public PainelJogo(JanelaJogo janela) {
        this.janela = janela;
        setPreferredSize(new Dimension(Tabuleiro.COLUNAS * TAMANHO + 2,
                Tabuleiro.LINHAS * TAMANHO + 2));
        setBackground(new Color(20, 17, 43));
        setFocusable(true);

        temporizador = new Timer(600, new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if (!fimDeJogo) descerAutomaticamente();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (fimDeJogo) return;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> mover(-1);
                    case KeyEvent.VK_RIGHT -> mover(1);
                    case KeyEvent.VK_UP -> girar();
                    case KeyEvent.VK_DOWN -> descerUmaLinha();
                    case KeyEvent.VK_SPACE -> quedaRapida();
                }
            }
        });
    }

    public void iniciarNovaPartida() {
        tabuleiro.limpar();
        movimentos = 0;
        linhas = 0;
        fimDeJogo = false;

        // A dificuldade é sorteada novamente em toda partida.
        int sorteio = aleatorio.nextInt(3);
        if (sorteio == 0) {
            dificuldade = "Fácil";
            velocidade = 850;
        } else if (sorteio == 1) {
            dificuldade = "Média";
            velocidade = 600;
        } else {
            dificuldade = "Difícil";
            velocidade = 380;
        }
        temporizador.setDelay(velocidade);
        temporizador.setInitialDelay(velocidade);
        criarPeca();
        temporizador.start();
        atualizarPainel();
        requestFocusInWindow();
        repaint();
    }

    public void recomecar() { iniciarNovaPartida(); }

    private void criarPeca() {
        int indice = aleatorio.nextInt(formas.length);
        pecaAtual = new Peca(formas[indice], cores[indice]);
        if (!tabuleiro.podePosicionar(pecaAtual)) encerrarJogo();
    }

    public void mover(int direcao) {
        if (fimDeJogo) return;
        pecaAtual.mover(direcao, 0);
        if (!tabuleiro.podePosicionar(pecaAtual)) pecaAtual.mover(-direcao, 0);
        else registrarMovimento();
        repaint();
        requestFocusInWindow();
    }

    public void girar() {
        if (fimDeJogo) return;
        pecaAtual.girar();
        if (!tabuleiro.podePosicionar(pecaAtual)) {
            for (int i = 0; i < 3; i++) pecaAtual.girar();
        } else registrarMovimento();
        repaint();
        requestFocusInWindow();
    }

    public void descerUmaLinha() {
        if (fimDeJogo) return;
        pecaAtual.mover(0, 1);
        if (!tabuleiro.podePosicionar(pecaAtual)) {
            pecaAtual.mover(0, -1);
            fixarPeca();
        } else registrarMovimento();
        repaint();
        requestFocusInWindow();
    }

    public void quedaRapida() {
        if (fimDeJogo) return;
        boolean caiu = false;
        while (true) {
            pecaAtual.mover(0, 1);
            if (!tabuleiro.podePosicionar(pecaAtual)) {
                pecaAtual.mover(0, -1);
                break;
            }
            caiu = true;
        }
        if (caiu) registrarMovimento();
        fixarPeca();
        repaint();
        requestFocusInWindow();
    }

    private void descerAutomaticamente() {
        pecaAtual.mover(0, 1);
        if (!tabuleiro.podePosicionar(pecaAtual)) {
            pecaAtual.mover(0, -1);
            fixarPeca();
        }
        repaint();
    }

    private void fixarPeca() {
        tabuleiro.fixar(pecaAtual);
        int removidas = tabuleiro.removerLinhasCompletas();
        linhas += removidas;
        if (removidas > 0) {
            velocidade = Math.max(130, velocidade - removidas * 35);
            temporizador.setDelay(velocidade);
        }
        criarPeca();
        atualizarPainel();
    }

    private void registrarMovimento() {
        movimentos++;
        atualizarPainel();
    }

    private void atualizarPainel() {
        janela.atualizarInformacoes(movimentos, linhas, dificuldade);
    }

    private void encerrarJogo() {
        fimDeJogo = true;
        temporizador.stop();
        repaint();
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int linha = 0; linha < Tabuleiro.LINHAS; linha++) {
            for (int coluna = 0; coluna < Tabuleiro.COLUNAS; coluna++) {
                int x = coluna * TAMANHO;
                int y = linha * TAMANHO;
                Color cor = tabuleiro.getCelulas()[linha][coluna];
                desenharBloco(g2, x, y, cor == null ? new Color(42, 38, 72) : cor);
            }
        }

        if (pecaAtual != null && !fimDeJogo) {
            int[][] forma = pecaAtual.getForma();
            for (int linha = 0; linha < forma.length; linha++) {
                for (int coluna = 0; coluna < forma[linha].length; coluna++) {
                    if (forma[linha][coluna] != 0) {
                        desenharBloco(g2, (pecaAtual.getX() + coluna) * TAMANHO,
                                (pecaAtual.getY() + linha) * TAMANHO, pecaAtual.getCor());
                    }
                }
            }
        }

        g2.setColor(new Color(105, 95, 150));
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(0, 0, Tabuleiro.COLUNAS * TAMANHO, Tabuleiro.LINHAS * TAMANHO);

        if (fimDeJogo) desenharFimDeJogo(g2);
        g2.dispose();
    }

    private void desenharBloco(Graphics2D g2, int x, int y, Color cor) {
        g2.setColor(cor);
        g2.fillRoundRect(x + 2, y + 2, TAMANHO - 4, TAMANHO - 4, 7, 7);
        g2.setColor(new Color(255, 255, 255, 75));
        g2.drawRoundRect(x + 4, y + 4, TAMANHO - 8, TAMANHO - 8, 5, 5);
    }

    private void desenharFimDeJogo(Graphics2D g2) {
        int largura = getWidth();
        int altura = getHeight();
        g2.setColor(new Color(15, 10, 35, 210));
        g2.fillRect(0, 0, largura, altura);
        g2.setPaint(new GradientPaint(20, altura / 2 - 35,
                new Color(255, 90, 145), largura - 20, altura / 2 + 35,
                new Color(120, 80, 255)));
        g2.fillRoundRect(22, altura / 2 - 65, largura - 44, 130, 24, 24);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 31));
        String texto = "VOCÊ PERDEU";
        int textoLargura = g2.getFontMetrics().stringWidth(texto);
        g2.drawString(texto, (largura - textoLargura) / 2, altura / 2 - 8);
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        String dica = "Clique em Recomeçar para jogar novamente";
        int dicaLargura = g2.getFontMetrics().stringWidth(dica);
        g2.drawString(dica, (largura - dicaLargura) / 2, altura / 2 + 25);
    }
}
