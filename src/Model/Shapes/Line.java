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

    @Override
    public String generateAndGetTikzCode() {
        //Defining the position where the label will be drawn
        double labelPosY = Math.min(getyOrigin(),getyDestination());
        double labelPosX=0;
        if(labelPosY == getyOrigin()){
            labelPosX=getxOrigin();
        }
        else{
            labelPosX=getxDestination();
        }

        String code = "\\draw";
        //Width
        code += " [line width=" + getStrokeWidth() + "mm";
        //Color
        code += super.getColorTikzCode();
        //Draw line Position + Label in the Middle
        code += " (" + getxOrigin() + "," + getyOrigin() + ")";
        code += " -- (" + (getxDestination()+getxOrigin())/2 + "," + (getyDestination()+getyOrigin())/2 + ") " ;
        code += "-- (" + getxDestination() + "," + getyDestination() + ") ";
        //Draw the label of the shape
        code += "node[color=black, below] at (" + labelPosX + "," + labelPosY + "){" + getLabel() +"}; \n" ;
        return code;
    }

}
