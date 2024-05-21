package model;

import boardifier.control.Logger;
import boardifier.model.Model;
import model.data.AIData;
import model.data.PlayerData;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import java.util.Random;

/**
 * Represents the model for configuring the game before starting.
 * It allows setting player mode, logger mode, adding players, and updating the logger settings.
 */
public class GameConfigurationModel {

    public static final Random RANDOM = new Random();

    public static final int DEFAULT_PLAYER_MODE = 0;
    public static final int DEFAULT_LOGGER_MODE = 0;

    private final Model model;
    private int playerMode;
    private int loggerMode;

    /**
     * Map to store the player data and AI data.
     * PlayerData is the key and AIData is the value.
     */
    private final Map <PlayerData, AIData> playerDataAIDataMap;

    /**
     * Constructs a GameConfigurationModel with the specified model, player mode, and logger mode.
     *
     * @param model the game model.
     * @param playerMode the player mode (0: Human vs. Human, 1: Human vs. AI, 2: AI vs. AI).
     * @param loggerMode the logger mode (0: None, 1: Debug).
     */
    public GameConfigurationModel(Model model, int playerMode, int loggerMode) {
        this.model = model;
        this.playerDataAIDataMap = new EnumMap<>(PlayerData.class);

        setPlayerMode(playerMode);
        setLoggerMode(loggerMode);
    }

    /**
     * Constructs a GameConfigurationModel with the specified model, using default player and logger modes.
     *
     * @param model the game model.
     */
    public GameConfigurationModel(Model model) {
        this(model, DEFAULT_PLAYER_MODE, DEFAULT_LOGGER_MODE);
    }

    /**
     * Gets the current player mode.
     *
     * @return the player mode.
     */
    public int getPlayerMode() {
        return playerMode;
    }

    /**
     * Gets the current logger mode.
     *
     * @return the logger mode.
     */
    public int getLoggerMode() {
        return loggerMode;
    }

    /**
     * Gets the map of player data and AI data.
     *
     * @return the map of player data and AI data.
     */
    public Map<PlayerData, AIData> getPlayerDataAIDataMap() {
        return playerDataAIDataMap;
    }

    /**
     * Sets the player mode.
     *
     * @param playerMode the player mode to set.
     */
    public void setPlayerMode(int playerMode) {
        this.playerMode = (playerMode < 0) || (playerMode > 2) ? DEFAULT_PLAYER_MODE : playerMode;
    }

    /**
     * Sets the logger mode.
     *
     * @param loggerMode the logger mode to set.
     */
    public void setLoggerMode(int loggerMode) {
        this.loggerMode = (loggerMode < 0) || (loggerMode > 1) ? DEFAULT_LOGGER_MODE : loggerMode;
    }

    /**
     * Adds players to the game based on the player mode.
     *
     * @param player1 the name of player 1.
     * @param player2 the name of player 2.
     */
    public void addPlayers(String player1, String player2) {
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

    /**
     * Updates the logger settings based on the logger mode.
     */
    public void updateLogger() {
        if (loggerMode == 0) {
            Logger.setLevel(Logger.LOGGER_NONE);
            Logger.setVerbosity(Logger.VERBOSE_NONE);
        } else if (loggerMode == 1) {
            Logger.setLevel(Logger.LOGGER_DEBUG);
            Logger.setVerbosity(Logger.VERBOSE_HIGH);
        }
    }
}