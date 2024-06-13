package model;

import boardifier.control.Logger;
import boardifier.model.Model;
import model.data.AIData;
import model.data.PlayerData;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

/**
 * Represents the model for configuring the game before starting.
 * It allows setting player mode, logger mode, adding players, and updating the logger settings.
 */
public class GameConfigurationModel {

    public static final Random RANDOM = new Random();

    public static final int DEFAULT_PLAYER_MODE = 0;
    public static final long RANDOM_SEED = 0;
    public static final int DEFAULT_LOGGER_MODE = 0;
    public static final boolean DEFAULT_PLAYER_INTERACTION = true;
    public static final boolean DEFAULT_RENDER_GAME = true;

    private final Model model;

    private boolean renderGame;
    private boolean playerInteraction;
    private int playerMode;
    private int loggerMode;

    /**
     * Map to store the player data and AI data.
     * PlayerData is the key and AIData is the value.
     */
    private final Map<PlayerData, AIData> playerDataAIDataMap;

    /**
     * Constructs a GameConfigurationModel with the specified model, player mode, and logger mode.
     *
     * @param model      the game model.
     * @param playerMode the player mode (0: Human vs. Human, 1: Human vs. AI, 2: AI vs. AI).
     * @param loggerMode the logger mode (0: None, 1: Debug).
     */
    public GameConfigurationModel(Model model, long seed, int playerMode, int loggerMode, boolean playerInteraction, boolean renderGame) {
        this.model = model;
        this.playerDataAIDataMap = new EnumMap<>(PlayerData.class);

        setLoggerMode(loggerMode);
        updateLogger();

        setSeed(seed);
        setPlayerMode(playerMode);
        setPlayerInteraction(playerInteraction);
        setRenderGame(renderGame);
    }

    public GameConfigurationModel(Model model, int playerMode, int loggerMode, boolean playerInteraction, boolean renderGame) {
        this(model, RANDOM_SEED, playerMode, loggerMode, playerInteraction, renderGame);
    }

    /**
     * Constructs a GameConfigurationModel with the specified model, using default player and logger modes.
     *
     * @param model the game model.
     */
    public GameConfigurationModel(Model model) {
        this(model, RANDOM_SEED, DEFAULT_PLAYER_MODE, DEFAULT_LOGGER_MODE, DEFAULT_PLAYER_INTERACTION, DEFAULT_RENDER_GAME);
    }

    public void setSeed(long seed) {
        if(seed != 0) RANDOM.setSeed(seed);
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
     * Allows you to know whether you are rendering the game or not.
     *
     * @return a boolean that says whether the game should be rendered
     */
    public boolean isRenderGame() {
        return renderGame;
    }

    public void addAI(Map<PlayerData, AIData> AIData) {
        if (AIData.size() > 2 || AIData.isEmpty())
            throw new IllegalArgumentException("AIData must contain exactly "+playerMode+" player");

        if (playerMode != AIData.size())
            throw new IllegalCallerException("You cannot add "+AIData.size()+" AIs if the player mode is not set to " + playerMode);

        playerDataAIDataMap.putAll(AIData);
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
        this.loggerMode = (loggerMode < 0) || (loggerMode > 3) ? DEFAULT_LOGGER_MODE : loggerMode;
    }

    /**
     * Ensuring that players can interact directly with the game
     *
     * @param playerInteraction the player interaction mode to set.
     */
    public void setPlayerInteraction(boolean playerInteraction) {
        this.playerInteraction = playerInteraction;
    }

    /**
     * Whether to render the game.
     * It's useful to disable rendering for AI testing.
     *
     * @param renderGame defines whether the game should be rendered.
     */
    public void setRenderGame(boolean renderGame) {
        this.renderGame = renderGame;
    }

    /**
     * Gets the current player interaction mode.
     *
     * @return the player interaction mode.
     */
    public boolean isPlayerInteraction() {
        return playerInteraction;
    }

    /**
     * Adds players to the game based on the player mode.
     *
     * @param player1 the name of player 1.
     * @param player2 the name of player 2.
     */
    public void addPlayers(String player1, String player2) {
        model.getPlayers().clear();
        if (playerMode == 0) {
            model.addHumanPlayer(player1);
            model.addHumanPlayer(player2);
        } else if (playerMode == 1) {
            model.addHumanPlayer(player1);
            model.addComputerPlayer(player2);
        } else if (playerMode == 2) {
            model.addComputerPlayer(player1);
            model.addComputerPlayer(player2);
            Logger.info("add computer");
        }
    }

    /**
     * Updates the logger settings based on the logger mode.
     */
    public void updateLogger() {
        if (loggerMode == 0) {
            Logger.setLevel(Logger.LOGGER_NONE);
            Logger.setVerbosity(Logger.VERBOSE_NONE);
        } else {
            Logger.setLevel(loggerMode);
            Logger.setVerbosity(Logger.VERBOSE_HIGH);
        }
    }


}
