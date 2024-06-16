package model.data;

import boardifier.model.Model;
import javafx.scene.paint.Color;

/**
 * Enumeration representing player data for the game.
 * Provides methods to access player information.
 */
public enum PlayerData {

    NONE(-1, null),
    PLAYER_BLUE(0, Color.BLUE),
    PLAYER_RED(1, Color.RED);

    private final int id;
    private final Color color;

    /**
     * Constructs a PlayerData enum with the specified ID and background color.
     *
     * @param id              the unique ID of the player.
     * @param color the color associated with the player.
     */
    PlayerData(int id, Color color) {
        this.id = id;
        this.color = color;
    }

    /**
     * Returns the ID of the player.
     *
     * @return the player ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the background color associated with the player.
     *
     * @return the background color.
     */
    public Color getBackgroundColor() {
        return color;
    }

    /**
     * Returns the name of the player in lowercase.
     *
     * @return the player's name in lowercase.
     */
    public String getName() {
        return name().toLowerCase();
    }

    /**
     * Returns the PlayerData of the next player in turn.
     *
     * @return the PlayerData of the next player.
     */
    public PlayerData getNextPlayerData() {
        return (this == PLAYER_RED) ? PLAYER_BLUE : PLAYER_RED;
    }

    /**
     * Returns the PlayerData corresponding to the specified ID.
     *
     * @param id the player ID.
     * @return the PlayerData with the specified ID, or null if no matching PlayerData is found.
     */
    public static PlayerData getPlayerData(int id) {
        if (id == PLAYER_RED.id) return PLAYER_RED;
        if (id == PLAYER_BLUE.id) return PLAYER_BLUE;
        return (id == NONE.id) ? NONE : null;
    }

    /**
     * Returns the PlayerData of the current player based on the model.
     *
     * @param model the game model.
     * @return the PlayerData of the current player.
     */
    public static PlayerData getCurrentPlayerData(Model model) {
        return getPlayerData(model.getIdPlayer());
    }

}