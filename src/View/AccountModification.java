package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AccountModification extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("accountModification.fxml"));
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        //TextField username = (TextField) root.lookup("#usernameField");
        stage.show();
    }
}
