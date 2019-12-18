package caro;

import java.awt.Image;
import util.ImageCaro;

;

/**
 * Đại diện cho một người chơi
 * typeColor tên ngườichơi
 * hashVale định danh của người chơi, dùng để đánh dấu trên bàn cờ
 * @author maidoanh
 */
public enum Player {
    BLACK("black", "lastblack", "BLACK", 1), WHITE("white", "lastwhite", "WHITE", 2);

    private Image image;
    private Image lastImage;
    private String typeColor;
    private int hashValue;
    private ImageCaro help = new ImageCaro();

    Player(String ImgName, String lastImageName, String typeColor, int hashValue) {
        this.image = help.getImage(ImgName);
        this.lastImage = help.getImage(lastImageName);
        this.typeColor = typeColor;
        this.hashValue = hashValue;
    }

    public Image getImg() {
        return image;
    }

    public Image getLastImage() {
        return lastImage;
    }

    public String getTypeColor() {
        return typeColor;
    }

    public int getHashValue() {
        return hashValue;
    }

    /**
         * 
         * @return  trả về đối thủ của người chơi
         */
    public Player getOpponent() {
        if (this == BLACK) {
            return Player.WHITE;
        }
        return Player.BLACK;
    }
}
