package Controller;

import Model.Exceptions.ProjectSaveException;
import Model.Project;
import Model.ProjectHandler;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Main controller class used to decide which screen to show depending on the
 * notifications received from lower tier controllers using interfaces
 */

public class ScreenHandler extends Application implements LoginScreenController.LoginScreenControllerListener,
        AccountCreationController.AccountCreationControllerListener, MainPageController.MainPageControllerListener,
        AccountModificationController.AccountModificationControllerListener, ProjectSelectionController.ProjectSelectionControllerListener {

    private Stage stage;
    private LoginScreenController loginScreenController;
    private AccountCreationController accountCreationController;
    private AccountModificationController accountModificationController;
    private MainPageController mainPageController;
    private ProjectSelectionController projectSelectionController;

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
        loginScreenController = new LoginScreenController(stage, this);
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
     * Creates a popup stage when a successful account modification has been made to inform user.
     */
    @Override
    public void createAccountModificationPopup() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Account modification");
        popupStage.initModality(Modality.APPLICATION_MODAL);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.getChildren().add(new Label("Account successfully modified !"));
        int width = 300;
        Button button = new Button("OK");
        button.setOnMouseClicked(e -> {
            popupStage.close();
            onModificationDone();
        });
        popupStage.setOnCloseRequest(e -> {
            onModificationDone();
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
        goToProjectScreen();
    }

    private void goToProjectScreen() {
        projectSelectionController = new ProjectSelectionController(stage, this);
        projectSelectionController.show();
    }

    /**
     * Change the scene to the main page (PDF and code creation page)
     * @param project
     */
    @Override
    public void goToMainPage(Project project) {
        Session.getInstance().setCurrentProject(project);
        mainPageController = new MainPageController(stage, this);
        mainPageController.show();
    }

    /**
     * Change the scene when user wants to create an account
     */
    @Override
    public void onAccountCreationRequest() {
        accountCreationController = new AccountCreationController(stage, this);
        accountCreationController.show();
    }

    /**
     * Change the scene to login screen when request is received
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
     * Notification from the main page modification button
     */
    @Override
    public void onModificationDone() { goToProjectScreen(); }

    @Override
    public void goBackToProjectScreen() { goToProjectScreen(); }
}
