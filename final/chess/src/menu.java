import javax.imageio.ImageIO;
import javax.management.InvalidAttributeValueException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class menu {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();

    public void ShowMenu () throws IOException {
        frame.setSize(750,750);
        frame.setTitle("Play Chess");
        frame.setResizable(false);

        JLabel label = new JLabel();
        ImageIcon icon = new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("photo-output.JPG")));
        icon.getImage().getScaledInstance(750,750,750);
        label.setIcon(icon);
        label.setOpaque(true);

        panel.setOpaque(true);

        JLabel l2 = new JLabel();
        l2.setSize(750,750);
        l2.setOpaque(false);
        l2.setLayout(null);

        JButton newGameB = new JButton();
        newGameB.setBounds(410,350,150,75);
        newGameB.setText("New Game");

        newGameB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Page_2();
                return;
            }
        });

        JButton previousGame = new JButton();
        previousGame.setBounds(410,450,150,75);
        previousGame.setText("Previous Game");
        previousGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

                int[][] matrix = new int[8][8];
                try {
                    Scanner output = new Scanner(new File("previousGame.txt"));

                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            while (output.hasNextLine()) {
                                matrix[i][j] = output.nextInt();
                                break;
                            }
                        }
                    }

                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                Board board = null;

                try {
                    board = new Board(matrix);

                } catch (InvalidAttributeValueException | FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                GraphicBoard graphicBoard = new GraphicBoard();
                try {
                    graphicBoard.showBoard(board);
                } catch (IOException | InvalidAttributeValueException ex) {
                    ex.printStackTrace();
                }
                boolean tempTurn = false;
                try {
                    Scanner turnout = new Scanner(new File("previousTurn.txt"));
                    boolean this_turn = turnout.nextBoolean();
                    graphicBoard.is_turn = !this_turn;
                    tempTurn = !this_turn;
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }

                try {
                    Scanner is_ai = new Scanner(new File("previousIsAI.txt"));
                    Scanner is_ai_white = new Scanner(new File("IsAIwhite.txt"));

                    boolean isAI = is_ai.nextBoolean();
                    boolean isAIWhite = is_ai_white.nextBoolean();
                    graphicBoard.ai_isWhite = isAIWhite;
                    graphicBoard.ai_turn = isAI;
                    if (isAI) {
                        graphicBoard.is_turn = !tempTurn;
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                try{
                    Scanner out1 = new Scanner(new File("previousBlackOutPieces.txt"));
                    Scanner out2 = new Scanner(new File("previousWhiteOutPieces.txt"));

                    while (out1.hasNextLine()) {
                        String piece = out1.nextLine();
                        if (piece.equals("PAWN")) {
                            graphicBoard.tempOutPiecesBlack.add(PieceKind.PAWN);
                        } else if (piece.equals("QUEEN")) {
                            graphicBoard.tempOutPiecesBlack.add(PieceKind.QUEEN);
                        } else if (piece.equals("ROOK")) {
                            graphicBoard.tempOutPiecesBlack.add(PieceKind.ROOK);
                        } else if (piece.equals("KNIGHT")) {
                            graphicBoard.tempOutPiecesBlack.add(PieceKind.KNIGHT);
                        } else if (piece.equals("BISHOP")) {
                            graphicBoard.tempOutPiecesBlack.add(PieceKind.BISHOP);
                        } else if (piece.equals("KING")) {
                            graphicBoard.tempOutPiecesBlack.add(PieceKind.KING);
                        }
                        graphicBoard.b_idX ++;
                    }

                    while (out2.hasNextLine()) {
                        String piece = out2.nextLine();
                        if (piece.equals("PAWN")) {
                            graphicBoard.tempOutPiecesWhite.add(PieceKind.PAWN);
                        } else if (piece.equals("QUEEN")) {
                            graphicBoard.tempOutPiecesWhite.add(PieceKind.QUEEN);
                        } else if (piece.equals("ROOK")) {
                            graphicBoard.tempOutPiecesWhite.add(PieceKind.ROOK);
                        } else if (piece.equals("KNIGHT")) {
                            graphicBoard.tempOutPiecesWhite.add(PieceKind.KNIGHT);
                        } else if (piece.equals("BISHOP")) {
                            graphicBoard.tempOutPiecesWhite.add(PieceKind.BISHOP);
                        } else if (piece.equals("KING")) {
                            graphicBoard.tempOutPiecesWhite.add(PieceKind.KING);
                        }
                        graphicBoard.w_idX ++;
                    }

                    if (graphicBoard.tempOutPiecesBlack.contains(PieceKind.KING)) {
                        frame.dispose();
                        graphicBoard.frame.dispose();
                        Page_4();
                        return;
                    }

                    if (graphicBoard.tempOutPiecesWhite.contains(PieceKind.KING)) {
                        frame.dispose();
                        graphicBoard.frame.dispose();
                        Page_4();
                        return;
                    }


                }catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }

                return;
            }
        });

        l2.add(newGameB);
        l2.add(previousGame);
        label.add(l2);
        panel.add(label);
        frame.add(panel);
        frame.setVisible(true);
    }

    public void Page_2() {
        JFrame f = new JFrame();
        f.setSize(750,750);
        f.setTitle("Play Chess");
        f.setResizable(false);

        JPanel p = new JPanel();

        JLabel label = new JLabel();
        ImageIcon icon = new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("photo-output.JPG")));
        icon.getImage().getScaledInstance(750,750,750);
        label.setIcon(icon);
        label.setOpaque(true);

        p.setOpaque(true);

        JLabel l2 = new JLabel();
        l2.setSize(750,750);
        l2.setOpaque(false);
        l2.setLayout(null);

        JButton onePlayerB = new JButton();
        onePlayerB.setBounds(410,350,150,75);
        onePlayerB.setText("One Player");

        onePlayerB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                f.dispose();
                Page_3();
                return;
            }
        });

        JButton twoPlayerB = new JButton();
        twoPlayerB.setBounds(410,450,150,75);
        twoPlayerB.setText("Two Player");
        twoPlayerB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                f.dispose();
                int[][] matrix =  {{-3,-5,-4,-2,-1,-4,-5,-3},{-6,-6,-6,-6,-6,-6,-6,-6}
                        ,{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}
                        ,{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}
                        ,{6,6,6,6,6,6,6,6},{3,5,4,2,1,4,5,3}};
                Board board = null;
                try {
                    board = new Board(matrix);
                } catch (InvalidAttributeValueException | FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                GraphicBoard graphicBoard = null;
                graphicBoard = new GraphicBoard();
                try {
                    graphicBoard.showBoard(board);
                } catch (IOException | InvalidAttributeValueException ex) {
                    ex.printStackTrace();
                }
                return;
            }
        });

        l2.add(onePlayerB);
        l2.add(twoPlayerB);
        label.add(l2);
        p.add(label);
        f.add(p);
        f.setVisible(true);
    }

    public void Page_3 () {
        JFrame f1 = new JFrame();
        f1.setSize(750,750);
        f1.setTitle("Play Chess");
        f1.setResizable(false);

        JPanel p1 = new JPanel();

        JLabel l1 = new JLabel();
        ImageIcon icon = new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("photo-output.JPG")));
        icon.getImage().getScaledInstance(750,750,750);
        l1.setIcon(icon);
        l1.setOpaque(true);

        p1.setOpaque(true);

        JLabel l2 = new JLabel();
        l2.setSize(750,750);
        l2.setOpaque(false);
        l2.setLayout(null);

        JButton white = new JButton();
        white.setBounds(410,350,150,75);
        white.setText("White");

        white.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f1.dispose();
                frame.dispose();
                int[][] matrix =  {{-3,-5,-4,-2,-1,-4,-5,-3},{-6,-6,-6,-6,-6,-6,-6,-6}
                        ,{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}
                        ,{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}
                        ,{6,6,6,6,6,6,6,6},{3,5,4,2,1,4,5,3}};
                AIPlayer aiPlayer = new AIPlayer();
                Board b = null;
                GraphicBoard gb = new GraphicBoard();

                gb.ai_turn = true;
                gb.ai_isWhite = false;
                try {
                    b = new Board(matrix);
                    gb.showBoard(b);
                } catch (InvalidAttributeValueException | IOException ex) {
                    ex.printStackTrace();
                }
                return;
            }
        });

        JButton black = new JButton();
        black.setBounds(410,450,150,75);
        black.setText("Black");
        black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f1.dispose();
                frame.dispose();
                int[][] matrix =  {{-3,-5,-4,-2,-1,-4,-5,-3},{-6,-6,-6,-6,-6,-6,-6,-6}
                        ,{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}
                        ,{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}
                        ,{6,6,6,6,6,6,6,6},{3,5,4,2,1,4,5,3}};
                AIPlayer aiPlayer = new AIPlayer();
                Board b = null;
                GraphicBoard gb = new GraphicBoard();
                gb.ai_turn = true;
                try {
                    b = new Board(aiPlayer.nextMove(matrix,true));
                    gb.showBoard(b);
                    gb.is_turn = false;
                } catch (InvalidAttributeValueException | IOException ex) {
                    ex.printStackTrace();
                }
                return;
            }
        });

        l2.add(white);
        l2.add(black);
        l1.add(l2);
        p1.add(l1);
        f1.add(p1);
        f1.setVisible(true);
    }

    public void Page_4 () {
        JFrame endFrame = new JFrame();
        endFrame.setSize(new Dimension(500, 350));
        endFrame.setResizable(false);
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setLayout(null);
        panel.setBackground(new Color(255,238,236));

        JLabel label = new JLabel();
        label.setText("NO Games Saved!");
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
}