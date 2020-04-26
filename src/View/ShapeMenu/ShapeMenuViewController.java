package View.ShapeMenu;

import Controller.ShapeMenuController;
import View.ViewControllers.ControllerSuperclass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller of the menu to add a new shape. It is linked to the "AddShapeMenuController.fxml" file
 * It handles the behavior of the pop-up window
 */
public class ShapeMenuViewController implements Initializable {

    @FXML private SubScene shapeScene;
    @FXML private Text rectangleText;
    @FXML private Text circleText;
    @FXML private Text arrowText;
    @FXML private Text curvedLineText;
    @FXML private Text lineText;
    @FXML private Text triangleText;
    @FXML private GridPane gridPaneAddShape;

    private ShapeMenuViewControllerListener listener;

    private ArrayList<Text> allTexts = new ArrayList<>();

    public final static int ARROW = 4;
    final static int NUMBER_OF_MENUS = 6;


    /**
     * Initialize the controller, load and add the different menus to an array list
     * The parameters are not needed in this case but because it overrides an abstract function,
     * they have to stay here
     * @param url               URL (not used)
     * @param resourceBundle    ResourceBundle(not used)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale.setDefault(Locale.ENGLISH);
        setupTexts();
        shapeScene.widthProperty().bind(gridPaneAddShape.widthProperty().multiply(0.8));
        shapeScene.heightProperty().bind(gridPaneAddShape.heightProperty());
    }

    /**
     * Setup the texts and add them to an array list
     */
    public void setupTexts() {
        allTexts.add(rectangleText);
        allTexts.add(circleText);
        allTexts.add(lineText);
        allTexts.add(curvedLineText);
        allTexts.add(arrowText);
        allTexts.add(triangleText);
    }

    /**
     * The following functions changes the cursor to an hand cursor when we enter the text area
     */
    public void rectangleTextHand(){this.changeCursorToHand(rectangleText);}
    public void circleTextHand(){this.changeCursorToHand(circleText);}
    public void arrowTextHand(){this.changeCursorToHand(arrowText);}
    public void curvedLineTextHand(){this.changeCursorToHand(curvedLineText);}
    public void lineTextHand(){this.changeCursorToHand(lineText);}
    public void triangleTextHand(){this.changeCursorToHand(triangleText);}

    /**
     * Change the text color once the menu is selected
     * @param id                    Id of the menu
     */
    public void changeTextColor(int id){
        final String BLUE = "-fx-fill: #4568d4";
        final String WHITE = "-fx-fill: white";

        for (int i = 0; i < NUMBER_OF_MENUS; i++){
            if (i == id){
                allTexts.get(i).setStyle(BLUE);
            } else{
                allTexts.get(i).setStyle(WHITE);
            }
        }
    }

    /**
     * Changes cursor to hand.
     *
     * @param text when given text is hovered, cursor changes to hand.
     */
    private void changeCursorToHand(Text text){
        text.setCursor(Cursor.HAND);
    }

    /**
     * Create a shape once all the fields are valid. It is called from the button "Confirm" in the pop-up window which
     * is used to create a new shape
     */
    public void confirmShape(){
        listener.onConfirmButtonPressed();

    }

    public void setListener(ShapeMenuViewControllerListener listener){
        this.listener = listener;
    }

    /**
     * Changes the shape creation menu to the one corresponding to the Text that was clicked.
     *
     * @param mouseEvent        Used to find out which Text was clicked.
     */
    public void changeMenu(MouseEvent mouseEvent) {
        String data = (String) ((Node) mouseEvent.getSource()).getUserData();
        listener.changeToMenu(Integer.parseInt(data));
    }

    public SubScene getShapeScene() {
        return shapeScene;
    }

    public interface ShapeMenuViewControllerListener{
        void onConfirmButtonPressed();
        void changeToMenu(int menuID);
    }
}
