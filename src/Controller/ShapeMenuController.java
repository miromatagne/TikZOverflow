package Controller;

import Model.Shapes.FactoryShape;
import Model.Shapes.Shape;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ShapeMenuController {
    private CompileListener compileListener;

    public void setCompileListener(CompileListener compileListener) {
        this.compileListener = compileListener;
    }

    public void addShape(int idCurrent, ArrayList<Float> allData, Color color){
        Shape shape = FactoryShape.getInstance(idCurrent,allData, color);
        compileListener.addShape(shape);
        compileListener.closePopup();
    }
}
