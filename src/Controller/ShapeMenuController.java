package Controller;

import Controller.Exceptions.AddSceneException;
import Controller.Exceptions.ShapeMenuControllerConstructorException;
import Model.FieldChecker;
import Model.Shapes.ShapeFactory;
import Model.Shapes.Shape;
import View.ViewControllers.MainPageViewController;
import View.ShapeMenu.AllShapeMenus.MenuController;
import View.ShapeMenu.ShapeMenuViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles shape creation with popup and adding them to the shape list.
 */
public class ShapeMenuController implements MainPageViewController.AddNewShapeButtonListener, ShapeMenuViewController.ShapeMenuViewControllerListener {
    private ArrayList<MenuController> allControllers;
    private ArrayList<Parent> allShapes;
    private ShapeMenuViewController shapeMenuViewController;
    private MainPageViewController mainPageViewController;
    private Stage popUpStage;
    private int idCurrent;

    /**
     * Create the Pop Up menu for the shapes and add the menus to it.
     *
     * @throws ShapeMenuControllerConstructorException If there was an error while constructing the instance
     */
    public ShapeMenuController()  throws ShapeMenuControllerConstructorException {
        try {
            popUpStage = new Stage();
            popUpStage.setTitle("Add Shape Menu");
            popUpStage.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ShapeMenu/FxmlFiles/addShapeMenu.fxml"));
            Parent addShapeMenuRoot = loader.load();
            shapeMenuViewController = loader.getController();
            shapeMenuViewController.setListener(this);
            allControllers = new ArrayList<>();
            allShapes = new ArrayList<>();
            setUpScenes();
            popUpStage.setScene(new Scene(addShapeMenuRoot));
            changeToMenu(ShapeMenuViewController.ARROW);
        } catch (IOException | AddSceneException e) {
            throw new ShapeMenuControllerConstructorException(e);
        }
    }

    public void setMainPageViewController(MainPageViewController mainPageViewController) {
        this.mainPageViewController = mainPageViewController;
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
        mainPageViewController.addShape(shape);
        closePopup();
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
     * @throws AddSceneException when a scene can not be added
     */
    public void setUpScenes() throws AddSceneException {
        addScene("/View/ShapeMenu/FxmlFiles/rectangleMenu.fxml");
        addScene("/View/ShapeMenu/FxmlFiles/circleMenu.fxml");
        addScene("/View/ShapeMenu/FxmlFiles/lineMenu.fxml");
        addScene("/View/ShapeMenu/FxmlFiles/curvedLineMenu.fxml");
        addScene("/View/ShapeMenu/FxmlFiles/arrowMenu.fxml");
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
     * @throws AddSceneException when the fxml file is not loaded successfully
     */

    private void addScene(String scenePath) throws AddSceneException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
            allShapes.add(loader.load());
            allControllers.add(loader.getController());
        } catch (IOException e) {
            throw new AddSceneException(e);
        }
    }

    /**
     * Check if the fields for the properties of the shape are correctly filled.
     * Highlight them in red if not.
     */
    public void verifyShape() {
        boolean canAddShape = true;
        FieldChecker fieldChecker = new FieldChecker();
        ArrayList<String> allFields = allControllers.get(idCurrent).getAllFields();
        ArrayList<Float> allDataInField = new ArrayList<>();
        String redStyle = "-fx-text-box-border: red";
        String normalStyle = "";
        for (int i = 0; i < allFields.size(); i++) {
            String tempStringInField = allFields.get(i);
            if (!fieldChecker.isValidNumber(tempStringInField) || tempStringInField == null) {
                canAddShape = false;
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
        if(label.equals("")) {
            allControllers.get(idCurrent).getLabel().setStyle(redStyle);
            canAddShape=false;
        }
        if(canAddShape) {
            addShape(idCurrent, allDataInField, allControllers.get(idCurrent).getColor(), label);
        }
    }



    /**
     * Update and show the Pop Up menu.
     */
    public void showPopUp() {
        clearShapeMenus();
        changeToMenu(ShapeMenuViewController.ARROW);
        popUpStage.show();
    }

    /**
     * Hide the Pop Up menu.
     */
    public void closePopup() {
        popUpStage.hide();
    }

    @Override
    public void onButtonPressed() {
        showPopUp();
    }


    @Override
    public void onConfirmButtonPressed() {
        verifyShape();
    }
}
