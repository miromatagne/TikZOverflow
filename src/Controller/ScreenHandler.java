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
    // Scene IDs :
    public static int LOGIN_SCREEN = 0;
    public static int MAIN_PAGE = 1;
    public static int ACCOUNT_CREATION = 2;
    public static int MODIFICATION_SCREEN = 3;

    private Stage stage;
    private LoginScreenController loginScreenController;
    private AccountCreationController accountCreationController;
    private AccountModificationController accountModificationController;
    private MainPageController mainPageController;


    /**
     * changeScene is a static function and will be used by other object such as Controller in order to change the root
     * of the active scene.
     *
     * @param idScene this is an int and it is used to choose which screen will be displayed.(Refer to the list above)
     */
    public static void changeScene(int idScene){
        /*
        scene.setRoot(screens.get(idScene));
        idCurrent = idScene;
        controllers.get(idScene).update();
         */
    }

    /**
     * Method used to add a fxml file to the ArrayList and add the Controller link to that fxml in the ArrayList of
     * Controller.
     *
     * @param scenePath String containing the path of the fxml file that will be loaded.
     */

    private void addScene(String scenePath) {
        /*
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
            screens.add(loader.load());
            controllers.add(loader.getController());
        } catch (Exception e) {
            System.out.println("Error loading all screens " + scenePath);
            e.printStackTrace();
        }

         */
    }

    /**
     * At first, start() adds all scenes to screens attribute.
     *
     * @param stage Stage for the application.
     * @throws Exception If all scenes were not successfully added to the stage.
     */
    @Override
    public void start(Stage stage) throws Exception {
        /*
        addScene("/View/FXML/LoginScreen.fxml");
        addScene("/View/FXML/MainPage.fxml");
        addScene("/View/FXML/accountCreation.fxml");
        addScene("/View/FXML/accountModification.fxml");
        if (screens.isEmpty()) {
            throw new Exception("Failed to add all scenes");
        }
         */

        stage.setTitle("TikZOverflow");
        stage.setMaximized(true);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        this.stage = stage;
        loginScreenController = new LoginScreenController(stage, this);
        loginScreenController.createScene();
    }

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





    public static ArrayList<Parent> getScreens() {
        return null; //screens;
    }



    @Override
    public void onSuccessfulLoginRequest() {
        goToMainPage();
    }

    private void goToMainPage() {
        mainPageController = new MainPageController(stage, this);
        mainPageController.show();
    }

    @Override
    public void onAccountCreationRequest() {
        accountCreationController = new AccountCreationController(stage, this);
        accountCreationController.show();
    }

    @Override
    public void backToLoginScreenRequest() {
        showLoginScreen();
    }

    @Override
    public void logout() {
        Session.getInstance().logOut();
        showLoginScreen();
    }

    @Override
    public void accountModificationRequest() {
        accountModificationController = new AccountModificationController(stage, this);
        accountModificationController.show();
    }

    @Override
    public void onModificationDone() {
        goToMainPage();
    }
}
