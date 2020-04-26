package Controller;

import View.ViewControllers.AccountCreationViewController;
import View.ViewControllers.MainPageViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController implements MainPageViewController.MainPageViewControllerListener {
    private Stage stage;
    private MainPageControllerListener listener;
    private MainPageViewController controller;
    private ShapeMenuController shapeMenuController;
    private LatexController latexController;

    public MainPageController(Stage stage, MainPageControllerListener listener){
        this.stage = stage;
        this.listener = listener;
    }

    public void show(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/MainPage.fxml"));
            loader.load();
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


    public interface MainPageControllerListener{
        void logout();
        void accountModificationRequest();
    }
}
