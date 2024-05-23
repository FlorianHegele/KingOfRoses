import boardifier.model.GameException;
import boardifier.model.Model;
import control.ConsoleController;
import model.GameConfigurationModel;
import model.GameStatistic;
import model.KoRStageModel;
import model.data.AIData;
import model.data.PlayerData;
import utils.Boardifiers;
import utils.ContainerElements;
import utils.Strings;

import java.util.Map;

/**
 * The KoRConsole class serves as the entry point for the King of Roses game in console mode.
 * It initializes the necessary components such as the console controller, model, and game configuration,
 * and starts the game loop.
 */
public class KoRAI {

    /**
     * The main method serves as the entry point for the King of Roses game in console mode.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        final AIData blueAI = AIData.valueOf(args[PlayerData.PLAYER_BLUE.getId()]);
        final AIData redAI = AIData.valueOf(args[PlayerData.PLAYER_RED.getId()]);

        final int iter = Strings.parseInt(args[2], 500);

        final GameStatistic gameStatistic = new GameStatistic(blueAI, redAI, iter);

        for (int i = 1; i < iter+1; i++) {
            System.out.println("Iteration #" + i);

            // Create the model
            final Model model = new Model();

            // Set up game configuration
            final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(
                    model,
                    2,
                    0,
                    true,
                    false
            );
            gameConfigurationModel.addAI(Map.of(PlayerData.PLAYER_BLUE, blueAI, PlayerData.PLAYER_RED, redAI));

            // Init Game
            final Boardifiers boardifiers = new Boardifiers(model, gameConfigurationModel);
            boardifiers.initGame();

            // Start Game
            try {
                boardifiers.startGame();
                // Game finish

                gameStatistic.addStatistic(model);
            } catch (GameException e) {
                System.err.println("Cannot start the game n°"+ i +". Abort");
                break;
            }
            System.out.println();
        }

        gameStatistic.printStatistics();
    }

}
