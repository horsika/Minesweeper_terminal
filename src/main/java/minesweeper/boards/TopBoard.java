package minesweeper.boards;

import minesweeper.ANSI;
import minesweeper.fields.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TopBoard {

    protected BottomBoard bottomBoard;
    protected List<Field> revealedFields;

    public TopBoard(BottomBoard bottomBoard) {
        this.bottomBoard = bottomBoard;
        revealedFields = new ArrayList<>();
    }


    public void printBoard() {
        for (int i = 0; i <= bottomBoard.getColumns(); i++) {
            for (int j = 0; j <= bottomBoard.getRows(); j++) {
                if (returnField(i, j) == null) {
                    System.out.print(ANSI.BLUE_BG + ANSI.WHITE_FONT + "   " + ANSI.RESET);
                } else if (Objects.requireNonNull(returnField(i, j)).getValue() == 9) {
                    System.out.print(ANSI.RED_BG + ANSI.WHITE_FONT + " X " + ANSI.RESET);
                } else if (Objects.requireNonNull(returnField(i, j)).getValue() == 0) {
                    System.out.print(ANSI.PURPLE_BG + ANSI.WHITE_FONT + "   " + ANSI.RESET);
                } else {
                    System.out.print(ANSI.PURPLE_BG + ANSI.WHITE_FONT + " " + Objects.requireNonNull(returnField(i, j)).getValue() + " " + ANSI.RESET);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public Field returnField(int i, int j) {
        for (Field field : revealedFields) {
            if (field.getColumnPosition() == i && field.getRowPosition() == j) {
                return field;
            }
        }
        return null;
    }
}
