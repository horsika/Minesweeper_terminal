package minesweeper.boards;

import minesweeper.fields.Field;

import java.util.*;

public class BottomBoard {
    private final List<Field> fields;
    private final int columns;
    private final int rows;
    private final Random r;

    public BottomBoard(int columns, int rows, int colChoice, int rowChoice) {
        this.columns = columns;
        this.rows = rows;
        r = new Random();
        fields = new ArrayList<>();
        initBoard(colChoice, rowChoice);
    }
    public List<Field> getBottomFields() {
        return fields;
    }

    public int getColumns() {
        return columns;
    }
    public int getRows() {
        return rows;
    }
    public void initBoard(int chosenCol, int chosenRow){
        for (int i = 0; i <= columns; i++) {
            for (int j = 0; j <= rows; j++) {
                fields.add(new Field(0, i, j));
            }
        }
        placeMines(chosenCol, chosenRow);
        placeNeighbors();
    }

    public Field returnField(int i, int j) {
        for (Field field : fields) {
            if(field.getColumnPosition() == i && field.getRowPosition() == j){
                return field;
            }
        }
        return null;
    }

    // PLACE MINES ----------------------------------------------------------------------------------------------------
    public void placeMines(int chosenCol, int chosenRow){
        int numberOfMines = decideNumberofMines();

        for (int i = 0; i < numberOfMines; i++) {
            int columnPosition = r.nextInt(columns);
            int rowPosition = r.nextInt(rows);
            while(!isAlreadyABomb(columnPosition, rowPosition) &&
                    !isInTheVicinityOfChosenPosition(chosenCol,chosenRow, columnPosition, rowPosition)) {
                for (Field field : fields) {
                    if(field.getColumnPosition() == columnPosition && field.getRowPosition() == rowPosition){
                        field.setValue(9);
                    }
                }
            }
        }
    }
    private int decideNumberofMines() {
        return (columns * rows)/5;
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

    private boolean isInTheVicinityOfChosenPosition(int chosenCol, int chosenRow, int col, int row){
        if((chosenCol < col-1 || chosenCol > col+1) && (chosenRow < row-1 || chosenRow > row+1)){
            return false;
        } else {
            return true;
        }
    }

    // PLACE NEIGHBORING FIELDS ---------------------------------------------------------------------------------------
    public void placeNeighbors(){
        for (Field field : fields) {
            int conditionCounter = 0;

            if(field.getValue() != 9) {
                conditionCounter = getFieldValue(field, conditionCounter);
                field.setValue(field.getValue() + conditionCounter);
            }
        }
    }

    private int getFieldValue(Field field, int conditionCounter) {
        int col = field.getColumnPosition();
        int row = field.getRowPosition();
        try {
            List<Field> neighbors = new ArrayList<>();
            neighbors.add(returnField(col - 1, row - 1));
            neighbors.add(returnField(col, row - 1));
            neighbors.add(returnField(col + 1, row - 1));
            neighbors.add(returnField(col - 1, row));
            neighbors.add(returnField(col + 1, row));
            neighbors.add(returnField(col - 1, row + 1));
            neighbors.add(returnField(col, row + 1));
            neighbors.add(returnField(col + 1, row + 1));

            for (Field neighbor : neighbors) {
                if(neighbor.getValue() == 9){
                    conditionCounter++;
                }
            }
        } catch (NullPointerException ignored){
            //ignore
        }
        return conditionCounter;
    }


}
