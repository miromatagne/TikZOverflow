package Controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class AlertController {

    public static void showStageError(String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
        Platform.exit();
    }
}
