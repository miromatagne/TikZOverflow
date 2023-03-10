package Model.Shapes;

/**
 * Class for the Arrow shape (link-type).
 */

public class Arrow extends Link {
    private float arrowHeadLength = 5;
    private float arrowHeadWidth = 5;

    public Arrow(float xOrigin, float yOrigin, float xDestination, float yDestination) {
        super(xOrigin, yOrigin, xDestination, yDestination);
    }

    public Arrow(Node origin, Node destination) {
        super(origin, destination);
    }

    /**
     * Generate TikZ code that creates the shape using the properties.
     *
     * @return generated code
     */
    @Override
    public String generateAndGetTikzCode() {
        //Defining the position where the label will be drawn
        double labelPosY = Math.min(getYOrigin(), getYDestination());
        double labelPosX;
        if(labelPosY == getYOrigin()){
            labelPosX= getXOrigin();
        }
        else{
            labelPosX= getXDestination();
        }
        String code = "\\draw";
        //Arrow
        code += " [arrows={->[length=" + getArrowHeadLength() + "mm, width="
                + getArrowHeadWidth() + "mm]}";
        //Width
        code += " ,line width=" + getStrokeWidth() + "mm";
        //Color
        code += super.getColorTikzCode();
        //Draw line Position + Label in the Middle
        code += " (" + getXOrigin() + "," + getYOrigin() + ")";
        code += " -- (" + (getXDestination()+ getXOrigin())/2 + "," + (getYDestination()+ getYOrigin())/2 + ")" ;
        code += " -- (" + getXDestination() + "," + getYDestination() + ") ";
        //label
        code += "node[color=black, below] at (" + labelPosX + "," + labelPosY + "){" + getLabel() +"}; \n" ;
        return code;

    }

    public float getArrowHeadLength() {
        return arrowHeadLength;
    }

    public void setArrowHeadLength(float arrowHeadLength) {
        this.arrowHeadLength = arrowHeadLength;
    }

    public float getArrowHeadWidth() {
        return arrowHeadWidth;
    }

    public void setArrowHeadWidth(float arrowHeadWidth) {
        this.arrowHeadWidth = arrowHeadWidth;
    }

}
