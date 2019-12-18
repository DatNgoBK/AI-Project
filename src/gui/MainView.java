package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ai.AlphaBetaPrunning;
import caro.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class MainView extends JPanel {

    public static final int SIZEX = 20;
    public static final int SIZEY = 20;
    private static final int SQUAD = 30;
    private GameState gameState;
    private int victory;
    JPanel pnMain;
    JButton btnNewGame, btnUndo, btnExit;
    JComboBox<String> cbo, cboTime;
    DefaultComboBoxModel<String> dcb, dcbTime;
    JLabel lblChoose, lblTimer;
    JLabel[] x, y;
    Graphics g;
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    boolean inProcessGame = false;

    public MainView() {
        pnMain = new JPanel();
        pnMain.setLayout(null);
        pnMain.setBackground(new Color(244, 244, 244));
        x = new JLabel[20];
        y = new JLabel[20];
        for (int i = 0; i < 20; i++) {
            x[i] = new JLabel(i + "");
            x[i].setBounds(30 + 30 * i, 40, 25, 25);
            y[i] = new JLabel(i + "");
            y[i].setBounds(2, 62 + 30 * i, 25, 25);
            pnMain.add(x[i]);
            pnMain.add(y[i]);
        }

        setBackground(new Color(244, 244, 244));
        btnUndo = new JButton("Undo");
        lblChoose = new JLabel("Start :");
        lblTimer = new JLabel("A");
        dcb = new DefaultComboBoxModel<String>();
        dcb.addElement("AI");
        dcb.addElement("Human");
        cbo = new JComboBox<>();
        cboTime = new JComboBox<>();
        dcbTime = new DefaultComboBoxModel<>();
        dcbTime.addElement("Easy");
        dcbTime.addElement("Normal");
        dcbTime.addElement("Hard");
        cboTime.setModel(dcbTime);
        cbo.setModel(dcb);
        cboTime.setBorder(border);
        cbo.setBorder(border);
        this.setLayout(null);
        btnNewGame = new JButton("New Game");
        btnExit = new JButton("Exit");
        btnExit.setBounds(520, 15, 100, 25);
        btnNewGame.setBounds(300, 15, 100, 25);
        cbo.setBounds(80, 15, 100, 25);
        lblChoose.setBounds(20, 15, 120, 25);
        btnUndo.setBounds(410, 15, 100, 25);
        cboTime.setBounds(190, 15, 100, 25);
        this.setBounds(20, 60, 601, 601);

        btnNewGame.setBackground(new Color(235, 235, 235));
        btnUndo.setBackground(new Color(235, 235, 235));
        btnExit.setBackground(new Color(235, 235, 235));
        btnNewGame.setBorder(border);
        btnExit.setBorder(border);
        btnUndo.setBorder(border);
        pnMain.add(cboTime);
        pnMain.add(btnExit);
        pnMain.add(btnUndo);
        pnMain.add(lblChoose);
        pnMain.add(cbo);
        pnMain.add(btnNewGame);
        pnMain.add(lblTimer);
        pnMain.add(this);
        victory = -1;
        addEvent();
    }

    public void addEvent() {

        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!inProcessGame) {
                    btnNewGame.setText("Stop Game");
                    if (cbo.getSelectedItem().toString().equals("AI")) {
                        gameState = new GameState(Player.BLACK);
                        gameState.addSquare(new Square(SIZEX / 2 - 1, SIZEY / 2 - 1));
                        layoutChildren();
                    } else {
                        gameState = new GameState(Player.WHITE);
                        layoutChildren();
                    }

                    if (cboTime.getSelectedItem().toString().equals("Easy")) {
                        AlphaBetaPrunning.timeMax = (long) 1590999800f;
                    }
                    if (cboTime.getSelectedItem().toString().equals("Normal")) {
                        AlphaBetaPrunning.timeMax = (long) 2590999800f;
                    }
                    if (cboTime.getSelectedItem().toString().equals("Hard")) {
                        AlphaBetaPrunning.timeMax = (long) 4990999800f;
                    }
                    victory = -1;
                    inProcessGame = true;
                } else {
                    btnNewGame.setText("New Game");
                    gameState = null;
                    layoutChildren();
                    inProcessGame = false;
                }
            }
        });

        btnNewGame.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btnNewGame.setBackground(new Color(243, 243, 243));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnNewGame.setBackground(new Color(235, 235, 235));
            }
        });

        btnUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameState != null && gameState.getMove().size() >= 2) {
                    int temp2 = 2;
                    while (temp2 != 0) {
                        temp2--;
                        gameState.removeSquare(gameState.getLastMove().square);
                    }
                    victory = -1;
                } else if (gameState == null) {
                    JOptionPane.showMessageDialog(null, "Init Game?");
                }
                layoutChildren();
            }
        });

        btnUndo.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btnNewGame.setBackground(new Color(243, 243, 243));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnNewGame.setBackground(new Color(235, 235, 235));
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btnExit.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btnNewGame.setBackground(new Color(243, 243, 243));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnNewGame.setBackground(new Color(235, 235, 235));
            }
        });

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int coordX = (e.getX()) / SQUAD;
                int coordY = (e.getY()) / SQUAD;
                if (coordX >= 0 && coordY >= 0 && gameState != null) {
                    if (gameState.getCurrentPlayer() == Player.WHITE && e.getButton() == 1) {
                        Piece[][] board = gameState.getBoard();
                        if (board[coordX][coordY].index == 0) {
                            try {
                                gameState.addSquare(new Square(coordX, coordY));
                                layoutChildren();
                                Pair pair = AlphaBetaPrunning.search(gameState);
                                if (pair.getSquare() == null) {
                                    int status = gameState.terminal();
                                    if (status == 2) {
                                        victory = 2;
                                    } else {
                                        victory = 0;
                                    }
                                } else {
                                    gameState.addSquare(pair.getSquare());
                                    layoutChildren();
                                    if (gameState.terminal() == 1) {
                                        victory = 1;
                                    }
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Failed");
                }
                switch (victory) {
                    case 1:
                        JOptionPane.showMessageDialog(null, "AI is Winner!");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(null, "Humman is Winner!");
                        break;
                    case 0:
                        JOptionPane.showMessageDialog(null, "Draw!");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBG(g);
    }

    public void paintBG(Graphics g) {

        for (int i = 0; i <= SIZEX; i++) {
            g.drawLine(SQUAD * i, 0, SQUAD * i, SQUAD * SIZEY);
        }
        for (int i = 0; i <= SIZEY; i++) {
            g.drawLine(0, SQUAD * i, SQUAD * SIZEX, SQUAD * i);
        }
    }

    public void layoutChildren() {
        Graphics g = this.getGraphics();
        super.paintComponent(g);
        paintBG(g);
        if (gameState != null) {
            ArrayList<Piece> move = gameState.getMove();
            for (int i = 0; i < move.size(); i++) {
                Piece piece = move.get(i);
                Player player = null;
                if (piece.index == 1) {
                    player = Player.BLACK;
                }
                if (piece.index == 2) {
                    player = Player.WHITE;
                }
                if (i == move.size() - 1) {
                    g.drawImage(player.getLastImage(), SQUAD * piece.square.coordX + 1,
                            SQUAD * piece.square.coordY + 1, this);
                } else {
                    g.drawImage(player.getImg(), SQUAD * piece.square.coordX + 1,
                            SQUAD * piece.square.coordY + 1, this);
                }

            }
            Font defaultFont = g.getFont();
        }
    }

    public static void main(String[] ags) {
        System.out.println("Play");
        MainView mv = new MainView();
        JFrame frame = new JFrame();
        frame.add(mv.pnMain);
        frame.setTitle("Gomoku Game");
        frame.setSize(646, 705);
        frame.setBackground(Color.GRAY);
        frame.setFocusable(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
