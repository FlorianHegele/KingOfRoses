package control.ai;

import boardifier.control.Controller;
import boardifier.control.Logger;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.data.PlayerData;

import java.util.List;

/**
 * This class defines an AI that makes random decisions for the player.
 */
public class KoRDeciderRandom extends KoRDecider {

    /**
     * Constructor for the random decision-making AI.
     *
     * @param playerData the player data for whom the AI is making decisions.
     * @param model the game model.
     * @param control the game controller.
     */
    public KoRDeciderRandom(Model model, Controller control, PlayerData playerData) {
        super(model, control, playerData);
    }

    /**
     * Decides on an action list by randomly selecting from all possible actions.
     *
     * @return a randomly selected action list.
     */
    @Override
    public ActionList decide() {
        // Get all possible actions for the current player.
        final List<ActionList> actionListList = simpleActionList.getPossiblePlayerActions();

        Logger.debug("Playing for " + playerData);

        // Return one randomly selected action list.
        return actionListList.get(LOTO.nextInt(actionListList.size()));
    }

}