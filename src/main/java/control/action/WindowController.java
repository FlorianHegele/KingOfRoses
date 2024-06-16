package control.action;

import boardifier.control.Controller;
import control.KoRControllerAction;
import model.data.WindowType;
import view.KoRView;
import view.window.WindowView;

/**
 * Abstract class representing a controller for different types of windows.
 * Manages the common functionalities needed for window controllers.
 */
public abstract class WindowController {

    protected final KoRView koRView;
    protected final WindowView windowView;
    protected final KoRControllerAction actionController;
    protected final Controller controller;

    /**
     * Constructs a WindowController with the specified action controller, view, and window type.
     *
     * @param actionController the action controller managing the game actions.
     * @param koRView the main view containing the windows.
     * @param windowType the type of window to be controlled.
     */
    protected WindowController(KoRControllerAction actionController, KoRView koRView, WindowType windowType) {
        this.actionController = actionController;
        this.controller = actionController.getController();
        this.koRView = koRView;
        this.windowView = koRView.getWindow(windowType);
    }

    /**
     * Abstract method to set the handler for the window's events.
     * Needs to be implemented by subclasses.
     */
    public abstract void setHandler();

}
