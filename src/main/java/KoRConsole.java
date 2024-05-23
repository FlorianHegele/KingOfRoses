import boardifier.model.GameException;
import boardifier.model.Model;
import control.ConsoleController;
import model.GameConfigurationModel;
import utils.Boardifiers;

import static model.GameConfigurationModel.*;

/**
 * The KoRConsole class serves as the entry point for the King of Roses game in console mode.
 * It initializes the necessary components such as the console controller, model, and game configuration,
 * and starts the game loop.
 */
public class KoRConsole {

    /**
     * The main method serves as the entry point for the King of Roses game in console mode.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Create a console controller (to handle keyboard input)
        final ConsoleController consoleController = new ConsoleController(false);

        // Create the model
        final Model model = new Model();

        // Set up game configuration
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(model, 1,
                DEFAULT_PLAYER_MODE, DEFAULT_LOGGER_MODE, DEFAULT_PLAYER_INTERACTION, DEFAULT_RENDER_GAME);

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
