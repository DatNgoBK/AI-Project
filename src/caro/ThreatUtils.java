package caro;

import caro.GameState;
import caro.Piece;
import caro.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Ä�Æ°á»£c sá»­ dá»¥ng Ä‘á»ƒ tÃ¬m kiáº¿m cÃ¡c má»‘i Ä‘e dá»�a trong game. CÃ¡c má»‘i Ä‘e dá»�a
 * tá»“n táº¡i xung quanh má»™t nÆ°á»›c Ä‘i vÃ  chá»‰ cáº§n kiá»ƒm tra 4 Ã´ vá»›i má»—i hÆ°á»›ng Ä‘á»ƒ
 * tÃ¬m má»‘i Ä‘e dá»�a
 */
public class ThreatUtils {

    List<ThreatPattern> REFUTATIONS;
    List<ThreatPattern> THREES;
    List<ThreatPattern> FOURS;

    public ThreatUtils() {
        this.THREES = new ArrayList<ThreatPattern>();
        this.FOURS = new ArrayList<ThreatPattern>();
        this.REFUTATIONS = new ArrayList<ThreatPattern>();

        THREES.add(new ThreatPattern(new int[] {0, 1, 1, 1, 0, 0}, new int[]
                {0, 4, 5}));
        THREES.add(new ThreatPattern(new int[] {0, 0, 1, 1, 1, 0}, new int[]
                {0, 1, 5}));
        THREES.add(new ThreatPattern(new int[] {0, 1, 0, 1, 1, 0}, new int[]
                {0, 2, 5}));
        THREES.add(new ThreatPattern(new int[] {0, 1, 1, 0, 1, 0}, new int[]
                {0, 3, 5}));

        FOURS.add(new ThreatPattern(new int[] {1, 1, 1, 1, 0}, new int[] {4} ));
        FOURS.add(new ThreatPattern(new int[] {1, 1, 1, 0, 1}, new int[] {3} ));
        FOURS.add(new ThreatPattern(new int[] {1, 1, 0, 1, 1}, new int[] {2} ));
        FOURS.add(new ThreatPattern(new int[] {1, 0, 1, 1, 1}, new int[] {1} ));
        FOURS.add(new ThreatPattern(new int[] {0, 1, 1, 1, 1}, new int[] {0} ));

        REFUTATIONS.add(new ThreatPattern(new int[] {1, 1, 1, 0, 0}, new
                int[] {3, 4}));
        REFUTATIONS.add(new ThreatPattern(new int[] {1, 1, 0, 0, 1}, new
                int[] {2, 3} ));
        REFUTATIONS.add(new ThreatPattern(new int[] {1, 0, 0, 1, 1}, new
                int[] {1, 2} ));
        REFUTATIONS.add(new ThreatPattern(new int[] {0, 0, 1, 1, 1}, new
                int[] {0, 1} ));
        
    }

     /**
     * Kiá»ƒm tra 1 Ã´ vá»›i cÃ¡c máº«u 3 Ã´ tháº³ng hÃ ng liá»�n nhau hoáº·c cÃ¡ch nhau 
     * (0XXX0 and 0X0XX0).
     * @param playerIndex Ä‘á»‹nh danh ngÆ°á»�i chÆ¡i
     * @param state tráº¡ng thÃ¡i hiá»‡n táº¡i cá»§a trÃ² chÆ¡i
     * @param field Ã´ kiá»ƒm tra
     * @return Danh sÃ¡ch cÃ¡c Ã´ cÃ³ thá»ƒ Ä‘Ã¡nh nháº±m táº¥n cÃ´ng hoáº·c phÃ²ng thá»§ Ä‘á»‘i
     * vá»›i cÃ¡c máº«u Ä‘e dá»�a tÃ¬m Ä‘Æ°á»£c
     */ 
    public List<Square> getThrees(GameState state, Piece field, int playerIndex) {
        return getThreatMoves(THREES, state, field, playerIndex);
    }

    /**
     * Kiá»ƒm tra 1 Ã´ vá»›i cÃ¡c máº«u 4 Ã´ tháº³ng hÃ ng liá»�n nhau hoáº·c cÃ¡ch nhau 
     * mÃ  bá»‹ cháº·n á»Ÿ 1 Ä‘áº§u (0XXXX and XX0XX).
     * @param playerIndex Ä‘á»‹nh danh ngÆ°á»�i chÆ¡i
     * @param state tráº¡ng thÃ¡i hiá»‡n táº¡i cá»§a trÃ² chÆ¡i
     * @param field Ã´ kiá»ƒm tra
     * @return Danh sÃ¡ch cÃ¡c Ã´ cÃ³ thá»ƒ Ä‘Ã¡nh nháº±m táº¥n cÃ´ng hoáº·c phÃ²ng thá»§ Ä‘á»‘i
     * vá»›i cÃ¡c máº«u Ä‘e dá»�a tÃ¬m Ä‘Æ°á»£c
     */ 
    public List<Square> getFours(GameState state, Piece field, int playerIndex) {
        return getThreatMoves(FOURS, state, field, playerIndex);
    }
    /**
     * Kiá»ƒm tra 1 Ã´ vá»›i cÃ¡c máº«u cÃ³ thá»ƒ Ä‘Ã¡nh Ä‘á»ƒ táº¡o thÃ nh 4 Ã´ tháº³ng hÃ ng liá»�n nhau hoáº·c cÃ¡ch nhau 
     * mÃ  bá»‹ cháº·n á»Ÿ 1 Ä‘áº§u Ä‘áº§u (0XOXX and XXO0X).
     * @param playerIndex Ä‘á»‹nh danh ngÆ°á»�i chÆ¡i
     * @param state tráº¡ng thÃ¡i hiá»‡n táº¡i cá»§a trÃ² chÆ¡i
     * @param field Ã´ kiá»ƒm tra
     * @return Danh sÃ¡ch cÃ¡c Ã´ cÃ³ thá»ƒ Ä‘Ã¡nh nháº±m táº¥n cÃ´ng hoáº·c phÃ²ng thá»§ Ä‘á»‘i
     * vá»›i cÃ¡c máº«u Ä‘e dá»�a tÃ¬m Ä‘Æ°á»£c
     */  List<Square> getRefutations(GameState state, Piece field, int playerIndex) {
        return getThreatMoves(REFUTATIONS, state, field, playerIndex);
    }
    
    

    /**
     * TÃ¬m kiáº¿m cÃ¡c má»‘i Ä‘e dá»�a xung quanh má»™t Ã´ trong trÃ² chÆ¡i,
     * vá»›i má»—i má»‘i Ä‘e dá»�a, Ä‘Æ°a ra cÃ¡c nÆ°á»›c táº¥n cÃ´ng hoáº·c phÃ²ng thá»§ tÆ°Æ¡ng á»©ng.
     * @param patternList danh sÃ¡ch cÃ¡c Ä‘á»‘i tÆ°á»£ng ThreatPattern Ä‘á»ƒ tÃ¬m kiáº¿m
     * @param state tráº¡ng thÃ¡i hiá»‡n táº¡i cá»§a trÃ² chÆ¡i
     * @param field Ã´ mÃ  chÆ°Æ¡ng trÃ¬nh sáº½ tÃ¬m kiáº¿m xung quanh nÃ³
     * @param playerIndex Ä‘á»‹nh danh ngÆ°á»�i chÆ¡i tÃ¬m kiáº¿m
     * @return
     */
    private List<Square> getThreatMoves(List<ThreatPattern> patternList, GameState state, Piece field, int playerIndex) {
        List<Square> threatMoves = new ArrayList<Square>();
        // Loop around the field in every direction
        // (diagonal/horizontal/vertical)
        for(int direction = 0; direction < 4; direction++) {
            Piece[] directionArray = state.getDirections()[field.square.coordX][field.square.coordY][direction];
            for(ThreatPattern pattern : patternList) {
                // Try to find the pattern
                int patternIndex = matchPattern(directionArray, pattern.getPattern(playerIndex));
                if(patternIndex != -1) {
                    // Found pattern, get the squares in the pattern and map
                    // them to moves on the board
                    for(int patternSquareIndex : pattern.getPatternSquares()) {
                        Piece patternSquareField = directionArray[patternIndex +patternSquareIndex];
                        threatMoves.add(new Square(patternSquareField.square.coordX, patternSquareField.square.coordY));
                    }
                }
            }
        }
        return threatMoves;
    }

    /**
     * So sÃ¡nh má»™t máº£ng cÃ¡c Ã´ vá»›i má»™t máº«u
     * @param direction máº£ng cÃ¡c Ã´ Ä‘Æ°á»£c so sÃ¡nh
     * @param pattern máº«u Ä‘á»ƒ so sÃ¡nh
     * @return Tráº£ vá»� vá»‹ trÃ­ báº¯t Ä‘áº§u cá»§a máº£ng mÃ  khá»›p vá»›i máº«u, náº¿u khÃ´ng
     * tráº£ vá»� -1
     */
    private int matchPattern(Piece[] direction, int[] pattern) {
        for(int i = 0; i < direction.length; i++) {
            // Check if the pattern lies within the bounds of the direction
            if(i + (pattern.length - 1) < direction.length) {
                int count = 0;
                for(int j = 0; j < pattern.length; j++) {
                    if(direction[i + j].index == pattern[j]) {
                        count++;
                    } else {
                        break;
                    }
                }
                // Má»�i pháº§n tá»­ Ä‘á»�u trÃ¹ng vá»›i máº«u, tráº£ vá»� pháº§n tá»­ Ä‘áº§u tiÃªn
                if(count == pattern.length) {
                    return i;
                }
            } else {
                break;
            }
        }
        return -1;
    }
    
    private List<Integer> getThreatMoves2(List<ThreatPattern> patternList, GameState state, Piece field, int playerIndex) {
        List<Integer> threatMoves = new ArrayList<Integer>();
        // Loop around the field in every direction
        // (diagonal/horizontal/vertical)
        for(int direction = 0; direction < 4; direction++) {
            Piece[] directionArray = state.getDirections()[field.square.coordX][field.square.coordY][direction];
            for(ThreatPattern pattern : patternList) {
                // Try to find the pattern
                int patternIndex = matchPattern(directionArray, pattern.getPattern(playerIndex));
                if(patternIndex != -1) {
                    threatMoves.add(direction);
                }
            }
        }
        return threatMoves;
    }
    
    public List<Integer> getFours2(GameState state, Piece field, int playerIndex) {
        return getThreatMoves2(FOURS, state, field, playerIndex);
    }

    public List<Integer> getThrees2(GameState state, Piece field, int playerIndex) {
        return getThreatMoves2(THREES, state, field, playerIndex);
    }
    
    public List<Integer> getRefutation2(GameState state, Piece field, int playerIndex) {
        return getThreatMoves2(REFUTATIONS, state, field, playerIndex);
    }
}
