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

public class KoRDeciderRandom extends Decider {

    private static final Random LOTO = new Random(Calendar.getInstance().getTimeInMillis());

    public KoRDeciderRandom(Model model, Controller control) {
        super(model, control);
    }

    @Override
    public ActionList decide() {
        // do a cast get a variable of the real type to get access to the attributes of KoRStageModel
        KoRStageModel stage = (KoRStageModel) model.getGameStage();

        // GET ALL POSSIBLE ACTIONS
        final List<ActionList> actionListList = stage.getPossiblePlayerActions(PlayerData.getCurrentPlayerData(model));

        // RETURN ONE OF THEM

        Logger.debug("Playing for " + PlayerData.getCurrentPlayerData(model));
        return actionListList.get(LOTO.nextInt(actionListList.size()));
    }
}