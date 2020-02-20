package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginScreen extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        primaryStage.setTitle("TikZOverflow");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public void login(ActionEvent actionEvent) {

    }

    public void newAccount(MouseEvent mouseEvent) {
        System.out.println("test");
    }
}
