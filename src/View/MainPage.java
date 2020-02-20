package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
