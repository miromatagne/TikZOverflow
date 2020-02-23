package View;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
        Text tcu = (Text) root.lookup("#termsAndConditionsText");

        /*
        tcu.setOnMouseClicked(mouseEvent ->  {
            try {
                Parent tcuRoot = FXMLLoader.load(getClass().getResource("termsAndConditions.fxml"));
                stage.setScene(new Scene(tcuRoot));
                File f = new File("src/View/tcu.txt");
                BufferedReader br = new BufferedReader(new FileReader(f));
                String tmp, text = "";
                while((tmp = br.readLine()) != null) {
                    text = text.concat(tmp+'\n');
                }
                Text tcuFullText = (Text) tcuRoot.lookup("#tcuFullText");
                tcuFullText.setText(text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        */
        tcu.setOnMouseClicked(mouseEvent -> {
            try {
                Parent tcuRoot = FXMLLoader.load(getClass().getResource("termsAndConditions.fxml"));
                Scene tcuScene = new Scene(tcuRoot);
                Stage tcuStage = new Stage();
                tcuStage.initModality(Modality.APPLICATION_MODAL);
                tcuStage.setTitle("Terms and conditions");
                File f = new File("src/View/tcu.txt");
                BufferedReader br = new BufferedReader(new FileReader(f));
                String tmp, text = "";
                while((tmp = br.readLine()) != null) {
                    text = text.concat(tmp+'\n');
                }
                tcuStage.setScene(tcuScene);
                tcuStage.show();
                Text tcuFullText = (Text) tcuRoot.lookup("#tcuFullText");
                tcuFullText.setText(text);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        tcu.setOnMouseMoved(mouseEvent -> {
            try {
                tcu.setCursor(Cursor.HAND);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        stage.show();
    }
}
