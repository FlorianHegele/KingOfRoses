import control.KoRController;
import boardifier.model.GameException;
import boardifier.view.View;

import boardifier.control.StageFactory;
import boardifier.model.Model;
import control.SetupController;

public class KoRConsole {

    public static void main(String[] args) {
        // CREATE MODEL (WITH SPECIFIC DATA IF YOU WANT)
        Model model = new Model();
        SetupController setupController = SetupController.init(model, args);

        // LOAD GAME ELEMENTS
        StageFactory.registerModelAndView("kor", "model.KoRStageModel", "view.KoRStageView");
        View korView = new View(model);
        KoRController control = new KoRController(model, korView, setupController);
        control.setFirstStageName("kor");
        try {
            control.startGame();
            control.stageLoop();
        } catch (GameException e) {
            System.out.println("Cannot start the game. Abort");
        }
    }

}
