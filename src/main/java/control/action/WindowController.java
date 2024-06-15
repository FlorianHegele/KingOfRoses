package control.action;

import boardifier.control.Controller;
import control.KoRController;
import control.KoRControllerAction;
import model.data.WindowType;
import view.KoRView;
import view.window.WindowView;

public abstract class WindowController {

    protected final KoRView koRView;
    protected final WindowView windowView;
    protected final KoRControllerAction actionController;
    protected final Controller controller;

    protected WindowController(KoRControllerAction actionController, KoRView koRView, WindowType windowType) {
        this.actionController = actionController;
        this.controller = actionController.getController();
        this.koRView = koRView;
        this.windowView = koRView.getWindow(windowType);
    }

    public abstract void setHandler();

}
