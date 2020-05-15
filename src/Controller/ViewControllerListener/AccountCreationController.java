package Controller.ViewControllerListener;

import Controller.AlertController;
import Model.Exceptions.UserAlreadyExistsException;
import Model.Exceptions.UserHandler.SaveUserCreationException;
import Model.Exceptions.UserHandler.SetupDirectoryException;
import Model.User;
import Model.UserHandler;
import View.ViewControllers.AccountCreationViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/accountCreationScreen.fxml"));
            loader.load();
            controller = loader.getController();
            controller.setListener(this);
            stage.getScene().setRoot(loader.getRoot());
        } catch (IOException e) {
            AlertController.showStageError("Error while loading the account creation fxml file.", "Process aborted", true);
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
            controller.setTCUStyle(AccountCreationViewController.TCU_ERROR_STYLE);
        } else {
            controller.setTCUStyle(AccountCreationViewController.TCU_DEFAULT_STYLE);
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
        try (InputStream inputStream = getClass().getResourceAsStream("/tcu.txt")) {
            Parent tcuRoot = FXMLLoader.load(getClass().getResource("/View/FXML/termsAndConditions.fxml"));
            Scene tcuScene = new Scene(tcuRoot);
            Stage tcuStage = new Stage();
            tcuStage.initModality(Modality.APPLICATION_MODAL);
            tcuStage.setTitle("Terms and conditions");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String tmp, text = "";
            while ((tmp = br.readLine()) != null) {
                text = text.concat(tmp + '\n');
            }
            br.close();
            tcuStage.setScene(tcuScene);
            tcuStage.show();
            Text tcuFullText = (Text) tcuRoot.lookup("#tcuFullText");
            tcuFullText.setText(text);
            tcuFullText.wrappingWidthProperty().bind(tcuScene.widthProperty().subtract(20));
        } catch (IOException e) {
            AlertController.showStageError("Error while loading the terms and conditions file.", "File not readable");
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
        UserHandler userHandler = UserHandler.getInstance();
        try {
            if (userHandler.saveUserExists(username)) {//User already exists
                return false;
            }
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setPassword(password);
            newUser.setMail(mail);
            userHandler.setupSaveUserDirectory(UserHandler.DEFAULT_DIRECTORY);
            userHandler.createUserSave(newUser);
            return true;
        } catch (SaveUserCreationException | SetupDirectoryException e) {
            AlertController.showStageError("Error while creating an account.", "Account creation failed");
        } catch (UserAlreadyExistsException e) {
            AlertController.showStageError("Error while creating an account.", "Username is already taken, choose another one.");
        }
        return false;
    }

    /**
     * Interface used to communicate with ScreenHandler, which is this controller's listener.
     */
    public interface AccountCreationControllerListener {
        void backToLoginScreenRequest();

        void createAccountCreationPopup(String s, boolean b);
    }
}
