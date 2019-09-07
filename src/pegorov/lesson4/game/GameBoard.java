package pegorov.lesson4.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {
    static int dimension = 3;
    static int cellSize = 150;
    private char[][] gameField;
    private GameButton[] gameButtons;

    private Game game;
    public final char nullSymbol = ' ';

    public GameBoard(Game currentgame) {
        this.game = currentgame;
        initField();
    }

    private void initField() {
        setBounds(cellSize * dimension, cellSize * dimension, 400, 300);
        setTitle("Крестики нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel controPanel = new JPanel();
        JButton newGameButton = new JButton("Новая игра");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyField();
            }
        });

        controPanel.setLayout(new BoxLayout(controPanel, BoxLayout.Y_AXIS));
        controPanel.add(newGameButton);
        controPanel.setSize(cellSize * dimension, 150);

        JPanel gameFieldPanel = new JPanel();
        gameFieldPanel.setLayout(new GridLayout(dimension, dimension));
        gameFieldPanel.setSize(cellSize * dimension, cellSize * dimension);

        gameField = new char[dimension][dimension];
        gameButtons = new GameButton[dimension * dimension];

        for (int i = 0; i < (dimension * dimension); i++) {
            GameButton fieldButton = new GameButton(i, this);
            gameFieldPanel.add(fieldButton);
            gameButtons[i] = fieldButton;
        }


        emptyField();

        getContentPane().add(controPanel, BorderLayout.NORTH);
        getContentPane().add(gameFieldPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    void emptyField() {
        for (int i = 0; i < (dimension * dimension); i++) {
            gameButtons[i].setText("");

            int x = i / GameBoard.dimension;
            int y = i % GameBoard.dimension;

            setNullGameField(x,y);
        }
    }

    Game getGame() {
        return this.game;
    }

    boolean isTurnable(int x, int y) {
        return (gameField[y][x] == nullSymbol) ? true : false;
    }

    void updateGameField(int x, int y) {
        gameField[y][x] = game.getCurrentPlayer().getPlayerSign();
    }

    boolean checkWin() {
        boolean result = false;
        char playerSymbol = getGame().getCurrentPlayer().getPlayerSign();

        if (checkWinDiagonals(playerSymbol) || checkWinLines(playerSymbol)) {
            result = true;
        }

        return result;
    }

    private boolean checkWinLines(char playerSymbol) {
        boolean cols, rows;

        for (int col = 0; col < dimension; col++) {
            cols = true;
            rows = true;

            for (int row = 0; row < dimension; row++) {
                cols &= (gameField[col][row] == playerSymbol);
                rows &= (gameField[row][col] == playerSymbol);
            }

            if (cols || rows){
                return true; /// Сразу возвращаем значение, дальше бегать по строкам нет смысла
            }
        }

        return false;
    }

    private boolean checkWinDiagonals(char playerSymbol) {
        boolean diagL = true;
        boolean diagR = true;
        /// Беру из 2-го урока.
        for (int i = 0, j = 0; i == j && i < dimension && j < dimension; i++, j++) {
            diagL &= (gameField[i][j] == playerSymbol);
            diagR &= (gameField[i][dimension - 1 - j] == playerSymbol);
        }

        if (diagL || diagR) {
            return true;
        }
        return false;
    }

    boolean isFull(){
        /// Убираем цикл в цикле. Взято из 3-го урока
        for (int i = 0; i < dimension * dimension; i++) {
            int j_ = i % dimension;
            int i_ = i / dimension;

            if (gameField[i_][j_] == nullSymbol) {
                //result = false;
                return false; // Возвращаем сразу ложь, т.к. дальше нет смысла ходить по циклу.
            }
        }
        return true;
    }

    public void setNullGameField(int i, int j){
        this.gameField[j][i] = nullSymbol;
    }

    public GameButton getGameButton(int index) {
        return gameButtons[index];
    }

    public char getGameField(int i, int j){
        return this.gameField[j][i];
    }
}
