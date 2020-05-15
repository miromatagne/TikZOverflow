package Model.Shapes;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Circle objects (extending Node)
 */

class TestCircle {

    @Test
    void generateAndGetTikzCode() {
        Circle c = new Circle(3,4);
        c.setColor(Color.valueOf("#990000"));
        c.setOutlineThickness(60);
        c.setRadius(3);
        c.setLabel("Circle");
        String code = c.generateAndGetTikzCode();
        assertEquals("\\filldraw[fill={rgb,1:red,0.6000000238418579;green,0.0;blue,0.0},line width=3.0] (3.0,4.0) node[color=black, below] at (3.0,1.0){Circle} circle (3.0);\n",code);
    }

    @Test
    void setPosX() {
        Circle c = new Circle(3,5);
        c.setRadius(3);
        c.setPosX(8);
        assertEquals(10.1,Math.round(c.getXCenter() * 100.0) / 100.0);
    }

    @Test
    void setPosY() {
        Circle c = new Circle(3,5);
        c.setRadius(3);
        c.setPosY((float)7.2);
        assertEquals(9.3,Math.round(c.getYCenter() * 100.0) / 100.0);
    }
}