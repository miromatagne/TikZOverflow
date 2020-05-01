package View.ViewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * This abstract class is used for account view controllers (create and modification) because they have similarities
 */
public abstract class AccountViewController {
    @FXML
    TextField usernameField, firstNameField, lastNameField, emailField;
    @FXML
    PasswordField passwordField, passwordConfirmationField;

    /**
     * Change TextField style. Used when a field is not correct.
     * @param field Name of the field that must change
     * @param style New style to be applied. If different from "red",  it's
     *              considered to be "default".
     */
    //@FPL: cette méthode apporte de la vue dans le contrôleur car
    // le contrôleur va demander "mets en rouge le champs username"
    // alors qu'il devrait demander "mets en erreur le champs username"
    // Je sais que c'est minime comme différence mais on replace la responsabilité
    // de la vue au bon endroit
    public void setTextFieldStyle(String field, String style){
        String textFieldStyle = "-fx-text-inner-color: black;";
        if(style.equals("red")) {
            textFieldStyle = "-fx-text-inner-color: red; -fx-text-box-border: red;";
        }
        switch (field){
            //@FPL : "magic string" => utilisez des constantes ou des méthodes
            case "username": usernameField.setStyle(textFieldStyle);break;
            case "firstName": firstNameField.setStyle(textFieldStyle);break;
            case "lastName": lastNameField.setStyle(textFieldStyle);break;
            case "password": passwordField.setStyle(textFieldStyle);break;
            case "passwordConfirmation": passwordConfirmationField.setStyle(textFieldStyle);break;
            case "email": emailField.setStyle(textFieldStyle);
        }
    }
}
