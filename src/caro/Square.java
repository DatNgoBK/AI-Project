package caro;

import java.util.Objects;

/**
 * Lưu tọa độ 1 ô trên bàn cờ
 * coordX tọa độ theo chiều ngang
 * coordY tọa độ theo chiều dọc
 * @author maidoanh
 */
public class Square {
	public int coordX;
	public int coordY;
	
	public Square() {
		coordX = 0;
		coordY = 0;
	}

	public Square(int coordX, int coordY) {
		this.coordX = coordX;
		this.coordY = coordY;
	}

	@Override
    public boolean equals(Object obj) {
        if(obj instanceof Square) {
            Square square = (Square) obj;
            return square.coordX == this.coordX && square.coordY == this.coordY;
        }
        return false;
    }

    @Override
    public int hashCode() {
       return Objects.hash(this.coordX, this.coordY);
    }
}
