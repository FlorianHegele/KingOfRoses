import boardifier.model.Model;
import control.ConsoleController;
import control.KoRController;
import boardifier.model.GameException;
import boardifier.view.View;

import boardifier.control.StageFactory;
import control.GameConfigurationController;
import model.GameConfigurationModel;
import model.data.AIData;
import model.data.PlayerData;
import utils.Boardifiers;

import java.util.Map;

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
        // Create a console controller (to handle keyboard input)
        final ConsoleController consoleController = new ConsoleController(true);

        // Create the model
        final Model model = new Model();

        // Set up game configuration
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(
                model,
                2,
                1,
                true
        );
        gameConfigurationModel.addAI(Map.of(PlayerData.PLAYER_BLUE, AIData.RANDOM, PlayerData.PLAYER_RED, AIData.GUIDE));

        // Init Game
        final Boardifiers boardifiers = new Boardifiers(model, consoleController, gameConfigurationModel);
        boardifiers.initGame();

        // Start Game
        try {
            boardifiers.startGame();
        } catch (GameException e) {
            System.out.println("Cannot start the game. Abort");
        }
        System.exit(model.getIdWinner());
    }

}
