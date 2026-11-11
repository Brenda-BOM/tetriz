package tetris;

import java.awt.Color;
import java.util.Arrays;

public class Tabuleiro {
    public static final int COLUNAS = 10;
    public static final int LINHAS = 20;
    private final Color[][] celulas = new Color[LINHAS][COLUNAS];

    public Tabuleiro() { limpar(); }

    public void limpar() {
        for (Color[] linha : celulas) Arrays.fill(linha, null);
    }

    public boolean podePosicionar(Peca peca) {
        int[][] forma = peca.getForma();
        for (int linha = 0; linha < forma.length; linha++) {
            for (int coluna = 0; coluna < forma[linha].length; coluna++) {
                if (forma[linha][coluna] == 0) continue;
                int x = peca.getX() + coluna;
                int y = peca.getY() + linha;
                if (x < 0 || x >= COLUNAS || y >= LINHAS) return false;
                if (y >= 0 && celulas[y][x] != null) return false;
            }
        }
        return true;
    }

    public void fixar(Peca peca) {
        int[][] forma = peca.getForma();
        for (int linha = 0; linha < forma.length; linha++) {
            for (int coluna = 0; coluna < forma[linha].length; coluna++) {
                int x = peca.getX() + coluna;
                int y = peca.getY() + linha;
                if (forma[linha][coluna] != 0 && y >= 0 && y < LINHAS && x >= 0 && x < COLUNAS) {
                    celulas[y][x] = peca.getCor();
                }
            }
        }
    }

    public int removerLinhasCompletas() {
        int removidas = 0;
        for (int linha = LINHAS - 1; linha >= 0; linha--) {
            boolean completa = true;
            for (int coluna = 0; coluna < COLUNAS; coluna++) {
                if (celulas[linha][coluna] == null) {
                    completa = false;
                    break;
                }
            }
            if (completa) {
                removidas++;
                for (int y = linha; y > 0; y--) {
                    celulas[y] = Arrays.copyOf(celulas[y - 1], COLUNAS);
                }
                celulas[0] = new Color[COLUNAS];
                linha++;
            }
        }
        return removidas;
    }

    public Color[][] getCelulas() { return celulas; }
}
