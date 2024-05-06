package model;

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

}