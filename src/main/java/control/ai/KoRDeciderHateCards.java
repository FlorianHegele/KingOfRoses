package control.ai;

import boardifier.control.Controller;
import boardifier.control.Logger;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import control.ActionPoints;
import model.data.PlayerData;

import java.util.Collections;
import java.util.List;

/*
 * Represent an AI player priorizing putting new pieces on the board
 * Will look to increase it's score whenever possible
 */
public class KoRDeciderHateCards extends KoRDecider {

    public KoRDeciderHateCards(Model model, Controller control, PlayerData playerData) {
        super(model, control, playerData);
    }

    @Override
    public ActionList decide() {
        Logger.debug("Playing for " + playerData);

        // GET ALL ACTION REGARDING PLAYABLE CARDS
        final List<ActionPoints> actionCardList = simpleActionList.getPossibleMovementCards();
        // GET ALL ACTION TO TAKE NEW CARD
        final List<ActionList> actionTakeList = simpleActionList.getPossibleTakeCardAction();
        // GET ALL ACTION REGARDING HERO CARDS
        final List<ActionPoints> actionHeroList = simpleActionList.getPossibleHeroMove();

        // Is playing a card possible ?
        if(!actionCardList.isEmpty()) {
            Logger.debug("A card is playable for : " + playerData);
            // order by most to least points and do the move lending the most points
            Collections.sort(actionCardList);
            for(ActionPoints actionCard : actionCardList) {
                System.out.println();
                Logger.debug("ActionCard : " + actionCard);
            }
            return actionCardList.get(0).getActionList();
        }

        // play on the opponent
        if(!actionHeroList.isEmpty()) {
            Logger.debug("A hero card is playable for : " + playerData);
            return actionHeroList.get(0).getActionList();
        }
        // finally take a card
        Logger.debug("A take card action is playable for : " + playerData);
        return actionTakeList.get(0);
    }
}



