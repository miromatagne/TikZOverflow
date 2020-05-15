package Model.Shapes;

import javafx.scene.paint.Color;

/**
 * Superclass for the different shapes that can be added to a diagram.
 * Shapes have a position (x, y) and a color by default.
 */

public abstract class Shape {
    private float posX;
    private float posY;
    private Color color;
    private String label;

    public Shape(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

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


    /**
     * Generate TikZ code that creates the shape using the properties. Has to be overwritten by child classes.
     *
     * @return generated code
     */
    public abstract String generateAndGetTikzCode();

    public Color getColor() {
        return color;
    }

    public void setColor(Color c) {
        color = c;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
