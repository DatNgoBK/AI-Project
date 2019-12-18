package caro;

/**
 * Thể hiện một mẫu trạng thái đe dọa trên bàn cờ
 * như các tập các ô ba hoặc 4 thẳng hàng: OOOOX hoặc OOOXX
 */
public class ThreatPattern {
    private int[][] pattern;
    private final int[] patternSquares;

     /**
     * Tạo một mẫu đe dọa mới
     * @param pattern Là mảng một chiều, biểu diễn một mẫu đe dọa, 
     * giá trị 0 thể hiện ô đó trống, giá trị 1 thể hiện ô đó đã được đánh
     * @param patternSquares Các nước tấn công hoặc phòng thủ cho pattern
     * thực chấn là các ô có giá trị 0 trong mảng pattern
     */
    public ThreatPattern(int[] pattern, int[] patternSquares) {
        // Store the pattern from each players perspective in pattern[][]
        this.pattern = new int[2][1];
        this.pattern[0] = pattern;
        this.pattern[1] = switchPattern(pattern);
        this.patternSquares = patternSquares;
    }

    /**
     * Lấy mẫu đe dọa ứng với người chơi
     * @param playerIndex định danh người chơi
     * @return mảng chứa mẫu đe dọa
     */
    public int[] getPattern(int playerIndex) {
        return this.pattern[playerIndex - 1];
    }

    /**
     * Trả về các nước đi tấn công hoặc phòng thủ đối với mẫu đe dọa
     * @return int[] chứa các nước đi
     */
    public int[] getPatternSquares() {
        return this.patternSquares;
    }

     /**
     * Chuyển mẫu đe dọa đầu vào thành mẫu đe dọa của người chơi còn lại.
     * @param pattern mảng chứa mẫu đe dọa
     * @return mảng tương tự với các giá trị 1 chuyển thành 2
     */
    private int[] switchPattern(int[] pattern) {
        int[] patternSwitched = new int[pattern.length];
        for(int i = 0; i < pattern.length; i++) {
            if(pattern[i] == 1) {
                patternSwitched[i] = 2;
            }
        }
        return patternSwitched;
    }
}