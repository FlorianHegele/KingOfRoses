package control;

import model.GameConfigurationModel;
import utils.Strings;

public class GameConfigurationController {

    private final GameConfigurationModel configurationModel;
    private final ConsoleController console;

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
     * Depends if the player mode is PvAI or AIvAI
     * If the player mode is PvP, the IA is not selected
     */
    private void selectIA(){
        if (configurationModel.getPlayerMode() == 1 || configurationModel.getPlayerMode() == 2) {
            console.printCheckMessage("select IA (0: Random, 1: MinMax, 2: AlphaBeta) ? ");
            final String line = console.getCheckConsoleLine();
            if (!line.isEmpty()) configurationModel.setIA(Strings.parseInt(line));
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
