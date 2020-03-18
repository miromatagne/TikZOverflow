package View;

import Controller.ScreenHandler;
import Model.FileHandler;
import Model.LatexCompiler;


import Controller.Session;;
import Model.Shapes.*;
import Controller.ScreenHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController extends ControllerSuperclass  implements Initializable{

    @FXML private TextArea codeInterface;
    @FXML private VBox suiviForme;
    @FXML private ScrollPane scroll;

    /**
     * when we click on "compile" button it sends the text to a save file
     *
     */
    @FXML
    public void compile() {
       /* FileHandler fh = new FileHandler();
        fh.setupSaveProjectDirectory("project");
        boolean res = fh.createProject(codeInterface.getText());*/ //Done in the first it
        String filePath = "./Latex/" + Session.getInstance().getUser().getUsername() + ".tex";
        try {
            LatexCompiler.runProcess(filePath);
        }
        catch(Exception e){System.err.println("Error in compilation :  " + e.toString());}
    }

    @FXML
    public void modificationButtonAction()
    {
        ScreenHandler.changeScene(ScreenHandler.MODIFICATIONSCREEN);
    }

    @Override
    public void update() {
        //Nothing to update for now but the project it will needs to display will probably be updated
    }

    /**
     * Action of "Log Out" button. Logs current user out and goes back to LoginScreen.
     */
    public void logout() {
        Session.getInstance().logOut();
        ScreenHandler.changeScene(ScreenHandler.LOGINSCREEN);
    }

    /**
     * Adds a Label to the panel on the right hand side of the screen describing the
     * shape that was added.
     * @param shape Shape to be added
     */
    @FXML
    public void addShape(Shape shape) {
        //String shapeText = "Added rectangle of length 5 and width 3 at position 5 ggggggeqegggggggggg.";
        String shapeText = createString(shape);

        Label label = new Label(shapeText);
        label.setTextFill(Paint.valueOf("White"));
        label.setStyle("-fx-border-color: #3A3A3A; -fx-background-color: #4D4D4D");
        label.setPadding(new Insets(5,5,5,5));
        label.prefWidthProperty().bind(suiviForme.prefWidthProperty());
        label.setWrapText(true);
        suiviForme.getChildren().add(label);
    }

    /**
     * Initialization of the region where the names of the added shapes will appear
     * (VBox and ScrollPane).
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        suiviForme.prefWidthProperty().bind(scroll.prefWidthProperty());
        suiviForme.prefHeightProperty().bind(scroll.prefHeightProperty());
    }

    /**
     * Creation of the String to insert into the label when a new shape has been added.
     * This String is different depending on the shape added.
     * @param shape Shape to be added
     * @return returnString
     */
    public String createString(Shape shape) {
        String returnString = "Added ";

        returnString += shape.getDescription();

        return returnString;
    }

}
