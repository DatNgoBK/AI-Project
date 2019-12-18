package util;

import caro.GameState;
import caro.Piece;
import caro.Player;

/**
 * Hàm tính toán điểm cho các ô trong bàn cờ. Việc tính toán được thực hiện
 * trên mỗi ô bằng cách tính 'độ mạnh' của ô đó trên bàn cờ
 * 
 * Độ mạnh của mỗi ô trên bàn cờ được tính bởi xem xét 4 hướng
 * xung quanh ô đó (dọc, ngang, và 2 đường chéo. 
 * Từng 5 ô một được truyền vào theo mỗi hướng và số nước đi để 5 ô đó
 * tạo thành 5 ô thẳng hàng(trạng thái chiến thằng) do cùng một người chơi đánh
 * được ghi lại.
 *
 * Với mỗi trạng thái chiến thắng đạt được sau một nước, ô đang xét nhận thêm 704 điểm, 
 * 176 điểm nếu đạt được sau hai nước, 44 sau ba nước, 11 sau bốn nước và 3 sau năm nước
 * sau khi mọi ô được tính điểm, điểm trạng thái hiện tại của bàn cờ được tính bằng
 * tổng điểm của người chơi hiện tại trừ tổng điểm của đối thủ
 *
 */
public class Evaluator {

    private static final Evaluator INSTANCE = new Evaluator();
    private static final int[] SCORES = {23, 18, 13, 8, 3};
    

    private Evaluator() {
    }

    public static Evaluator getInstance() {
        return INSTANCE;
    }

    /**
     * nhận vào một dãy biểu diễn các hướng ngang, dọc, 2 đường chéo của một ô 
     * trên bàn cờ, tính toán điểm dựa trên có bao nhiêu trạng thái chiến thắng và
     * sau bao nhiêu nước với mỗi trạng thái chiến thắng
     *
     * @param direction dãy 1 chiểu biểu diễn 1 hướng trên bàn cờ
     * @param index định danh của người chơi hiện tại
     * @return điểm đánh giá cho hướng này
     */
    private static int scoreDirection(Piece[] direction, int index) {
        int score = 0;

        // Xem xét 5 ô liền nhau
        for (int i = 0; (i + 4) < direction.length; i++) {
            int empty = 0;
            int stones = 0;
            for (int j = 0; j <= 4; j++) {
                if (direction[i + j].index == 0) {
                    empty++;
                } else if (direction[i + j].index == index) {
                    stones++;
                } else {
                    // Đối phương đã đánh 1 ô nào đó trong 5 ô này
                    //không thể tạo được trạng thái chiến thắng
                    break;
                }
            }
            // Bỏ qua nếu đã đạt đc trạng thái chiến thắng, hoặc 5 ô đều rông
            if (empty == 0 || empty == 5) {
                continue;
            }

            // 5 ô đang xét chỉ chứa các ô do người chơi hiện tại đánh và các ô trống
            //, có thể đạt được trạng thái chiến thắng, tính điểm dựa trên số nước
            // cần đánh để đạt được trạng thái chiến thắng
            if (stones + empty == 5) {
                score += SCORES[empty];
            }
        }
        return score;
    }

    /**
     * Đánh giá trạng thái hiện tại của bàn cờ đối với người chơi hiện tại
     *
     * @param state trạng thái bàn cờ
     * @return điểm trạng thái của bàn cờ đối với người chơi hiện tại
     */
    public static int evaluateState(GameState state, int depth) {
        int playerIndex = state.getCurrentPlayer().getHashValue();
        int opponentIndex = playerIndex == 1 ? 2 : 1;

        // Check for a winning/losing position
        int terminal = state.terminal();
        if (terminal == Player.BLACK.getHashValue()) {
            return (20000 + depth);
        }
        if (terminal == Player.WHITE.getHashValue()) {
            return (-20000 - depth);
        }
        terminal = state.isHaveDoubleThreat();
        if (terminal == Player.BLACK.getHashValue()) {
            return (15000 + depth);
        }
        if (terminal == Player.WHITE.getHashValue()) {
            return (-15000 - depth);
        }

        // đánh giá các ô một cách độc lập, trừ vào điểm tổng nếu ô đó dối thủ đánh,
        //cộng vào điểm tổng nếu ô đó do người chơi hiện tại đánh
        int score = 0;
        for (int i = 0; i < state.board.length; i++) {
            for (int j = 0; j < state.board.length; j++) {
                if (state.board[i][j].index == opponentIndex) {
                    score -= evaluateField(state, i, j, opponentIndex);
                } else if (state.board[i][j].index == playerIndex) {
                    score += evaluateField(state, i, j, playerIndex);
                }
            }
        }
        return score;
    }
    /**
     * Đánh giá các ô trên bàn cờ theo 4 hướng
     * @param state trạng thái bàn cờ
     * @param row tọa độ theo chiều dọc của ô
     * @param col tọa độ theo chiều ngang của ô
     * @param index định danh người chơi đánh ô này
     * @return 
     */
    public static int evaluateField(GameState state, int row, int col, int index) {
        int score = 0;
        for (int direction = 0; direction < 4; direction++) {
            score += scoreDirection(state.getDirections()[row][col][direction],
                    index);
        }
        return score;
    }

}
