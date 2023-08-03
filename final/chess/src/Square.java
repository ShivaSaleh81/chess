import javax.management.InvalidAttributeValueException;

import static java.lang.Math.abs;

/**
 * this method give every square in chessboard a position and specific whether it has a piece or not
 */

public class Square {
    Piece piece;
    Boolean possibleMove = false;
    int x;
    int y;
    int pieceNumber;

    /**
     * its constructor giving a position for each square and give them a number that specific their piece
     * if piece number is 0 it means this square doesn't have a piece
     */

    public Square(int x, int y, int pieceNumber) throws InvalidAttributeValueException {
        this.pieceNumber = pieceNumber;
        if (x < 8 && x >= 0)
            this.x = x;
        else
            throw new InvalidAttributeValueException("Enter valid number between 0 and 7");

        if (y < 8 && y >= 0)
            this.y = y;
        else
            throw new InvalidAttributeValueException("Enter valid number between 0 and 7");

        if (pieceNumber < 7 && pieceNumber > -7) {
            Piece piece = new Piece();
            if (pieceNumber < 0)
                piece.pieceColor = PieceColor.BLACK;
            else if (pieceNumber > 0)
                piece.pieceColor = PieceColor.WHITE;

            if (pieceNumber == 0){
                piece.pieceKind = null;
            } else {
                piece.pieceKind = PieceKind.values()[abs(pieceNumber) - 1];
            }
            this.piece = piece;

        }
        else
            throw new InvalidAttributeValueException("Enter valid number between -6 and 6");

    }
}