package Model.Shapes;

import javafx.scene.paint.Color;

public abstract class Shape {
    private float posX;
    private float posY;
    private Color color;

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void setColor(Color c){color=c;}

    public Color getColor() {
        return color;
    }

}
