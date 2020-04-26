package Controller;

import View.ViewControllers.AccountCreationViewController;
import View.ViewControllers.LoginScreenViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AccountCreationController implements AccountCreationViewController.AccountCreationViewControllerListener {
    private Stage stage;
    private AccountCreationControllerListener listener;
    private AccountCreationViewController controller;

    public AccountCreationController(Stage stage, AccountCreationControllerListener listener){
        this.stage = stage;
        this.listener = listener;
    }

    public void show(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/accountCreation.fxml"));
            loader.load();
            controller = loader.getController();
            controller.setListener(this);
            stage.setScene(new Scene(loader.getRoot()));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error loading /View/FXML/accountCreation.fxml");
            e.printStackTrace();
        }
    }

    public interface AccountCreationControllerListener{

    }
}
