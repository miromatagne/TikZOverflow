package Controller.ShapeMenu;

import Model.Shapes.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class FactoryShape {

    public Shape factoryShape(int id, ArrayList<Float> data, Color color) {
        Shape s = null;
        switch (id) {
            case 0:
                Rectangle r = new Rectangle(data.get(0),data.get(1));
                r.setHeight(data.get(2));
                r.setWidth(data.get(3));
                r.setColor(color);
                s=r;
                break;
            case 1:
                Circle c = new Circle(data.get(0),data.get(1));
                c.setRadius(data.get(2));
                c.setColor(color);
                s=c;
                break;
            case 2:
                Line l = new Line(data.get(0),data.get(1),data.get(2),data.get(3));
                l.setStrokeWidth(data.get(4));
                l.setColor(color);
                s=l;
                break;
            case 3:
                CurvedLine cl = new CurvedLine(data.get(0),data.get(1),data.get(2),data.get(3));
                cl.setStrokeWidth(data.get(4));
                cl.setCurveRadius(data.get(5));
                cl.setColor(color);
                s=cl;
                break;
            case 4:
                Arrow a = new Arrow(data.get(0),data.get(1),data.get(2),data.get(3));
                a.setStrokeWidth(data.get(4));
                a.setColor(color);
                s=a;
                break;
        }
        return s;
    }
}
