package minesweeper.fields;

public class TopField extends Field {

    private boolean isFlagged;
    public TopField(int value, int columnPosition, int rowPosition) {
        super(value, columnPosition, rowPosition);
        this.isFlagged = false;
    }
    public boolean isFlagged() {
        return isFlagged;
    }
    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }
}
