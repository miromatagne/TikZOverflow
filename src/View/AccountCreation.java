package View;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class AccountCreation extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("accountCreation.fxml"));
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        TextField username = (TextField) root.lookup("#usernameField");
        stage.show();
    }
}
