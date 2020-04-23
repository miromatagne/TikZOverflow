package Controller;

import Model.FieldChecker;
import Model.Shapes.ShapeFactory;
import Model.Shapes.Shape;
import View.MainPageController;
import View.ShapeMenu.AllShapeMenus.MenuController;
import View.ShapeMenu.ShapeMenuViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles shape creation with popup and adding them to the shape list.
 */
public class ShapeMenuController {
    private static ArrayList<MenuController> allControllers = new ArrayList<>();
    private static ArrayList<Parent> allShapes = new ArrayList<>();
    ShapeMenuViewController shapeMenuViewController;
    private MainPageController mainPageController;
    private FieldChecker fieldChecker = new FieldChecker();
    private Stage popUpStage;
    private int idCurrent;


    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    /**
     * Creates a new shape and sends the String to be added to the main page.
     *
     * @param idCurrent id of the current scene: identifies the type of shape to create
     * @param allData   Properties of the shape
     * @param color     Color of the shape
     * @param label     Label of the shape
     */
    public void addShape(int idCurrent, ArrayList<Float> allData, Color color, String label) {
        Shape shape = ShapeFactory.getInstance(idCurrent, allData, color, label);
        mainPageController.addShape(createString(shape));
        closePopup();
    }

    /**
     * Creation of the String to insert into the label when a new shape has been added.
     * This String is different depending on the shape added.
     *
     * @param shape Shape which has to be converted in a string
     * @return returnString     String which describes the shape given in parameter
     */
    public String createString(Shape shape) {
        String returnString = "Added ";
        returnString += shape.getDescription();
        returnString += " Color : " + shape.getColor() + ".";
        return returnString;
    }

    /**
     * Clear the shape menus by calling the update function of the controllers
     */
    public void clearShapeMenus() {
        for (MenuController menuController : allControllers) {
            menuController.update();
        }
    }

    /**
     * Add the scenes for all the shape menus to the ArrayList.
     *
     * @throws IOException If there was an error while reading a .fxml file
     */
    public void setUpScenes() throws IOException {
        addScene("/View/ShapeMenu/FxmlFiles/rectangleMenu.fxml");
        addScene("/View/ShapeMenu/FxmlFiles/circleMenu.fxml");
        addScene("/View/ShapeMenu/FxmlFiles/lineMenu.fxml");
        addScene("/View/ShapeMenu/FxmlFiles/curvedLineMenu.fxml");
        addScene("/View/ShapeMenu/FxmlFiles/arrowMenu.fxml");
        if (allShapes.isEmpty()) {
            throw new IOException();
        }
    }

    /**
     * Change the menu to the menu indicated by the value given in parameter
     *
     * @param idNew Corresponds to the id of the menu which is going to be set
     */
    public void changeToMenu(int idNew) {
        shapeMenuViewController.getShapeScene().setRoot(allShapes.get(idNew));
        if (idCurrent != idNew) {
            clearShapeMenus();
        }
        shapeMenuViewController.changeTextColor(idNew);
        idCurrent = idNew;
    }

    /**
     * Add a scene to the array list containing all the menus and add its controller to an array too
     *
     * @param scenePath Path to the corresponding fxml file
     */
    private void addScene(String scenePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
            allShapes.add(loader.load());
            allControllers.add(loader.getController());
        } catch (Exception exc) {
            System.out.println("Error loading scene " + scenePath);
            exc.printStackTrace();
        }
    }

    /**
     * Check if the fields for the properties of the shape are correctly filled.
     * Highlight them in red if not.
     */
    public void verifyShape() {
        ArrayList<String> allFields = allControllers.get(idCurrent).getAllFields();
        ArrayList<Float> allDataInField = new ArrayList<>();
        String redStyle = "-fx-text-box-border: red";
        String normalStyle = "";
        for (int i = 0; i < allFields.size(); i++) {
            String tempStringInField = allFields.get(i);
            if (!fieldChecker.isValidNumber(tempStringInField) || tempStringInField == null) {
                if (i < allControllers.get(idCurrent).getAllTextFields().size()) {
                    allControllers.get(idCurrent).getAllTextFields().get(i).setStyle(redStyle);
                }
            } else {
                allDataInField.add(Float.parseFloat(tempStringInField));
                if (i < allControllers.get(idCurrent).getAllTextFields().size()) {
                    allControllers.get(idCurrent).getAllTextFields().get(i).setStyle(normalStyle);
                }
            }
        }
        String label = allControllers.get(idCurrent).getLabel().getText();
        System.out.println(label);
        if(label.equals("")) {
            allControllers.get(idCurrent).getLabel().setStyle(redStyle);
        }
        //addShape(idCurrent, allDataInField, allControllers.get(idCurrent).getColor(), label);
    }

    /**
     * Create the Pop Up menu for the shapes and add the menus to it.
     *
     * @throws IOException If there was an error while reading the .fxml file
     */
    public void popUpInitialize() throws IOException {
        popUpStage = new Stage();
        popUpStage.setTitle("Add Shape Menu");
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ShapeMenu/FxmlFiles/addShapeMenu.fxml"));
        Parent addShapeMenuRoot = loader.load();
        shapeMenuViewController = loader.getController();
        shapeMenuViewController.setShapeMenuController(this);
        setUpScenes();
        changeToMenu(ShapeMenuViewController.ARROW);
        popUpStage.setScene(new Scene(addShapeMenuRoot));
    }

    /**
     * Update and show the Pop Up menu.
     */
    public void showPopUp() {
        shapeMenuViewController.update();
        popUpStage.show();
    }

    /**
     * Hide the Pop Up menu.
     */
    public void closePopup() {
        popUpStage.hide();
    }

}
