package pegorov.lesson4.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameActionListener implements ActionListener {
    private GameButton button;
    private int row;
    private int cell;

    public GameActionListener(int rowNum, int cellNum, GameButton gameButton) {
        this.row = rowNum;
        this.cell = cellNum;
        this.button = gameButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameBoard board = button.getBoard();

        if (board.isTurnable(row, cell)){
            updateByPlayersDate(board);

            if (board.checkWin()){
                ///  Игрок победил ничего делать не нужно
            }else
            if (board.isFull()){
                board.getGame().showMessage("Ничья!");
                board.emptyField();
            }else{
                updateByAiData(board);
            }
        }else
            board.getGame().showMessage("Error");
    }

    private void updateByAiData(GameBoard board) {
        /// Реализуем логику умного компьютера с учетом урока 3. Адаптируя под наш код
        int maxPoint = -1; // максимальные очки по клетке

        int x = -1;
        int y = -1;

        for (int i = 0; i < GameBoard.dimension * GameBoard.dimension; i++) {
            int j_ = i % GameBoard.dimension;
            int i_ = i / GameBoard.dimension;

            if (board.isTurnable(i_, j_)) {
                /// Установим клетке значение компьютера и проверим на победу компьютера
                /// Нес смысла считать очки, если мы точно победим. По этому ставим проверку на победы первой

                board.updateGameField(i_, j_);
                if (board.checkWin()) {
                    /// компьютер выйгрет нужно ставить сюда.
                    x = i_;
                    y = j_;
                    board.getGame().showMessage("Компьютер выйграл!");
                    //board.emptyField();

                    /// Нет смысла что либо дальше делать. Завершим процедуру
                    return;
                }

                int point = 0; // очки по текущей клетке
                /// Считаем очки соседних клеток
                point = point + getPoint(i_, j_, board);

                /// Установим клетке значение игрока и проверим на проигрыш компьютера
                board.getGame().passTurn();
                board.setNullGameField(i_, j_);

                board.updateGameField(i_, j_);
                if (board.checkWin()) {
                    /// компьютер проиграет нужно ставить сюда, если нет победы
                    point = point + 10;
                }

                /// Возвращаем значение точки назад. Т.к. возможно это не наша точка.
                board.getGame().passTurn();
                board.setNullGameField(i_, j_);

                /// Если это не победная клетка посчитаем очки. Если очки равные, я оставлю первую точку.
                if (maxPoint < point) {
                    maxPoint = point;
                    x = i_;
                    y = j_;
                }
            }

        }

        board.updateGameField(x, y);
        int cellIndex = GameBoard.dimension *x + y;
        board.getGameButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

        board.getGame().passTurn();
    }

    private void updateByPlayersDate(GameBoard board) {
        board.updateGameField(row, cell);

        button.setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

        if (board.checkWin()){
            button.getBoard().getGame().showMessage("Вы выйграли");
        }else {
            board.getGame().passTurn();
        }
    }

    private static int getPoint(int i, int j, GameBoard board) {
        int result = 0;

        /// Проверяем все точки по очереди и суммируем их очки. Всего 8 вариантов.
        result += getOnePoint(i - 1, j - 1, board);
        result += getOnePoint(i - 1, j, board);
        result += getOnePoint(i - 1, j + 1, board);
        result += getOnePoint(i, j - 1, board);
        result += getOnePoint(i, j + 1, board);
        result += getOnePoint(i + 1, j - 1, board);
        result += getOnePoint(i + 1, j, board);
        result += getOnePoint(i + 1, j + 1, board);

        return result;
    }

    private static int getOnePoint(int i, int j, GameBoard board) {

        /// Проверяем на границы массива
        if (i < 0 || i >= GameBoard.dimension || j < 0 || j >= GameBoard.dimension) {
            return 0;
        }
        /// Проверяем на свою клетку
        if (board.getGameField(i,j) == board.getGame().getCurrentPlayer().getPlayerSign()) {
            return 2;
        }
        /// Проверяем на клетку противника
        if ((board.getGameField(i,j) != board.getGame().getCurrentPlayer().getPlayerSign())
            && (board.getGameField(i,j) != board.nullSymbol)) {
            return 0;
        }
        /// В противном случае это только пустая клетка
        return 1;
    }

}
