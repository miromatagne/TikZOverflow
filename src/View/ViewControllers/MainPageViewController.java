package View.ViewControllers;

import Controller.*;
import Model.Shapes.Shape;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.Parent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles the main page screen interaction, including TikZ compilation, display and shape addition.
 */
public class MainPageViewController implements Initializable {

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
    private ImageView renderedImageView;

    @FXML
    private ScrollPane imageScrollPane;
    private MainPageViewControllerListener listener;
    private CodeInterfaceListener codeInterfaceListener;
    private AddNewShapeButtonListener shapeButtonListener;


    @FXML
    private Button errorsButton;
    @FXML
    private Button compileButton;

    private String textSaved = null;

    @FXML
    private Button fullCodeButton;
    @FXML
    private Text codeTitle;

    @FXML
    private Button buttonCircle, buttonRectangle, buttonTriangle, buttonArrow, buttonLine, buttonCurvedLine, buttonSquare;
    @FXML
    private ImageView imageCircle, imageRectangle, imageTriangle, imageArrow, imageLine, imageCurvedLine, imageSquare;
    private ImageView movingImage;
    private int movingShapeID;
    private Shape movingShape;

    final static int RECTANGLE = 0;
    final static int CIRCLE = 1;
    final static int LINE = 2;
    final static int CURVED_LINE = 3;
    final static int ARROW = 4;
    final static int TRIANGLE = 5;
    final static int SQUARE = 6;

    PredefinedShapesPanelController predefinedShapesPanelController;

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
        initializeImageButton();
        imageScrollPane.widthProperty().addListener((observable, oldValue, newValue) -> compile());
    }

    /**
     * Update is a function of the ControllerSuperClass and will be called every time the mainPage screen is displayed.
     */
    public void updateText() {
        //update of codeInterface a textArea
        if (textSaved == null) {
            textSaved = codeInterfaceListener.getShapesOnlyText();
            currentCodeDisplay = SHAPES_ONLY;
        }
        fillWithTextSaved();
    }

    /**
     * Fill the code interface with the text saved
     */
    private void fillWithTextSaved() {
        codeInterface.setText(this.textSaved);
    }


    /**
     * Compiles code in text area into pdf file and displays it on UI.
     */
    @FXML
    public void compile() {
        codeInterfaceListener.onCompilationAttempt(codeInterface.getText());
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
        int errorsCount = codeInterfaceListener.getErrorsCounter();
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

            String errors = codeInterfaceListener.getErrorsText();

            codeInterface.setStyle("-fx-border-color: #3A3A3A; -fx-border-insets: 0,0,0,0; -fx-focus-traversable: false; -fx-border-width: 2; -fx-background-color: transparent; -fx-text-fill: #ff1200; -fx-highlight-fill: blue; -fx-highlight-text-fill: red; -fx-control-inner-background: #404040; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            codeInterface.setText("You got " + errorsCount + " errors on the last compilation \n" + errors);
        }
    }

    /**
     * Switches between editable, shapes-only display and uneditable full code display.
     */
    public void switchCodeDisplay() {
        if (currentCodeDisplay == SHAPES_ONLY) {
            displayFullCode();
        } else if (currentCodeDisplay == FULL_CODE) {
            displayShapesOnly();
        }
    }

    /**
     * Shows full LaTeX code. This code cannot be edited.
     */
    private void displayFullCode() {
        codeInterfaceListener.saveCodeInterfaceCode(codeInterface.getText());
        currentCodeDisplay = FULL_CODE;
        textSaved = codeInterface.getText();
        String textInLatexFile = codeInterfaceListener.getFullText(); // full code is NEVER saved as textSaved
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
        codeInterfaceListener.saveCodeInterfaceCode(codeInterface.getText());
        listener.accountModificationRequest();
    }

    /**
     * Action of "Log Out" button. Logs current user out and goes back to LoginScreen.
     */
    public void logout() {
        codeInterfaceListener.saveCodeInterfaceCode(codeInterface.getText()); //saving code
        listener.onLogoutRequest(); //requesting logout
    }


    /**
     * Create a pop-up which allows to create a new shape
     */
    @FXML
    public void addShapeMenu() {
        shapeButtonListener.onButtonPressed();
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
        renderedImageView.setImage(renderedImage);
        renderedImageView.setFitWidth(imageScrollPane.getWidth());
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
     *
     * @param imageButton Image to bind
     * @param button      Button to bind
     */
    private void bindImageButton(ImageView imageButton, Button button) {
        //0.6 is an empirical value
        imageButton.fitWidthProperty().bind(button.widthProperty().multiply(0.6));
        imageButton.fitHeightProperty().bind(button.heightProperty().multiply(0.6));
    }

    /**
     * This method creates the drag of the mouse
     *
     * @param event  Mouse event
     * @param button The source of the drag
     */
    private void createDragAndDrop(MouseEvent event, Button button) {
        Dragboard db = button.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        cb.putString("shapeTransfer");
        db.setContent(cb);
        event.consume();

    }

    /**
     * The following methods send a message to the controller to create the corresponding shape when the button is clicked
     */
    public void circleClicked(MouseEvent mouseEvent) {
        movingShape = predefinedShapesPanelController.createShape(CIRCLE);
        movingShapeID = CIRCLE;
        createDragAndDrop(mouseEvent, buttonCircle);
    }

    public void rectangleClicked(MouseEvent mouseEvent) {
        movingShape = predefinedShapesPanelController.createShape(RECTANGLE);
        movingShapeID = RECTANGLE;
        createDragAndDrop(mouseEvent, buttonRectangle);
    }

    public void lineClicked(MouseEvent mouseEvent) {
        movingShape = predefinedShapesPanelController.createShape(LINE);
        movingShapeID = LINE;
        createDragAndDrop(mouseEvent, buttonLine);
    }

    public void curvedLineClicked(MouseEvent mouseEvent) {
        movingShape = predefinedShapesPanelController.createShape(CURVED_LINE);
        movingShapeID = CURVED_LINE;
        createDragAndDrop(mouseEvent, buttonCurvedLine);
    }

    public void arrowClicked(MouseEvent mouseEvent) {
        movingShape = predefinedShapesPanelController.createShape(ARROW);
        movingShapeID = ARROW;
        createDragAndDrop(mouseEvent, buttonArrow);
    }

    public void squareClicked(MouseEvent mouseEvent) {
        movingShape = predefinedShapesPanelController.createShape(SQUARE);
        movingShapeID = SQUARE;
        createDragAndDrop(mouseEvent, buttonSquare);
    }

    public void triangleClicked(MouseEvent mouseEvent) {
        movingShape = predefinedShapesPanelController.createShape(TRIANGLE);
        movingShapeID = TRIANGLE;
        createDragAndDrop(mouseEvent, buttonTriangle);
    }

    /**
     * This method generates the image that follows the mouse during the drag and drop gesture
     *
     * @param path path of the image to create
     */
    public void createMovingImage(String path) {
        Parent root = listener.getRoot();
        movingImage = new ImageView(path);
        movingImage.setFitHeight(50);
        movingImage.setFitWidth(50);
        ((GridPane) root).getChildren().add(movingImage);
    }


    /**
     * This method drop the shape at the position of the mouse
     *
     * @param event drag event
     */
    public void handleDragDropped(DragEvent event) {
        double x = event.getX();
        double y = event.getY();
        if (errorsButton.getText().equals("Hide errors")) {
            hideErrors(codeInterfaceListener.getErrorsCounter());
        }
        if (movingImage != null) {
            Parent root = listener.getRoot();
            ((GridPane) root).getChildren().remove(movingImage);
        }
        if (movingShape != null) {
            listener.onReleaseShape(x, y, movingShape);
        }
        compile();

    }

    /**
     * This method create an image of the dragged shape if the mouse enters the PDF area
     */
    public void handleDragEntered() {
        if (movingShapeID == CIRCLE) {
            createMovingImage("defaultCircle.png");
        } else if (movingShapeID == RECTANGLE) {
            createMovingImage("defaultRectangle.png");
        } else if (movingShapeID == LINE) {
            createMovingImage("defaultLine.png");
        } else if (movingShapeID == CURVED_LINE) {
            createMovingImage("defaultCurvedLine.png");
        } else if (movingShapeID == ARROW) {
            createMovingImage("defaultArrow.png");
        } else if (movingShapeID == SQUARE) {
            createMovingImage("defaultSquare.png");
        } else if (movingShapeID == TRIANGLE) {
            createMovingImage("defaultTriangle.png");
        }
    }

    /**
     * This method remove the image of the dragged shape if the mouse exits the PDF area
     */
    public void handleDragExited() {
        if (movingImage != null) {
            Parent root = listener.getRoot();
            ((GridPane) root).getChildren().remove(movingImage);
        }
    }

    /**
     * This method shows the accessible zone for the drop
     *
     * @param event drag event
     */
    public void handleDragOver(DragEvent event) {
        if (movingImage != null) {
            movingImage.setTranslateX(event.getX() + buttonArrow.getLayoutX() * 1.95);
            movingImage.setTranslateY(event.getY() - 45);
        }
        event.acceptTransferModes(TransferMode.ANY);
    }

    public void setListener(MainPageViewControllerListener listener) {
        this.listener = listener;
    }

    public void setPredefinedShapesPanelController(PredefinedShapesPanelController predefinedShapesPanelController) {
        this.predefinedShapesPanelController = predefinedShapesPanelController;
    }

    public void setShapeButtonListener(AddNewShapeButtonListener shapeButtonListener) {
        this.shapeButtonListener = shapeButtonListener;
    }

    public void setErrorButtonText(String errorsButtonText) {
        errorsButton.setText(errorsButtonText);
    }

    public void setCodeInterfaceListener(CodeInterfaceListener listener) {
        this.codeInterfaceListener = listener;
    }


    public ImageView getRenderedImageView() {
        return renderedImageView;
    }

    public ScrollPane getImageScrollPane() {
        return imageScrollPane;
    }

    /**
     * Send a request to the listener to create a shape
     *
     * @param shape shape to be created
     */
    public void addShape(Shape shape) {
        listener.addShapeRequest(shape);
    }

    public interface MainPageViewControllerListener {
        void onLogoutRequest();

        void accountModificationRequest();

        void onReleaseShape(double x, double y, Shape movingShape);

        void addShapeRequest(Shape shape);

        Parent getRoot();
    }

    public interface AddNewShapeButtonListener {
        void onButtonPressed();
    }

    public interface CodeInterfaceListener {
        void onCompilationAttempt(String code);

        String getShapesOnlyText();

        int getErrorsCounter();

        String getFullText();

        void saveCodeInterfaceCode(String tikzCode);

        String getErrorsText();
    }


}
