package Controller;

import Controller.Exceptions.GetTextInFileException;
import Controller.Exceptions.LatexControllerConstructorException;
import Controller.Exceptions.ShapeMenuControllerConstructorException;
import Model.Exceptions.LatexWritingException;
import Model.Exceptions.ProjectSaveException;
import Model.LatexHandler;
import Model.Project;
import Model.ProjectHandler;
import Model.Shapes.Shape;
import View.ViewControllers.MainPageViewController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Controller of the main page which handles interactions on the main page
 */
public class MainPageController implements MainPageViewController.MainPageViewControllerListener {
    private final Stage stage;
    private final MainPageControllerListener listener;
    private MainPageViewController controller;
    private ShapeMenuController shapeMenuController;
    private LatexController latexController;
    private Parent root;


    public MainPageController(Stage stage, MainPageControllerListener listener) {
        this.stage = stage;
        this.listener = listener;
    }

    /**
     * Show the main page scene by loading and creating all the controllers this instance need to handle
     */
    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/MainPage.fxml"));
            root = loader.load();
            stage.getScene().setRoot(root);
            controller = loader.getController();
            controller.setListener(this);
            PredefinedShapesPanelController predefinedShapesPanelController = new PredefinedShapesPanelController();
            shapeMenuController = new ShapeMenuController();
            latexController = new LatexController(controller);
            shapeMenuController.setMainPageViewController(controller);
            controller.setPredefinedShapesPanelController(predefinedShapesPanelController);
            controller.setShapeButtonListener(shapeMenuController);
            controller.setCodeInterfaceListener(latexController);
            controller.updateText();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    e.consume();
                    if(Session.getInstance().getCurrentProject() != null){
                        controller.saveSuggestionPopup(false);
                    }
                }
            });
        } catch (ShapeMenuControllerConstructorException e) {
            System.err.println("Error while creating the shape menu controller");
            e.printStackTrace();
            e.getCause().printStackTrace();
            AlertController.showStageError("Error while creating the shape menu controller.", "Process aborted", true);
        } catch (IOException e) {
            System.err.println("Error while loading the fxml file");
            e.printStackTrace();
            e.getCause().printStackTrace();
            AlertController.showStageError("Error while loading the main page fxml file.", "Process aborted", true);
        } catch (LatexControllerConstructorException e) {
            System.err.println("Error while creating the latex controller");
            e.printStackTrace();
            e.getCause().printStackTrace();
            AlertController.showStageError("Error while creating the latex controller.", "Process aborted", true);
        }
    }


    @Override
    public void goBackToProjectScreen() {
        Session.getInstance().setCurrentProject(null);
        listener.goBackToProjectScreen();
    }

    @Override
    public void saveProject(String code){
        try{
            code = LatexHandler.getInstance().buildFullCodeFromShapesOnlyCode(code, latexController.getTextInFile());
            ProjectHandler projectHandler = new ProjectHandler();
            projectHandler.makeTexFile(code);
            projectHandler.saveProjectInfo(Session.getInstance().getCurrentProject());
        }
        catch(GetTextInFileException e){
            Alert.AlertType.valueOf("Error on opening Tex file for save");
        }catch (LatexWritingException ex) {
            Alert.AlertType.valueOf("Error on save Tex file");
        }catch(ProjectSaveException pe){
            Alert.AlertType.valueOf("Error on save properties file");
        }
    }

    @Override
    public void closeStage() {
        stage.close();
    }

    /**
     * Create a shape when the shape is released (drag and drop)
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
     * Convert the position x of the mouse to the position x of the PDF
     *
     * @param x x position to convert
     * @return posXTikz         a float number for Tikz Language
     */
    public float xMouseToPdf(double x) {
        double scrollPaneWidth = controller.getImageScrollPane().getWidth();
        double pdfWidth = 21.4; //Size of pdf in Tikz language
        double widthConvert = scrollPaneWidth / pdfWidth;
        double xOffset = -1.25; // x = 0.0 in Tikz language
        return (float) ((x / widthConvert) + xOffset);
    }

    /**
     * Convert the position y of the mouse to the position y of the PDF
     *
     * @param y y position to convert
     * @return posYTikz         a float number for Tikz language
     */
    public float yMouseToPdf(double y) {
        double pdfHeight = 25.7; //Size of pdf in Tikz language
        double yOffset = 19.75; // y = 0.0 in Tikz language
        double scrollPaneHeight = controller.getImageScrollPane().getHeight();
        double imageMaxHeight = 0;

        Image image = controller.getRenderedImageView().getImage();
        if (image != null) {
            double imageWidth = image.getWidth();
            double imageHeight = image.getHeight();
            imageMaxHeight = (controller.getImageScrollPane().getWidth() / imageWidth) * imageHeight; //Total height of the re-sized image(PDF)
        }

        double correctionFactor = 4 * (imageMaxHeight / 1415); //Empirical value
        double heightConvert = imageMaxHeight / pdfHeight - correctionFactor;
        double pdfNotShown = 0;
        if (scrollPaneHeight < imageMaxHeight) {
            pdfNotShown = (imageMaxHeight - scrollPaneHeight);
        }
        double scroll = controller.getImageScrollPane().getVvalue();

        float posYTikz = 0;
        if (heightConvert != 0) {
            posYTikz = (float) (yOffset - (y + (scroll * pdfNotShown)) / heightConvert);
        }
        return posYTikz;
    }


    /**
     * Adds shape code to the coding interface according to the shape received as a parameter
     *
     * @param shape Shape whose code has to be generated in the coding interface
     */
    @Override
    public void addShapeRequest(Shape shape) {
        String code = shape.generateAndGetTikzCode();
        controller.getCodeInterface().appendText(code);
    }

    @Override
    public Parent getRoot() {
        return root;
    }





    public interface MainPageControllerListener {
        void goBackToProjectScreen();

    }
}
