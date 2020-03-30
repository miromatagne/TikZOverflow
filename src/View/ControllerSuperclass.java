package View;

/**
 * ControllerSuperclass is an abstract class and the Mother class of all view related controllers.
 * The function update is called whenever the function changeScene of ScreenHandler is used in order to load
 * another scene.
 */
abstract public class ControllerSuperclass {

    abstract public void update();
}
