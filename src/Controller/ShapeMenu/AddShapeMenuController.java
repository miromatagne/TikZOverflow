package Controller.ShapeMenu;

import Controller.CompileListener;
import Controller.ControllerSuperclass;
import Model.FieldChecker;
import Model.Shapes.Shape;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.SubScene;
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
public class AddShapeMenuController extends ControllerSuperclass implements Initializable {

    @FXML private SubScene shapeScene;
    @FXML private Text rectangleText;
    @FXML private Text circleText;
    @FXML private Text arrowText;
    @FXML private Text curvedLineText;
    @FXML private Text lineText;
    @FXML private GridPane gridPaneAddShape;

    private CompileListener compileListener;
    private FieldChecker fieldChecker = new FieldChecker();

    private static ArrayList<Parent> allShapes = new ArrayList<>() ;
    private static ArrayList<Text> allTexts = new ArrayList<>();
    private static ArrayList<MenuController> allControllers = new ArrayList<>();

    int idCurrent;
    final static int RECTANGLE = 0;
    final static int CIRCLE = 1;
    final static int LINE = 2;
    final static int CURVED_LINE = 3;
    final static int ARROW = 4;
    final static int NUMBER_OF_MENUS = 5;

    /**
     * Setup the texts and add them to an array list
     */
    public void setupTexts() {
        allTexts.add(rectangleText);
        allTexts.add(circleText);
        allTexts.add(lineText);
        allTexts.add(curvedLineText);
        allTexts.add(arrowText);
    }


    /**
     * Function called when a change occurs on the window.
     * Allows to get a clear page when changing between menus
     */
    @Override
    public void update() {
        changeToArrowMenu();
        clearShapeMenus();
    }

    /**
     * Clear the shape menus by calling the update function of the controllers
     */
    private void clearShapeMenus(){
        for (MenuController menuController : allControllers) {
            menuController.update();
        }
    }

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
        shapeScene.widthProperty().bind(gridPaneAddShape.widthProperty().multiply(0.8));
        shapeScene.heightProperty().bind(gridPaneAddShape.heightProperty());
        changeToArrowMenu();

    }


    /**
     * The following functions changes the cursor to an hand cursor when we enter the text area
     */
    public void rectangleTextHand(){this.changeCursorToHand(rectangleText);}
    public void circleTextHand(){this.changeCursorToHand(circleText);}
    public void arrowTextHand(){this.changeCursorToHand(arrowText);}
    public void curvedLineTextHand(){this.changeCursorToHand(curvedLineText);}
    public void lineTextHand(){this.changeCursorToHand(lineText);}


    /**
     * Change the menu to the menu indicated by the value given in parameter
     * @param idNew                 Corresponds to the id of the menu which is going to be set
     */
    private void changeToMenu(int idNew){
        shapeScene.setRoot(allShapes.get(idNew));
        if (idCurrent != idNew){
            clearShapeMenus();
        }
        idCurrent = idNew ;
    }

    /**
     * Change the text color once the menu is selected
     * @param id                    Id of the menu
     */
    private void changeTextColor(int id){
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
     * These following functions change the menu to the one described by the name of the method
     */
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
     *
     * @param text when given text is hovered, cursor changes to hand.
     */
    private void changeCursorToHand(Text text){
        text.setCursor(Cursor.HAND);
    }


    /**
     * Add a scene to the array list containing all the menus and add its controller to an array too
     * @param scenePath             Path to the corresponding fxml file
     */
    private void addScene(String scenePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
            allShapes.add(loader.load());
            allControllers.add(loader.getController());
        }
        catch(Exception exc){
            System.out.println("Error loading all screen" + scenePath);
            exc.printStackTrace();
        }
    }

    public void setCompileListener(CompileListener listener){
        compileListener = listener;
    }

    /**
     * Create a shape once all the fields are valid. It is called from the button "Confirm" in the pop-up window which
     * is used to create a new shape
     */
    public void confirmShape(){
        //Verify Fields
        boolean allFieldsValid = true;

        ArrayList<String> allFields = allControllers.get(idCurrent).getAllFields();
        ArrayList<Float> allDataInField = new ArrayList<>();
        String redStyle = "-fx-text-box-border: red";
        String normalStyle = "";
        for(int i=0; i<allFields.size() ;i++) {
            String tempStringInField = allFields.get(i);
            if (!fieldChecker.isValidNumber(tempStringInField) || tempStringInField == null) {
                allFieldsValid = false;
                if (i < allControllers.get(idCurrent).getAllTextFields().size()) {
                    allControllers.get(idCurrent).getAllTextFields().get(i).setStyle(redStyle);
                }
            } else {
                allDataInField.add(Float.parseFloat(tempStringInField));
                if (i < allControllers.get(idCurrent).getAllTextFields().size()) {
                    allControllers.get(idCurrent).getAllTextFields().get(i).setStyle(normalStyle);
                }
            }
        }
        //Nothing happened if fields are wrong or empty
        if(!allFieldsValid)
            return;

        Shape s = FactoryShape.getInstance(idCurrent,allDataInField,allControllers.get(idCurrent).getColor());

        compileListener.addShape(s);
        compileListener.closePopup();
    }


}
