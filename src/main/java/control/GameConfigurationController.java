package control;

import model.GameConfigurationModel;
import model.data.PlayerData;
import model.data.AIData;
import utils.Strings;

/**
 * This class controls the game configuration during the game startup.
 * It allows setting various configurations such as logger mode, random seed, player mode, player names, and AI settings.
 */
public class GameConfigurationController {

    private final GameConfigurationModel configurationModel;
    private final ConsoleController console;

    /**
     * Constructs a GameConfigurationController with the specified configuration model and console controller.
     *
     * @param gameConfigurationModel the game configuration model to be used.
     * @param consoleController the console controller for handling user input and output.
     */
    public GameConfigurationController(GameConfigurationModel gameConfigurationModel, ConsoleController consoleController) {
        this.configurationModel = gameConfigurationModel;
        this.console = consoleController;
    }

    /**
     * Initiates the configuration check process by prompting the user to set various configuration options.
     * Allows skipping input by pressing enter.
     */
    public void doCheck() {
        console.printlnCheckMessage("Press enter if you want to skip !");

        //setLoggerMode();
        setSeed();
        setPlayerMode();
        setPlayerName();
        setAI();
    }

    /**
     * Prompts the user to set the logger mode.
     * 0: None, 1: Full.
     * @deprecated because of the logger mode in the GameConfigurationModel
     */
    @Deprecated(since = "20/05/2024", forRemoval = true)
    private void setLoggerMode() {
        console.printCheckMessage("Logger Mode (0: None, 1:Full) ? ");
        final String line = console.getCheckConsoleLine();
        if (!line.isEmpty()) configurationModel.setLoggerMode(Strings.parseInt(line));
        configurationModel.updateLogger();
    }

    /**
     * Prompts the user to set a specific seed for the game.
     */
    private void setSeed() {
        console.printCheckMessage("specific seed ? ");
        final String line = console.getCheckConsoleLine();
        if (!line.isEmpty()) GameConfigurationModel.RANDOM.setSeed(Strings.parseLong(line));
    }

    /**
     * Prompts the user to set the player mode.
     * 0: PvP, 1: PvAI, 2: AIvAI.
     */
    private void setPlayerMode() {
        console.printCheckMessage("specific player mode (0: PvP, 1: PvAI, 2: AIvAI) ? ");
        final String line = console.getCheckConsoleLine();
        if (!line.isEmpty()) configurationModel.setPlayerMode(Strings.parseInt(line));
    }

    /**
     * Prompts the user to select AI settings based on the player mode.
     * <p>
     * - If the player mode is PvP, no AI is selected.
     * <p>
     * - If the player mode is PvAI, AI is selected for player 2.
     * <p>
     * - If the player mode is AIvAI, AI is selected for both players.
     * <p>
     * AI options:
     * 0 = Random, 1 = Camarade, 2 = HateCards, 3 = Guide.
     */
    private void setAI(){
        // if the player mode is PvP, the IA is not selected
        if (configurationModel.getPlayerMode() == 0) {return;}

        // if the player mode is PvAI, the IA is selected for player 2
        if (configurationModel.getPlayerMode() == 1) {

            // select IA for player 2
            console.printCheckMessage("specific AI for player 2 (0: Random, 1: Camarade, 2: HateCards, 3: Guide) ? ");
            final String line = console.getCheckConsoleLine();
            
            // if the line is not empty,
            // the IA is selected for player 2 (PLAYER_BLUE)
            // by adding the player data and AI data to the map 
            if (!line.isEmpty()) {
                configurationModel.getPlayerDataAIDataMap().put(PlayerData.getPlayerData(1), AIData.getAIData(Strings.parseInt(line)));
            }
        }

        // if the player mode is AIvAI,
        // the IA is selected for player 1 and player 2
        if (configurationModel.getPlayerMode() == 2) {
            // select IA for player 1
            console.printCheckMessage("specific AI for player 2 (0: Random, 1: Camarade, 2: HateCards, 3: Guide) ? ");
            final String line = console.getCheckConsoleLine();

            // if the line is not empty,
            // the IA is selected for player 1
            // by adding the player data and AI data to the map
            if (!line.isEmpty()) {
                configurationModel.getPlayerDataAIDataMap().put(PlayerData.getPlayerData(0), AIData.getAIData(Strings.parseInt(line)));
            }

            // select IA for player 2
            console.printCheckMessage("specific AI for player 2 (0: Random, 1: Camarade, 2: HateCards, 3: Guide) ? ");
            final String line2 = console.getCheckConsoleLine();

            // if the line is not empty,
            // the IA is selected for player 2
            // by adding the player data and AI data to the map
            if (!line2.isEmpty()) {
                configurationModel.getPlayerDataAIDataMap().put(PlayerData.getPlayerData(1), AIData.getAIData(Strings.parseInt(line2)));
            }
        }
    }

    /**
     * Prompts the user to set the names of the players.
     */
    private void setPlayerName() {
        final String[] playersName = {"player1", "player2"};
        for (int i = 1; i < 3; i++) {
            console.printCheckMessage("specific player name (" + i + ") ? ");
            final String line = console.getCheckConsoleLine();
            if (!line.isEmpty()) playersName[i - 1] = line;
        }
        configurationModel.addPlayers(playersName[0], playersName[1]);
    }

}
