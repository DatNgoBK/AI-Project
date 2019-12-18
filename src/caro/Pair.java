package caro;

/**
 * Lưu tọa độ 1 ô trên bàn cờ cùng điểm được đánh giá theo thuật toán minimax
 *
 * @author maidoanh
 */
public class Pair implements Comparable<Pair>{
	private Square square;
	private int value;

        /**
     * @param square tọa độ 1 ô trên bàn cờ
     * @param value điểm của ô đó được tính theo thuật toán minimax
     */
	public Pair(Square square, int value) {
		this.square = square;
		this.value = value;
	}

	public Square getSquare() {
		return square;
	}

	public void setSquare(Square square) {
		this.square = square;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int compareTo(Pair comparePair) {
		// TODO Auto-generated method stub
		return comparePair.getValue() - this.value;
	}
        

}
