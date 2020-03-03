package View;

import Controller.ControllerSuperclass;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

/*Class used to contain all the FXML in an ArrayList in order to switch between screen using a static method.
    -void main() for the application that launch the start method
    -void start(Stage stage) will initialize all the object from the class and fill the ArrayList of FXML view
    -void change_scene(int) static method that takes an int which will be the id of the new screen loaded. Static because
     it will be called by other objects such as button controller
    -Pane get_current_scene() return the actual scene which is a Pane object
    -int get_current_id() return the current id of the actual loaded view
 */

public class ScreenHandler extends Application
{
    //Id scene :
    public static int LOGINSCREEN = 0 ;
    public static int MAINPAGE = 1 ;
    public static int ACCOUNTCREATION = 2 ;
    public static int MODIFICATIONSCREEN = 3 ;

    //Attribut
    private static ArrayList<Parent> screens = new ArrayList<>() ;
    static ArrayList<ControllerSuperclass> controllers = new ArrayList<>() ;
    static Scene scene ;
    static int idCurrent = 0 ; // Allow to see at which screen/scene number we are

    //Method
    public static void changeScene(int id_scene)
    {
        scene.setRoot(screens.get(id_scene));
        idCurrent = id_scene ;
        controllers.get(id_scene).update();
    }

    public static void main(String[] args) {
        try{ launch(args);}
        catch(Exception expc){System.out.println("Error in launching the start() method");}
    }

    @Override
    public void start(Stage stage) throws Exception {
        try
        {
            FXMLLoader loader_login_screen = new FXMLLoader(getClass().getResource("LoginScreen.fxml")) ;
            screens.add(loader_login_screen.load()) ;
            controllers.add(loader_login_screen.getController());

            FXMLLoader loader_main_page = new FXMLLoader(getClass().getResource("MainPage.fxml")) ;
            screens.add(loader_main_page.load()) ;
            controllers.add(loader_main_page.getController());

            FXMLLoader loader_account_creation = new FXMLLoader(getClass().getResource("accountCreation.fxml")) ;
            screens.add(loader_account_creation.load()) ;
            controllers.add(loader_account_creation.getController());

            FXMLLoader loader_modification = new FXMLLoader(getClass().getResource("accountModification.fxml"));
            screens.add(loader_modification.load()) ;
            controllers.add(loader_modification.getController());

            scene = new Scene(screens.get(LOGINSCREEN));
        }
        catch(Exception expc){ System.out.println("Error loading all screen"); expc.printStackTrace();}
        stage.setTitle("TikZOverflow");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}
