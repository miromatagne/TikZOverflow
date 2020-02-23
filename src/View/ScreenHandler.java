package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

public class ScreenHandler extends Application {
    //Attribut
    static ArrayList<Pane> all_scene = new ArrayList<Pane>() ;
    static AnchorPane root ; //AnchorPane is the basic settings for all Pane
    static int id_current = 0 ; // Allow to see at which screen/scene number we are

    //Methode
    public static void change_scene(int id_scene)
    {
        if(id_scene != id_current){root.getChildren().remove(get_current_scene());}
        id_current = id_scene ;
        root.getChildren().add(all_scene.get(id_scene));
        System.out.println(id_current);
    }

    public static Pane get_current_scene(){return all_scene.get(id_current);}
    public static int get_id_current(){return id_current;}


    public static void main(String[] args) {
        try{ launch(args);}
        catch(Exception expc){System.out.println("Error in launching the start() method");}
    }

    @Override
    public void start(Stage stage) throws Exception {
        try
        {
            root = (AnchorPane) FXMLLoader.load(getClass().getResource("starting_screen.fxml"));

            all_scene.add(FXMLLoader.load(getClass().getResource("principaleScreen.fxml"))) ;
            all_scene.add(FXMLLoader.load(getClass().getResource("accountModification.fxml"))) ;

            change_scene(0);
        }
        catch(Exception expc){ System.out.println("Error loading all screen"); expc.printStackTrace();}
        stage.setTitle("Home");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
