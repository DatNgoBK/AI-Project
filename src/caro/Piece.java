package caro;

/**
 * Lưu tọa độ 1 ô trên bàn cờ cùng định danh của ngươi đánh ô đó
 * @author maidoanh
 */
public class Piece {
	public Square square;
	public int index;
	
        /**
         * @param square lưu tọa độ 1 ô trên bàn cờ
         * @param index định danh người chơi.
         */
	public Piece(Square square,int index) {
		this.square = square;
		this.index = index;
	}
        
        public Piece() {       
		index = 3;
	}

	
	
}
