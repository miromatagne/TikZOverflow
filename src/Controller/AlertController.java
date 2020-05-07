package Controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class AlertController {

    public static void showStageError(String header, String content){
        AlertController.showStageError(header,content,false);
    }
    public static void showStageError(String header, String content, boolean hasToQuit){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        createAlert(header, content, alert);
        if (hasToQuit) {
            Platform.exit();
        }
    }

    public static void showStageInfo(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        createAlert(header, content, alert);
    }

    private static void createAlert(String header, String content, Alert alert) {
        alert.setTitle(header);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
