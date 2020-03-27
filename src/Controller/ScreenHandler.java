package Controller;

import View.ControllerSuperclass;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
Class used to contain all the FXML files in an ArrayList in order to switch between screens using a static method.
All the Controllers linked to the screens will be contained in a Array of Controllers and their update method will be
called whenever we change screen.
 */

public class ScreenHandler extends Application {
    //Scene IDs :
    public static int LOGINSCREEN = 0 ;
    public static int MAINPAGE = 1 ;
    public static int ACCOUNTCREATION = 2 ;
    public static int MODIFICATIONSCREEN = 3 ;

    //Attributes
    private static ArrayList<Parent> screens = new ArrayList<>() ;
    static ArrayList<ControllerSuperclass> controllers = new ArrayList<>() ;
    static Scene scene ;
    static int idCurrent = 0 ; // Allow to see at which screen/scene number we are


    /**
     * changeScene is a static function and will be used by other object such as Controller in order to change the root
     * the active scene.
     *
     * @param idScene  this is an int and it is used to choose which screen will be displayed.(Refer to the list above)
     */
    public static void changeScene(int idScene)
    {
        scene.setRoot(screens.get(idScene));
        idCurrent = idScene ;
        controllers.get(idScene).update();
    }

    /**
     *Method used to add a fxml file to the ArrayList and add the Controller link to that fxml in the ArrayList of
     * Controller.
     *
     * @param scenePath  String containing the path of the fxml file that will be loaded.
     */

    private void addScene(String scenePath)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
            screens.add(loader.load());
            controllers.add(loader.getController());
        }
        catch(Exception expc){System.out.println("Error loading all screen" + scenePath); expc.printStackTrace();}
    }

    @Override
    public void start(Stage stage) throws Exception {
        addScene("../View/LoginScreen.fxml");
        addScene("../View/MainPage.fxml");
        addScene("../View/accountCreation.fxml");
        addScene("../View/accountModification.fxml");

        if(screens.isEmpty()) {throw new Exception("Failed to log allScene") ;}

        scene = new Scene(screens.get(LOGINSCREEN));
        stage.setTitle("TikZOverflow");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}
