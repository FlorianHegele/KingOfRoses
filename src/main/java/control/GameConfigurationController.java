package control;

import model.GameConfigurationModel;
import utils.Strings;

public class GameConfigurationController {

    private final GameConfigurationModel configurationModel;
    private final ConsoleController console;

    public GameConfigurationController(GameConfigurationModel gameConfigurationModel, ConsoleController consoleController) {
        this.configurationModel = gameConfigurationModel;
        this.console = consoleController;
        doCheck();
    }

    private void doCheck() {
        console.printlnCheckMessage("Press enter if you want to skip !");

        setSeed();
        setPlayerName();
        configurationModel.updateLogger();
    }

    private void setSeed() {
        console.printCheckMessage("specific seed ? ");
        final String line = console.getCheckConsoleLine();
        if (!line.isEmpty()) GameConfigurationModel.RANDOM.setSeed(Strings.parseLong(line));
    }

    private void setPlayerName() {
        final String[] playersName = {"player1", "player2"};
        for (int i = 1; i < 3; i++) {
            console.printCheckMessage("specific player name (" + i + ") ? ");
            final String line = console.getCheckConsoleLine();
            if (!line.isEmpty()) playersName[i - 1] = line;
        }
        configurationModel.addPlayer(playersName[0], playersName[1]);
    }

}
