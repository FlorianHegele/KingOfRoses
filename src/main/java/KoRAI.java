import boardifier.model.GameException;
import boardifier.model.Model;
import model.GameConfigurationModel;
import model.GameStatistic;
import model.data.AIData;
import model.data.PlayerData;
import utils.Boardifiers;
import utils.Strings;

import java.util.Map;

/**
 * The KoRAI class serves as a testing framework for running multiple iterations of AI games.
 * It initializes the AI components, sets up the game configuration,
 * and gathers statistics over a specified number of iterations.
 */
public class KoRAI {

    /**
     * The main method serves as the entry point for running AI tests in the King of Roses game.
     * It takes command line arguments to specify the AI types and number of iterations, and runs
     * the game multiple times to gather statistics.
     *
     * @param args Command line arguments:
     *             <p>
     *             args[0] - The AI type for the blue player.
     *             <p>
     *             args[1] - The AI type for the red player.
     *             <p>
     *             args[2] - The number of iterations to run (default is 500 if not specified).
     */
    public static void main(String[] args) {
        // Retrieve AI types from command line arguments
        final AIData blueAI = AIData.valueOf(args[PlayerData.PLAYER_BLUE.getId()]);
        final AIData redAI = AIData.valueOf(args[PlayerData.PLAYER_RED.getId()]);

        // Retrieve the number of iterations from command line arguments, default is 500
        final int iter = Strings.parseInt(args[2], 500);

        // Create a GameStatistic object to track game statistics
        final GameStatistic gameStatistic = new GameStatistic(blueAI, redAI, iter);

        // Run the game for the specified number of iterations
        for (int i = 1; i < iter + 1; i++) {
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

            try {
                // Start Game
                boardifiers.startGame();
                // When the game finishes, add statistics
                gameStatistic.addStatistic(model);
            } catch (GameException e) {
                System.err.println("Cannot start the game n°" + i + ". Abort");
                break;
            }
            System.out.println();
        }

        // Print the gathered statistics
        gameStatistic.printStatistics();
    }

}
