package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.control.Logger;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.view.View;
import model.GameConfigurationModel;
import model.KoRStageModel;
import model.data.AIData;
import model.data.PlayerData;

public class KoRController extends Controller {

    private final GameConfigurationModel configurationModel;

    public KoRController(Model model, View view, GameConfigurationModel configurationModel) {
        super(model, view);
        this.configurationModel = configurationModel;

        setControlKey(new KoRControllerKey(model, view, this));
        setControlMouse(new KoRControllerMouse(model, view, this));
        setControlAction(new KoRControllerAction(model, view, this, configurationModel));
    }

    @Override
    public void startGame() throws GameException {
        super.startGame();

        view.getStage().setWidth(1280);
        view.getStage().setHeight(1000);

        playerPlay();
    }

    @Override
    public void endOfTurn() {
        KoRStageModel stageModel = (KoRStageModel) model.getGameStage();

        PlayerData playerData = PlayerData.getCurrentPlayerData(model);
        if(stageModel.playerCanPlay(playerData.getNextPlayerData())) {
            // use the default method to compute next player
            model.setNextPlayer();
            // get the new player
            Player p = model.getCurrentPlayer();
            // change the text of the TextElement
            stageModel.getPlayerName().setText(p.getName());
        }

        playerPlay();
    }

    public void playerPlay() {
        Player p = model.getCurrentPlayer();

        if(model.isEndGame()) return;

        final PlayerData playerData = PlayerData.getCurrentPlayerData(model);
        final AIData ai = configurationModel.getPlayerDataAIDataMap().get(playerData);

        if (p.getType() == Player.COMPUTER) {
            Logger.debug("COMPUTER PLAYS USING :" + ai);
            final Decider decider = ai.getDecider(playerData, model, this);

            ActionPlayer play = new ActionPlayer(model, this, decider, null);
            play.start();
            Logger.trace("play");
        } else {
            Logger.debug("PLAYER PLAYS");
        }
    }
}
