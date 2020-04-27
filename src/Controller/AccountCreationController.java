package Controller;

import Model.FieldChecker;
import Model.FileHandler;
import View.ViewControllers.AccountCreationViewController;
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
            stage.getScene().setRoot(loader.getRoot());
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading /View/FXML/accountCreation.fxml");
            e.printStackTrace();
        }
    }

    /**
     * Checks whether all fields are valid, and highlights in red those that are not.
     *
     * @return TRUE if all fields are  valid
     * FALSE otherwise
     */
    public boolean validateInformation(String username, String firstName, String lastName, String email, String password, String passwordConfirmation, boolean isBoxChecked) {
        FieldChecker fieldChecker = new FieldChecker();
        if (!fieldChecker.isValidUsername(username)) {
            controller.setTextFieldStyle("username", "red");
        } else {
            controller.setTextFieldStyle("username", "default");
        }
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
        if (!isBoxChecked) {
            controller.setTCUStyle("red");
        }
        else {
            controller.setTCUStyle("default");
        }

        return fieldChecker.isValidAccount(username, firstName, lastName, email, password, passwordConfirmation) && isBoxChecked;
    }

    @Override
    public void backToLoginScreen() {
        listener.backToLoginScreenRequest();
    }

    @Override
    public void onAccountCreationAttempt(String username, String firstName, String lastName, String email, String password, String passwordConfirmation, boolean isBoxChecked) {
        if (validateInformation(username, firstName, lastName, email, password, passwordConfirmation, isBoxChecked)){
            if(Session.getInstance().createAccount(username, firstName, lastName, email, password)) {
                listener.createAccountCreationPopup("Account successfully created !", true);
            }else {
                listener.createAccountCreationPopup("Error creating a new account. Username already in use.", false);
            }
        }
    }

    /**
     * Creates a pop-up window when user clicks on "I accept terms and conditions".
     */
    @Override
    public void showTermsAndConditions() {
        try {
            Parent tcuRoot = FXMLLoader.load(getClass().getResource("/View/FXML/termsAndConditions.fxml"));
            Scene tcuScene = new Scene(tcuRoot);
            Stage tcuStage = new Stage();
            tcuStage.initModality(Modality.APPLICATION_MODAL);
            tcuStage.setTitle("Terms and conditions");
            File f = new File("tcu.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String tmp, text = "";
            while ((tmp = br.readLine()) != null) {
                text = text.concat(tmp + '\n');
            }
            tcuStage.setScene(tcuScene);
            tcuStage.show();
            Text tcuFullText = (Text) tcuRoot.lookup("#tcuFullText");
            tcuFullText.setText(text);
            tcuFullText.wrappingWidthProperty().bind(tcuScene.widthProperty().subtract(20));
        } catch (IOException e) {
            System.err.println("Could not open TCU");
            e.printStackTrace();
        }

    }

    public interface AccountCreationControllerListener{

        void backToLoginScreenRequest();

        void createAccountCreationPopup(String s, boolean b);
    }
}
