package control;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.control.Logger;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.KoRStageModel;
import model.PlayerData;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/*
 * Represent an AI player priorizing cutting the other player lines
 */
public class KoRDeciderGuide extends Decider {

    private static final Random LOTO = new Random(Calendar.getInstance().getTimeInMillis());

    public KoRDeciderGuide(Model model, Controller control) {
        super(model, control);
    }

    @Override
    public ActionList decide() {
        final int threshold = 4;
        // do a cast get a variable of the real type to get access to the attributes of KoRStageModel
        KoRStageModel stage = (KoRStageModel) model.getGameStage();

        Logger.debug("Playing for " + PlayerData.getCurrentPlayerData(model));

        // GET ALL ACTION REGARDING PLAYABLE CARDS
        final List<ActionPoints> actionCardList = stage.getPossibleMovementCards(PlayerData.getCurrentPlayerData(model));
        // GET ALL ACTION TO TAKE NEW CARD
        final List<ActionList> actionTakeList = stage.getPossibleTakeCardAction(PlayerData.getCurrentPlayerData(model));
        // GET ALL ACTION REGARDING HERO CARDS
        final List<ActionPoints> actionHeroList = stage.getPossibleHeroMove(PlayerData.getCurrentPlayerData(model), PlayerData.getCurrentPlayerData(model).getNextPlayerData());

        // TODO : IMPLEMENT THE DECISION MAKING PROCESS
        // Is playing  hero card possible options ?
        if(!actionHeroList.isEmpty()) {
            Logger.debug("A card and a hero card is playable for : " + PlayerData.getCurrentPlayerData(model));
            // Should the AI attack ?
            Collections.sort(actionHeroList);
            for(ActionPoints action : actionHeroList) {
                Logger.debug("HeroCard: " + action);
                if (action.getPoint() >= threshold)
                    return action.getActionList();
            }
        }

        // Is playing a card possible ?
        if(!actionCardList.isEmpty()) {
            Logger.debug("A card is playable for : " + PlayerData.getCurrentPlayerData(model));
            // order by most to least points and do the move lending the most points
            Collections.sort(actionCardList);
            for(ActionPoints actionCard : actionCardList) {
                System.out.println();
                Logger.debug("ActionCard : " + actionCard);
            }
            return actionCardList.get(0).getActionList();
        }

        // finally take a card
        Logger.debug("A take card action is playable for : " + PlayerData.getCurrentPlayerData(model));
        return actionTakeList.get(0);

    }
}
