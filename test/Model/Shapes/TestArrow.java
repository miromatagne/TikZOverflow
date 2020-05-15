package Model.Shapes;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Arrow objects (extending Link)
 */

class TestArrow {

    @Test
    void generateAndGetTikzCode() {
        Arrow a = new Arrow(0,1,4,4);
        a.setArrowHeadLength(5);
        a.setArrowHeadWidth(7);
        a.setColor(Color.valueOf("#990000"));
        a.setStrokeWidth(3);
        a.setLabel("Arrow");
        String code = a.generateAndGetTikzCode();

        Circle c = new Circle(3,3);
        Rectangle r = new Rectangle(1,2);
        Arrow a2 = new Arrow(c,r);
        a2.setArrowHeadLength(2);
        a2.setArrowHeadWidth(9);
        a2.setColor(Color.valueOf("#990000"));
        a2.setStrokeWidth(2);
        a2.setLabel("Arrow2");
        String code2 = a2.generateAndGetTikzCode();

        assertEquals("\\draw [arrows={->[length=5.0mm, width=7.0mm]} ,line width=3.0mm ,color={rgb,1:red,0.6000000238418579;green,0.0;blue,0.0}] (0.0,1.0) -- (2.0,2.5) -- (4.0,4.0) node[color=black, below] at (0.0,1.0){Arrow}; \n",code);
        assertEquals("\\draw [arrows={->[length=2.0mm, width=9.0mm]} ,line width=2.0mm ,color={rgb,1:red,0.6000000238418579;green,0.0;blue,0.0}] (3.0,3.0) -- (2.0,2.5) -- (1.0,2.0) node[color=black, below] at (1.0,2.0){Arrow2}; \n",code2);
    }

    @Test
    void setPos() {
        Arrow a3 = new Arrow(1,1,3,4);
        a3.setPosX((float)2.1);
        a3.setPosY((float)5.1);
        assertEquals(2.1,Math.round(a3.getXOrigin() * 100.0) / 100.0);
        assertEquals(4.1,Math.round(a3.getXDestination() * 100.0) / 100.0);
        assertEquals(5.1,Math.round(a3.getYOrigin() * 100.0) / 100.0);
        assertEquals(8.1,Math.round(a3.getYDestination() * 100.0) / 100.0);
    }
}