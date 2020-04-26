package View.ViewControllers;

/**
 * ControllerSuperclass is an abstract class and the Mother class of all view related controllers it only contain the
 * abstract function update and is necessary to regroup all the view controller in a single Array in ScreenHandler
 * allowing the use of generic function to switch between scene.
 * The abstract function update will be overwritten in each class and it can be void for some of them because only  a
 * portion of them contains object that needs to be update. For example some TextArea in certain view contains
 * information of the current user and needs to be updated every time the user is switch.
 * The function update is called whenever the function changeScene of ScreenHandler is used.
 */
abstract public class ControllerSuperclass {

    abstract public void update();
}
