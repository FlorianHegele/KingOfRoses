package control;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.KoRStageModel;
import model.PlayerData;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

/*
 * Represent an AI player priorizing putting new pieces on the board
 */
public class KoRDeciderCamarade extends Decider {

    private static final Random LOTO = new Random(Calendar.getInstance().getTimeInMillis());

    public KoRDeciderCamarade(Model model, Controller control) {
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
        return actionListList.get(LOTO.nextInt(actionListList.size()));
    }
}
