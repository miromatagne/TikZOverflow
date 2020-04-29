package Controller;

import View.ViewControllers.ProjectSelectionViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectSelectionController implements ProjectSelectionViewController.ProjectSelectionViewControllerListener {

    private final Stage stage;
    private final ProjectSelectionControllerListener listener;
    private ProjectSelectionViewController controller;

    /**
     * Constructor.
     *
     * @param stage    stage this screen needs to be in.
     * @param listener listener used to communicate with ScreenHandler
     */
    public ProjectSelectionController(Stage stage, ProjectSelectionControllerListener listener) {
        this.stage = stage;
        this.listener = listener;
    }
    /**
     * Opens this scene coming from another one.
     */
    public void show() {
        try {
            FXMLLoader loader = getLoader();
            stage.getScene().setRoot(loader.getRoot());
        } catch (IOException e) {
            System.err.println("Error loading /View/FXML/projectSelection.fxml");
            e.printStackTrace();
        }
    }

    /**
     * Gets parent root from FXML file.
     * @return root
     * @throws IOException if FXML file was not found
     */
    private FXMLLoader getLoader() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/projectSelection.fxml"));
        loader.load();
        controller = loader.getController();
        controller.setListener(this);
        return loader;
    }

    @Override
    public void accountModificationRequest() {
        listener.accountModificationRequest();
    }

    @Override
    public void onLogoutRequest() {
        listener.logout();
    }

    private void showPopUp(String FXMLPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLPath));
        Parent root = loader.load();
        Stage popupStage = new Stage();
        popupStage.setTitle(title);
        Scene scene = new Scene(root, 350, 150);
        popupStage.setScene(scene);
        popupStage.show();
    }

    @Override
    public void showSharePopUp() throws IOException {
        showPopUp("/View/FXML/shareProjectPopUp.fxml", "Share your project");
    }

    @Override
    public void showRenamePopUp() throws IOException {
        showPopUp("/View/FXML/renameProjectPopUp.fxml", "Rename your project");
    }

    public interface ProjectSelectionControllerListener {
        void accountModificationRequest();
        void logout();
    }
}
