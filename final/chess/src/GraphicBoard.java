import javax.imageio.ImageIO;
import javax.management.InvalidAttributeValueException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class GraphicBoard extends AIPlayer{
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JPanel header_panel = new JPanel();
    JPanel footer_panel = new JPanel();
    JPanel center_panel = new JPanel();
    PieceKind tempPieceKind;
    PieceColor tempPieceColor;
    int tempI = -1, tempJ = -1, tempNum = 0;
    boolean is_turn = true;
    int b_idX = -1, w_idX = -1;
    ArrayList<PieceKind> tempOutPiecesBlack = new ArrayList<>();
    ArrayList<PieceKind> tempOutPiecesWhite = new ArrayList<>();
    boolean ai_turn = false;
    boolean ai_isWhite = true;
    boolean ai_pawnMove = false;

    int bc_1 = 1, bc_2 = 1, bc_3 = 2, bc_4 = 2, bc_5 = 2, bc_6 = 8;
    int wc_1 = 1, wc_2 = 1, wc_3 = 2, wc_4 = 2, wc_5 = 2, wc_6 = 8;


    /**
     * this method convert board and piece move to a graphic board and show them in a graphic chess board
     */
    public void showBoard(Board board) throws IOException, InvalidAttributeValueException {
        /**
         * here we check if King is out of game or not
         * to stop the game
         */
        if (board.outPiecesWhite.get(PieceKind.KING) == 1) {
            frame.dispose();
            Black_Win();
            return;
        }

        if (board.outPiecesBlack.get(PieceKind.KING) == 1) {
            frame.dispose();
            White_Win();
            return;
        }

        /**
         * adding the panel of the main frame
         * we have header and footer panel which has 16 buttons
         * and a center panel that our chess board
         */
        center_panel.removeAll();
        center_panel.repaint();

        header_panel.removeAll();
        header_panel.repaint();

        footer_panel.removeAll();
        footer_panel.repaint();

        header_panel.setLayout (new BoxLayout(header_panel, BoxLayout.X_AXIS));
        header_panel.setPreferredSize(new Dimension(700,50));
        header_panel.setVisible(true);

        JButton[] b_outPieces = new JButton[16];
        for (int i = 0; i < 16; i++) {
            b_outPieces[i] = new JButton();
            b_outPieces[i].setPreferredSize(new Dimension(43,50));
            header_panel.add(b_outPieces[i]);
        }

        footer_panel.setLayout(new BoxLayout(footer_panel, BoxLayout.X_AXIS));
        footer_panel.setPreferredSize(new Dimension(700,50));
        footer_panel.setVisible(true);

        JButton[] w_outPieces = new JButton[16];
        for (int i = 0; i < 16; i++) {
            w_outPieces[i] = new JButton();
            w_outPieces[i].setPreferredSize(new Dimension(43,50));
            footer_panel.add(w_outPieces[i]);
        }

        // these "for" used for show the out pieces in the main frame
        for (int i = 0; i <= b_idX; i++) {
            setBlackOutPieceIcon(i,b_outPieces,tempOutPiecesBlack);
        }
        for (int i = 0; i <= w_idX; i++) {
            setWhiteOutPieceIcon(i,w_outPieces,tempOutPiecesWhite);
        }

        // here we fill the chess board with the button
        JButton[][] gBoard = new JButton[8][8];

        /**
         * here give them a color base on real chess board
         * and if their possible move is true we color them red
         */

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                final int finalI = i;
                final int finalJ = j;
                gBoard[i][j] = new JButton();
                if ((board.board[i][j].x + board.board[i][j].y) % 2 == 0) {
                    gBoard[i][j].setBackground(new Color(237,237,237));
                    if (board.board[i][j].possibleMove) {
                        gBoard[i][j].setBackground(new Color(130,0,0));
                    }
                }
                else {
                    gBoard[i][j].setBackground(new Color(61,61,61));
                    if (board.board[i][j].possibleMove) {
                        gBoard[i][j].setBackground(new Color(100,0,0));
                    }
                }

                gBoard[i][j].setOpaque(true);
                gBoard[i][j].setBorderPainted(false);
                center_panel.add(gBoard[i][j]);

                // set the pieces on a board
                setIcon(i, j, gBoard, board);

                // click on one button
                gBoard[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        boolean is_select = true;
                        boolean isMove = true;
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                if (board.board[i][j].possibleMove) {
                                    is_select = false;
                                    break;
                                }
                            }
                        }

                        /**
                         * we have two mode when we click on a button
                         * 1. we want to select the square that our piece move to
                         * 2. our piece move to the selected square
                         * here if is_select is true means num 1
                         */

                        if (is_select) {
                            for (PieceKind p : PieceKind.values()) {
                                if (board.board[finalI][finalJ].piece.pieceKind == p) {
                                    if (board.board[finalI][finalJ].piece.pieceColor == PieceColor.BLACK && !is_turn) {
                                        for (ArrayList<int[]> movesList : board.board[finalI][finalJ].piece.pieceMove.get(board.board[finalI][finalJ].piece.pieceKind)) {
                                            for (int[] moves : movesList) {
                                                try {
                                                    if (board.board[finalI + moves[1]][finalJ + moves[0]].piece.pieceKind == null) {
                                                        board.board[finalI + moves[1]][finalJ + moves[0]].possibleMove = !board.board[finalI + moves[1]][finalJ + moves[0]].possibleMove;
                                                    } else {
                                                        if (board.board[finalI + moves[1]][finalJ + moves[0]].piece.pieceColor == PieceColor.BLACK) {
                                                            board.board[finalI + moves[1]][finalJ + moves[0]].possibleMove = board.board[finalI + moves[1]][finalJ + moves[0]].possibleMove;
                                                        } else {
                                                            board.board[finalI + moves[1]][finalJ + moves[0]].possibleMove = !board.board[finalI + moves[1]][finalJ + moves[0]].possibleMove;
                                                        }
                                                        break;
                                                    }
                                                } catch (Exception ignored) {}
                                            }
                                        }
                                        if (board.board[finalI][finalJ].piece.pieceKind == PieceKind.PAWN){
                                            try {
                                                if (board.board[finalI + 1][finalJ + 1].piece.pieceColor == PieceColor.WHITE) {
                                                    board.board[finalI + 1][finalJ + 1].possibleMove = !board.board[finalI + 1][finalJ + 1].possibleMove;
                                                }
                                            }
                                            catch (Exception ignored){}
                                            try {
                                                if (board.board[finalI + 1][finalJ - 1].piece.pieceColor == PieceColor.WHITE) {
                                                    board.board[finalI + 1][finalJ - 1].possibleMove = !board.board[finalI + 1][finalJ - 1].possibleMove;
                                                }
                                            }
                                            catch (Exception ignored){}

                                            try {
                                                if (board.board[finalI + 1][finalJ].piece.pieceColor == PieceColor.WHITE) {
                                                    board.board[finalI + 1][finalJ].possibleMove = false;
                                                }
                                            }catch (Exception ignored){}
                                            try {
                                                if (board.board[finalI + 2][finalJ].piece.pieceColor == PieceColor.WHITE) {
                                                    board.board[finalI + 2][finalJ].possibleMove = false;
                                                }
                                            }catch (Exception ignored){}
                                        }
                                    } else if (board.board[finalI][finalJ].piece.pieceColor == PieceColor.WHITE && is_turn) {
                                        for (ArrayList<int[]> movesList : board.board[finalI][finalJ].piece.pieceMove.get(board.board[finalI][finalJ].piece.pieceKind)) {
                                            for (int[] moves : movesList) {
                                                try {
                                                    if (board.board[finalI - moves[1]][finalJ - moves[0]].piece.pieceKind == null) {
                                                        board.board[finalI - moves[1]][finalJ - moves[0]].possibleMove = !board.board[finalI - moves[1]][finalJ - moves[0]].possibleMove;
                                                    } else {
                                                        if (board.board[finalI - moves[1]][finalJ - moves[0]].piece.pieceColor == PieceColor.WHITE) {
                                                            board.board[finalI - moves[1]][finalJ - moves[0]].possibleMove = board.board[finalI - moves[1]][finalJ - moves[0]].possibleMove;
                                                        } else {
                                                            board.board[finalI - moves[1]][finalJ - moves[0]].possibleMove = !board.board[finalI - moves[1]][finalJ - moves[0]].possibleMove;
                                                        }
                                                        break;
                                                    }
                                                } catch (Exception ignored) {

                                                }
                                            }
                                        }
                                        if (board.board[finalI][finalJ].piece.pieceKind == PieceKind.PAWN){
                                            try {
                                                if (board.board[finalI - 1][finalJ + 1].piece.pieceColor == PieceColor.BLACK) {
                                                    board.board[finalI - 1][finalJ + 1].possibleMove = !board.board[finalI - 1][finalJ + 1].possibleMove;
                                                }
                                            }
                                            catch (Exception ignored){}
                                            try {
                                                if (board.board[finalI - 1][finalJ - 1].piece.pieceColor == PieceColor.BLACK) {
                                                    board.board[finalI - 1][finalJ - 1].possibleMove = !board.board[finalI - 1][finalJ - 1].possibleMove;
                                                }
                                            }
                                            catch (Exception ignored){}
                                            try{
                                                if (board.board[finalI - 1][finalJ].piece.pieceColor == PieceColor.BLACK) {
                                                    board.board[finalI - 1][finalJ].possibleMove = false;
                                                }
                                            } catch (Exception ignored){}

                                            try {
                                                if (board.board[finalI - 2][finalJ].piece.pieceColor == PieceColor.BLACK) {
                                                    board.board[finalI - 2][finalJ].possibleMove = false;
                                                }
                                            } catch (Exception ignored){}

                                        }
                                    }
                                    tempPieceKind = board.board[finalI][finalJ].piece.pieceKind;
                                    tempPieceColor = board.board[finalI][finalJ].piece.pieceColor;
                                    tempI = finalI;
                                    tempJ = finalJ;
                                    tempNum = board.board[finalI][finalJ].pieceNumber;
                                }
                            }
                        }
                        else {
                            if (board.board[finalI][finalJ].possibleMove){
                                if (board.board[finalI][finalJ].piece.pieceColor == PieceColor.BLACK){
                                    board.outPiecesBlack.put(board.board[finalI][finalJ].piece.pieceKind, board.outPiecesBlack.get(board.board[finalI][finalJ].piece.pieceKind) + 1);
                                    tempOutPiecesBlack.add(board.board[finalI][finalJ].piece.pieceKind);
                                    b_idX ++;
                                }
                                if (board.board[finalI][finalJ].piece.pieceColor == PieceColor.WHITE) {
                                    board.outPiecesWhite.put(board.board[finalI][finalJ].piece.pieceKind, board.outPiecesWhite.get(board.board[finalI][finalJ].piece.pieceKind) + 1);
                                    tempOutPiecesWhite.add(board.board[finalI][finalJ].piece.pieceKind);
                                    w_idX ++;
                                }

                                board.board[finalI][finalJ].piece.pieceKind = tempPieceKind;
                                board.board[finalI][finalJ].piece.pieceColor = tempPieceColor;
                                board.board[finalI][finalJ].pieceNumber = tempNum;
                                board.board[tempI][tempJ].piece.pieceKind = null;
                                board.board[tempI][tempJ].piece.pieceColor = null;
                                board.board[tempI][tempJ].pieceNumber = 0;

                                int[][] previousGame = new int[8][8];
                                for (int i = 0; i < 8; i++) {
                                    for (int j = 0; j < 8; j++) {
                                        previousGame[i][j] = board.board[i][j].pieceNumber;
                                    }
                                }

                                PrintStream previous_game = null;
                                PrintStream previous_b_outPieces = null;
                                PrintStream previous_w_outPieces = null;
                                PrintStream previous_turn = null;
                                try {
                                    previous_game = new PrintStream(new FileOutputStream("previousGame.txt",false));
                                    previous_b_outPieces = new PrintStream(new FileOutputStream("previousBlackOutPieces.txt", false));
                                    previous_w_outPieces = new PrintStream(new FileOutputStream("previousWhiteOutPieces.txt", false));
                                    previous_turn = new PrintStream(new FileOutputStream("previousTurn.txt",false));
                                } catch (FileNotFoundException ex) {
                                    ex.printStackTrace();
                                }

                                for (int i = 0; i < 8; i++) {
                                    for (int j = 0; j < 8; j++) {
                                        previous_game.print(previousGame[i][j] + " ");
                                    }
                                    previous_game.println();
                                }

                                for (PieceKind pieceKind : tempOutPiecesBlack) {
                                    previous_b_outPieces.println(pieceKind);
                                }

                                for (PieceKind pieceKind : tempOutPiecesWhite) {
                                    previous_w_outPieces.println(pieceKind);
                                }

                                previous_turn.print(is_turn);

                                if (board.board[finalI][finalJ].piece.pieceKind == PieceKind.PAWN){
                                    ArrayList<ArrayList<int[]>> pawnPosition = new ArrayList<>();
                                    pawnPosition.add(new ArrayList<>());
                                    pawnPosition.get(0).add(new int[]{0, 1});
                                    board.board[finalI][finalJ].piece.pieceMove.put(PieceKind.PAWN, pawnPosition);
                                }
                                is_turn = ! is_turn;
                            }

                            if (board.board[finalI][finalJ].possibleMove)
                                isMove = true;
                            else
                                isMove = false;
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    board.board[i][j].possibleMove = false;
                                }
                            }
                        }
                        PrintStream previous_isAI = null;
                        PrintStream Is_ai_white = null;
                        try {
                            previous_isAI = new PrintStream(new FileOutputStream("previousIsAI.txt",false));
                            Is_ai_white = new PrintStream(new FileOutputStream("IsAIwhite.txt"),false);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        previous_isAI.print(ai_turn);
                        Is_ai_white.print(ai_isWhite);

                        try {
                            if (ai_turn && !is_select && ai_isWhite && isMove) {
                                ArrayList<PieceKind> b_outPieceAI = new ArrayList<>();
                                int c_1 = 0,c_2 = 0,c_3 = 0,c_4 = 0,c_5 = 0,c_6 = 0;
                                for (int i = 0; i < 8; i ++) {
                                    for (int j = 0; j < 8; j++) {
                                        if (board.board[i][j].pieceNumber == -1) {
                                            c_1++;
                                        } else if (board.board[i][j].pieceNumber == -2) {
                                            c_2++;
                                        }else if (board.board[i][j].pieceNumber == -3) {
                                            c_3++;
                                        }else if (board.board[i][j].pieceNumber == -4) {
                                            c_4++;
                                        }else if (board.board[i][j].pieceNumber == -5) {
                                            c_5++;
                                        }else if (board.board[i][j].pieceNumber == -6) {
                                            c_6++;
                                        }
                                    }
                                }

                                if (c_1 != 1) {
                                    frame.dispose();
                                    White_Win();
                                    return;
                                } else if (c_2 != bc_2) {
                                    b_outPieceAI.add(PieceKind.QUEEN);
                                } else if (c_3 != bc_3) {
                                    b_outPieceAI.add(PieceKind.ROOK);
                                } else if (c_4 != bc_4) {
                                    b_outPieceAI.add(PieceKind.BISHOP);
                                } else if (c_5 != bc_5) {
                                    b_outPieceAI.add(PieceKind.KNIGHT);
                                } else if (c_6 != bc_6) {
                                    b_outPieceAI.add(PieceKind.PAWN);
                                }
                                bc_1 = c_1; bc_2 = c_2; bc_3 = c_3;
                                bc_4 = c_4; bc_5 = c_5; bc_6 = c_6;

                                for (PieceKind kind : b_outPieceAI) {
                                    tempOutPiecesBlack.add(kind);
                                    b_idX ++;
                                }
                                showBoard(changeToBoard(nextMove(changeToInt(board),true),board));
                                is_turn = false;
                                ai_pawnMove = true;
                                if (ai_pawnMove){
                                    if (board.board[finalI][finalJ].piece.pieceKind == PieceKind.PAWN){
                                        ArrayList<ArrayList<int[]>> pawnPosition = new ArrayList<>();
                                        pawnPosition.add(new ArrayList<>());
                                        pawnPosition.get(0).add(new int[]{0, 1});
                                        board.board[finalI][finalJ].piece.pieceMove.put(PieceKind.PAWN, pawnPosition);
                                    }
                                }
                            }else if (ai_turn && !is_select && !ai_isWhite && isMove){
                                ArrayList<PieceKind> w_outPieceAI = new ArrayList<>();
                                int c_1 = 0,c_2 = 0,c_3 = 0,c_4 = 0,c_5 = 0,c_6 = 0;
                                for (int i = 0; i < 8; i ++) {
                                    for (int j = 0; j < 8; j++) {
                                        if (board.board[i][j].pieceNumber == 1) {
                                            c_1++;
                                        } else if (board.board[i][j].pieceNumber == 2) {
                                            c_2++;
                                        }else if (board.board[i][j].pieceNumber == 3) {
                                            c_3++;
                                        }else if (board.board[i][j].pieceNumber == 4) {
                                            c_4++;
                                        }else if (board.board[i][j].pieceNumber == 5) {
                                            c_5++;
                                        }else if (board.board[i][j].pieceNumber == 6) {
                                            c_6++;
                                        }
                                    }
                                }
                                if (c_1 != 1) {
                                    frame.dispose();
                                    Black_Win();
                                    return;
                                } else if (c_2 != wc_2) {
                                    w_outPieceAI.add(PieceKind.QUEEN);
                                } else if (c_3 != wc_3) {
                                    w_outPieceAI.add(PieceKind.ROOK);
                                } else if (c_4 != wc_4) {
                                    w_outPieceAI.add(PieceKind.BISHOP);
                                } else if (c_5 != wc_5) {
                                    w_outPieceAI.add(PieceKind.KNIGHT);
                                } else if (c_6 != wc_6) {
                                    w_outPieceAI.add(PieceKind.PAWN);
                                }
                                wc_1 = c_1; wc_2 = c_2; wc_3 = c_3;
                                wc_4 = c_4; wc_5 = c_5; wc_6 = c_6;

                                for (PieceKind kind : w_outPieceAI) {
                                    tempOutPiecesWhite.add(kind);
                                    w_idX ++;
                                }
                                showBoard(changeToBoard(nextMove(changeToInt(board),false),board));
                                ai_pawnMove = true;
                                if (ai_pawnMove){
                                    if (board.board[finalI][finalJ].piece.pieceKind == PieceKind.PAWN){
                                        ArrayList<ArrayList<int[]>> pawnPosition = new ArrayList<>();
                                        pawnPosition.add(new ArrayList<>());
                                        pawnPosition.get(0).add(new int[]{0, 1});
                                        board.board[finalI][finalJ].piece.pieceMove.put(PieceKind.PAWN, pawnPosition);
                                    }
                                }
                                is_turn = true;



                            } else {
                                showBoard(board);
                            }
                        } catch (IOException | InvalidAttributeValueException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        }

        /**
         * set a layout and panel and add them in a main frame
         */
        center_panel.setLayout(new GridLayout(8,8));
        center_panel.setPreferredSize(new Dimension(700,700));
        center_panel.setVisible(true);

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(header_panel);
        panel.add(center_panel);
        panel.add(footer_panel);
        frame.add(panel);
        frame.setSize(700,800);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * this method set a pieces in a buttons
     */
    public void setIcon(int i, int j, JButton[][] gBoard, Board board) throws IOException {
        if (board.board[i][j].piece.pieceKind == PieceKind.values()[5]  && board.board[i][j].piece.pieceColor == PieceColor.BLACK) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("b_pawn.png")));
            Image im2 = img.getScaledInstance(70,70,50);
            gBoard[i][j].setIcon(new ImageIcon(im2));
        } else if (board.board[i][j].piece.pieceKind == PieceKind.values()[0] && board.board[i][j].piece.pieceColor == PieceColor.BLACK) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("b_king.png")));
            Image im2 = img.getScaledInstance(70,70,50);
            gBoard[i][j].setIcon(new ImageIcon(im2));
        } else if (board.board[i][j].piece.pieceKind == PieceKind.values()[5]  && board.board[i][j].piece.pieceColor == PieceColor.WHITE) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("w_pawn.png")));
            Image im2 = img.getScaledInstance(70,70,50);
            gBoard[i][j].setIcon(new ImageIcon(im2));
        } else if (board.board[i][j].piece.pieceKind == PieceKind.values()[0] && board.board[i][j].piece.pieceColor == PieceColor.WHITE) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("w_king.png")));
            Image im2 = img.getScaledInstance(70,70,50);
            gBoard[i][j].setIcon(new ImageIcon(im2));
        } else if (board.board[i][j].piece.pieceKind == PieceKind.values()[1]  && board.board[i][j].piece.pieceColor == PieceColor.BLACK) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("b_queen.png")));
            Image im2 = img.getScaledInstance(70,70,50);
            gBoard[i][j].setIcon(new ImageIcon(im2));
        } else if (board.board[i][j].piece.pieceKind == PieceKind.values()[1]  && board.board[i][j].piece.pieceColor == PieceColor.WHITE) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("w_queen.png")));
            Image im2 = img.getScaledInstance(70,70,50);
            gBoard[i][j].setIcon(new ImageIcon(im2));
        } else if (board.board[i][j].piece.pieceKind == PieceKind.values()[2]  && board.board[i][j].piece.pieceColor == PieceColor.BLACK) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("b_rook.png")));
            Image im2 = img.getScaledInstance(70,70,50);
            gBoard[i][j].setIcon(new ImageIcon(im2));
        } else if (board.board[i][j].piece.pieceKind == PieceKind.values()[2]  && board.board[i][j].piece.pieceColor == PieceColor.WHITE) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("w_rook.png")));
            Image im2 = img.getScaledInstance(70,70,50);
            gBoard[i][j].setIcon(new ImageIcon(im2));
        } else if (board.board[i][j].piece.pieceKind == PieceKind.values()[3]  && board.board[i][j].piece.pieceColor == PieceColor.BLACK) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("b_bishop.png")));
            Image im2 = img.getScaledInstance(70,70,50);
            gBoard[i][j].setIcon(new ImageIcon(im2));
        } else if (board.board[i][j].piece.pieceKind == PieceKind.values()[3]  && board.board[i][j].piece.pieceColor == PieceColor.WHITE) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("w_bishop.png")));
            Image im2 = img.getScaledInstance(70,70,50);
            gBoard[i][j].setIcon(new ImageIcon(im2));
        } else if (board.board[i][j].piece.pieceKind == PieceKind.values()[4]  && board.board[i][j].piece.pieceColor == PieceColor.BLACK) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("b_knight.png")));
            Image im2 = img.getScaledInstance(70,70,50);
            gBoard[i][j].setIcon(new ImageIcon(im2));
        } else if (board.board[i][j].piece.pieceKind == PieceKind.values()[4]  && board.board[i][j].piece.pieceColor == PieceColor.WHITE) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("w_knight.png")));
            Image im2 = img.getScaledInstance(70,70,50);
            gBoard[i][j].setIcon(new ImageIcon(im2));
        }
    }

    /**
     * these methods set a pieces in a header and footer
     */
    public void setBlackOutPieceIcon (int i, JButton[] b, ArrayList<PieceKind> outPiecesBlack) throws IOException {
        if (outPiecesBlack.get(i) == PieceKind.PAWN){
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("b_pawn.png")));
            Image im2 = img.getScaledInstance(30,30,10);
            b[i].setIcon(new ImageIcon(im2));
        } else if (outPiecesBlack.get(i) == PieceKind.KING) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("b_king.png")));
            Image im2 = img.getScaledInstance(30,30,10);
            b[i].setIcon(new ImageIcon(im2));
        } else if (outPiecesBlack.get(i) == PieceKind.QUEEN) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("b_queen.png")));
            Image im2 = img.getScaledInstance(30,30,10);
            b[i].setIcon(new ImageIcon(im2));
        } else if (outPiecesBlack.get(i) == PieceKind.KNIGHT) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("b_knight.png")));
            Image im2 = img.getScaledInstance(30,30,10);
            b[i].setIcon(new ImageIcon(im2));
        } else if (outPiecesBlack.get(i) == PieceKind.ROOK) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("b_rook.png")));
            Image im2 = img.getScaledInstance(30,30,10);
            b[i].setIcon(new ImageIcon(im2));
        } else if (outPiecesBlack.get(i) == PieceKind.BISHOP) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("b_bishop.png")));
            Image im2 = img.getScaledInstance(30,30,10);
            b[i].setIcon(new ImageIcon(im2));
        }
    }

    public void setWhiteOutPieceIcon (int i, JButton[] b, ArrayList<PieceKind> outPiecesWhite) throws IOException {
        if (outPiecesWhite.get(i) == PieceKind.PAWN){
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("w_pawn.png")));
            Image im2 = img.getScaledInstance(30,30,10);
            b[i].setIcon(new ImageIcon(im2));
        } else if (outPiecesWhite.get(i) == PieceKind.KING) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("w_king.png")));
            Image im2 = img.getScaledInstance(30,30,10);
            b[i].setIcon(new ImageIcon(im2));
        } else if (outPiecesWhite.get(i) == PieceKind.QUEEN) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("w_queen.png")));
            Image im2 = img.getScaledInstance(30,30,10);
            b[i].setIcon(new ImageIcon(im2));
        } else if (outPiecesWhite.get(i) == PieceKind.KNIGHT) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("w_knight.png")));
            Image im2 = img.getScaledInstance(30,30,10);
            b[i].setIcon(new ImageIcon(im2));
        } else if (outPiecesWhite.get(i) == PieceKind.ROOK) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("w_rook.png")));
            Image im2 = img.getScaledInstance(30,30,10);
            b[i].setIcon(new ImageIcon(im2));
        } else if (outPiecesWhite.get(i) == PieceKind.BISHOP) {
            Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("w_bishop.png")));
            Image im2 = img.getScaledInstance(30,30,10);
            b[i].setIcon(new ImageIcon(im2));
        }
    }

    /**
     * these methods call when the king is out and the game is over
     * these methods have a button to go back to the menu page
     */
    public static void White_Win(){
        JFrame endFrame = new JFrame();
        endFrame.setSize(new Dimension(500, 350));
        endFrame.setResizable(false);
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setLayout(null);
        panel.setBackground(new Color(255,238,236));

        JLabel label = new JLabel();
        label.setText("White is a winner");
        label.setBounds(195,80,150,50);

        JButton button = new JButton();
        button.setText("back to menu");
        button.setBounds(175,150,150,50);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endFrame.dispose();
                menu menu = new menu();
                try {
                    menu.ShowMenu();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return;
            }
        });

        panel.add(button);
        panel.add(label);
        endFrame.add(panel);
        endFrame.setVisible(true);
    }

    public static void Black_Win(){
        JFrame endFrame = new JFrame();
        endFrame.setSize(new Dimension(500, 350));
        endFrame.setResizable(false);
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setLayout(null);
        panel.setBackground(new Color(255,238,236));

        JLabel label = new JLabel();
        label.setText("Black is a winner");
        label.setBounds(195,80,150,50);

        JButton button = new JButton();
        button.setText("back to menu");
        button.setBounds(175,150,150,50);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endFrame.dispose();
                menu menu = new menu();
                try {
                    menu.ShowMenu();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return;
            }
        });

        panel.add(button);
        panel.add(label);
        endFrame.add(panel);
        endFrame.setVisible(true);
    }

    /**
     * these two method change the data type
     * use for AIPlayer
     */
    public int[][] changeToInt (Board board){
        int[][] b = new int[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                b[i][j] = board.board[i][j].pieceNumber;
            }
        }
        return b;
    }

    public Board changeToBoard (int[][] matrix , Board board) throws InvalidAttributeValueException, FileNotFoundException {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.board[i][j] = new Square(i, j, matrix[i][j]);
            }
        }
        return board;
    }
}