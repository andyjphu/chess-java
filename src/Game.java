//Written by Andy Phu, phu00003


import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        int startRow = -1;
        int startCol = -1;
        int endRow = -1;
        int endCol = -1;
        boolean blackTurn = true;

        Board board = new Board();
        //Load up the board pieces in standard format
        Fen.load("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", board);
        Scanner scanner = new Scanner(System.in);
        String nextLine = "";
        while(!board.isGameOver()) {

            System.out.println("Board: ");
            System.out.println(board);
            if (blackTurn) {
                System.out.println("\n it's currently black's turn to play.");
            }
            else {
                System.out.println("\n it's currently white's turn to play.");
            }
            System.out.println("\n What is your move (format: [start row] [start col] [end row] [end col]");

            startRow = scanner.nextInt();
            startCol = scanner.nextInt();
            endRow = scanner.nextInt();
            endCol = scanner.nextInt();
            if (board.getPiece(startRow, startCol) != null) {
                if (board.getPiece(startRow, startCol).getIsBlack() == blackTurn) {
                    board.movePiece(startRow, startCol, endRow, endCol);
                    blackTurn = !blackTurn;
                }
            }

        }
        //blackTurn had to be inversed before the while loop ended,
        //therefore if !blackturn was true, the king was killed on black's turn, and vice versa
        if (!blackTurn == true) {
            System.out.println("Game done, black wins");
        }
        else {
            System.out.println("Game done, white wins");
        }
    }
}
