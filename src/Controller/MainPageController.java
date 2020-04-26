package Controller;

import Model.Shapes.Shape;
import View.ViewControllers.AccountCreationViewController;
import View.ViewControllers.MainPageViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController implements MainPageViewController.MainPageViewControllerListener {
    private Stage stage;
    private MainPageControllerListener listener;
    private MainPageViewController controller;
    private ShapeMenuController shapeMenuController;
    private LatexController latexController;
    private Parent root;

    public MainPageController(Stage stage, MainPageControllerListener listener){
        this.stage = stage;
        this.listener = listener;
    }

    public void show(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/MainPage.fxml"));
            root = loader.load();
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

            stage.getScene().setRoot(loader.getRoot());
            stage.show();
        } catch (Exception e) {
            System.out.println("Error loading /View/FXML/accountCreation.fxml");
            e.printStackTrace();
        }

    }

    @Override
    public void onLogoutRequest() {
        listener.logout();
    }

    @Override
    public void accountModificationRequest() {
        listener.accountModificationRequest();
    }

    @Override
    public void onReleaseShape(double x, double y, Shape movingShape) {
        float convertedX = xMouseToPdf(x);
        float convertedY = yMouseToPdf(y);
        movingShape.setPosX(convertedX);
        movingShape.setPosY(convertedY);
        addShape(movingShape);
        movingShape = null;
    }

    /**
     * Convert the position x of the mouse to the position x of the PDF
     * @param x                 x position to convert
     * @return
     */
    public float xMouseToPdf(double x){
        double scrollPaneWidth = controller.getImageScrollPane().getWidth();
        double pdfWidth =  21.4;
        double widthConvert = scrollPaneWidth/pdfWidth;
        double xOffset = -1.25;

        return (float) ((x/widthConvert) + xOffset);
    }

    /**
     * Convert the position y of the mouse to the position y of the PDF
     * @param y                 y position to convert
     * @return
     */
    public float yMouseToPdf(double y){
        double pdfHeight =  25.7;
        double yOffset = 19.75;
        double scrollPaneHeight = controller.getImageScrollPane().getHeight();
        double imageMaxHeight = 0;

        Image image = controller.getRenderedImageView().getImage();
        if(image != null) {
            double imageWidth = image.getWidth();
            double imageHeight = image.getHeight();
            imageMaxHeight = (controller.getImageScrollPane().getWidth() / imageWidth) * imageHeight;
        }

        double correctionFactor = 4*(imageMaxHeight/1415); //Empirical value
        double heightConvert = imageMaxHeight/pdfHeight - correctionFactor ;
        double pdfNotShown = 0;
        if(scrollPaneHeight < imageMaxHeight) {
            pdfNotShown = (imageMaxHeight - scrollPaneHeight);
        }
        double scroll = controller.getImageScrollPane().getVvalue();

        float valueToReturn = 0;
        if(heightConvert != 0){
            valueToReturn = (float) (yOffset - (y+(scroll*pdfNotShown))/heightConvert);
        }

        return valueToReturn;
    }

    /**
     * Adds shape code to the coding interface according to the shape received as a parameter
     *
     * @param shape Shape whose code has to be generated in the coding interface
     */
    @Override
    public void addShape(Shape shape) {
        String code = shape.generateAndGetTikzCode();
        controller.getCodeInterface().appendText(code);
    }

    @Override
    public Parent getRoot() {
        return root;
    }


    public interface MainPageControllerListener{
        void logout();
        void accountModificationRequest();
    }
}
