package control.ai;

import boardifier.control.Controller;
import boardifier.control.Logger;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import control.ActionPoints;
import model.data.PlayerData;

import java.util.*;

/**
 * Represents an AI player that prioritizes placing new pieces on the board.
 * This AI will look to increase its score whenever possible.
 */
public class KoRDeciderCamarade extends KoRDecider {

    /**
     * Constructs a KoRDeciderCamarade with the specified model, control, and player data.
     *
     * @param model the game model.
     * @param control the game controller.
     * @param playerData the player data.
     */
    public KoRDeciderCamarade(Model model, Controller control, PlayerData playerData) {
        super(model, control, playerData);
    }

    /**
     * Decides the next action for the AI player.
     * The decision is based on prioritizing actions that place new pieces on the board to maximize the score.
     *
     * @return the list of actions the AI player will take.
     */
    @Override
    public ActionList decide() {
        // Log the current player
        Logger.debug("Playing for " + playerData);

        // Get all possible actions for playing movement cards
        final List<ActionPoints> actionCardList = simpleActionList.getPossibleMovementCards();
        // Get all possible actions for taking new cards
        final List<ActionList> actionTakeList = simpleActionList.getPossibleTakeCardAction();
        // Get all possible actions for playing hero cards
        final List<ActionPoints> actionHeroList = simpleActionList.getPossibleHeroMove();

        // Check if playing a movement card is possible
        if(!actionCardList.isEmpty()) {
            Logger.debug("A card is playable for : " + playerData);
            // Order actions by most to least points and choose the action with the most points
            Collections.sort(actionCardList);
            for(ActionPoints actionCard : actionCardList) {
                Logger.debug("ActionCard : " + actionCard);
            }
            return actionCardList.get(0).getActionList();
        }

        // Check if taking a card is possible
        if(!actionTakeList.isEmpty()) {
            Logger.debug("A take card action is playable for : " + playerData);
            return actionTakeList.get(0);
        }

        // As a last resort, play a hero card trying to get the most points out of it
        Logger.debug("A hero card is playable for : " + playerData);
        return actionHeroList.get(0).getActionList();
    }

}
