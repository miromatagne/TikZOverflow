package View;

import Controller.*;
import Controller.ScreenHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Handles main screen interaction, including TikZ compilation, display and shape addition.
 */
public class MainPageController extends ControllerSuperclass implements Initializable {


    public static final int SHAPES_ONLY = 0;
    public static final int FULL_CODE = 1;

    private int currentCodeDisplay;

    private boolean canvasIsCreated = false;

    private Canvas canvas = new Canvas(250,250);

    @FXML
    private TextArea codeInterface;
    @FXML
    private ImageView renderedImageView;
    @FXML
    private ScrollPane imageScrollPane;
    private ShapeMenuController shapeMenuController = new ShapeMenuController();
    private LatexController latexController = new LatexController(this);


    @FXML
    private Button errorsButton;
    @FXML
    private Button compileButton;

    private String textSaved = null;

    @FXML
    private Button buttonCircle, buttonRectangle, buttonTriangle, buttonArrow, buttonLine,buttonCurvedLine, buttonSquare;
    @FXML
    private ImageView imageCircle, imageRectangle, imageTriangle, imageArrow, imageLine, imageCurvedLine, imageSquare;
    private ImageView movingImage;

    final static int RECTANGLE = 0;
    final static int CIRCLE = 1;
    final static int LINE = 2;
    final static int CURVED_LINE = 3;
    final static int ARROW = 4;
    final static int SQUARE = 5;
    final static int TRIANGLE = 6;

    PredefinedShapesPanelController predefinedShapesPanelController;

    /**
     * Update is a function of the ControllerSuperClass and will be called every time the mainPage screen is displayed.
     */
    public void update() {
        //update of codeInterface a textArea
        if (textSaved == null) {
            String textInLatexFile = latexController.getTextInFile();
            codeInterface.setText(textInLatexFile);
            textSaved = textInLatexFile;
        } else {
            fillWithTextSaved();
        }
    }

    private void fillWithTextSaved() {
        codeInterface.setText(this.textSaved);
    }


    /**
     * Compiles code in text area into pdf file and displays it on UI.
     *
     * @throws IOException If reading the log was unsuccessful.
     */
    @FXML
    public void compile() throws IOException {
        String sourceCode = "";
        if(currentCodeDisplay == SHAPES_ONLY){
            sourceCode = latexController.buildFullCodeFromShapesOnlyCode(codeInterface.getText());
        } else if (currentCodeDisplay == FULL_CODE) {
            sourceCode = codeInterface.getText();
        }
        String errorsButtonText = latexController.compileTikz(sourceCode);
        errorsButton.setText(errorsButtonText);
    }


    /**
     * when clicking on 'Hide errors' button, the user is sent back on the code interface
     *
     * @param errorsCount number of errors
     */
    @FXML
    public void hideErrors(int errorsCount) {
        compileButton.setDisable(false);
        compileButton.setVisible(true);

        errorsButton.setText("Errors (" + errorsCount + ")");
        codeInterface.setEditable(true);
        codeInterface.setStyle("-fx-border-color: #3A3A3A; -fx-border-insets: 0,0,0,0; -fx-focus-traversable: false; -fx-border-width: 2; -fx-background-color: transparent; -fx-text-fill: white; -fx-highlight-fill: blue; -fx-highlight-text-fill: white; -fx-control-inner-background: #404040; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        this.fillWithTextSaved(); // Put the saved text in the codeInterface
    }

    /**
     * when clicking on "errors (..)" button, the user is sent on a screen which shows him the errors after the last
     * compile
     */
    @FXML
    public void showErrors() {

        int errorsCount = latexController.getFileHandler().getErrorsCounter();
        if (errorsButton.getText().equals("Hide errors")) {
            hideErrors(errorsCount);
        } else {
            textSaved = codeInterface.getText(); // Save the text in the box before showing the errors

            compileButton.setDisable(true);
            compileButton.setVisible(false);
            errorsButton.setText("Hide errors");
            codeInterface.setEditable(false);

            String errors = latexController.getFileHandler().getErrors();

            codeInterface.setStyle("-fx-border-color: #3A3A3A; -fx-border-insets: 0,0,0,0; -fx-focus-traversable: false; -fx-border-width: 2; -fx-background-color: transparent; -fx-text-fill: #ff1200; -fx-highlight-fill: blue; -fx-highlight-text-fill: red; -fx-control-inner-background: #404040; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            codeInterface.setText("You got " + errorsCount + " errors on the last compilation \n" + errors);
        }


    }


    /**
     * Action of "Modification" button. Sends user to the account modification screen.
     */
    @FXML
    public void modificationButtonAction() {
        textSaved = codeInterface.getText(); // Save the text
        ScreenHandler.changeScene(ScreenHandler.MODIFICATION_SCREEN);
    }

    /**
     * Action of "Log Out" button. Logs current user out and goes back to LoginScreen.
     */
    public void logout() {
        Session.getInstance().logOut();
        clearScreen();
        ScreenHandler.changeScene(ScreenHandler.LOGIN_SCREEN);
    }

    /**
     * Adds a Label to the panel on the right hand side of the screen describing the
     * shape that was added.
     *
     * @param shapeText Description of the Shape to be added
     */
    public void addShape(String shapeText) {
       /* Label label = new Label(shapeText);
        label.setTextFill(Paint.valueOf("White"));
        label.setStyle("-fx-border-color: #3A3A3A; -fx-background-color: #4D4D4D");
        label.setPadding(new Insets(5, 5, 5, 5));
        label.prefWidthProperty().bind(shapeList.prefWidthProperty());
        label.setWrapText(true);
        shapeList.getChildren().add(label);*/
    }

    /**
     * Initialization of the region where the names of the added shapes will appear
     * (VBox and ScrollPane).
     *
     * @param location  URL (not used)
     * @param resources ResourceBundle(not used)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        shapeMenuController.setMainPageController(this);
        try {
            shapeMenuController.popUpInitialize();
        } catch (Exception e) {
            e.printStackTrace();
        }

        predefinedShapesPanelController = new PredefinedShapesPanelController();
        initializeImageButton();


    }

    /**
     * Create a pop-up which allows to create a new shape
     */
    @FXML
    public void addShapeMenu() {
        shapeMenuController.showPopUp();
    }


    public TextArea getCodeInterface() {
        return codeInterface;
    }

    /**
     * Renders image from compilation on UI.
     *
     * @param renderedImage Image from the compilation of the source code.
     */
    public void renderImage(Image renderedImage) {
        renderedImageView.setFitWidth(imageScrollPane.getWidth());
        renderedImageView.setImage(renderedImage);
    }

    /**
     * Clear the main page screen. Useful when the user logs out
     */
    private void clearScreen() {
        renderedImageView.setImage(null);
        //shapeList.getChildren().clear(); // Clear all the added shapes
        latexController.getFileHandler().clearErrors(); // Clear all the errors
        errorsButton.setText("Errors (0)");
        textSaved = null; // Set the textSaved to null in order to display the correct one during the next login
        fillWithTextSaved();
    }

    /**
     * Initialize the image buttons for the predefined shapes
     */
    private void initializeImageButton() {
        bindImageButton(imageCircle, buttonCircle);
        bindImageButton(imageRectangle, buttonRectangle);
        bindImageButton(imageTriangle, buttonTriangle);
        bindImageButton(imageArrow, buttonArrow);
        bindImageButton(imageLine, buttonLine);
        bindImageButton(imageCurvedLine, buttonCurvedLine);
        bindImageButton(imageSquare, buttonSquare);

    }


    /**
     * Bind the image and the button to keep a scale between them.
     * @param imageButton       Image to bind
     * @param button            Button to bind
     */
    private void bindImageButton(ImageView imageButton, Button button) {
        //0.6 is an empirical value
        imageButton.fitWidthProperty().bind(button.widthProperty().multiply(0.6));
        imageButton.fitHeightProperty().bind(button.heightProperty().multiply(0.6));
    }

    /**
     * The following methods send a message to the controller to create the corresponding shape when the button is clicked
     */
    public void circleClicked(MouseEvent mouseEvent) {
        predefinedShapesPanelController.createShape(CIRCLE);
        createMovingImage("defaultCircle.png", mouseEvent);

    }

    public void rectangleClicked(MouseEvent mouseEvent) {
        predefinedShapesPanelController.createShape(RECTANGLE);
        createMovingImage("defaultRectangle.png", mouseEvent);
    }

    public void lineClicked(MouseEvent mouseEvent) {
        predefinedShapesPanelController.createShape(LINE);
        createMovingImage("defaultLine.png", mouseEvent);
    }

    public void curvedLineClicked(MouseEvent mouseEvent) {
        predefinedShapesPanelController.createShape(CURVED_LINE);
        createMovingImage("defaultCurvedLine.png", mouseEvent);
    }

    public void arrowClicked(MouseEvent mouseEvent) {
        predefinedShapesPanelController.createShape(ARROW);
        createMovingImage("defaultArrow.png", mouseEvent);
    }

    public void squareClicked(MouseEvent mouseEvent) {
        predefinedShapesPanelController.createShape(SQUARE);
        createMovingImage("defaultSquare.png", mouseEvent);
    }

    public void triangleClicked(MouseEvent mouseEvent) {
        predefinedShapesPanelController.createShape(TRIANGLE);
        createMovingImage("defaultTriangle.png", mouseEvent);
    }

    public void createMovingImage(String path, MouseEvent mouseEvent){
        Parent root = ScreenHandler.getScreens().get(ScreenHandler.MAIN_PAGE);
        Button button = (Button) mouseEvent.getSource();
        movingImage = new ImageView(path);
        movingImage.setFitHeight(50);
        movingImage.setFitWidth(50);
        movingImage.setTranslateX(mouseEvent.getX() + button.getLayoutX());
        movingImage.setTranslateY(mouseEvent.getY() + button.getLayoutY());
        ((GridPane) root).getChildren().add(movingImage);
    }



    public void mouseDragged(MouseEvent mouseEvent) {
        Button button = ((Button) mouseEvent.getSource());
        System.out.println("Mouse at: (" + mouseEvent.getX() + ", " + mouseEvent.getY() + ")");
        if(movingImage != null) {
            movingImage.setTranslateX(mouseEvent.getX() + button.getLayoutX());
            movingImage.setTranslateY(mouseEvent.getY() + button.getLayoutY());
        }
    }


    public void mouseDragReleased(MouseEvent mouseEvent) {
        System.out.println("released");
        if(movingImage != null) {
            //System.out.println("Canvas removed");
            Parent root = ScreenHandler.getScreens().get(ScreenHandler.MAIN_PAGE);
            ((GridPane) root).getChildren().remove(movingImage);
        }
    }
}
