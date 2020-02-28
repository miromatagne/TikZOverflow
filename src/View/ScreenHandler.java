package View;

import Controller.Controller_superclass;
import Controller.Modification_Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    private static ArrayList<Parent> all_scene = new ArrayList<Parent>() ;
    static ArrayList<Controller_superclass> all_controller = new ArrayList<Controller_superclass>() ;
    static Scene scene ;
    static int id_current = 0 ; // Allow to see at which screen/scene number we are

    //Method
    public static void change_scene(int id_scene)
    {
        scene.setRoot(all_scene.get(id_scene));
        id_current = id_scene ;
        all_controller.get(id_scene).update();
    }

    public static Parent get_current_scene(){return all_scene.get(id_current);}
    public static int get_id_current(){return id_current;}


    public static void main(String[] args) {
        try{ launch(args);}
        catch(Exception expc){System.out.println("Error in launching the start() method");}
    }

    @Override
    public void start(Stage stage) throws Exception {
        try
        {
            FXMLLoader loader_login_screen = new FXMLLoader(getClass().getResource("LoginScreen.fxml")) ;
            all_scene.add(loader_login_screen.load()) ;
            all_controller.add(loader_login_screen.getController());

            FXMLLoader loader_main_page = new FXMLLoader(getClass().getResource("MainPage.fxml")) ;
            all_scene.add(loader_main_page.load()) ;
            all_controller.add(loader_main_page.getController());

            FXMLLoader loader_account_creation = new FXMLLoader(getClass().getResource("accountCreation.fxml")) ;
            all_scene.add(loader_account_creation.load()) ;
            all_controller.add(loader_account_creation.getController());

            FXMLLoader loader_modification = new FXMLLoader(getClass().getResource("accountModification.fxml"));
            all_scene.add(loader_modification.load()) ;
            all_controller.add(loader_modification.getController());

            scene = new Scene(all_scene.get(LOGINSCREEN));
        }
        catch(Exception expc){ System.out.println("Error loading all screen"); expc.printStackTrace();}
        stage.setTitle("TikZOverflow");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}
