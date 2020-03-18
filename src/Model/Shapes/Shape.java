package Model.Shapes;

import javafx.scene.paint.Color;

public abstract class Shape {
    private float posX;
    private float posY;
    private Color color;

    public  Shape(float posX, float posY){ this.posX = posX;this.posY=posY;}

    public float getPosX() {
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
    public abstract String getDescription();

    public void setColor(Color c){color=c;}

    public Color getColor() {
        return color;
    }

}
