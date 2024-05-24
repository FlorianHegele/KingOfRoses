package control.ai;

import boardifier.control.Controller;
import boardifier.control.Logger;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import control.ActionPoints;
import model.data.PlayerData;

import java.util.Collections;
import java.util.List;

/**
 * Represents an AI player that prioritizes putting new pieces on the board.
 * This AI will look to increase its score whenever possible and prefers to play movement cards over taking new cards.
 */
public class KoRDeciderHateCards extends KoRDecider {

    /**
     * Constructs a KoRDeciderHateCards with the specified model, control, and player data.
     *
     * @param model      the game model.
     * @param control    the game controller.
     * @param playerData the player data.
     */
    public KoRDeciderHateCards(Model model, Controller control, PlayerData playerData) {
        super(model, control, playerData);
    }

    /**
     * Decides the next action for the AI player.
     * The decision is based on prioritizing actions that put new pieces on the board
     * to maximize the score and prefers playing movement cards over taking new cards.
     *
     * @return the list of actions the AI player will take.
     */
    @Override
    public ActionList decide() {
        // Log the current player
        Logger.debug("Playing for " + playerData);

        // Get all possible actions for playing movement cards
        final List<ActionPoints> actionCardList = simpleActionList.getPossibleMovementCards();

        // Check if playing a movement card is possible
        if (!actionCardList.isEmpty()) {
            Logger.debug("A card is playable for : " + playerData);
            // Order actions by most to least points and choose the action with the most points
            Collections.sort(actionCardList);
            for (ActionPoints actionCard : actionCardList) {
                Logger.debug("ActionCard : " + actionCard);
            }
            return actionCardList.get(0).getActionList();
        }

        // Get all possible actions for playing hero cards
        final List<ActionPoints> actionHeroList = simpleActionList.getPossibleHeroMove();

        // If no movement card action is possible, check if playing a hero card is possible
        if (!actionHeroList.isEmpty()) {
            Logger.debug("A hero card is playable for : " + playerData);
            return actionHeroList.get(0).getActionList();
        }

        // Get all possible actions for taking new cards
        final List<ActionList> actionTakeList = simpleActionList.getPossibleTakeCardAction();

        // As a last resort, take a card
        Logger.debug("A take card action is playable for : " + playerData);
        return actionTakeList.get(0);
    }

}



