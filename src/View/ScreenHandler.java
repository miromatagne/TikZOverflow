package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ScreenHandler extends Application {
    //Attribut
    static ArrayList<Pane> all_scene = new ArrayList<Pane>() ;
    static AnchorPane root ; //AnchorPane is the basic settings for all Pane
    static int id_current = 0 ; // Allow to see at which screen/scene number we are

    //Methode
    public static void change_scene(int id_scene)
    {
        if(id_scene != id_current){root.getChildren().remove(id_current);}
        id_current = id_scene ;
        root.getChildren().add(all_scene.get(id_scene));
    }

    public static Pane get_scene(int id_scene){return all_scene.get(id_scene);}

    public static void main(String[] args) {
        try{ launch(args);}
        catch(Exception expc){System.out.println("Nop");}
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
