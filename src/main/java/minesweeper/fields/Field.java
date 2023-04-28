package minesweeper.fields;

public class Field {

    private int value; // 0=blank, 1-8=neighbours, 9=bomb
    private int columnPosition;
    private int rowPosition;

    public Field(int value, int columnPosition, int rowPosition) {
        this.value = value;
        this.columnPosition = columnPosition;
        this.rowPosition = rowPosition;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getColumnPosition() {
        return columnPosition;
    }
    public int getRowPosition() {
        return rowPosition;
    }

}
