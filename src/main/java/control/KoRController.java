package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.Logger;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.view.View;
import model.KoRStageModel;

public class KoRController extends Controller {

    public KoRController(Model model, View view) {
        super(model, view);
        setControlKey(new KoRControllerKey(model, view, this));
        setControlMouse(new KoRControllerMouse(model, view, this));
        setControlAction(new KoRControllerAction(model, view, this));
    }

    public void endOfTurn() {
        // use the default method to compute next player
        model.setNextPlayer();
        // get the new player
        Player p = model.getCurrentPlayer();
        // change the text of the TextElement
        KoRStageModel stageModel = (KoRStageModel) model.getGameStage();
        stageModel.getPlayerName().setText(p.getName());
        if (p.getType() == Player.COMPUTER) {
            Logger.debug("COMPUTER PLAYS");
            KoRDecider decider = new KoRDecider(model, this);
            ActionPlayer play = new ActionPlayer(model, this, decider, null);
            play.start();
        } else {
            Logger.debug("PLAYER PLAYS");
        }
    }
}
