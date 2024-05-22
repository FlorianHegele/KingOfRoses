import boardifier.model.GameException;
import boardifier.model.Model;
import control.ConsoleController;
import model.GameConfigurationModel;
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

        int counterBlue = 0;
        int counterRed = 0;
        int counterTie = 0;

        int blueCardHeroUse = 0;
        int redCardHeroUse = 0;

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
                final PlayerData playerData = PlayerData.getPlayerData(model.getIdWinner());
                if(playerData == PlayerData.NONE) {
                    counterTie++;
                } else if (playerData == PlayerData.PLAYER_BLUE) {
                    counterBlue++;
                } else if (playerData == PlayerData.PLAYER_RED) {
                    counterRed++;
                } else if(playerData == null) {
                    throw new IllegalArgumentException("Player data is null");
                }
                final KoRStageModel stageModel = (KoRStageModel) model.getGameStage();
                blueCardHeroUse += 4 - ContainerElements.countElements(stageModel.getBlueHeroCardStack());
                redCardHeroUse += 4 - ContainerElements.countElements(stageModel.getRedHeroCardStack());

                System.out.println("Winner : " + playerData.name().toLowerCase());
            } catch (GameException e) {
                System.err.println("Cannot start the game n°"+ i +". Abort");
                break;
            }
            System.out.println();
        }

        System.out.println("Total game : " + iter);
        printState(PlayerData.PLAYER_BLUE, blueAI, counterBlue, blueCardHeroUse, iter);
        printState(PlayerData.PLAYER_RED, redAI, counterRed, redCardHeroUse, iter);
        printState(PlayerData.NONE, null, counterTie, -1, iter);
    }

    private static void printState(PlayerData playerData, AIData ai, int totalWin, int heroCard, int totalGame) {
        if(playerData == PlayerData.NONE) {
            System.out.println("Tie :");
            System.out.println("| Total tie: " + totalWin);
            System.out.println("| Tie rate: " + (100 * totalWin / totalGame)+"%");
        } else {
            System.out.println("Win AI "+(playerData.getId()+1)+" ("+ai.name().toLowerCase()+"):");
            System.out.println("| Total win: " + totalWin);
            System.out.println("| Win rate: " + (100 * totalWin / totalGame)+"%");
            System.out.println("| Total Hero card: " + heroCard);
            System.out.println("| Hero card rate: " + (25 * heroCard / totalGame)+"%");
        }
        System.out.println();
    }

}
