import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * this method give every pieces their property
 */

public class Piece {
    PieceKind pieceKind;
    PieceColor pieceColor;
    HashMap<PieceKind, ArrayList<ArrayList<int[]>>> pieceMove = new HashMap<>();

    /**
     * its constructor give a possible move of each piece to pieceMove field
     */
    public Piece(){
        ArrayList<ArrayList<int[]>> kingPosition = new ArrayList<>();
        for (int i = 0; i < 8; i ++) {
            kingPosition.add(new ArrayList<>());
        }
        kingPosition.get(0).add(new int[]{-1, 0});
        kingPosition.get(1).add(new int[]{-1, -1});
        kingPosition.get(2).add(new int[]{1, 0});
        kingPosition.get(3).add(new int[]{1, 1});
        kingPosition.get(4).add(new int[]{0, -1});
        kingPosition.get(5).add(new int[]{0, 1});
        kingPosition.get(6).add(new int[]{-1, 1});
        kingPosition.get(7).add(new int[]{1, -1});
        pieceMove.put(PieceKind.KING, kingPosition);

        ArrayList<ArrayList<int[]>> queenPosition = new ArrayList<>();
        for (int i = 0; i < 8; i ++) {
            queenPosition.add(new ArrayList<>());
        }
        for (int i = 1; i < 8; i ++) {
            queenPosition.get(0).add(new int[]{0, i});
            queenPosition.get(1).add(new int[]{0, -1 * i});
            queenPosition.get(2).add(new int[]{i, 0});
            queenPosition.get(3).add(new int[]{-1 * i, 0});
            queenPosition.get(4).add(new int[]{i, i});
            queenPosition.get(5).add(new int[]{-1 * i, -1 * i});
            queenPosition.get(6).add(new int[]{-1 * i, i});
            queenPosition.get(7).add(new int[]{i, -1 * i});
        }
        pieceMove.put(PieceKind.QUEEN, queenPosition);

        ArrayList<ArrayList<int[]>> bishopPosition = new ArrayList<>();
        for (int i = 0; i < 4; i ++) {
            bishopPosition.add(new ArrayList<>());
        }
        for (int i = 1; i < 8; i ++) {
            bishopPosition.get(0).add(new int[]{i, i});
            bishopPosition.get(1).add(new int[]{-1 * i, -1 * i});
            bishopPosition.get(2).add(new int[]{i, -1 * i});
            bishopPosition.get(3).add(new int[]{-1 * i, i});
        }
        pieceMove.put(PieceKind.BISHOP, bishopPosition);

        ArrayList<ArrayList<int[]>> knightPosition = new ArrayList<>();
        for (int i = 0; i < 8; i ++) {
            knightPosition.add(new ArrayList<>());
        }
        knightPosition.get(0).add(new int[]{-1, -2});
        knightPosition.get(1).add(new int[]{1, -2});
        knightPosition.get(2).add(new int[]{-1, 2});
        knightPosition.get(3).add(new int[]{1, 2});
        knightPosition.get(4).add(new int[]{-2, -1});
        knightPosition.get(5).add(new int[]{-2, +1});
        knightPosition.get(6).add(new int[]{2, -1});
        knightPosition.get(7).add(new int[]{2, +1});
        pieceMove.put(PieceKind.KNIGHT, knightPosition);

        ArrayList<ArrayList<int[]>> pawnPosition = new ArrayList<>();
        pawnPosition.add(new ArrayList<>());
        pawnPosition.get(0).add(new int[]{0, 1});
        pawnPosition.get(0).add(new int[]{0, 2});
        pieceMove.put(PieceKind.PAWN, pawnPosition);


        ArrayList<ArrayList<int[]>> rookPosition = new ArrayList<>();
        for (int i = 0; i < 4; i ++) {
            rookPosition.add(new ArrayList<>());
        }
        for (int i = 1; i < 8; i ++) {
            rookPosition.get(0).add(new int[]{i, 0});
            rookPosition.get(1).add(new int[]{-1 * i, 0});
            rookPosition.get(2).add(new int[]{0, i});
            rookPosition.get(3).add(new int[]{0, -1 * i});
        }
        pieceMove.put(PieceKind.ROOK, rookPosition);

    }
}
