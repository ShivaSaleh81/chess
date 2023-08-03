import javax.management.InvalidAttributeValueException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;

/**
 * this class give us a board that is composed of a 2D Array of Square
 */

public class Board {
    Square[][] board = new Square[8][8];
    HashMap<PieceKind, Integer> outPiecesWhite = new HashMap<>();
    HashMap<PieceKind, Integer> outPiecesBlack = new HashMap<>();

    public Board(int[][] setupBoard) throws InvalidAttributeValueException, FileNotFoundException {
        for (PieceKind kind: PieceKind.values()) {
            outPiecesBlack.put(kind, 0);
            outPiecesWhite.put(kind, 0);
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Square(i, j, setupBoard[i][j]);
            }
        }
    }
}