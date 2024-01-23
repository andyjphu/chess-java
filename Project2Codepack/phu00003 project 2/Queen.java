//Written by Andy Phu, phu00003


public class Queen {
    /**
     * Constructor.
     * @param row   The current row of the pawn.
     * @param col   The current column of the pawn.
     * @param isBlack   The color of the pawn.
     */
    public Queen(int row, int col, boolean isBlack) {
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    /**
     * Checks if a move to a destination square is legal.
     * @param board     The game board.
     * @param endRow    The row of the destination square.
     * @param endCol    The column of the destination square.
     * @return True if the move to the destination square is legal, false otherwise.
     */
    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        if (board.verifyDiagonal(row, col, endRow, endCol)) {
            if (board.getPiece(endRow,endCol) == null || isBlack != board.getPiece(endRow,endCol).getIsBlack()) {
                return true;
            }
        }
        if (board.verifyVertical(this.row, this.col, endRow, endCol) || board.verifyHorizontal(this.row, this.col, endRow, endCol)){
            if (board.getPiece(endRow, endCol) == null) {
                return true; //if there isn't a piece at the desired location, then move
            }
            else if (board.getPiece(endRow, endCol).getIsBlack() != isBlack) {
                return true; //Return true only if the move is a valid vertical or horizontal and the piece is of the opposite color
            }
        }
        return false; //Return false by default
    }

    // Instance variables
    private int row;
    private int col;
    private boolean isBlack;
}
