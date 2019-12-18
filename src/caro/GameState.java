package caro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * LÆ°u giá»¯ tráº¡ng thÃ¡i cá»§a trÃ² chÆ¡i reducer: Ä‘á»‘i tÆ°á»£ng ThreatUtils dÃ¹ng Ä‘á»ƒ so
 * sÃ¡nh tráº¡ng thÃ¡i bÃ n cá»� vá»›i cÃ¡c máº«u Ä‘e dá»�a vÃ  Ä‘Æ°a ra cÃ¡c nÆ°á»›c Ä‘i táº¥n cÃ´ng hoáº·c
 * phÃ²ng thá»§ tÆ°Æ¡ng á»©ng
 *
 * currentPlayer: ngÆ°á»�i chÆ¡i sáº½ Ä‘Ã¡nh nÆ°á»›c tiáº¿p theo
 *
 * move: danh sÃ¡ch cÃ¡c nÆ°á»›c Ä‘Ã£ Ä‘Ã¡nh
 *
 * board: bÃ n cá»� cá»§a trÃ² chÆ¡i
 *
 * directions: máº£ng 4 chiá»�u lÆ°u cÃ¡c Ã´ trÃªn bÃ n cá»� cÃ¹ng cÃ¡c hÃ ng xÃ³m cá»§a nÃ³ theo
 * 4 hÆ°á»›ng, má»—i hÆ°á»›ng lÆ°u 4 Ã´ hÃ ng xÃ³m, thá»ƒ hiá»‡n nhÆ° hÃ¬nh dÆ°á»›i: * * * * * * * *
 * * * * * * * * * [X] * * * * * * * * * * * * * * * *
 *
 * HÃ ng xÃ³m cá»§a 1 Ã´ Ä‘Æ°á»£c lÆ°u nhÆ° sau: [row][col][0][0-9] -> Ä�Æ°á»�ng chÃ©o tá»« trÃªn
 * trÃ¡i Ä‘áº¿n dÆ°á»›i pháº£i [row][col][1][0-9] -> Ä�Æ°á»�ng chÃ©o tá»« trÃªn pháº£i Ä‘áº¿n dÆ°á»›i
 * trÃ¡i [row][col][2][0-9] -> HÃ ng dá»�c tá»« trÃªn xuá»‘ng [row][col][3][0-9] -> HÃ ng
 * ngang tá»« trÃ¡i sang pháº£i
 *
 * @author maidoanh
 */
public class GameState {

    private final ThreatUtils reducer;
    protected Player currentPlayer;
    private ArrayList<Piece> move;
    public final Piece[][] board;
    private final Piece[][][][] directions;
    public static boolean flag;

    public GameState(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.reducer = new ThreatUtils();
        this.move = new ArrayList<Piece>();
        /*
		 * (1, 1) -> (SIZEX, SIZEY)
         */
        board = new Piece[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                board[i][j] = new Piece(new Square(i, j), 0);
            }
        }
        directions = new Piece[20][20][4][9];
        this.generateDirections(board);
    }

    /**
     * ThÃªm má»™t Ã´ vÃ o bÃ n cá»�
     *
     * @param square Ã´ Ä‘Æ°á»£c thÃªm vÃ o bÃ n cá»�
     */
    public void addSquare(Square square) {
        int coordX = square.coordX;
        int coordY = square.coordY;
        board[coordX][coordY].index = currentPlayer.getHashValue();
        move.add(new Piece(square, currentPlayer.getHashValue()));
        currentPlayer = currentPlayer.getOpponent(); // Ä‘á»•i lÆ°á»£t ngÆ°á»�i chÆ¡i 
    }

    /**
     * Rá»�i má»™t Ã´ khá»�i bÃ n cá»�
     *
     * @param square Ã´ Ä‘Æ°á»£c rá»�i khá»�i bÃ n cá»�
     */
    public void removeSquare(Square square) {
        int coordX = square.coordX;
        int coordY = square.coordY;
        board[coordX][coordY].index = 0;
        move.remove(move.size() - 1);
        currentPlayer = currentPlayer.getOpponent();
    }

    /**
     * Kiá»ƒm tra tá»�a Ä‘á»™ Ä‘áº§u vÃ o cÃ³ náº±m trong bÃ n cá»� khÃ´ng
     *
     * @param coordX tá»�a Ä‘á»™ theo chiá»�u dá»�c
     * @param coordY tá»�a Ä‘á»™ theo chiá»�u ngang
     * @return true náº¿u tá»�a Ä‘á»™ Ä‘áº§u vÃ o náº±m trong bÃ n cá»�, ngÆ°á»�i láº¡i false
     */
    public boolean isInBoard(int coordX, int coordY) {
        if (coordX < 0 || coordX >= 20) {
            return false;
        }
        if (coordY < 0 || coordY >= 20) {
            return false;
        }
        return true;
    }

    /**
     * Ä�Æ°a ra cÃ¡c nÆ°á»›c Ä‘i tiá»�m nÄƒng cho ngÆ°á»�i chÆ¡i tiáº¿p theo
     *
     * @return danh sÃ¡ch cÃ¡c nÆ°á»›c Ä‘i tiá»�m nÄƒng
     */
    public List<Square> getSuccessors() {
        int playerIndex = currentPlayer.getHashValue();
        int opponentIndex = playerIndex == 1 ? 2 : 1;

        HashSet<Square> fours = new HashSet<Square>();
        HashSet<Square> threes = new HashSet<Square>();
        HashSet<Square> opponentFours = new HashSet<Square>();
        HashSet<Square> opponentThrees = new HashSet<Square>();
        HashSet<Square> refutations = new HashSet<Square>();
        // Kiá»ƒm tra cÃ¡c má»‘i Ä‘e dá»�a vÃ  tráº£ láº¡i cÃ¡c nÆ°á»›c Ä‘i tÆ°Æ¡ng á»©ng
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                if (this.board[i][j].index == opponentIndex) {
                    opponentFours.addAll(reducer.getFours(this, board[i][j], opponentIndex));
                    opponentThrees.addAll(reducer.getThrees(this, board[i][j], opponentIndex));
                } else if (this.board[i][j].index == playerIndex) {
                    fours.addAll(reducer.getFours(this, board[i][j], playerIndex));
                    threes.addAll(reducer.getThrees(this, board[i][j], playerIndex));
                    refutations.addAll(reducer.getRefutations(this, board[i][j], playerIndex));
                }
            }
        }
        /**
         * NgÆ°á»�i chÆ¡i hiá»‡n táº¡i cÃ³ 4 Ã´ tháº³ng hÃ ng, tiáº¿p tá»¥c Ä‘Ã¡nh nÃ³
         */
        if (!fours.isEmpty()) {
            return new ArrayList<Square>(fours);

        }
        // Ä�á»‘i phÆ°Æ¡ng cÃ³ 4 Ã´ tháº³ng hÃ ng, cháº·n nÃ³
        if (!opponentFours.isEmpty()) {
            return new ArrayList<Square>(opponentFours);
        }

        if (!threes.isEmpty()) {
            return new ArrayList<Square>(threes);
        }
        /**
         * Ä�á»‘i phÆ°Æ¡ng cÃ³ 3 Ã´ tháº³ng hÃ ng liá»�n nhau vÃ  khÃ´ng bá»‹ cháº·n cáº£ 2 Ä‘áº§u CÃ¢n
         * nháº¯c cháº·n nÃ³ hay phÃ¡t triá»ƒn cÃ¡c nÆ°á»›c 4 cá»§a mÃ¬nh
         */
        if (!opponentThrees.isEmpty()) {
            opponentThrees.addAll(refutations);
            return new ArrayList<Square>(opponentThrees);
        }
        /**
         * Cáº£ mÃ¬nh vÃ  Ä‘á»‘i phÆ°Æ¡ng Ä‘á»�u khÃ´ng cÃ³ nÆ°á»›c Ä‘e dá»�a xem xÃ©t cÃ¡c nÆ°á»›c xung
         * quanh cÃ³ cÃ¡c Ã´ Ä‘Ã£ Ä‘Ã¡nh vá»›i chu vi 1
         */
        HashSet<Square> successors = new HashSet<Square>();
        boolean[][] mark = new boolean[20][20];
        for (int i = 0; i < 20; i++) {
            Arrays.fill(mark[i], false);
        }
        for (Piece piece : move) {
            int coordX = piece.square.coordX;
            int coordY = piece.square.coordY;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int dimX = coordX + i;
                    int dimY = coordY + j;
                    if (isInBoard(dimX, dimY) && board[dimX][dimY].index == 0) {
                        mark[dimX][dimY] = true;
                    }
                }
            }
        }
        for (int coordX = 0; coordX < 20; coordX++) {
            for (int coordY = 0; coordY < 20; coordY++) {
                if (mark[coordX][coordY]) {
                    successors.add(new Square(coordX, coordY));
                }
            }
        }
        flag = false;
        return new ArrayList<Square>(successors);
    }

    /**
     * Táº¡o máº£ng direction tá»« máº£ng board
     *
     * @param board báº£ng trÃ² chÆ¡i
     */
    private void generateDirections(Piece[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                directions[row][col][0][4] = board[row][col];
                directions[row][col][1][4] = board[row][col];
                directions[row][col][2][4] = board[row][col];
                directions[row][col][3][4] = board[row][col];

                for (int k = 0; k < 5; k++) {
                    // Ä�Æ°á»�ng chÃ©o 1, hÆ°á»›ng phÃ­a trÃªn bÃªn trÃ¡i
                    if (row - k >= 0 && col - k >= 0) {
                        directions[row][col][0][4 - k] = board[row - k][col - k];
                    } else {
                        directions[row][col][0][4 - k] = new Piece();
                    }

                    // Ä�Æ°á»�ng chÃ©o 1, hÆ°á»›ng phÃ­a dÆ°á»›i bÃªn pháº£i
                    if (row + k < board.length && col + k < board.length) {
                        directions[row][col][0][4 + k]
                                = board[row + k][col + k];
                    } else {
                        directions[row][col][0][4 + k] = new Piece();
                    }

                    // Ä�Æ°á»�ng chÃ©o 2, hÆ°á»›ng phÃ­a trÃªn bÃªn pháº£i
                    if (row - k >= 0 && col + k < board.length) {
                        directions[row][col][1][4 - k]
                                = board[row - k][col + k];
                    } else {
                        directions[row][col][1][4 - k] = new Piece();
                    }

                    // Ä�Æ°á»�ng chÃ©o 2, hÆ°á»›ng phÃ­a dÆ°á»›i bÃªn trÃ¡i
                    if (row + k < board.length && col - k >= 0) {
                        directions[row][col][1][4 + k]
                                = board[row + k][col - k];
                    } else {
                        directions[row][col][1][4 + k] = new Piece();
                    }

                    // HÃ ng dá»�c phÃ­a trÃªn
                    if (row - k >= 0) {
                        directions[row][col][2][4 - k]
                                = board[row - k][col];
                    } else {
                        directions[row][col][2][4 - k] = new Piece();
                    }

                    // HÃ ng dá»�c phÃ­a dÆ°á»›i
                    if (row + k < board.length) {
                        directions[row][col][2][4 + k] = board[row + k][col];
                    } else {
                        directions[row][col][2][4 + k] = new Piece();
                    }

                    // HÃ ng ngang phÃ­a trÃ¡i
                    if (col - k >= 0) {
                        directions[row][col][3][4 - k]
                                = board[row][col - k];
                    } else {
                        directions[row][col][3][4 - k] = new Piece();
                    }

                    // HÃ ng ngang phÃ­a bÃªn pháº£i
                    if (col + k < board.length) {
                        directions[row][col][3][4 + k]
                                = board[row][col + k];
                    } else {
                        directions[row][col][3][4 + k] = new Piece();
                    }
                }
            }
        }
    }

    public Piece[][][][] getDirections() {
        return directions;
    }

    /**
     * Kiá»ƒm tra game Ä‘Ã£ á»Ÿ tráº¡ng thÃ¡i káº¿t thÃºc chÆ°a
     *
     * @return 1/2 náº¿u má»™t ngÆ°á»�i chÆ¡i tháº¯ng, 3 náº¿u bÃ n cá»� Ä‘áº§y, 0 náº¿u game chÆ°a
     * káº¿t thÃºc
     */
    public int terminal() {
        Piece piece = move.get(move.size() - 1);
        int row = piece.square.coordX;
        int col = piece.square.coordY;
        int lastIndex = piece.index;

        // Kiá»ƒm tra nÆ°á»›c Ä‘i cuá»‘i cÃ¹ng cÃ³ táº¡o thÃ nh 5 Ã´ tháº³ng hÃ ng cá»§a 1 ngÆ°á»�i chÆ¡i hay khÃ´ng
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                if (directions[row][col][i][j].index == lastIndex) {
                    int count = 0;
                    for (int k = 1; k < 5; k++) {
                        if (directions[row][col][i][j + k].index == lastIndex) {
                            count++;
                        } else {
                            break;
                        }
                    }
                    if (count == 4) {
                        return lastIndex;
                    }
                }
            }
        }

        return move.size() == board.length * board.length ? 3 : 0;
    }

    public int isHaveDoubleThreat() {
        HashSet<Integer> opponentFours = new HashSet<Integer>();
        HashSet<Integer> opponentThrees = new HashSet<Integer>();
        HashSet<Integer> threes = new HashSet<Integer>();
        HashSet<Integer> fours = new HashSet<Integer>();
        HashSet<Integer> refutations = new HashSet<Integer>();
        HashSet<Integer> opponentRefutations = new HashSet<Integer>();
        int index = currentPlayer.getHashValue();
        int opponentIndex = index == 1 ? 2 : 1;
        // Kiá»ƒm tra cÃ¡c má»‘i Ä‘e dá»�a vÃ  tráº£ láº¡i cÃ¡c nÆ°á»›c Ä‘i tÆ°Æ¡ng á»©ng
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                if (this.board[i][j].index == opponentIndex) {
                    opponentRefutations.addAll(reducer.getRefutation2(this, board[i][j], opponentIndex));
                    opponentFours.addAll(reducer.getFours2(this, board[i][j], opponentIndex));
                    opponentThrees.addAll(reducer.getThrees2(this, board[i][j], opponentIndex));
                } else if (this.board[i][j].index == index) {
                    refutations.addAll(reducer.getRefutation2(this, board[i][j], opponentIndex));
                    fours.addAll(reducer.getFours2(this, board[i][j], index));
                    threes.addAll(reducer.getThrees2(this, board[i][j], index));
                }
            }
        }
        if (!opponentFours.isEmpty()) {
            if (opponentFours.size() > 1) {
                return opponentIndex;
            }
            int direction = (Integer) opponentFours.toArray()[0];
            if (!opponentThrees.isEmpty()) {
                for (int i : opponentThrees) {
                    if (i != direction) {
                        return opponentIndex;
                    }
                }
            }
        }
        
        if (!fours.isEmpty()) {
            if (fours.size() > 1) {
                return index;
            }
            int direction = (Integer) fours.toArray()[0];
            if (!threes.isEmpty()) {
                for (int i : threes) {
                    if (i != direction) {
                        return index;
                    }
                }
            }
        }
        

        return 0;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ArrayList<Piece> getMove() {
        return move;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public Piece getLastMove() {
        return move.get(move.size() - 1);
    }

}
