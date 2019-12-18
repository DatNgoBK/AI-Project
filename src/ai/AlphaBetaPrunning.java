package ai;

import java.util.ArrayList;
import java.util.Collections;

import caro.GameState;
import caro.Pair;
import caro.Player;
import caro.Square;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import util.Evaluator;

public class AlphaBetaPrunning {

    private static ArrayList<Pair> lastPair = new ArrayList();
    private static Evaluator evaluator = Evaluator.getInstance();
    public static int cnt = 0;
    public static long timeMax = (long) 3990999800f;
    public static long timeStart = 0;
    private static int MAXDEPTH;

    public static Pair search(GameState game) throws IOException {
        timeStart = System.nanoTime();
        lastPair.clear();
        int temp1 = game.getMove().size();
        Pair pair = null;
        int i;

        for (i = 2; i <= 10; i = i + 2) {
            MAXDEPTH = i;
            try {
                if (game.getCurrentPlayer() == Player.BLACK) {
                    pair = maxValue(game, Integer.MIN_VALUE, Integer.MAX_VALUE, i);
                    if (pair != null) {
                        lastPair.add(pair);
                    }
                } else {
                    pair = minValue(game, Integer.MIN_VALUE, Integer.MAX_VALUE, i);
                }

            } catch (InterruptedException e) {
                int temp2 = game.getMove().size() - temp1;
                while (temp2 != 0) {
                    temp2--;
                    game.removeSquare(game.getLastMove().square);
                }
            }
        }
        
        
         FileWriter file = new FileWriter("test.txt", true);
        for (int j = 0; j < lastPair.size(); j++) {
            file.append(lastPair.get(j).getSquare().coordX +"-"+ lastPair.get(j).getSquare().coordY+" "+lastPair.get(j).getValue() + " "+ "Maxdepth = "+ MAXDEPTH);
            file.append("\r\n");
        }
        file.append("-------------------------------------");
        file.append("\r\n");
        file.close();

        if (!lastPair.isEmpty()) {
            return lastPair.get(lastPair.size() - 1);
        } else {
            return new Pair(null, 0);
        }

    }

    public static Pair maxValue(GameState game, int alpha, int beta, int depth) throws InterruptedException, IOException {
        if (System.nanoTime() - timeStart > timeMax) {
            throw new InterruptedException();
        }
        int value = Integer.MIN_VALUE;
        if (game.terminal() != 0 || depth == 0 || (depth != MAXDEPTH && game.isHaveDoubleThreat() != 0)) {
            return new Pair(null, Evaluator.evaluateState(game, depth));
        }
        List<Square> successors = game.getSuccessors();
        ArrayList<Pair> successorSort = new ArrayList<Pair>();
        for (Square square : successors) {
            game.addSquare(square);
            int f = Evaluator.evaluateField(game, square.coordX, square.coordY, game.getCurrentPlayer().getOpponent().getHashValue());
            successorSort.add(new Pair(square, f));
            game.removeSquare(square);
        }

        Collections.sort(successorSort);

        FileWriter file = new FileWriter("test1.txt", true);
        for (int j = 0; j < successorSort.size(); j++) {
            file.append(successorSort.get(j).getSquare().coordX +"-"+ successorSort.get(j).getSquare().coordY+" "+successorSort.get(j).getValue() + " "+ "Maxdepth = "+ MAXDEPTH);
            file.append("\r\n");
        }
        file.append("-------------------------------------");
        file.append("\r\n");
        file.close();
        Square bestMove = null;
        for (Pair pair : successorSort) {
            cnt++;
            Square square = pair.getSquare();
            game.addSquare(square);
            Pair tryMove = minValue(game, alpha, beta, depth - 1);
            if (value < tryMove.getValue()) {
                value = tryMove.getValue();
                bestMove = square;
            }
            if (value >= beta) {
                game.removeSquare(square);
                return new Pair(bestMove, value);
            }
            alpha = Math.max(alpha, value);
            game.removeSquare(square);
        }
        return new Pair(bestMove, value);
    }

    public static Pair minValue(GameState game, int alpha, int beta, int depth) throws InterruptedException, IOException {
        if (System.currentTimeMillis() - timeStart > timeMax) {
            throw new InterruptedException();
        }
        int value = Integer.MAX_VALUE;
        if (game.terminal() != 0 || depth == 0 || (depth != MAXDEPTH && game.isHaveDoubleThreat() != 0)) {
            return new Pair(null, Evaluator.evaluateState(game, depth));
        }
        List<Square> successors = game.getSuccessors();
        ArrayList<Pair> successorSort = new ArrayList<Pair>();
        for (Square square : successors) {
            game.addSquare(square);

            int f = Evaluator.evaluateField(game, square.coordX, square.coordY, game.getCurrentPlayer().getOpponent().getHashValue());
            successorSort.add(new Pair(square, f));
            game.removeSquare(square);
        }

        Collections.sort(successorSort);
        
        FileWriter file = new FileWriter("test2.txt", true);
        for (int j = 0; j < successorSort.size(); j++) {
            file.append(successorSort.get(j).getSquare().coordX +"-"+ successorSort.get(j).getSquare().coordY+" "+successorSort.get(j).getValue() + " "+ "Maxdepth = "+ MAXDEPTH);
            file.append("\r\n");
        }
        file.append("-------------------------------------");
        file.append("\r\n");
        file.close();
        Square bestMove = null;
        for (Pair pair : successorSort) {
            Square square = pair.getSquare();
            cnt++;
            game.addSquare(square);
            Pair tryMove = maxValue(game, alpha, beta, depth - 1);
            if (value > tryMove.getValue()) {
                value = tryMove.getValue();
                bestMove = square;
            }
            if (value <= alpha) {
                game.removeSquare(square);
                return new Pair(bestMove, value);
            }
            beta = Math.min(beta, value);
            game.removeSquare(square);
        }
        return new Pair(bestMove, value);
    }
}
