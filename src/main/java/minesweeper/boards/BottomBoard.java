package minesweeper.boards;
import minesweeper.fields.Field;

import java.util.*;

public class BottomBoard {
    private final List<Field> fields;
    private final int columns;
    private final int rows;
    private final Random r;

    public BottomBoard(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        r = new Random();
        fields = new ArrayList<>();
        initBoard();
    }

    public Field returnField(int i, int j) {
        for (Field field : fields) {
            if (field.getColumnPosition() == i && field.getRowPosition() == j) {
                return field;
            }
        }
        return null;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public Random getR() {
        return r;
    }

    public List<Field> getFields() {
        return fields;
    }

    private void initBoard() {
        for (int i = 0; i <= columns; i++) {
            for (int j = 0; j <= rows; j++) {
                fields.add(new Field(0, i, j));
            }
        }
        placeMines();
        placeNeighbors();
    }

    // PLACE MINES ----------------------------------------------------------------------------------------------------
    private void placeMines() {
        int mineCounter = 0;

        while (mineCounter < decideNumberofMines()) {
            int columnPosition = r.nextInt(columns + 1);
            int rowPosition = r.nextInt(rows + 1);
            while (!isAlreadyABomb(columnPosition, rowPosition)) {

                for (Field field : fields) {
                    if (field.getColumnPosition() == columnPosition && field.getRowPosition() == rowPosition) {
                        field.setValue(9);
                        mineCounter++;
                        break;
                    }
                }
            }
        }
    }

    private int decideNumberofMines() {
        return (columns * rows) / 8;
    }

    private boolean isAlreadyABomb(int columnPosition, int rowPosition) {
        for (Field field : fields) {
            if (field.getColumnPosition() == columnPosition
                    && field.getRowPosition() == rowPosition
                    && field.getValue() == 9) {
                return true;
            }
        }
        return false;
    }

    // PLACE NEIGHBORING FIELDS ---------------------------------------------------------------------------------------

    private void placeNeighbors() {
        for (Field field : fields) {
            int col = field.getColumnPosition();
            int row = field.getRowPosition();
            if (field.getValue() != 9) {
                int conditionCounter = minesNearBy(col, row);
                field.setValue(field.getValue() + conditionCounter);
            }
        }
    }

    public int minesNearBy(int col, int row) {

        List<Field> neighbors = new ArrayList<>();
        neighbors.add(returnField(col - 1, row - 1));
        neighbors.add(returnField(col, row - 1));
        neighbors.add(returnField(col + 1, row - 1));
        neighbors.add(returnField(col - 1, row));
        neighbors.add(returnField(col + 1, row));
        neighbors.add(returnField(col - 1, row + 1));
        neighbors.add(returnField(col, row + 1));
        neighbors.add(returnField(col + 1, row + 1));

        neighbors.removeAll(Collections.singleton(null)); // null values must be removed

        int mineCounter = 0;
        for (Field neighbor : neighbors) {
            if (neighbor.getValue() == 9) {
                mineCounter++;
            }
        }
        return mineCounter;
    }


}
