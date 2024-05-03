package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.Logger;
import boardifier.model.GameElement;
import boardifier.model.ContainerElement;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.model.action.ActionList;
import boardifier.view.View;
import model.KoRStageModel;
import model.element.Pawn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// TODO : Adapt the class to the new game

public class KoRController extends Controller {

    BufferedReader consoleIn;
    boolean firstPlayer;

    public KoRController(Model model, View view) {
        super(model, view);
        firstPlayer = true;
    }

    /**
     * Defines what to do within the single stage of the single party
     * It is pretty straight forward to write :
     */
    public void stageLoop() {
        consoleIn = new BufferedReader(new InputStreamReader(System.in));
        update();
        while (!model.isEndStage()) {
            playTurn();
            endOfTurn();
            update();
        }
        endGame();
    }

    private void playTurn() {
        // get the new player
        Player p = model.getCurrentPlayer();
        if (p.getType() == Player.COMPUTER) {
            System.out.println("COMPUTER PLAYS");
            KoRDecider decider = new KoRDecider(model, this);
            ActionPlayer play = new ActionPlayer(model, this, decider, null);
            play.start();
        } else {
            boolean ok = false;
            while (!ok) {
                System.out.print(p.getName() + " > ");
                try {
                    String line = consoleIn.readLine();
                    if (!line.isEmpty() && line.length() <= 2) {
                        ok = analyseAndPlay(line);
                    }
                    if (!ok) {
                        System.out.println("incorrect instruction. retry !");
                    }
                } catch (IOException e) {
                }
            }
        }
    }

    public void endOfTurn() {
        model.setNextPlayer();
        // get the new player to display its name
        Player p = model.getCurrentPlayer();
        KoRStageModel stageModel = (KoRStageModel) model.getGameStage();
        stageModel.getPlayerName().setText(p.getName());
    }

    private boolean analyseAndPlay(String line) {
        KoRStageModel gameStage = (KoRStageModel) model.getGameStage();

        /*
        // get the pawn value from the first char
        int pawnIndex = (int) (line.charAt(0) - '1');
        if ((pawnIndex < 0) || (pawnIndex > 3)) return false;
        // get the ccords in the board
        int col = (int) (line.charAt(1) - 'A');
        int row = (int) (line.charAt(2) - '1');
        // check coords validity
        if ((row < 0) || (row > 2)) return false;
        if ((col < 0) || (col > 2)) return false;
        // check if the pawn is still in its pot
        ContainerElement pot = null;
        if (model.getIdPlayer() == Pawn.Status.BLUE_PAWN.getID()) {
            pot = gameStage.getBluePot();
        } else {
            pot = gameStage.getRedPot();
        }
        if (pot.isEmptyAt(pawnIndex, 0)) return false;
        GameElement pawn = pot.getElement(pawnIndex, 0);
        // compute valid cells for the chosen pawn
        gameStage.getBoard().setValidCells(null);
        if (!gameStage.getBoard().canReachCell(row, col)) return false;
        */

        final char action = line.charAt(0);
        final int length = line.length();
        if(action == 'P') {
            // TODO : CHECK TOTAL PLAYER CARD IF < 5
            if(length != 1) return false;
            // TODO : TAKE A CARD FROM THE DECK
        } else if (action == 'D') {
            // TODO : CHECK TOTAL PLAYER CARD IF > 0
            if(length != 2) return false;
            final int posCard = getIntFromString(line.substring(1));

            if(posCard == -1) return false;
        } else if (action == 'H') {
            // TODO : CHECK TOTAL PLAYER CARD IF > 0 ??
            // TODO : CHECK TOTAL HERO PLAYER CARD IF > 0
            if(length != 2) return false;
            final int posCard = getIntFromString(line.substring(1));

            if(posCard == -1) return false;
        } else {
            return false;
        }
        Logger.trace("action : " + line);

        ActionList actions = ActionFactory.generatePutInContainer(model, null, "KoRboard", -1, -1);
        actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
        ActionPlayer play = new ActionPlayer(model, this, actions);
        play.start();
        return true;
    }

    public static int getIntFromString(String str) {
        int resultat;
        try {
            resultat = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            resultat = -1;
        }
        return resultat;
    }

}