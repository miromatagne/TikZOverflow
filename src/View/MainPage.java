package View;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

public class MainPage extends Application {

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
