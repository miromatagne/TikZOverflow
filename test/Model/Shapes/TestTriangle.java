package Model.Shapes;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Triangle objects (extending Node)
 */

class TestTriangle {

    @Test
    void generateAndGetTikzCode() {
        Triangle t = new Triangle(2,1,4,5,1,8);
        t.setColor(Color.valueOf("#990000"));
        t.setOutlineThickness(60);
        t.setLabel("Triangle");
        String code = t.generateAndGetTikzCode();
        assertEquals("\\filldraw[fill={rgb,1:red,0.6000000238418579;green,0.0;blue,0.0},line width=3.0] (2.0,1.0) node[color=black, below] at (2.5,1.0){Triangle} --(4.0,5.0) --(1.0,8.0) -- cycle;\n",code);
    }

    @Test
    void setPosX() {
        Triangle t = new Triangle(1,1,6,5,3,2);
        t.setPosX((float)6.4);
        assertEquals(6.4,Math.round(t.getX1() * 100.0) / 100.0);
        assertEquals(11.4,Math.round(t.getX2() * 100.0) / 100.0);
        assertEquals(8.4,Math.round(t.getX3() * 100.0) / 100.0);
    }

    @Test
    void setPosY() {
        Triangle t = new Triangle(1,1,6,5,3,2);
        t.setPosY((float)7.9);
        assertEquals(7.9,Math.round(t.getY1() * 100.0) / 100.0);
        assertEquals(11.9,Math.round(t.getY2() * 100.0) / 100.0);
        assertEquals(8.9,Math.round(t.getY3() * 100.0) / 100.0);
    }

}
