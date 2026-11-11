package tetris;

import java.awt.Color;

public class Peca {
    private int[][] forma;
    private final Color cor;
    private int x;
    private int y;

    public Peca(int[][] forma, Color cor) {
        this.forma = copiar(forma);
        this.cor = cor;
        this.x = 3;
        this.y = 0;
    }

    private int[][] copiar(int[][] matriz) {
        int[][] copia = new int[matriz.length][matriz[0].length];
        for (int linha = 0; linha < matriz.length; linha++) {
            System.arraycopy(matriz[linha], 0, copia[linha], 0, matriz[linha].length);
        }
        return copia;
    }

    public int[][] getForma() { return forma; }
    public Color getCor() { return cor; }
    public int getX() { return x; }
    public int getY() { return y; }
    public void mover(int dx, int dy) { x += dx; y += dy; }
    public void definirPosicao(int novoX, int novoY) { x = novoX; y = novoY; }

    public void girar() {
        int altura = forma.length;
        int largura = forma[0].length;
        int[][] girada = new int[largura][altura];
        for (int linha = 0; linha < altura; linha++) {
            for (int coluna = 0; coluna < largura; coluna++) {
                girada[coluna][altura - 1 - linha] = forma[linha][coluna];
            }
        }
        forma = girada;
    }
}
