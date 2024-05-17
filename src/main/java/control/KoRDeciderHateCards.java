package control;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.control.Logger;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.KoRStageModel;
import model.PlayerData;

import java.util.Collections;
import java.util.List;

/*
 * Represent an AI player priorizing putting new pieces on the board
 * Will look to increase it's score whenever possible
 */
public class KoRDeciderHateCards extends Decider {

    public KoRDeciderHateCards(Model model, Controller control) {
        super(model, control);
    }

    @Override
    public ActionList decide() {
        // do a cast get a variable of the real type to get access to the attributes of KoRStageModel
        KoRStageModel stage = (KoRStageModel) model.getGameStage();

        Logger.debug("Playing for " + PlayerData.getCurrentPlayerData(model));

        // GET ALL ACTION REGARDING PLAYABLE CARDS
        final List<ActionPoints> actionCardList = stage.getPossibleMovementCards(PlayerData.getCurrentPlayerData(model));
        // GET ALL ACTION TO TAKE NEW CARD
        final List<ActionList> actionTakeList = stage.getPossibleTakeCardAction(PlayerData.getCurrentPlayerData(model));
        // GET ALL ACTION REGARDING HERO CARDS
        final List<ActionPoints> actionHeroList = stage.getPossibleHeroMove(PlayerData.getCurrentPlayerData(model));

        // TODO : IMPLEMENT THE DECISION MAKING PROCESS
        // Is playing a card possible ?
        if(!actionCardList.isEmpty()) {
            Logger.debug("A card is playable for : " + PlayerData.getCurrentPlayerData(model));
            // order by most to least points and do the move lending the most points
            Collections.sort(actionCardList);
            for(ActionPoints actionCard : actionCardList) {
                System.out.println();
                Logger.debug("ActionCard : " + actionCard);
            }
            return actionCardList.get(0).al;
        }

        // play on the opponent
        if(!actionHeroList.isEmpty()) {
            Logger.debug("A hero card is playable for : " + PlayerData.getCurrentPlayerData(model));
            return actionHeroList.get(0).al;
        }
        // finally take a card
        Logger.debug("A take card action is playable for : " + PlayerData.getCurrentPlayerData(model));
        return actionTakeList.get(0);
    }
}



