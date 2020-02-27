package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;


public class MainPage extends Application {
    /*
        Handles the visual aspect of the main page, which contains the canvas, the code interface, the configuration
        panel, as well as several buttons (e.g. profile modification, list of projects).
     */

    public static void main(String[] args){
        Application.launch(MainPage.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        primaryStage.setTitle("TikZOverflow");

        Scene scene = new Scene(root, 500, 500);

        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
