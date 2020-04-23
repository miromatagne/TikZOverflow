package View;

import Controller.LatexController;
import Controller.ScreenHandler;
import Controller.Session;
import Controller.ShapeMenuController;
import Model.Shapes.Shape;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Handles main screen interaction, including TikZ compilation, display and shape addition.
 */
public class MainPageController extends ControllerSuperclass implements Initializable {

    public static final int SHAPES_ONLY = 0;
    public static final int FULL_CODE = 1;

    private int currentCodeDisplay;

    @FXML
    private TextArea codeInterface;
    @FXML
    private VBox shapeList;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Button addShapeButton;
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
    @FXML
    private Button fullCodeButton;
    @FXML
    private Text codeTitle;

    private String textSaved = null;

    /**
     * Update is a function of the ControllerSuperClass and will be called every time the mainPage screen is displayed.
     */
    public void update() {
        //update of codeInterface a textArea
        if (textSaved == null) {
            String textInLatexFile = latexController.getTextInFile();
            // Display only shapes from .tex file
            currentCodeDisplay = SHAPES_ONLY;
            textSaved = latexController.extractShapesSubCode(textInLatexFile, true);
        }
        fillWithTextSaved();
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
        fullCodeButton.setDisable(false);
        fullCodeButton.setVisible(true);
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
            fullCodeButton.setVisible(false);
            fullCodeButton.setDisable(true);
            errorsButton.setText("Hide errors");
            codeInterface.setEditable(false);

            String errors = latexController.getFileHandler().getErrors();

            codeInterface.setStyle("-fx-border-color: #3A3A3A; -fx-border-insets: 0,0,0,0; -fx-focus-traversable: false; -fx-border-width: 2; -fx-background-color: transparent; -fx-text-fill: #ff1200; -fx-highlight-fill: blue; -fx-highlight-text-fill: red; -fx-control-inner-background: #404040; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            codeInterface.setText("You got " + errorsCount + " errors on the last compilation \n" + errors);
        }
    }

    /**
     * Switches between editable, shapes-only display and uneditable full code display.
     */
    public void switchCodeDisplay() {
        if(currentCodeDisplay == SHAPES_ONLY){
            displayFullCode();
        } else if(currentCodeDisplay == FULL_CODE){
            displayShapesOnly();
        }
    }

    /**
     * Shows full LaTeX code. This code cannot be edited.
     */
    private void displayFullCode() {
        latexController.saveTikz(latexController.buildFullCodeFromShapesOnlyCode(codeInterface.getText()));
        currentCodeDisplay = FULL_CODE;
        textSaved = codeInterface.getText();
        String textInLatexFile = latexController.getTextInFile(); // full code is NEVER saved as textSaved
        codeInterface.setStyle("-fx-border-color: #3A3A3A; -fx-border-insets: 0,0,0,0; -fx-focus-traversable: false; -fx-border-width: 2; -fx-background-color: transparent; -fx-text-fill: grey; -fx-highlight-fill: blue; -fx-highlight-text-fill: grey; -fx-control-inner-background: #404040; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        codeInterface.setText(textInLatexFile);
        codeInterface.setEditable(false);
        compileButton.setVisible(false);
        compileButton.setDisable(true);
        errorsButton.setVisible(false);
        errorsButton.setDisable(true);
        fullCodeButton.setText("Display shapes-only code");
        codeTitle.setText("Full LaTeX code");
    }

    /**
     * Shows shapes-only TikZ code. This code can be edited.
     */
    private void displayShapesOnly() {
        currentCodeDisplay = SHAPES_ONLY;
        fillWithTextSaved();
        codeInterface.setStyle("-fx-border-color: #3A3A3A; -fx-border-insets: 0,0,0,0; -fx-focus-traversable: false; -fx-border-width: 2; -fx-background-color: transparent; -fx-text-fill: white; -fx-highlight-fill: blue; -fx-highlight-text-fill: white; -fx-control-inner-background: #404040; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        codeInterface.setEditable(true);
        compileButton.setVisible(true);
        compileButton.setDisable(false);
        errorsButton.setVisible(true);
        errorsButton.setDisable(false);
        fullCodeButton.setText("Display full code");
        codeTitle.setText("Shapes-only code");
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
     * Adds shape code to the coding interface according to the shape received as a parameter
     *
     * @param shape Shape whose code has to be generated in the coding interface
     */
    public void addShape(Shape shape) {
        String code = shape.generateAndGetTikzCode();
        codeInterface.appendText(code);
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
        shapeList.prefWidthProperty().bind(scroll.prefWidthProperty());
        shapeList.prefHeightProperty().bind(scroll.prefHeightProperty());
        shapeMenuController.setMainPageController(this);
        try {
            shapeMenuController.popUpInitialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a pop-up which allows to create a new shape
     */
    @FXML
    public void addShapeMenu() {
        shapeMenuController.showPopUp();
    }

    @FXML
    public void changeMouseToHand() {
        addShapeButton.setCursor(Cursor.HAND);
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
        shapeList.getChildren().clear(); // Clear all the added shapes
        latexController.getFileHandler().clearErrors(); // Clear all the errors
        errorsButton.setText("Errors (0)");
        textSaved = null; // Set the textSaved to null in order to display the correct one during the next login
        fillWithTextSaved();
    }

    public int getCurrentCodeDisplay() {
        return currentCodeDisplay;
    }
}
