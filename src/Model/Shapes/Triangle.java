package Model.Shapes;

public class Triangle extends Node {
    private float side_a,side_b,side_c;

    public Triangle(float xCenter, float yCenter) { super(xCenter, yCenter); }

    @Override
    public String getDescription() {
        return "Triangle of center (" + this.getXCenter() + "," + this.getYCenter() + ").";
    }

    public float getSide_a() {
        return side_a;
    }

    public void setSide_a(float side_a) {
        this.side_a = side_a;
    }

    public float getSide_b() {
        return side_b;
    }

    public void setSide_b(float side_b) {
        this.side_b = side_b;
    }

    public float getSide_c() {
        return side_c;
    }

    public void setSide_c(float side_c) {
        this.side_c = side_c;
    }
}
