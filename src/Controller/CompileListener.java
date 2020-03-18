package Controller;

import View.ScreenHandler;
import View.ShapeMenu.ShapeMenuViewController;
import Model.FileHandler;


import Model.Session;
import Model.Shapes.Circle;
import Model.Shapes.Rectangle;
import Model.Shapes.Shape;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CompileListener extends ControllerSuperclass implements Initializable {

    @FXML private TextArea codeInterface;
    @FXML private VBox suiviForme;
    @FXML private ScrollPane scroll;
    @FXML private Button addShapeButton;
    private Stage popUpStage;
    private ShapeMenuViewController shapeMenuViewController;
    private ShapeMenuController shapeMenuController;



    /**
     * when we click on "compile" button it send the text to a save file
     *
     */
    @FXML
    public void compile() {
        FileHandler fh = new FileHandler();
        fh.setupSaveProjectDirectory("project");
        fh.createProject(codeInterface.getText());

    }

    @FXML
    public void modifButtonAction()
    {
        ScreenHandler.changeScene(ScreenHandler.MODIFICATIONSCREEN);
    }

    @Override
    public void update() {
        //Nothing to update for now but the project it will needs to display will probably be updated
    }

    /**
     * Action of "Log Out" button. Logs current user out and goes back to LoginScreen.
     */
    public void logout() {
        Session.getInstance().logOut();
        ScreenHandler.changeScene(ScreenHandler.LOGINSCREEN);
    }

    /**
     * Adds a Label to the panel on the right hand side of the screen describing the
     * shape that was added.
     * @param shape         Shape to be added
     */
    @FXML
    public void addShape(Shape shape) {
        //String shapeText = "Added rectangle of length 5 and width 3 at position 5 ggggggeqegggggggggg.";
        String shapeText = createString(shape);

        Label label = new Label(shapeText);
        label.setTextFill(Paint.valueOf("White"));
        label.setStyle("-fx-border-color: #3A3A3A; -fx-background-color: #4D4D4D");
        label.setPadding(new Insets(5,5,5,5));
        label.prefWidthProperty().bind(suiviForme.prefWidthProperty());
        label.setWrapText(true);
        suiviForme.getChildren().add(label);
    }

    /**
     * Initialization of the region where the names of the added shapes will appear
     * (VBox and ScrollPane).
     * @param location      URL (not used)
     * @param resources     ResourceBundle(not used)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        suiviForme.prefWidthProperty().bind(scroll.prefWidthProperty());
        suiviForme.prefHeightProperty().bind(scroll.prefHeightProperty());
        try{
            popUpInitialize();
        } catch(Exception ignored){

        }

    }

    private void popUpInitialize() throws IOException{
        popUpStage = new Stage();
        popUpStage.setTitle("Add Shape Menu");
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ShapeMenu/FxmlFiles/addShapeMenu.fxml"));
        Parent addShapeMenuRoot = loader.load();
        shapeMenuViewController = loader.getController();
        shapeMenuController = new ShapeMenuController();
        shapeMenuController.setCompileListener(this);
        shapeMenuViewController.setShapeMenuController(shapeMenuController);
        popUpStage.setScene(new Scene(addShapeMenuRoot));
    }

    /**
     * Creation of the String to insert into the label when a new shape has been added.
     * This String is different depending on the shape added.
     * @param shape             Shape which has to be converted in a string
     * @return returnString     String which describes the shape given in parameter
     */
    public String createString(Shape shape) {
        String returnString = "Added";
        if ( shape instanceof Circle) {
            returnString += "circle of radius " + ((Circle) shape).getRadius() + "and center (" + shape.getPosX() + ", "+ shape.getPosY() + ").";
        }
        else if ( shape instanceof Rectangle) {
            returnString += "Rectangle of height " + ((Rectangle) shape).getHeight() + "and width " + ((Rectangle) shape).getWidth() + ".";
        }

        return returnString;
    }


    /**
     * Create a pop-up which allows to create a new shape
     */
    @FXML
    public void addShapeMenu(){
        shapeMenuViewController.update();
        popUpStage.show();
    }

    @FXML
    public void changeMouseToHand(){
        addShapeButton.setCursor(Cursor.HAND);
    }

    public void closePopup() {
        popUpStage.hide();
    }
}
