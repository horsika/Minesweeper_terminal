package minesweeper;

import minesweeper.boards.BottomBoard;
import minesweeper.boards.TopBoard;

import java.util.Scanner;

public class Main {

    static int initialColChoice;
    static int initialRowChoice;
    static Scanner sc = new Scanner(System.in);
    static BottomBoard bb = new BottomBoard(8, 8);
    static TopBoard tb = new TopBoard(bb);
    public static void main(String[] args) {
        chooseRandomBlankField();
        tb.revealFieldsRecursive(initialColChoice,initialRowChoice);
        bb.printBoard();
        tb.printBoard();

    }

    public static void chooseRandomBlankField(){
        int colChoice = bb.getR().nextInt(bb.getColumns()+1);
        int rowChoice = bb.getR().nextInt(bb.getRows()+1);
        while (bb.returnField(colChoice, rowChoice).getValue() != 0){
            colChoice = bb.getR().nextInt(bb.getColumns()+1);
            rowChoice = bb.getR().nextInt(bb.getRows()+1);
        }
        initialColChoice = colChoice;
        initialRowChoice = rowChoice;
    }

}