package Controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * Class used to show alerts to the user and sometimes exit the application if necessary.
 */
public final class AlertController {

    private AlertController(){}

    /**
     * Shows an error alert to the user without quitting the application.
     *
     * @param header Header of the alert
     * @param content Content of the alert message
     */
    public static void showStageError(String header, String content){
        AlertController.showStageError(header,content,false);
    }

    /**
     * Shows an error alert to the user and quits the application in certain cases.
     *
     * @param header Header of the alert
     * @param content Content of the alert message
     * @param hasToQuit If true, quits the application after showing the alert.
     */
    public static void showStageError(String header, String content, boolean hasToQuit){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        createAlert(header, content, alert);
        if (hasToQuit) {
            Platform.exit();
        }
    }

    /**
     * Shows an information alert to the user.
     *
     * @param header Header of the alert
     * @param content Content of the alert message
     */
    public static void showStageInfo(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        createAlert(header, content, alert);
    }

    /**
     * Creates and shows an alert.
     *
     * @param header Header of the alert
     * @param content Content of the alert message
     * @param alert Alert to create and show
     */
    private static void createAlert(String header, String content, Alert alert) {
        alert.setTitle(header);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
