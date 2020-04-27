package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class used to contain all the FXML files in an ArrayList in order to switch between screens using a static method.
 * All the Controllers linked to the screens will be contained in a Array of ControllerSuperclass and their update
 * method will be called whenever the changeScene method is used.
 */

public class ScreenHandler extends Application implements LoginScreenController.LoginScreenControllerListener,
        AccountCreationController.AccountCreationControllerListener, MainPageController.MainPageControllerListener,
        AccountModificationController.AccountModificationControllerListener {

    private Stage stage;
    private LoginScreenController loginScreenController;
    private AccountCreationController accountCreationController;
    private AccountModificationController accountModificationController;
    private MainPageController mainPageController;

    /**
     * At first, start() loads the login screen
     *
     * @param stage Stage for the application.
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("TikZOverflow");
        stage.setMaximized(true);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        this.stage = stage;
        loginScreenController = new LoginScreenController(stage,this);
        loginScreenController.createScene();
    }

    /**
     * Change the scene for the login screen
     */
    private void showLoginScreen() {
        loginScreenController = new LoginScreenController(stage, this);
        loginScreenController.show();
    }

    /**
     * Creates a popup stage when account creation has been attempted to inform user.
     *
     * @param message message to display to user
     * @param success defines if account creation was successful or not
     */

    @Override
    public void createAccountCreationPopup(String message, boolean success) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Account creation");
        popupStage.initModality(Modality.APPLICATION_MODAL);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.getChildren().add(new Label(message));
        int width = (message.length() <= 30) ? 200 : 300;
        Button button = new Button("OK");
        button.setOnMouseClicked(e -> {
            popupStage.close();
            if (success) {
                showLoginScreen();
            }
        });
        popupStage.setOnCloseRequest(e -> {
            if (success) {
                showLoginScreen();
            }
        });
        vBox.getChildren().add(button);
        Scene scene = new Scene(vBox, width, 75);
        popupStage.setScene(scene);
        popupStage.show();
    }

    /**
     * Call when the identification is correct and change the scene
     */
    @Override
    public void onSuccessfulLoginRequest() {
        goToMainPage();
    }

    /**
     * Change the scene for the main page (PDF and code creation page)
     */
    private void goToMainPage() {
        mainPageController = new MainPageController(stage, this);
        mainPageController.show();
    }

    /**
     * Change the scene when guest wants to create an account
     */
    @Override
    public void onAccountCreationRequest() {
        accountCreationController = new AccountCreationController(stage, this);
        accountCreationController.show();
    }

    /**
     * Change the scene for login screen when request is received
     */
    @Override
    public void backToLoginScreenRequest() {
        showLoginScreen();
    }

    /**
     * Disconnect the user and the login screen is loaded
     */
    @Override
    public void logout() {
        Session.getInstance().logOut();
        showLoginScreen();
    }

    /**
     * Change the scene for account modification screen when request is received
     */
    @Override
    public void accountModificationRequest() {
        accountModificationController = new AccountModificationController(stage, this);
        accountModificationController.show();
    }


    /**
     * Change scene for main page screen when request is received
     */
    @Override
    public void onModificationDone() {
        goToMainPage();
    }

    public AccountCreationController getAccountCreationController(){
        return this.accountCreationController;
    }
}
