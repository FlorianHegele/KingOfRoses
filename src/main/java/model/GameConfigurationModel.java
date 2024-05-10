package model;

import boardifier.control.Logger;
import boardifier.model.Model;

import java.util.Random;

public class GameConfigurationModel {

    public static final Random RANDOM = new Random();

    public static final int DEFAULT_PLAYER_MODE = 0;
    public static final int DEFAULT_LOGGER_MODE = 0;

    private final Model model;
    private int playerMode;
    private int loggerMode;

    public GameConfigurationModel(Model model, int playerMode, int loggerMode) {
        this.model = model;
        this.playerMode = playerMode;
        this.loggerMode = loggerMode;
    }

    public GameConfigurationModel(Model model) {
        this(model, DEFAULT_PLAYER_MODE, DEFAULT_LOGGER_MODE);
    }

    public int getPlayerMode() {
        return playerMode;
    }

    public int getLoggerMode() {
        return loggerMode;
    }

    public void setPlayerMode(int playerMode) {
        this.playerMode = playerMode;
    }

    public void setLoggerMode(int loggerMode) {
        this.loggerMode = loggerMode;
    }

    public void addPlayer(String player1, String player2) {
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

    public void updateLogger() {
        if (loggerMode == 0) {
            Logger.setLevel(Logger.LOGGER_NONE);
            Logger.setVerbosity(Logger.VERBOSE_NONE);
        } else if (loggerMode == 1) {
            Logger.setLevel(Logger.LOGGER_TRACE);
            Logger.setVerbosity(Logger.VERBOSE_HIGH);
        }
    }
}
