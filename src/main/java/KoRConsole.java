import boardifier.model.Model;
import control.ConsoleController;
import control.KoRController;
import boardifier.model.GameException;
import boardifier.view.View;

import boardifier.control.StageFactory;
import control.GameConfigurationController;
import model.GameConfigurationModel;

/**
 * The KoRConsole class serves as the entry point for the King of Roses game in console mode.
 * It initializes the necessary components such as the console controller, model, and game configuration,
 * and starts the game loop.
 */
public class KoRConsole {

    /**
     * The main method serves as the entry point for the King of Roses game in console mode.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Create a console controller
        final ConsoleController consoleController = new ConsoleController(false);

        // Create the model
        final Model model = new Model();

        // Set up game configuration
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(
                model,
                GameConfigurationModel.DEFAULT_PLAYER_MODE,
                1
        );
        final GameConfigurationController gameConfigurationController = new GameConfigurationController(gameConfigurationModel, consoleController);
        gameConfigurationController.doCheck();

        // Load game elements
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
