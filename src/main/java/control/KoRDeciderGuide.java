package control;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.control.Logger;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.KoRStageModel;
import model.PlayerData;

import java.util.Calendar;
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
        // do a cast get a variable of the real type to get access to the attributes of KoRStageModel
        KoRStageModel stage = (KoRStageModel) model.getGameStage();

        // GET ALL POSSIBLE ACTIONS
        final List<ActionList> actionListList = stage.getPossiblePlayerActions(PlayerData.getCurrentPlayerData(model));

        // TODO : IMPLEMENT THE DECISION MAKING PROCESS
        // RETURN ONE OF THEM
        Logger.debug("Playing for " + PlayerData.getCurrentPlayerData(model));
        return actionListList.get(LOTO.nextInt(actionListList.size()));
    }
}
