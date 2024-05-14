package model;

import boardifier.model.Model;
import boardifier.view.ConsoleColor;


/*
 * This enum is used to represent the different types of AI data.
 */
public enum AIData {
    RANDOM(0), // Random AI
    CAMARADE(1), // Prioritize placing pieces on the board
    GUIDE(2); // Prioritize cutting the opponent's lines

    private final int id = 0;

    AIData(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
