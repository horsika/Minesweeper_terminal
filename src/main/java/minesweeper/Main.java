package minesweeper;
import minesweeper.boards.BottomBoard;
import minesweeper.boards.TopBoard;
import java.util.Scanner;
public class Main {
    static int initialColChoice;
    static int initialRowChoice;
    static Scanner sc = new Scanner(System.in);
    static BottomBoard bb = new BottomBoard(8, 9);
    static TopBoard tb = new TopBoard(bb);
    public static void main(String[] args) {
        runGame();
    }
    private static void runGame() {
        chooseRandomBlankField();
        tb.revealFieldsRecursive(initialColChoice, initialRowChoice);
        System.out.println("Welcome!");
        tb.printBoard();

        while (!tb.allFieldsAreRevealedExceptForBombs()) {
            int col = getCol();
            int row = getRow();
            if(bb.returnField(col, row) == null){
                System.out.println("Invalid inputs, try again!");
            } else if(tb.getRevealedFields().contains(bb.returnField(col, row))) {
                System.out.println("The chosen field is already revealed, try again!");
            } else if (bb.returnField(col, row).getValue() == 0) {
                tb.revealFieldsRecursive(col, row);
                tb.printBoard();
            } else if (bb.returnField(col, row).getValue() > 0 && bb.returnField(col, row).getValue() < 9) {
                tb.revealField(col, row);
                tb.printBoard();
            } else if (bb.returnField(col, row).getValue() == 9) {
                tb.revealAllBombs();
                tb.printBoard();
                System.out.println("KA-BOOM!");
                break;
            }
        }
        if(tb.allFieldsAreRevealedExceptForBombs()) {
            System.out.println("WINNER!");
        }

    }
    private static int getCol() {
        System.out.print("Row: ");
        return sc.nextInt();
    }
    private static int getRow() {
        System.out.print("Column: ");
        return sc.nextInt();
    }
    private static void chooseRandomBlankField() {
        int colChoice = bb.getR().nextInt(bb.getColumns() + 1);
        int rowChoice = bb.getR().nextInt(bb.getRows() + 1);
        while (bb.returnField(colChoice, rowChoice).getValue() != 0) {
            colChoice = bb.getR().nextInt(bb.getColumns() + 1);
            rowChoice = bb.getR().nextInt(bb.getRows() + 1);
        }
        initialColChoice = colChoice;
        initialRowChoice = rowChoice;
    }
}