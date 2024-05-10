package control;

import boardifier.control.Logger;
import boardifier.model.Model;
import model.KoRStageModel;
import utils.Strings;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SetupController {

    private static final int DEFAULT_PLAYER_MODE = 0;
    private static final int DEFAULT_LOGGER_MODE = 0;

    public final BufferedReader consoleIn;

    private final Model model;

    private final int playerMode;
    private final int loggerMode;
    private final boolean skipCheck;

    public SetupController(Model model, int playerMode, int loggerMode, boolean skipCheck) {
        this.consoleIn = new BufferedReader(new InputStreamReader(System.in));

        this.model = model;

        this.playerMode = ((playerMode < 0) || (playerMode > 2)) ? DEFAULT_PLAYER_MODE : playerMode;
        this.loggerMode = (loggerMode < 0) || (loggerMode > 1) ? DEFAULT_LOGGER_MODE : loggerMode;
        this.skipCheck = skipCheck;

        doCheck();
    }

    public static SetupController init(Model model, String[] args) {
        return new SetupController(model, Strings.parseInt(args, 0), Strings.parseInt(args, 1), Strings.parseBoolean(args, 2));
    }

    private void doCheck() {
        printlnCheckMessage("Press enter if you want to skip !");

        setSeed();
        setPlayerName();
        setLoggerMode();
    }

    private void setSeed() {
        printCheckMessage("specific seed ? ");
        final String line = getCheckConsoleLine();
        if (!line.isEmpty()) KoRStageModel.RANDOM.setSeed(Strings.parseLong(line));
    }

    private void setPlayerName() {
        final String[] playersName = {"player1", "player2"};
        for (int i = 1; i < 3; i++) {
            printCheckMessage("specific player name (" + i + ") ? ");
            final String line = getCheckConsoleLine();
            if (!line.isEmpty()) playersName[i - 1] = line;
        }
        addPlayer(playersName[0], playersName[1]);
    }

    private void addPlayer(String player1, String player2) {
        if (playerMode == 0) {
            model.addHumanPlayer(player1);
            model.addHumanPlayer(player2);
        } else if (playerMode == 1) {
            model.addHumanPlayer(player1);
            model.addComputerPlayer(player2);
        } else if (playerMode == 2) {
            model.addComputerPlayer(player1);
            model.addComputerPlayer(player2);
        }
    }

    private void setLoggerMode() {
        if (loggerMode == 0) {
            Logger.setLevel(Logger.LOGGER_NONE);
            Logger.setVerbosity(Logger.VERBOSE_NONE);
        } else if (loggerMode == 1) {
            Logger.setLevel(Logger.LOGGER_TRACE);
            Logger.setVerbosity(Logger.VERBOSE_HIGH);
        }
    }

    public String getConsoleLine() {
        try {
            return consoleIn.readLine();
        } catch (Exception e) {
            return "";
        }
    }

    private String getCheckConsoleLine() {
        return (skipCheck) ? "" : getConsoleLine();
    }

    private void printlnCheckMessage(String message) {
        if (!skipCheck) System.out.println(message);
    }

    private void printlnCheckMessage() {
        if (!skipCheck) System.out.println();
    }

    private void printCheckMessage(String message) {
        if (!skipCheck) System.out.print(message);
    }

}
