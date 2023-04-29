package minesweeper.boards;

import minesweeper.ANSI;
import minesweeper.fields.Field;

import java.util.ArrayList;
import java.util.Collections;
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

    public void revealField(int col, int row){
        Field chosenField = bottomBoard.returnField(col, row);
        revealedFields.add(chosenField);
    }

    public void revealFieldsRecursive(int col, int row) {
        Field chosenField = bottomBoard.returnField(col, row);

        if (chosenField != null && (minesNearBy(col, row) == 0 && !revealedFields.contains(chosenField))) {
            revealedFields.add(chosenField);
            revealFieldsRecursive(col - 1, row);
            revealFieldsRecursive(col + 1, row);
            revealFieldsRecursive(col, row - 1);
            revealFieldsRecursive(col, row + 1);
            //diagonals
            revealFieldsRecursive(col - 1, row - 1);
            revealFieldsRecursive(col + 1, row + 1);
            revealFieldsRecursive(col - 1, row + 1);
            revealFieldsRecursive(col + 1, row - 1);

            revealEdgeStraights(col, row);
            revealEdgeDiagonals(col, row);
        }
    }
    private void revealEdgeStraights(int col, int row) {
        Field chosenField;
        if (bottomBoard.returnField(col - 1, row) != null && minesNearBy(col - 1, row) > 0) {
            chosenField = bottomBoard.returnField(col - 1, row);
            revealedFields.add(chosenField);
        }
        if (bottomBoard.returnField(col + 1, row) != null && minesNearBy(col + 1, row) > 0) {
            chosenField = bottomBoard.returnField(col + 1, row);
            revealedFields.add(chosenField);
        }
        if (bottomBoard.returnField(col, row - 1) != null && minesNearBy(col, row - 1) > 0) {
            chosenField = bottomBoard.returnField(col, row - 1);
            revealedFields.add(chosenField);
        }
        if (bottomBoard.returnField(col, row + 1) != null && minesNearBy(col, row + 1) > 0) {
            chosenField = bottomBoard.returnField(col, row + 1);
            revealedFields.add(chosenField);
        }
    }
    private void revealEdgeDiagonals(int col, int row) {
        Field chosenField;
        if (bottomBoard.returnField(col - 1, row - 1) != null && minesNearBy(col - 1, row - 1) > 0) {
            chosenField = bottomBoard.returnField(col - 1, row - 1);
            revealedFields.add(chosenField);
        }
        if (bottomBoard.returnField(col + 1, row + 1) != null && minesNearBy(col + 1, row + 1) > 0) {
            chosenField = bottomBoard.returnField(col + 1, row + 1);
            revealedFields.add(chosenField);
        }
        if (bottomBoard.returnField(col - 1, row + 1) != null && minesNearBy(col - 1, row + 1) > 0) {
            chosenField = bottomBoard.returnField(col - 1, row + 1);
            revealedFields.add(chosenField);
        }
        if (bottomBoard.returnField(col + 1, row - 1) != null && minesNearBy(col + 1, row - 1) > 0) {
            chosenField = bottomBoard.returnField(col + 1, row - 1);
            revealedFields.add(chosenField);
        }
    }

    private int minesNearBy(int col, int row) {
        List<Field> neighbors = new ArrayList<>();
        neighbors.add(bottomBoard.returnField((col - 1), row));
        neighbors.add(bottomBoard.returnField((col + 1), row));
        neighbors.add(bottomBoard.returnField(col, (row - 1)));
        neighbors.add(bottomBoard.returnField(col, (row + 1)));
        //diagonals
        neighbors.add(bottomBoard.returnField(col - 1, row - 1));
        neighbors.add(bottomBoard.returnField(col + 1, row + 1));
        neighbors.add(bottomBoard.returnField(col - 1, row + 1));
        neighbors.add(bottomBoard.returnField(col + 1, row - 1));

        neighbors.removeAll(Collections.singleton(null));
        int mineCounter = 0;
        for (Field neighbor : neighbors) {
            if (neighbor.getValue() == 9) mineCounter++;
        }
        return mineCounter;
    }
}
