package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.SubScene;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AddShapeMenuController extends ControllerSuperclass implements Initializable {
    @FXML private SubScene shapeScene;
    @FXML private Text rectangleText;
    @FXML private Text circleText;
    @FXML private Text arrowText;
    @FXML private Text curvedlineText;
    @FXML private Text lineText;

    @Override
    public void update() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void rectangleTextHand(){this.changeCursorToHand(rectangleText);}
    public void circleTextHand(){this.changeCursorToHand(circleText);}
    public void arrowTextHand(){this.changeCursorToHand(arrowText);}
    public void curvedLineTextHand(){this.changeCursorToHand(curvedlineText);}
    public void lineTextHand(){this.changeCursorToHand(lineText);}


    /**
     * Changes cursor to hand.
     * @param text when given text is hovered, cursor changes to hand.
     */
    private void changeCursorToHand(Text text){
        text.setCursor(Cursor.HAND);
    }
}
