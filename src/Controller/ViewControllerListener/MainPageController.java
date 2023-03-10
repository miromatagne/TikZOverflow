package Controller.ViewControllerListener;

import Controller.AlertController;
import Controller.Exceptions.LatexController.GetTextInFileException;
import Controller.Exceptions.ShapeMenuController.ShapeMenuControllerConstructorException;
import Controller.LatexController;
import Controller.PredefinedShapesPanelController;
import Controller.Session;
import Model.Exceptions.ProjectHandler.LatexWritingException;
import Model.Exceptions.ProjectHandler.ProjectSaveException;
import Model.Latex.LatexHandler;
import Model.ProjectHandler;
import Model.Shapes.Shape;
import View.ViewControllers.MainPageViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller of the main page which handles interactions on the main page.
 */
public class MainPageController implements MainPageViewController.MainPageViewControllerListener {
    private final Stage stage;
    private final MainPageControllerListener listener;
    private MainPageViewController controller;
    private LatexController latexController;
    private Parent root;

    private static final double PDF_WIDTH = 21.4; //Size of pdf in Tikz language
    private static final double PDF_HEIGHT = 25.7; //Size of pdf in Tikz language

    private static final double X_OFFSET = -1.25; // x = 0.0 in Tikz language
    private static final double Y_OFFSET = 19.75; // y = 0.0 in Tikz language

    private static final double CORRECTION_FACTOR = 4.0/1415; //Empirical value


    /**
     * Constructor.
     *
     * @param stage    stage this screen needs to be in.
     * @param listener listener used to communicate with ScreenHandler
     */
    public MainPageController(Stage stage, MainPageControllerListener listener) {
        this.stage = stage;
        this.listener = listener;
    }

    /**
     * Show the main page scene by loading and creating all the controllers this instance need to handle.
     */
    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/mainPage.fxml"));
            root = loader.load();
            stage.getScene().setRoot(root);
            controller = loader.getController();
            controller.setListener(this);
            PredefinedShapesPanelController predefinedShapesPanelController = new PredefinedShapesPanelController();
            ShapeMenuController shapeMenuController = new ShapeMenuController();
            latexController = new LatexController(controller);
            shapeMenuController.setMainPageViewController(controller);
            controller.setPredefinedShapesPanelController(predefinedShapesPanelController);
            controller.setShapeButtonListener(shapeMenuController);
            controller.setCodeInterfaceListener(latexController);
            controller.updateText();
            stage.setOnCloseRequest(e -> {
                e.consume();
                if (Session.getInstance().getCurrentProject() != null) {
                    controller.saveSuggestionPopup(false);//suggest the user to save his project before quitting
                } else {
                    stage.close();
                }
            });
        } catch (ShapeMenuControllerConstructorException e) {
            AlertController.showStageError("Error while creating the shape menu controller.", "Process aborted", true);
        } catch (IOException e) {
            AlertController.showStageError("Error while loading the main page fxml file.", "Process aborted", true);
        }
    }

    /**
     * Return on the screen with all the user's project.
     */
    @Override
    public void goBackToProjectScreen() {
        controller.renderImage(null);
        Session.getInstance().setCurrentProject(null);
        listener.goBackToProjectScreen();
    }

    /**
     * Save the project without compiling it.
     *
     * @param code  the text from code interface to save
     */
    @Override
    public void saveProject(String code){
        try{
            String codeTex = LatexHandler.getInstance().buildFullCodeFromShapesOnlyCode(code, latexController.getTextInFile());
            ProjectHandler projectHandler = new ProjectHandler();
            projectHandler.makeTexFile(codeTex);
            projectHandler.saveProjectInfo(Session.getInstance().getCurrentProject());
        }
        catch(GetTextInFileException e){
            AlertController.showStageError("Save error", "Error on opening Tex file for save");
        }catch (LatexWritingException ex) {
            AlertController.showStageError("Save error", "Error on save Tex file");
        }catch(ProjectSaveException pe){
            AlertController.showStageError("Save error", "Error on save properties file");
        }
    }

    /**
     * Close the program.
     */
    @Override
    public void closeStage() {
        stage.close();
    }

    /**
     * Create a shape when the shape is released (drag and drop).
     *
     * @param x           x-position
     * @param y           y-position
     * @param movingShape Shape to create
     */
    @Override
    public void onReleaseShape(double x, double y, Shape movingShape) {
        float convertedX = xMouseToPdf(x);
        float convertedY = yMouseToPdf(y);
        movingShape.setPosX(convertedX);
        movingShape.setPosY(convertedY);
        addShapeRequest(movingShape);
    }

    /**
     * Convert the position x of the mouse to the position x of the PDF.
     *
     * @param x x position to convert
     * @return posXTikz         a float number for Tikz Language
     */
    public float xMouseToPdf(double x) {
        double scrollPaneWidth = controller.getImageWidth();
        double widthConvert = scrollPaneWidth / PDF_WIDTH;
        return (float) ((x / widthConvert) + X_OFFSET);
    }

    /**
     * Convert the position y of the mouse to the position y of the PDF.
     *
     * @param y y position to convert
     * @return posYTikz         a float number for Tikz language
     */
    public float yMouseToPdf(double y) {
        double scrollPaneHeight = controller.getImageHeight();
        double imageMaxHeight = 0;

        Image image = controller.getImage();
        if (image != null) {
            double imageWidth = image.getWidth();
            double imageHeight = image.getHeight();
            imageMaxHeight = (controller.getImageWidth() / imageWidth) * imageHeight; //Total height of the re-sized image(PDF)
        }

        double correctionTerm = (imageMaxHeight * CORRECTION_FACTOR);
        double heightConvert = imageMaxHeight / PDF_HEIGHT - correctionTerm;
        double pdfNotShown = 0;
        if (scrollPaneHeight < imageMaxHeight) {
            pdfNotShown = (imageMaxHeight - scrollPaneHeight);
        }
        double scroll = controller.getImageScrollValue();

        float posYTikz = 0;
        if (heightConvert != 0) {
            posYTikz = (float) (Y_OFFSET - (y + (scroll * pdfNotShown)) / heightConvert);
        }
        return posYTikz;
    }

    /**
     * Adds shape code to the coding interface according to the shape received as a parameter.
     *
     * @param shape Shape whose code has to be generated in the coding interface
     */
    @Override
    public void addShapeRequest(Shape shape) {
        String code = shape.generateAndGetTikzCode();
        controller.appendText(code);
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    /**
     * Interface used to communicate with ScreenHandler.
     */
    public interface MainPageControllerListener {
        void goBackToProjectScreen();
    }
}
