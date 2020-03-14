package Model.Shapes;

import javafx.scene.paint.Color;

public abstract class Shape {
    private int posX, posY;
    private Color color;
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

}
