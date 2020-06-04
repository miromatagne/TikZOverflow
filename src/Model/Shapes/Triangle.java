package Model.Shapes;

/**
 * Class for the Triangle shape (node-type).
 */

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

    /**
     * Generate TikZ code that creates the shape using the properties.
     *
     * @return generated code
     */
    @Override
    public String generateAndGetTikzCode() {
        float labelPosX = (Math.min(Math.min(getX1(),getX2()),getX3())+Math.max(Math.max(getX1(),getX2()),getX3()))/2;
        float labelPosY = Math.min(Math.min(getY1(),getY2()),getY3());

        String code = super.generateAndGetTikzCode();
        //first triangle's corner position
        code += "(" + getX1() + "," + getY1() + ") ";
        //position of the label and his content
        code += "node[color=black, below] at (" + labelPosX + "," + labelPosY + "){" + getLabel() +"} --";
        //second triangle's corner position
        code += "(" + getX2() + "," + getY2() + ") --";
        //third triangle's corner position
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
        setPosX2(x);
        setPosX3(x);
        setPosX1(x);
    }

    public void setPosX1(float x){
        x1 = x;
    }

    public void setPosX2(float x){
        float distance12 = getX2() - getX1();
        x2 = x + distance12;
    }

    public void setPosX3(float x){
        float distance13 = getX3() - getX1();
        x3 = x + distance13;
    }


    @Override
    public void setPosY(float y) {
        setPosY2(y);
        setPosY3(y);
        setPosY1(y);
    }

    public void setPosY1(float y){
        y1 = y;
    }

    public void setPosY2(float y){
        float distance12 = getY2() - getY1();
        y2 = y + distance12;
    }

    public void setPosY3(float y){
        float distance13 = getY3() - getY1();
        y3 = y + distance13;
    }

}
