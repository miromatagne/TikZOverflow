package View;

import Controller.LatexController;
import Controller.ScreenHandler;
import Controller.Session;
import Controller.ShapeMenuController;
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

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles main screen interaction, including TikZ compilation, display and shape addition.
 */
public class MainPageController extends ControllerSuperclass implements Initializable {

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

    private String textSaved;

    /**
     * Update is a function of the ControllerSuperClass and will be called every time the mainPage screen is displayed.
     */
    public void update() {
        //update of codeInterface a textArea
        if (this.textSaved == null) {
            String textInLatexFile = latexController.getTextInFile();
            this.codeInterface.setText(textInLatexFile);
            this.textSaved = textInLatexFile;
        } else {
            this.fillWithTextSaved();
        }

        //Update of renderedImageView an ImageView containing the pdf corresponding to the latex code
        renderedImageView.setImage(null);
    }

    private void fillWithTextSaved() {
        this.codeInterface.setText(this.textSaved);
    }


    /**
     * Compiles code in text area into pdf file and displays it on UI.
     */
    @FXML
    public void compile() throws Exception {
        String errorsButtonText = latexController.compileTikz();
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
            this.textSaved = this.codeInterface.getText(); // Save the text in the box before showing the errors
            compileButton.setDisable(true);
            compileButton.setVisible(false);
            latexController.saveTikz();
            errorsButton.setText("Hide errors");
            codeInterface.setEditable(false);

            String errors = latexController.getFileHandler().getErrors();

            codeInterface.setStyle("-fx-border-color: #3A3A3A; -fx-border-insets: 0,0,0,0; -fx-focus-traversable: false; -fx-border-width: 2; -fx-background-color: transparent; -fx-text-fill: #ff1200; -fx-highlight-fill: blue; -fx-highlight-text-fill: red; -fx-control-inner-background: #404040; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            codeInterface.setText("You got " + errorsCount + " errors on the last compilation \n" + errors);
        }


    }

    @FXML
    public void modificationButtonAction() {
        ScreenHandler.changeScene(ScreenHandler.MODIFICATION_SCREEN);
    }

    /**
     * Action of "Log Out" button. Logs current user out and goes back to LoginScreen.
     */
    public void logout() {
        this.textSaved = null; // Set the textSaved to null in order to display the correct one during the next login
        Session.getInstance().logOut();
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
        System.out.println("init");
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
     */
    public void renderImage(Image renderedImage) {
        renderedImageView.setFitWidth(imageScrollPane.getWidth());
        renderedImageView.setImage(renderedImage);
    }
}
