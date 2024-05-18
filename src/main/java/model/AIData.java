package model;

import boardifier.model.Model;
import boardifier.view.ConsoleColor;


/*
 * This enum is used to represent the different types of AI data.
 */
public enum AIData {
    RANDOM(0), // Random AI
    CAMARADE(1), // Prioritize placing pieces on the board without harming the opponent
    HATECARDS(2), // Really hates taking cards
    GUIDE(3); // Prioritize cutting the opponent's lines


    private int id = 0;

    AIData(int id) {
        this.id = id;
    }

    // Get the id of the AI data
    public static AIData getAIData(int id) {
        // Loop through the AI data values
        for (AIData aiData : AIData.values()) {
            // If the id of the AI data is equal to the id passed as a parameter
            if (aiData.getId() == id) {
                // Return the AI data
                return aiData;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }
}
