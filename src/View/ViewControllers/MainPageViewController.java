package View.ViewControllers;

import Controller.*;
import Model.Shapes.Shape;
import Model.Shapes.ShapeFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles the main page screen interaction, including TikZ compilation, display and shape addition.
 */
public class MainPageViewController implements Initializable {

    public static final int SHAPES_ONLY = 0;
    public static final int FULL_CODE = 1;
    public static final int ERRORS = 2;

    private int currentCodeDisplay;

    @FXML
    private TextArea codeInterface;

    @FXML
    private ImageView renderedImageView;

    @FXML
    private ScrollPane imageScrollPane;
    private MainPageViewControllerListener listener;
    private CodeInterfaceListener codeInterfaceListener;
    private AddNewShapeButtonListener shapeButtonListener;

    @FXML
    private Button changeModeButton;

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

    private static final int IMAGE_SIZE = 50;
    private static final int DRAG_IMAGE_OFFSET_X = 246;  // empirical values
    private static final int DRAG_IMAGE_OFFSET_Y = -45;
    private static final double SCALE_FACTOR = 0.6;

    PredefinedShapesPanelController predefinedShapesPanelController;
    private boolean rightHandMode = true;

    /**
     * Initialization of the region where the names of the added shapes will appear
     * (VBox and ScrollPane).
     *
     * @param location  URL (not used)
     * @param resources ResourceBundle(not used)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeImageButton();
        imageScrollPane.widthProperty().addListener((observable, oldValue, newValue) -> compile());
    }

    /**
     * Updates the text of the code interface area.
     */
    public void updateText() {
        if (textSaved == null) {
            textSaved = codeInterfaceListener.getShapesOnlyText();
            currentCodeDisplay = SHAPES_ONLY;
        }
        fillWithTextSaved();
    }

    /**
     * Fill the code interface with the text saved.
     */
    private void fillWithTextSaved() {
        codeInterface.setText(this.textSaved);
    }


    /**
     * Relays action of the compile button (sends code interface text needed for compilation).
     */
    @FXML
    public void compile() {
        codeInterfaceListener.onCompilationAttempt(codeInterface.getText());
    }

    /**
     * When clicking on 'Hide errors' button, the user is sent back on the code interface.
     *
     * @param errorsCount number of errors
     */
    @FXML
    public void hideErrors(int errorsCount) {
        currentCodeDisplay = SHAPES_ONLY;
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
     * compilation.
     */
    @FXML
    public void showErrors() {
        currentCodeDisplay = ERRORS;
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
     * Create a pop-up which allows to create a new shape.
     */
    @FXML
    public void addShapeMenu() {
        shapeButtonListener.onButtonPressed();
    }

    /**
     * Add code to source code interface
     * @param code code to be added.
     */
    public void appendText(String code){
        codeInterface.appendText(code);
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
     * Initialize the image buttons for the predefined shapes.
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
        imageButton.fitWidthProperty().bind(button.widthProperty().multiply(SCALE_FACTOR));
        imageButton.fitHeightProperty().bind(button.heightProperty().multiply(SCALE_FACTOR));
    }

    /**
     * This method creates the drag of the mouse.
     *
     * @param event  Mouse event
     * @param button The source of the drag
     */
    private void createDragAndDrop(MouseEvent event, Button button) {
        if((rightHandMode && event.getButton() == MouseButton.PRIMARY) || (!rightHandMode && event.getButton() == MouseButton.SECONDARY)) {
            Dragboard db = button.startDragAndDrop(TransferMode.ANY);
            ClipboardContent cb = new ClipboardContent();
            cb.putString("shapeTransfer");
            db.setContent(cb);
            event.consume();
        }

    }

    /**
     *  Called when a drag motion is detected on the circle button. Decides that a circle shape is
     *  going to move.
     *
     * @param mouseEvent mouse event  for the drag motion
     */
    public void circleDragged(MouseEvent mouseEvent) {
        movingShape = predefinedShapesPanelController.createShape(ShapeFactory.CIRCLE);
        movingShapeID = ShapeFactory.CIRCLE;
        createDragAndDrop(mouseEvent, buttonCircle);
    }

    /**
     *  Called when a drag motion is detected on the rectangle button. Decides that a rectangle shape is
     *  going to move.
     *
     * @param mouseEvent mouse event  for the drag motion
     */
    public void rectangleDragged(MouseEvent mouseEvent) {
        movingShape = predefinedShapesPanelController.createShape(ShapeFactory.RECTANGLE);
        movingShapeID = ShapeFactory.RECTANGLE;
        createDragAndDrop(mouseEvent, buttonRectangle);
    }

    /**
     *  Called when a drag motion is detected on the line button. Decides that a line shape is
     *  going to move.
     *
     * @param mouseEvent mouse event  for the drag motion
     */
    public void lineDragged(MouseEvent mouseEvent) {
        movingShape = predefinedShapesPanelController.createShape(ShapeFactory.LINE);
        movingShapeID = ShapeFactory.LINE;
        createDragAndDrop(mouseEvent, buttonLine);
    }

    /**
     *  Called when a drag motion is detected on the curved line button. Decides that a curved line shape is
     *  going to move.
     *
     * @param mouseEvent mouse event  for the drag motion
     */
    public void curvedLineDragged(MouseEvent mouseEvent) {
        movingShape = predefinedShapesPanelController.createShape(ShapeFactory.CURVED_LINE);
        movingShapeID = ShapeFactory.CURVED_LINE;
        createDragAndDrop(mouseEvent, buttonCurvedLine);
    }

    /**
     *  Called when a drag motion is detected on the arrow button. Decides that an arrow shape is
     *  going to move.
     *
     * @param mouseEvent mouse event  for the drag motion
     */
    public void arrowDragged(MouseEvent mouseEvent) {
        movingShape = predefinedShapesPanelController.createShape(ShapeFactory.ARROW);
        movingShapeID = ShapeFactory.ARROW;
        createDragAndDrop(mouseEvent, buttonArrow);
    }

    /**
     *  Called when a drag motion is detected on the square button. Decides that a square shape is
     *  going to move.
     *
     * @param mouseEvent mouse event  for the drag motion
     */
    public void squareDragged(MouseEvent mouseEvent) {
        movingShape = predefinedShapesPanelController.createShape(ShapeFactory.SQUARE);
        movingShapeID = ShapeFactory.SQUARE;
        createDragAndDrop(mouseEvent, buttonSquare);
    }

    /**
     *  Called when a drag motion is detected on the triangle button. Decides that a triangle shape is
     *  going to move.
     *
     * @param mouseEvent mouse event  for the drag motion
     */
    public void triangleDragged(MouseEvent mouseEvent) {
        movingShape = predefinedShapesPanelController.createShape(ShapeFactory.TRIANGLE);
        movingShapeID = ShapeFactory.TRIANGLE;
        createDragAndDrop(mouseEvent, buttonTriangle);
    }

    /**
     * This method generates the image that follows the mouse during the drag and drop gesture.
     *
     * @param path path of the image to create
     */
    public void createMovingImage(String path) {
        Parent root = listener.getRoot();
        movingImage = new ImageView(path);
        movingImage.setFitHeight(IMAGE_SIZE);
        movingImage.setFitWidth(IMAGE_SIZE);
        ((GridPane) root).getChildren().add(movingImage);
    }


    /**
     * This method drop the shape at the position of the mouse.
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
        compile(); //compile directly after a drop

    }

    /**
     * This method create an image of the dragged shape if the mouse enters the PDF area.
     */
    public void handleDragEntered() {
        switch (movingShapeID){
            case ShapeFactory.CIRCLE: createMovingImage("defaultCircle.png"); break;
            case ShapeFactory.RECTANGLE: createMovingImage("defaultRectangle.png"); break;
            case ShapeFactory.LINE: createMovingImage("defaultLine.png"); break;
            case ShapeFactory.CURVED_LINE: createMovingImage("defaultCurvedLine.png"); break;
            case ShapeFactory.ARROW: createMovingImage("defaultArrow.png"); break;
            case ShapeFactory.SQUARE: createMovingImage("defaultSquare.png"); break;
            case ShapeFactory.TRIANGLE: createMovingImage("defaultTriangle.png");
        }
    }

    /**
     * This method remove the image of the dragged shape if the mouse exits the PDF area.
     */
    public void handleDragExited() {
        if (movingImage != null) {
            Parent root = listener.getRoot();
            ((GridPane) root).getChildren().remove(movingImage);
        }
    }

    /**
     * This method shows to the user the accessible zone for the drop.
     *
     * @param event drag event
     */
    public void handleDragOver(DragEvent event) {
        if (movingImage != null) {
            movingImage.setTranslateX(event.getX() + DRAG_IMAGE_OFFSET_X);
            movingImage.setTranslateY(event.getY() + DRAG_IMAGE_OFFSET_Y);
        }
        event.acceptTransferModes(TransferMode.ANY);
    }

    /**
     * Open a pop-up asking if the user wants to the save the project before quiting.
     *
     * @param backToProject if true: goes back to project selection screen
     *                      if false: quits the application
     */
    public void saveSuggestionPopup(boolean backToProject) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Warning");
        popupStage.initModality(Modality.APPLICATION_MODAL);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.getChildren().add(new Label("Save before quit?"));
        int width = 300;
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        Button buttonSave = new Button("Yes");
        Button buttonQuit = new Button("No");
        buttonSave.setOnMouseClicked(e -> {
            popupStage.close();
            if(currentCodeDisplay != SHAPES_ONLY) {
                displayShapesOnly();
            }
            listener.saveProject(codeInterface.getText());
            if(backToProject){
                listener.goBackToProjectScreen();
            }
            else{
                listener.closeStage();
            }
        });
        buttonQuit.setOnMouseClicked(e -> {
            popupStage.close();
            if(backToProject){
                listener.goBackToProjectScreen();
            }
            else{
                listener.closeStage();
            }
        });
        popupStage.setOnCloseRequest(e -> {
        });
        hBox.getChildren().add(buttonSave);
        hBox.getChildren().add(buttonQuit);
        vBox.getChildren().add(hBox);
        Scene scene = new Scene(vBox, width, 75);
        popupStage.setScene(scene);
        popupStage.show();
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


    public Image getImage(){
        return renderedImageView.getImage();
    }

    public double getImageWidth(){
        return imageScrollPane.getWidth();
    }

    public double getImageHeight(){
        return imageScrollPane.getHeight();
    }

    public double getImageScrollValue(){
        return imageScrollPane.getVvalue();
    }

    /**
     * Send a request to the listener to create a shape.
     *
     * @param shape shape to be created
     */
    public void addShape(Shape shape) {
        listener.addShapeRequest(shape);
    }

    /**
     * Change mode to left-handed or right-handed when clicking the button.
     */
    public void changeMode() {
        if(rightHandMode){
            rightHandMode=false;
            changeModeButton.setText("Left-handed");
        }
        else{
            rightHandMode=true;
            changeModeButton.setText("Right-handed");
        }
    }

    public void backToProjectsButtonAction(){
        saveSuggestionPopup(true);
    }

    /**
     * Interface used to relay information to MainPageController.
     */
    public interface MainPageViewControllerListener {
        void goBackToProjectScreen();

        void saveProject(String code);

        void closeStage();

        void onReleaseShape(double x, double y, Shape movingShape);

        void addShapeRequest(Shape shape);

        Parent getRoot();
    }

    /**
     * Interface used to relay information to ShapeMenuController.
     */
    public interface AddNewShapeButtonListener {
        void onButtonPressed();
    }

    /**
     * Interface used to relay information to LatexController.
     */
    public interface CodeInterfaceListener {
        void onCompilationAttempt(String code);

        String getShapesOnlyText();

        int getErrorsCounter();

        String getFullText();

        void saveCodeInterfaceCode(String tikzCode);

        String getErrorsText();

    }


}
