package View;

import Controller.LatexController;
import Controller.ScreenHandler;
import Controller.Session;
import Controller.ShapeMenuController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            textSaved = extractShapesSubCode(textInLatexFile, true);
        }
        fillWithTextSaved();
    }

    private void fillWithTextSaved() {
        codeInterface.setText(this.textSaved);
    }

    private String extractShapesSubCode(String fullCode, boolean trim) {
        Pattern pattern = Pattern.compile("\\\\begin\\{tikzpicture}.*\\\\end\\{tikzpicture}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(fullCode);
        if(matcher.find()) {
            int index = 1;
            String totalString = "";
            String[] strings = matcher.group(0).split("\n");
            while (index < strings.length-1){
                if(trim) {
                    totalString = totalString.concat(strings[index++].trim()+"\n");
                } else {
                    totalString = totalString.concat(strings[index++]+"\n");
                }
            }
            return totalString;
        }
        return null;
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
            StringBuilder shapesOnlyCode = new StringBuilder();
            for(String line : codeInterface.getText().split("\n")){
                shapesOnlyCode.append("\t").append(line.trim()).append("\n");
            }
            sourceCode = latexController.getTextInFile()
                    .replace(Objects.requireNonNull(extractShapesSubCode(latexController.getTextInFile(), false)), shapesOnlyCode.toString());
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

    public void switchCodeDisplay() {
        if(currentCodeDisplay == SHAPES_ONLY){
            displayFullCode();
        } else if(currentCodeDisplay == FULL_CODE){
            displayShapesOnly();
        }
    }

    private void displayFullCode() {
        //TODO : save text before switching views
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
     * Adds a Label to the panel on the right hand side of the screen describing the
     * shape that was added.
     *
     * @param shapeText Description of the Shape to be added
     */
    public void addShape(String shapeText) {
        Label label = new Label(shapeText);
        label.setTextFill(Paint.valueOf("White"));
        label.setStyle("-fx-border-color: #3A3A3A; -fx-background-color: #4D4D4D");
        label.setPadding(new Insets(5, 5, 5, 5));
        label.prefWidthProperty().bind(shapeList.prefWidthProperty());
        label.setWrapText(true);
        shapeList.getChildren().add(label);
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


    public void mouseDragged(MouseEvent mouseEvent) {
        //System.out.println("Mouse at: (" + mouseEvent.getX() + ", " + mouseEvent.getY() + ")");
        canvas.setTranslateX(mouseEvent.getX());
        canvas.setTranslateY(mouseEvent.getY());
    }

    public void dragDetected(MouseEvent mouseEvent) {
        //System.out.println("Start: (" + mouseEvent.getX() + ", " + mouseEvent.getY() + ")");
        Parent root = ScreenHandler.getScreens().get(ScreenHandler.MAIN_PAGE);
        canvas.setTranslateX(mouseEvent.getX());
        canvas.setTranslateY(mouseEvent.getY());
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLUE);
        graphicsContext.fillRect(0, 0, 100, 100);
        ((GridPane) root).getChildren().add(canvas);
        canvasIsCreated = true;
    }

    public void mouseDragReleased(MouseEvent mouseEvent) {
        if(canvasIsCreated) {
            System.out.println("Canvas removed");
            Parent root = ScreenHandler.getScreens().get(ScreenHandler.MAIN_PAGE);
            ((GridPane) root).getChildren().remove(canvas);
            canvasIsCreated = false;
        }
    }
}
