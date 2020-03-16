package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddShapeMenuController extends ControllerSuperclass implements Initializable {
    @FXML private SubScene shapeScene;
    @FXML private Text rectangleText;
    @FXML private Text circleText;
    @FXML private Text arrowText;
    @FXML private Text curvedLineText;
    @FXML private Text lineText;
    @FXML private Pane rightPaneShapeMenu;
    private static ArrayList<Parent> allShapes = new ArrayList<>() ;
    private static ArrayList<Text> allTexts = new ArrayList<>();

    int idCurrent;
    final static int RECTANGLE = 0;
    final static int CIRCLE = 1;
    final static int LINE = 2;
    final static int CURVED_LINE = 3;
    final static int ARROW = 4;
    final static int NUMBER_OF_MENUS = 5;

    public void setupTexts() {
        allTexts.add(rectangleText);
        allTexts.add(circleText);
        allTexts.add(lineText);
        allTexts.add(curvedLineText);
        allTexts.add(arrowText);
    }

    @Override
    public void update() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addScene("/View/rectangleMenu.fxml");
        addScene("/View/circleMenu.fxml");
        addScene("/View/lineMenu.fxml");
        addScene("/View/curvedLineMenu.fxml");
        addScene("/View/arrowMenu.fxml");
        setupTexts();
        if(allShapes.isEmpty()){
            //Error
            return;
        }
        shapeScene.widthProperty().bind(rightPaneShapeMenu.widthProperty());
        shapeScene.heightProperty().bind(rightPaneShapeMenu.heightProperty());
        for (int i = 0; i < NUMBER_OF_MENUS; i++){
            changeToMenu(i);
        }
        changeToArrowMenu();

    }



    public void rectangleTextHand(){this.changeCursorToHand(rectangleText);}
    public void circleTextHand(){this.changeCursorToHand(circleText);}
    public void arrowTextHand(){this.changeCursorToHand(arrowText);}
    public void curvedLineTextHand(){this.changeCursorToHand(curvedLineText);}
    public void lineTextHand(){this.changeCursorToHand(lineText);}

    private void changeToMenu(int value){
        shapeScene.setRoot(allShapes.get(value));
        idCurrent = value ;
    }

    private void changeTextColor(int value){
        final String BLUE = "-fx-fill: #4568d4";
        final String WHITE = "-fx-fill: white";

        for (int i = 0; i < NUMBER_OF_MENUS; i++){
            if (i == value){
                allTexts.get(i).setStyle(BLUE);
            } else{
                allTexts.get(i).setStyle(WHITE);
            }
        }
    }

    public void changeToRectangleMenu(){
        changeToMenu(RECTANGLE);
        changeTextColor(RECTANGLE);
    }
    public void changeToCircleMenu(){
        changeToMenu(CIRCLE);
        changeTextColor(CIRCLE);

    }
    public void changeToArrowMenu(){
        changeToMenu(ARROW);
        changeTextColor(ARROW);

    }
    public void changeToLineMenu(){
        changeToMenu(LINE);
        changeTextColor(LINE);

    }
    public void changeToCurvedLineMenu(){
        changeToMenu(CURVED_LINE);
        changeTextColor(CURVED_LINE);

    }
    /**
     * Changes cursor to hand.
     * @param text when given text is hovered, cursor changes to hand.
     */
    private void changeCursorToHand(Text text){
        text.setCursor(Cursor.HAND);
    }


    private void addScene(String scenePath)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
            allShapes.add(loader.load());
        }
        catch(Exception expc){System.out.println("Error loading all screen" + scenePath); expc.printStackTrace();}
    }

}
