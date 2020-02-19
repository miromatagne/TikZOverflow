package View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/*
 This class is for testing interface only and is not meant
to be used to load fxml interface
 */
public class AccountCreation extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("accountCreation.fxml"));
        stage.setTitle("Account Creation");
        stage.setScene(new Scene(root));
        //stage.setMaximized(true);
        Text tcu = (Text) root.lookup("#termsAndConditionsText");
        tcu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Parent tcuRoot = FXMLLoader.load(getClass().getResource("termsAndConditions.fxml"));
                    stage.setScene(new Scene(tcuRoot));
                    File f = new File("src/View/tcu.txt");
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    String tmp, text = "";
                    while((tmp = br.readLine()) != null) {
                        text+=tmp+'\n';
                    }
                    Text tcuFullText = (Text) tcuRoot.lookup("#tcuFullText");
                    tcuFullText.setText(text);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        stage.show();
    }
}
