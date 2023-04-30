package minesweeper.boards;

import minesweeper.ANSI;
import minesweeper.fields.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TopBoard {

    private final BottomBoard bottomBoard;
    private final List<Field> revealedFields;

    public TopBoard(BottomBoard bottomBoard) {
        this.bottomBoard = bottomBoard;
        revealedFields = new ArrayList<>();
    }

    public List<Field> getRevealedFields() {
        return revealedFields;
    }

    public void printBoard() {
        for (int i = 0; i <= bottomBoard.getColumns()+1; i++) {
            for (int j = 0; j <= bottomBoard.getRows()+1; j++) {
                printFieldsAndIndexes(i, j);
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printFieldsAndIndexes(int i, int j) {
        if (i + j == bottomBoard.getColumns() + bottomBoard.getRows()+2) {
            System.out.print("   ");
        } else if (i == bottomBoard.getColumns()+1) {
            if(j < 10) {
                System.out.print(" " + j + " ");
            } else if (j >= 10){
                System.out.print(" " + j);
            }
        } else if (j == bottomBoard.getRows()+1) {
            if(i < 10) {
                System.out.print(" " + i + " ");
            } else if (i >= 10){
                System.out.print(i + " ");
            }
        } else if (returnField(i, j) == null) {
            System.out.print(ANSI.BLUE_BG + ANSI.WHITE_FONT + "   " + ANSI.RESET);
        } else if (Objects.requireNonNull(returnField(i, j)).getValue() == 9) {
            System.out.print(ANSI.RED_BG + ANSI.WHITE_FONT + " X " + ANSI.RESET);
        } else if (Objects.requireNonNull(returnField(i, j)).getValue() == 0) {
            System.out.print(ANSI.PURPLE_BG + ANSI.WHITE_FONT + "   " + ANSI.RESET);
        } else {
            System.out.print(ANSI.PURPLE_BG + ANSI.WHITE_FONT + " " + Objects.requireNonNull(returnField(i, j)).getValue() + " " + ANSI.RESET);
        }
    }

    public Field returnField(int i, int j) {
        for (Field field : revealedFields) {
            if (field.getColumnPosition() == i && field.getRowPosition() == j) {
                return field;
            }
        }
        return null;
    }

    public void revealField(int col, int row) {
        Field chosenField = bottomBoard.returnField(col, row);
        revealedFields.add(chosenField);
    }

    public void revealFieldsRecursive(int col, int row) {
        Field chosenField = bottomBoard.returnField(col, row);

        if (chosenField != null && (bottomBoard.minesNearBy(col, row) == 0 && !revealedFields.contains(chosenField))) {
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
        if (bottomBoard.returnField(col - 1, row) != null && bottomBoard.minesNearBy(col - 1, row) > 0) {
            chosenField = bottomBoard.returnField(col - 1, row);
            revealedFields.add(chosenField);
        }
        if (bottomBoard.returnField(col + 1, row) != null && bottomBoard.minesNearBy(col + 1, row) > 0) {
            chosenField = bottomBoard.returnField(col + 1, row);
            revealedFields.add(chosenField);
        }
        if (bottomBoard.returnField(col, row - 1) != null && bottomBoard.minesNearBy(col, row - 1) > 0) {
            chosenField = bottomBoard.returnField(col, row - 1);
            revealedFields.add(chosenField);
        }
        if (bottomBoard.returnField(col, row + 1) != null && bottomBoard.minesNearBy(col, row + 1) > 0) {
            chosenField = bottomBoard.returnField(col, row + 1);
            revealedFields.add(chosenField);
        }
    }

    private void revealEdgeDiagonals(int col, int row) {
        Field chosenField;
        if (bottomBoard.returnField(col - 1, row - 1) != null && bottomBoard.minesNearBy(col - 1, row - 1) > 0) {
            chosenField = bottomBoard.returnField(col - 1, row - 1);
            revealedFields.add(chosenField);
        }
        if (bottomBoard.returnField(col + 1, row + 1) != null && bottomBoard.minesNearBy(col + 1, row + 1) > 0) {
            chosenField = bottomBoard.returnField(col + 1, row + 1);
            revealedFields.add(chosenField);
        }
        if (bottomBoard.returnField(col - 1, row + 1) != null && bottomBoard.minesNearBy(col - 1, row + 1) > 0) {
            chosenField = bottomBoard.returnField(col - 1, row + 1);
            revealedFields.add(chosenField);
        }
        if (bottomBoard.returnField(col + 1, row - 1) != null && bottomBoard.minesNearBy(col + 1, row - 1) > 0) {
            chosenField = bottomBoard.returnField(col + 1, row - 1);
            revealedFields.add(chosenField);
        }
    }

    public void revealAllBombs() {
        for (Field field : bottomBoard.getFields()) {
            if (field.getValue() == 9) revealedFields.add(field);
        }
    }

    public boolean allFieldsAreRevealedExceptForBombs(){
        for (Field field : bottomBoard.getFields()) {
            if(!revealedFields.contains(field) && field.getValue() != 9){
                return false;
            }
        }
        return true;
    }
}
