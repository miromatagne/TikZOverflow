package Controller;

import Model.Exceptions.FileHandlerConstructorException;
import Model.Exceptions.SaveUserCreationException;
import Model.FileHandler;
import Model.User;
import View.ViewControllers.AccountCreationViewController;
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

/**
 * Handles account creation screen behaviour.
 */
public class AccountCreationController extends AccountController implements AccountCreationViewController.AccountCreationViewControllerListener {
    private final Stage stage;
    private final AccountCreationControllerListener listener;
    private AccountCreationViewController controller;

    /**
     * Constructor.
     *
     * @param stage    stage this screen needs to be in.
     * @param listener listener used to communicate with ScreenHandler
     */
    public AccountCreationController(Stage stage, AccountCreationControllerListener listener) {
        this.stage = stage;
        this.listener = listener;
    }

    /**
     * Method used when opening account creation screen.
     */
    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/accountCreation.fxml"));
            loader.load();
            controller = loader.getController();
            controller.setListener(this);
            stage.getScene().setRoot(loader.getRoot());
        } catch (IOException e) {
            System.err.println("Error loading /View/FXML/accountCreation.fxml");
            e.printStackTrace();
        }
    }

    /**
     *Checks whether all fields are valid, and highlights in red those that are not.
     *
     * @param username             username that needs to be checked
     * @param firstName            first name that needs to be checked
     * @param lastName             last name that needs to be checked
     * @param email                email that needs to be checked
     * @param password             password that needs to be checked
     * @param passwordConfirmation password confirmation that needs to be checked
     * @param isBoxChecked         whether the TCU CheckBox is checked
     * @return TRUE if all fields are  valid
     * FALSE otherwise
     */
    public boolean validateInformation(String username, String firstName, String lastName, String email, String password, String passwordConfirmation, boolean isBoxChecked) {
        boolean fieldsOk = super.validateInformation(controller, username, firstName, lastName, email, password, passwordConfirmation);
        if (!isBoxChecked) {
            controller.setTCUStyle("red");
        } else {
            controller.setTCUStyle("default");
        }
        return fieldsOk && isBoxChecked;
    }

    /**
     * Requests ScreenHandler to go back to login screen.
     */
    @Override
    public void backToLoginScreen() {
        listener.backToLoginScreenRequest();
    }

    /**
     * Creates user account and requests ScreenHandler to create resulting popup.
     *
     * @param username             username
     * @param firstName            first name
     * @param lastName             last name
     * @param email                email address
     * @param password             password
     * @param passwordConfirmation password confirmation
     * @param isBoxChecked         true if TCU box was checked; false otherwise
     */
    @Override
    public void onAccountCreationAttempt(String username, String firstName, String lastName, String email, String password, String passwordConfirmation, boolean isBoxChecked) {
        if (validateInformation(username, firstName, lastName, email, password, passwordConfirmation, isBoxChecked)) {
            if (createAccount(username, firstName, lastName, email, password)) {
                listener.createAccountCreationPopup("Account successfully created !", true);
            } else {
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

    /**
     * Create an account (a user save) if all the fields are ok
     *
     * @param username  username
     * @param firstName first name
     * @param lastName  last name
     * @param mail      mail
     * @param password  password
     * @return TRUE if account creation was successful
     * FALSE otherwise
     */
    public boolean createAccount(String username, String firstName, String lastName,
                                 String mail, String password) {
        try  {
            FileHandler fileHandler = new FileHandler();
            if (fileHandler.saveUserExists(username)) {//User already exists
                return false;
            }
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setPassword(password);
            newUser.setMail(mail);
            fileHandler.createUserSave(newUser);
            return true;
        } catch (SaveUserCreationException e) {
            System.err.println("Error while creating an account");
            e.printStackTrace();
            e.getCause().printStackTrace();
        } catch (FileHandlerConstructorException e) {
            System.err.println("Error while creating the file handler");
            e.printStackTrace();
            e.getCause().printStackTrace();
        }
        return false;
    }

    /**
     * Interface used to communicate with ScreenHandler.
     */
    public interface AccountCreationControllerListener {

        void backToLoginScreenRequest();

        void createAccountCreationPopup(String s, boolean b);
    }
}
