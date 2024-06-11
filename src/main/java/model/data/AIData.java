package model.data;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;


/**
 * This enum represents the different types of AI strategies.
 * Each type of AI has a unique ID and can generate a specific {@link Decider} based on the strategy.
 */
public enum AIData {
    RANDOM(0), // Random AI
    CAMARADE(1), // Prioritizes placing pieces on the board without harming the opponent
    HATE_CARDS(2), // Dislikes taking cards
    GUIDE(3); // Prioritizes cutting the opponent's lines

    private final int id;

    /**
     * Constructs an AIData enum with the specified ID.
     *
     * @param id the unique ID of the AI strategy.
     */
    AIData(int id) {
        this.id = id;
    }

    /**
     * Returns the AIData corresponding to the specified ID.
     *
     * @param id the ID of the AI strategy.
     * @return the AIData with the specified ID, or null if no matching AIData is found.
     */
    public static AIData getAIData(int id) {
        // Loop through the AI data values
        for (AIData aiData : AIData.values()) {
            // If the id of the AI data is equal to the id passed as a parameter
            if (aiData.getId() == id) return aiData;
        }
        return null;
    }

    /**
     * Returns the ID of the AI strategy.
     *
     * @return the ID of the AI strategy.
     */
    public int getId() {
        return id;
    }

    // TODO : IMPLEMENT THIS METHOD
    /**
     * Returns a Decider object based on the AI strategy.
     *
     * @param playerData the player data.
     * @param model      the game model.
     * @param controller the game controller.
     * @return the Decider object for the AI strategy.
     */
    public Decider getDecider(PlayerData playerData, Model model, Controller controller) {
        final Decider decider;

        // Choose which AI should play for the current player
        switch (this) {
            //case RANDOM -> decider = new KoRDeciderRandom(model, controller, playerData);
            //case CAMARADE -> decider = new KoRDeciderCamarade(model, controller, playerData);
            //case HATE_CARDS -> decider = new KoRDeciderHateCards(model, controller, playerData);
            //case GUIDE -> decider = new KoRDeciderGuide(model, controller, playerData);
            // Technically impossible
            default ->
                    throw new IllegalCallerException("Impossible to reach this error, a condition must be added here to implement the correct AI");
        }
        // return decider;
    }
}
