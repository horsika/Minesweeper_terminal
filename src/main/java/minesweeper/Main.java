package minesweeper;

import minesweeper.boards.BottomBoard;
import minesweeper.boards.TopBoard;

import java.util.Scanner;

public class Main {

    static int initialColChoice;
    static int initialRowChoice;
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        initialColChoice = 4;
        initialRowChoice = 5;
        TopBoard tb = new TopBoard(new BottomBoard(10, 10, initialColChoice, initialRowChoice));
        tb.revealFields(initialColChoice,initialRowChoice);
        tb.printBoard();
    }

}