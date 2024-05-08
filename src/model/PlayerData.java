package model;

import boardifier.model.Model;
import boardifier.view.ConsoleColor;

public enum PlayerData {

    PLAYER_RED(0, ConsoleColor.RED_BACKGROUND),
    PLAYER_BLUE(1, ConsoleColor.BLUE_BACKGROUND);

    private final int id;
    private final String backgroundColor;

    PlayerData(int id, String backgroundColor) {
        this.id = id;
        this.backgroundColor = backgroundColor;
    }

    public int getId() {
        return id;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getName() {
        return name().toLowerCase();
    }

    public PlayerData getNextPlayerData() {
        return (this == PLAYER_RED) ? PLAYER_BLUE : PLAYER_RED;
    }

    public static PlayerData getPlayerData(int id) {
        for (PlayerData playerData : PlayerData.values()) {
            if (playerData.getId() == id) {
                return playerData;
            }
        }
        return null;
    }

    public static PlayerData getCurrentPlayerData(Model model) {
        return getPlayerData(model.getIdPlayer());
    }

}