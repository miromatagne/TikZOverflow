package Model.Shapes;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the CurvedLine objects (extending Link)
 */

class TestCurvedLine {

    @Test
    void generateAndGetTikzCode() {
        CurvedLine c = new CurvedLine(1,1,3,4);
        c.setStrokeWidth(4);
        c.setColor(Color.valueOf("#990000"));
        c.setCurveOutAngle(30);
        c.setCurveInAngle(20);
        c.setLabel("Curved Line");
        String code = c.generateAndGetTikzCode();
        assertEquals("\\draw [line width=4.0mm ,color={rgb,1:red,0.6000000238418579;green,0.0;blue,0.0}] (1.0,1.0) to[out=30.0,in=20.0](3.0,4.0) node[color=black, below] at (1.0,1.0){Curved Line}; \n",code);
    }
}