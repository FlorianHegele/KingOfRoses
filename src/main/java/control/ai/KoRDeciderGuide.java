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
 * Represents an AI player that prioritizes cutting the other player's lines.
 * This AI will try to disrupt the opponent's strategy by making strategic moves.
 */
public class KoRDeciderGuide extends KoRDecider {

    private static final int THRESHOLD = 4;

    /**
     * Constructs a KoRDeciderGuide with the specified model, control, and player data.
     *
     * @param model the game model.
     * @param control the game controller.
     * @param playerData the player data.
     */
    public KoRDeciderGuide(Model model, Controller control, PlayerData playerData) {
        super(model, control, playerData);
    }

    /**
     * Decides the next action for the AI player.
     * The decision is based on prioritizing actions that cut the opponent's lines,
     * focusing on disrupting the opponent's strategy.
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

        // Check if playing a hero card is possible
        if(!actionHeroList.isEmpty()) {
            Logger.debug("A card and a hero card is playable for : " + playerData);
            // Should the AI attack?
            Collections.sort(actionHeroList);
            for(ActionPoints action : actionHeroList) {
                Logger.debug("HeroCard: " + action);
                if (action.getPoint() >= THRESHOLD)
                    return action.getActionList();
            }
        }

        // Check if playing a movement card is possible
        if(!actionCardList.isEmpty()) {
            Logger.debug("A card is playable for : " + playerData);
            // Order actions by most to least points and choose the action with the most points
            Collections.sort(actionCardList);
            for(ActionPoints actionCard : actionCardList) {
                System.out.println();
                Logger.debug("ActionCard : " + actionCard);
            }
            return actionCardList.get(0).getActionList();
        }

        // As a last resort, take a card
        Logger.debug("A take card action is playable for : " + playerData);
        return actionTakeList.get(0);
    }

}
