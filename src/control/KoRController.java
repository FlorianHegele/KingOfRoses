package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.Logger;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.view.View;
import model.KoRStageModel;
import model.PlayerData;
import model.container.KoRBoard;
import model.container.PawnPot;
import model.container.card.MovementCardSpread;
import model.element.card.MovementCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
                } catch (IOException ignored) {
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

        final char action = line.charAt(0);
        final int length = line.length();
        final ActionList actions;
        if(action == 'P') {
            if(length != 1) return false;
            final int cardToPlay;
            final ContainerElement container;
            if (model.getIdPlayer() == PlayerData.PLAYER_BLUE.getId()) {
                cardToPlay = gameStage.getBlueMovementCardToPlay();
                container = gameStage.getBlueMovementCardsSpread();
            } else {
                cardToPlay = gameStage.getRedMovementCardToPlay();
                container = gameStage.getRedMovementCardsSpread();
            }
            if(cardToPlay >= 0) {
                System.out.println("Vous avez déjà plus de 5 cartes mouvement");
                return false;
            }
            final Coord2D coord2D = gameStage.geEmptyPosition(container);
            actions = ActionFactory.generatePutInContainer(model, gameStage.getMovementCardStack(), container.getName(), (int) coord2D.getX(), (int) coord2D.getY());

        } else if (action == 'D') {
            if(length != 2) return false;
            final int indexCard = getIntFromString(line.substring(1));

            if(indexCard == -1) return false;

            final PawnPot pawnPot;
            final MovementCardSpread movementCardSpread;
            if (model.getIdPlayer() == PlayerData.PLAYER_BLUE.getId()) {
                pawnPot = gameStage.getBluePot();
                movementCardSpread = gameStage.getBlueMovementCardsSpread();
            } else {
                pawnPot = gameStage.getRedPot();
                movementCardSpread = gameStage.getRedMovementCardsSpread();
            }

            // SI N'A PLUS DE PION (IMPOSSIBLE TECHNIQUEMENT)
            if(pawnPot.isEmptyAt(0, 0)) {
                Logger.trace("Bug ICI, normalement le joueur est obligé de passer son tour.");
                System.out.println("Vous n'avez plus de pion à jouer!");
            }

            // SI N'A PLUS DE CARTE MOUVEMENT
            if(movementCardSpread.isEmpty()) {
                System.out.println("Vous n'avez pas de carte mouvement à jouer.\nPiocher une carte!");
                return false;
            }

            if(movementCardSpread.isEmptyAt(indexCard-1, 0)) {
                System.out.println("Sélectionner une carte que vous possédez.");
                return false;
            }

            final KoRBoard board = gameStage.getBoard();
            final MovementCard movementCard = (MovementCard) movementCardSpread.getElement(indexCard-1, 0);
            final GameElement pawn = pawnPot.getElement(0, 0);

            final GameElement king = gameStage.getKingPawn();
            final Coord2D kingPos = gameStage.getElementPosition(king, board);
            final Coord2D vector = movementCard.getDirectionVector();
            final Coord2D newKingPos = kingPos.add(vector);

            System.out.println("king pos " + kingPos);
            System.out.println("vector " + vector);
            System.out.println("new king pos " + newKingPos);

            final int newX = (int) newKingPos.getX();
            final int newY = (int) newKingPos.getY();

            if(!board.canReachCell(newX, newY)) {
                System.out.println("Vous ne pouvez pas jouer cette carte!");
                Logger.info("Sortie de tableau");
                return false;
            }

            Logger.trace("Initial coord -> x " + gameStage.getElementPosition(king, board).getX() + " , y " + gameStage.getElementPosition(king, board).getY());
            Logger.trace("x : " + newX + ", y : " + newY + " | " + (model.getIdPlayer() == PlayerData.PLAYER_BLUE.getId() ? "blue" : "red"));
            Logger.trace(movementCard.toString());

            // Remove Player Movement Card from his deck
            actions = ActionFactory.generateRemoveFromStage(model, movementCard);
            // Move Player Pawn
            //actions.addAll(ActionFactory.generatePutInContainer(model, pawn, board.getName(), newX, newY));
            // Move King Pawn
            actions.addAll(ActionFactory.generateMoveWithinContainer(model, king, newX, newY));

        } else if (action == 'H') {
            // TODO : CHECK TOTAL PLAYER CARD IF > 0 ??
            // TODO : CHECK TOTAL HERO PLAYER CARD IF > 0
            if(length != 2) return false;
            final int posCard = getIntFromString(line.substring(1));

            if(posCard == -1) return false;

            actions = null;
        } else {
            return false;
        }
        Logger.trace("action : " + line);

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