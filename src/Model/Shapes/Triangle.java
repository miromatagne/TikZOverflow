package Model.Shapes;

public class Triangle extends Node {
    private float x1, y1, x2, y2, x3, y3;

    public Triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        super(x1, y1);
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
    }

    @Override
    public String generateAndGetTikzCode() {
        String code = "\\filldraw";
        code += "[fill={rgb,1:red," + getColor().getRed() + ";green," + getColor().getGreen() + ";blue," + getColor().getBlue() + "}," +
                "line width=" + getOutlineThickness() / 20 + "] ";
        code += "(" + getX1() + "," + getY1() + ") --";
        code += "(" + getX2() + "," + getY2() + ") --";
        code += "(" + getX3() + "," + getY3() + ") -- cycle;\n";
        return code;
    }

    public float getX1() {
        return x1;
    }

    public float getY1() {
        return y1;
    }

    public float getX2() {
        return x2;
    }

    public float getY2() {
        return y2;
    }

    public float getX3() {
        return x3;
    }

    public float getY3() {
        return y3;
    }

    @Override
    public void setPosX(float x) {
        float distance12 = getX2() - getX1();
        float distance13 = getX3() - getX1();
        x1 = x;
        x2 = x + distance12;
        x3 = x + distance13;
    }

    @Override
    public void setPosY(float y) {
        float distance12 = getY2() - getY1();
        float distance13 = getY3() - getY1();
        y1 = y;
        y2 = y + distance12;
        y3 = y + distance13;
    }

}
