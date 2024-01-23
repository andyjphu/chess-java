//Written by Andy Phu, phu00003

public class Board {

    // Instance variables
    private Piece[][] board;

    // Construct an object of type Board using given arguments.
    public Board() {
        this.board = new Piece[8][8];
    }

    // Accessor Methods

    // Return the Piece object stored at a given row and column
    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    // Update a single cell of the board to the new piece.
    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    // Game functionality methods

    // Moves a Piece object from one cell in the board to another, provided that
    // this movement is legal. Returns a boolean to signify success or failure.
    // This method calls all necessary helper functions to determine if a move
    // is legal, and to execute the move if it is. Your Game class should not 
    // directly call any other method of this class.
    public boolean movePiece(int startRow, int startCol, int endRow, int endCol) {
        if (getPiece(startRow,startCol) == null) { //Prevent player from trying to move nonexistent pieces
            return false;
        }
        else if (getPiece(startRow,startCol).isMoveLegal(this, endRow,endCol)) {

            setPiece(endRow,endCol,getPiece(startRow,startCol));
            getPiece(endRow, endCol).setPosition(endRow,endCol);

            setPiece(startRow,startCol,null);
            return true;
        }
        else {
            return false;
        }
    }



    // Determines whether the game has been ended, i.e., if one player's King
    // has been captured.
    public boolean isGameOver() {

        int kingCounter = 0;
        for (int row = 0; row < 8; row++) {  //Since the board size is fixed, improve big O by removing need to reference board.length
            for (int col = 0; col < 8; col++) {
                if (getPiece(row,col) == null) {

                }
                else if (getPiece(row,col).getCharacter() == '\u265a' || getPiece(row,col).getCharacter() == '\u2654') {
                    kingCounter++;
                }
            }
        }
        if (kingCounter<2) {
            return true;
        }
        else {
            return false;
        }
    }

    // Construct a String that represents the Board object's 2D array. Return
    // the fully constructed String.
    public String toString() {

        StringBuilder returnString = new StringBuilder();

        returnString.append('\u2001').append('\u2001').append(" 0 1 2 3 4 5 6 7 ");

        for (int row = 0; row < 8; row++) {  //Since the board size is fixed, improve big O by removing need to reference board.length
            returnString.append("\n").append(row).append('\u2001').append("|");

            for (int col = 0; col < 8; col++) {
                if (getPiece(row, col) == null) {
                    returnString.append('\u2001').append("|");
                }
                else {
                    returnString.append(getPiece(row,col).getCharacter()).append("|");
                }
            }
        }

        return returnString.toString();
    }


    // Sets every cell of the array to null. For debugging and grading purposes.
    public void clear() {
        for (int row = 0; row < 8; row++) {  //Since the board size is fixed, improve big O by removing need to reference board.length
            for (int col = 0; col < 8; col++) {
                setPiece(row,col,null);
            }
        }
    }

    // Movement helper functions

    // Ensure that the player's chosen move is even remotely legal.
    // Returns a boolean to signify whether:
    // - 'start' and 'end' fall within the array's bounds.
    // - 'start' contains a Piece object, i.e., not null.
    // - Player's color and color of 'start' Piece match.
    // - 'end' contains either no Piece or a Piece of the opposite color.
    public boolean verifySourceAndDestination(int startRow, int startCol, int endRow, int endCol, boolean isBlack) {
        if (0 <= startRow && startRow <= 7 && 0 <= startCol && startCol <= 7) {
            if (0 <= endRow && endRow <= 7 && 0 <= endCol && endCol <= 7) {
                if (getPiece(startRow,startCol) != null) {
                    if (isBlack == getPiece(startRow,startCol).getIsBlack()) {
                        if (getPiece(endRow,endCol) == null || getPiece(endRow,endCol).getIsBlack() != isBlack) {
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }

    // Check whether the 'start' position and 'end' position are adjacent to each other
    public boolean verifyAdjacent(int startRow, int startCol, int endRow, int endCol) {
        if (Math.abs(startRow-endRow) <= 1 && Math.abs(startCol-endCol) <= 1) {
            return true;
        }
        return false;
    }

    // Checks whether a given 'start' and 'end' position are a valid horizontal move.
    // Returns a boolean to signify whether:
    // - The entire move takes place on one row.
    // - All spaces directly between 'start' and 'end' are empty, i.e., null.
    public boolean verifyHorizontal(int startRow, int startCol, int endRow, int endCol) {
        if (startRow != endRow) {
            return false;
        }
        if (startCol < endCol){
            for (int col = startCol + 1; col < endCol; col++) {
                if (getPiece(startRow, col) != null) {
                    return false;
                }
            }
        }
        else {
            for (int col = startCol - 1; col > endCol; col--) {
                if (getPiece(startRow, col) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    // Checks whether a given 'start' and 'end' position are a valid vertical move.
    // Returns a boolean to signify whether:
    // - The entire move takes place on one column.
    // - All spaces directly between 'start' and 'end' are empty, i.e., null.
    public boolean verifyVertical(int startRow, int startCol, int endRow, int endCol) {
        if (startCol != endCol) {
            return false;
        }
        if (startRow < endRow) {
            for (int row = startRow + 1; row < endRow; row++) {
                if (getPiece(row, startCol) != null) {
                    return false;
                }
            }
        }
        else {
            for (int row = startRow - 1; row > endRow; row--) {
                if (getPiece(row, startCol) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    // Checks whether a given 'start' and 'end' position are a valid diagonal move.
    // Returns a boolean to signify whether:
    // - The path from 'start' to 'end' is diagonal... change in row and col.
    // - All spaces directly between 'start' and 'end' are empty, i.e., null.
    public boolean verifyDiagonal(int startRow, int startCol, int endRow, int endCol) {
        int incrementVertical;
        int incrementHorizontal;
        if(Math.abs(startRow-endRow) != Math.abs(startCol - endCol)) {
            return false;
        }


        if(startRow<endRow) {
            incrementVertical = 1;
        }
        else {
            incrementVertical=-1;
        }
        if (startCol<endCol) {
            incrementHorizontal=1;
        }
        else {
            incrementHorizontal = -1;
        }

        for (int dist = 1; dist < Math.abs(startRow-endRow); dist++) {
            if (getPiece(startRow + (dist*incrementVertical),startCol + (dist*incrementHorizontal)) != null) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Board board1 = new Board();
        System.out.println(board1);

    }

}
