package View;

import Controller.ShapeMenuController;
import Model.FileHandler;
import Model.PDFHandler;
import View.ShapeMenu.ShapeMenuViewController;
import Controller.ScreenHandler;
import Model.LatexCompiler;
import Controller.Session;
import Model.Shapes.*;
import Controller.ScreenHandler;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController extends ControllerSuperclass  implements Initializable{

    @FXML private TextArea codeInterface;
    @FXML private VBox suiviForme;
    @FXML private ScrollPane scroll;
    @FXML private Button addShapeButton;
    @FXML private ImageView renderedImageView;
    private Stage popUpStage;
    private ShapeMenuViewController shapeMenuViewController;
    private ShapeMenuController shapeMenuController;


    @FXML private Button errorsButton;
    @FXML private Button compileButton;

    /**
     * when we click on "compile" button it sends the text to a save file
     *
     */
    @FXML
    public void compile() throws Exception {
        FileHandler fileHandler = new FileHandler();
        fileHandler.makeTexFile(Session.getInstance().getUser(), codeInterface.getText());
        String filePath = "./Latex/" + Session.getInstance().getUser().getUsername() + ".tex";

        try {
            LatexCompiler.runProcess(filePath);
            String pdfPath = "./Latex/out/" + Session.getInstance().getUser().getUsername() + ".pdf";
            renderImage(pdfPath);
        }
        catch(Exception e){
            System.err.println("Error in compilation :  " + e.toString());
            fileHandler.errorLogs("./Latex/out/" + Session.getInstance().getUser().getUsername() + ".log");
        }
        fileHandler.errorLogs("./Latex/out/" + Session.getInstance().getUser().getUsername() + ".log");
        int errorsCount = fileHandler.getErrorsCounter();
        errorsButton.setText("Errors (" + errorsCount + ")");
        System.out.println("You got "+ errorsCount+ " errors on the last compilation \n" + fileHandler.getErrors());
    }

    /**
     * when clicking on 'Hide errors' button, the user is sent back on the code interface
     * @param errorsCount
     */
    @FXML
    public void hideErrors(int errorsCount){
        compileButton.setDisable(false);
        compileButton.setVisible(true);

        errorsButton.setText("Errors (" + errorsCount + ")");
        codeInterface.setEditable(true);
        codeInterface.setStyle("-fx-border-color: #3A3A3A; -fx-border-insets: 0,0,0,0; -fx-focus-traversable: false; -fx-border-width: 2; -fx-background-color: transparent; -fx-text-fill: white; -fx-highlight-fill: blue; -fx-highlight-text-fill: white; -fx-control-inner-background: #404040; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        codeInterface.setText("");
    }

    /**
     * when clicking on 'errors (..)" button, the user is sent on a screen which shows him the errors after the last
     * compile
     * @throws Exception
     */
    @FXML
    public void showErrors() throws Exception {
        FileHandler fileHandler = new FileHandler();
        int errorsCount = fileHandler.getErrorsCounter();
        if(errorsButton.getText() == "Hide errors"){
            hideErrors(errorsCount);
        }
        else {

            compileButton.setDisable(true);
            compileButton.setVisible(false);
            fileHandler.makeTexFile(Session.getInstance().getUser(), codeInterface.getText());
            errorsButton.setText("Hide errors");
            codeInterface.setEditable(false);

            String errors = fileHandler.getErrors();

            codeInterface.setStyle("-fx-border-color: #3A3A3A; -fx-border-insets: 0,0,0,0; -fx-focus-traversable: false; -fx-border-width: 2; -fx-background-color: transparent; -fx-text-fill: #ff1200; -fx-highlight-fill: blue; -fx-highlight-text-fill: red; -fx-control-inner-background: #404040; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            codeInterface.setText("You got "+ errorsCount+ " errors on the last compilation \n" + errors);
        }


    }

    private void renderImage(String pdfPath) {
        System.out.println(pdfPath);
        PDFHandler pdfHandler = new PDFHandler(pdfPath);
        try {
            pdfHandler.convertPdfToImage();
        } catch (Exception e) {
            System.err.println("Error converting " + pdfPath + " to image");
            e.printStackTrace();
        }
        String imagePath = pdfPath.replace(".pdf", ".jpg");
        try {
            Image renderedImage = new Image(new FileInputStream(imagePath));
            renderedImageView.setImage(renderedImage);
        } catch (IOException e) {
            System.err.println("Image file not found");
            e.printStackTrace();
        }

    }

    @FXML
    public void modificationButtonAction()
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
        shapeMenuController.setMainPageController(this);
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
        String returnString = "Added ";
        if ( shape instanceof Circle) {
            returnString += "Circle of radius " + ((Circle) shape).getRadius() + " and center (" + shape.getPosX() + ","+ shape.getPosY() + ").";
        }
        else if ( shape instanceof Rectangle) {
            returnString += "Rectangle of height " + ((Rectangle) shape).getHeight() + " and width " + ((Rectangle) shape).getWidth() + ".";
        }
        else if ( shape instanceof Arrow) {
            returnString += "Arrow from (" + ((Arrow) shape).getxOrigin() + "," + ((Arrow) shape).getyOrigin() + ") to (" + ((Arrow) shape).getxDestination() + "," + ((Arrow) shape).getyDestination() + ").";
        }
        else if ( shape instanceof CurvedLine) {
            returnString += "Curved Line from (" + ((CurvedLine) shape).getxOrigin() + "," + ((CurvedLine) shape).getyOrigin() + ") to (" + ((CurvedLine) shape).getxDestination() + "," + ((CurvedLine) shape).getyDestination() + ").";
        }
        else if ( shape instanceof Line) {
            returnString += "Line from (" + ((Line) shape).getxOrigin() + "," + ((Line) shape).getyOrigin() + ") to (" + ((Line) shape).getxDestination() + "," + ((Line) shape).getyDestination() + ").";
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
