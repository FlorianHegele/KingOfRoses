package control;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.control.Logger;
import boardifier.model.Coord2D;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.KoRStageModel;
import model.PlayerData;
import model.container.PawnPot;
import model.container.card.HeroCardStack;
import model.container.card.MovementCardSpread;
import model.element.Pawn;
import model.element.card.MovementCard;
import utils.ContainerElements;

import java.util.*;

/*
 * Represent an AI player priorizing putting new pieces on the board
 * Will look to increase it's score whenever possible
 */
public class KoRDeciderCamarade extends Decider {

    public KoRDeciderCamarade(Model model, Controller control) {
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

        // TODO : IMPLEMENT THE DECISION MAKING PROCESS
        // Is playing a card possible ?
        if(!actionCardList.isEmpty()) {
            Logger.debug("A card is playable for : " + PlayerData.getCurrentPlayerData(model));
            // TODO : Which move will add to the longest line ? Need to sort the list
            Collections.sort(actionCardList);
            for(ActionPoints actionCard : actionCardList) {
                System.out.println();
                Logger.debug("ActionCard : " + actionCard);
            }
            return actionCardList.get(0).al;
        }
        // Is taking a card possible ?
        Logger.debug("A take card is playable for : " + PlayerData.getCurrentPlayerData(model));
        return actionTakeList.get(0);

    }


}
