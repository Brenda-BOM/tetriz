# Tetris Colorido

Projeto Java com interface gráfica Swing, organizado em classes separadas.

## Como abrir no NetBeans
1. Extraia o arquivo ZIP.
2. Abra o NetBeans.
3. Vá em **Arquivo > Abrir Projeto**.
4. Selecione a pasta `Tetris_Colorido` (a que contém `pom.xml`).
5. Execute o projeto pela classe `tetris.Main`.

É necessário ter Java 17 ou superior configurado.

## Controles
- Teclado: setas esquerda/direita movem; seta para cima gira; seta para baixo desce; espaço faz a queda rápida.
- Mouse: use os botões na lateral para executar os mesmos movimentos.
- O contador registra os comandos de movimento feitos pelo jogador.
- Cada nova partida sorteia uma dificuldade (fácil, média ou difícil).
- Ao perder, aparece **VOCÊ PERDEU**. Clique em **Recomeçar** para iniciar outra partida e sortear uma nova dificuldade.

## Organização
- `Main.java`: inicia o programa.
- `JanelaJogo.java`: monta a janela, informações e botões.
- `PainelJogo.java`: desenha e controla a partida.
- `Peca.java`: representa e gira as peças.
- `Tabuleiro.java`: controla as casas e remove linhas completas.
