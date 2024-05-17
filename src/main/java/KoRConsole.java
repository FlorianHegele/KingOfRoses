import boardifier.model.Model;
import control.ConsoleController;
import control.KoRController;
import boardifier.model.GameException;
import boardifier.view.View;

import boardifier.control.StageFactory;
import control.GameConfigurationController;
import model.GameConfigurationModel;

public class KoRConsole {

    public static void main(String[] args) {
        // CREATE CONSOLE CONTROLLER
        final ConsoleController consoleController = new ConsoleController(false);

        // CREATE MODEL
        final Model model = new Model();

        // SETUP GAME CONFIGURATION
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(
                model,
                GameConfigurationModel.DEFAULT_PLAYER_MODE,
                1
        );
        final GameConfigurationController gameConfigurationController = new GameConfigurationController(gameConfigurationModel, consoleController);
        gameConfigurationController.doCheck();

        // LOAD GAME ELEMENTS
        StageFactory.registerModelAndView("kor", "model.KoRStageModel", "view.KoRStageView");
        final View korView = new View(model);
        final KoRController control = new KoRController(model, korView, consoleController, gameConfigurationModel);
        control.setFirstStageName("kor");
        try {
            control.startGame();
            control.stageLoop();
        } catch (GameException e) {
            System.out.println("Cannot start the game. Abort");
        }
    }

}
