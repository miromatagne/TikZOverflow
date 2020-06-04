package Model.Shapes;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Rectangle objects (extending Node)
 */

class TestRectangle {

    @Test
    void generateAndGetTikzCode() {
        Rectangle r = new Rectangle(4,3);
        r.setHeight(3);
        r.setWidth(5);
        r.setColor(Color.valueOf("#990000"));
        r.setOutlineThickness(60);
        r.setLabel("Rectangle");
        String code = r.generateAndGetTikzCode();
        assertEquals("\\filldraw[fill={rgb,1:red,0.6000000238418579;green,0.0;blue,0.0},line width=3.0] (4.0,3.0) node[color=black, below] at (6.5,3.0){Rectangle} rectangle (9.0,6.0);\n",code);
    }

    @Test
    public void setPosX() {
        Rectangle r = new Rectangle(1,5);
        r.setPosX((float)4.1);
        assertEquals(4.1,Math.round(r.getXCenter() * 100.0) / 100.0);
    }

    @Test
    public void setPosY() {
        Rectangle r = new Rectangle(1,5);
        r.setPosY((float) 9.5);
        assertEquals(9.5,Math.round(r.getYCenter() * 100.0) / 100.0);
    }
}