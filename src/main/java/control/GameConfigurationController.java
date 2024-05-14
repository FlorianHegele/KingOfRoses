package control;

import model.GameConfigurationModel;
import utils.Strings;

public class GameConfigurationController {

    private final GameConfigurationModel configurationModel;
    private final ConsoleController console;

    // Map of player data and AI data
    // PlayerData is the key and AIData is the value
    private final Map <PlayerData, AIData> playerDataAIDataMap = new HashMap<>();

    public GameConfigurationController(GameConfigurationModel gameConfigurationModel, ConsoleController consoleController) {
        this.configurationModel = gameConfigurationModel;
        this.console = consoleController;
    }

    public void doCheck() {
        console.printlnCheckMessage("Press enter if you want to skip !");

        configurationModel.updateLogger();
        setSeed();
        setPlayerMode();
        setPlayerName();
    }

    private void setSeed() {
        console.printCheckMessage("specific seed ? ");
        final String line = console.getCheckConsoleLine();
        if (!line.isEmpty()) GameConfigurationModel.RANDOM.setSeed(Strings.parseLong(line));
    }

    private void setPlayerMode() {
        console.printCheckMessage("specific player mode (0: PvP, 1: PvAI, 2: AIvAI) ? ");
        final String line = console.getCheckConsoleLine();
        if (!line.isEmpty()) configurationModel.setPlayerMode(Strings.parseInt(line));
    }

    /*
     * Select IA for player 1 and player 2
     * If the player mode is PvP, the IA is not selected
     * If the player mode is PvAI, the IA is selected for player 2
     * If the player mode is AIvAI, the IA is selected for player 1 and player 2
     * The IA is selected from the AIData enum
     * 0 = Random AI, 1 = Camarade AI, 2 = Guide AI
     */
    private void setAI(){
        if (configurationModel.getPlayerMode() == 1 
                || configurationModel.getPlayerMode() == 2) {
            // Select IA for player 1
            console.printCheckMessage("O: random, 1: camarade, 2: guide ? ");
            final String line = console.getCheckConsoleLine();
            if (!line.isEmpty())
                configurationModel.setAI(Strings.parseInt(line));
                }
    }

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
