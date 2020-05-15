package Model.Shapes;

/**
 * Class for the Line shape (link-type).
 */

public class Line extends Link {
    public Line(float xOrigin, float yOrigin, float xDestination, float yDestination) {
        super(xOrigin, yOrigin, xDestination, yDestination);
    }

    public Line(Node origin, Node destination) {
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
        //Width
        code += " [line width=" + getStrokeWidth() + "mm";
        //Color
        code += super.getColorTikzCode();
        //Draw line Position + Label in the Middle
        code += " (" + getXOrigin() + "," + getYOrigin() + ")";
        code += " -- (" + (getXDestination()+ getXOrigin())/2 + "," + (getYDestination()+ getYOrigin())/2 + ") " ;
        code += "-- (" + getXDestination() + "," + getYDestination() + ") ";
        //Draw the label of the shape
        code += "node[color=black, below] at (" + labelPosX + "," + labelPosY + "){" + getLabel() +"}; \n" ;
        return code;
    }

}
