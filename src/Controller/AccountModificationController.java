package Controller;

import Model.FieldChecker;
import Model.FileHandler;
import Model.User;
import View.ViewControllers.AccountCreationViewController;
import View.ViewControllers.AccountModificationViewController;
import View.ViewControllers.LoginScreenViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AccountModificationController implements AccountModificationViewController.AccountModificationViewControllerListener {
    private Stage stage;
    private AccountModificationControllerListener listener;
    private AccountModificationViewController controller;


    public AccountModificationController(Stage stage, AccountModificationControllerListener listener){
        this.stage = stage;
        this.listener = listener;
    }

    public void show(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/accountModification.fxml"));
            loader.load();
            controller = loader.getController();
            controller.setListener(this);
            stage.getScene().setRoot(loader.getRoot());
            stage.show();
        } catch (Exception e) {
            System.out.println("Error loading /View/FXML/accountModification.fxml");
            e.printStackTrace();
        }
    }

    /**
     * Checks whether all fields are valid, and highlights in red those that are not.
     *
     * @return TRUE if all fields are  valid
     * FALSE otherwise
     */
    public boolean validateInformation(String firstName, String lastName, String email, String password, String passwordConfirmation) {
        FieldChecker fieldChecker = new FieldChecker();

        if (!fieldChecker.isValidName(firstName)) {
            controller.setTextFieldStyle("firstName", "red");
        } else {
            controller.setTextFieldStyle("firstName", "default");
        }
        if (!fieldChecker.isValidName(lastName)) {
            controller.setTextFieldStyle("lastName", "red");
        } else {
            controller.setTextFieldStyle("lastName", "default");
        }
        if (!fieldChecker.isValidMail(email)) {
            controller.setTextFieldStyle("email", "red");
        } else {
            controller.setTextFieldStyle("email", "default");
        }
        if (!password.equals(passwordConfirmation) || password.equals("")) {
            controller.setTextFieldStyle("password", "red");
            controller.setTextFieldStyle("passwordConfirmation", "red");
        } else {
            controller.setTextFieldStyle("password", "default");
            controller.setTextFieldStyle("passwordConfirmation", "default");
        }


        return fieldChecker.isValidAccount(Session.getInstance().getUser().getUsername(), firstName, lastName, email, password, passwordConfirmation);
    }
    /**
     * If all fields are valid, modifies the user's info.
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @param passwordConfirmation
     */
    public boolean validateModification(String firstName, String lastName, String email, String password, String passwordConfirmation) {
        if (validateInformation(firstName, lastName, email, password, passwordConfirmation)) {
            User userCurrent = Session.getInstance().getUser();
            userCurrent.setLastName(lastName);
            userCurrent.setFirstName(firstName);
            userCurrent.setMail(email);
            userCurrent.setPassword(password);
            Session.getInstance().setUser(userCurrent);
            FileHandler handler = new FileHandler();
            if (!handler.saveUser(userCurrent)) {
                System.out.println("Error in saving the user");
            }
            return true;
        }
        return false;
    }
    @Override
    public void onValidation(String firstName, String lastName, String email, String password, String passwordConfirmation) {
        if(validateModification(firstName, lastName, email, password, passwordConfirmation)){
            listener.onModificationDone();
        }

    }

    @Override
    public void onReturnButtonPressed() {
        listener.onModificationDone();
    }


    public interface AccountModificationControllerListener{
        void onModificationDone();
    }
}
