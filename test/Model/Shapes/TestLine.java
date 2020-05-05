package Model.Shapes;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Line objects (extending Link)
 */

class TestLine {

    @Test
    void generateAndGetTikzCode() {
        Line l = new Line(1,1,3,4);
        l.setStrokeWidth(4);
        l.setColor(Color.valueOf("#990000"));
        String code = l.generateAndGetTikzCode();

        Circle c = new Circle(2,4);
        Rectangle r = new Rectangle(9,2);
        Line l2 = new Line(c,r);
        l2.setStrokeWidth(7);
        l2.setColor(Color.valueOf("#990000"));
        String code2 = l2.generateAndGetTikzCode();

        assertEquals("\\draw [line width=4.0mm ,color={rgb,1:red,0.6000000238418579;green,0.0;blue,0.0}] (1.0,1.0) -- (3.0,4.0);\n",code);
        assertEquals("\\draw [line width=7.0mm ,color={rgb,1:red,0.6000000238418579;green,0.0;blue,0.0}] (2.0,4.0) -- (9.0,2.0);\n",code2);
    }
}