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
import model.element.Pawn;
import model.element.card.MovementCard;
import utils.ContainerElements;

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

            final ContainerElement container;
            if (model.getIdPlayer() == PlayerData.PLAYER_BLUE.getId()) {
                container = gameStage.getBlueMovementCardsSpread();
            } else {
                container = gameStage.getRedMovementCardsSpread();
            }
            // RÉCUPÈRE LA PREMIERE CASE VIDE DU JOUEUR DANS CA GRILLE DE CARTE DE DÉPLACEMENT
            final Coord2D coord2D = ContainerElements.geEmptyPosition(container);

            if(coord2D == null) {
                System.out.println("Vous avez déjà plus de 5 cartes mouvement");
                return false;
            }

            // RÉCUPÈRE LA 1ÈRE CARTE MOUVEMENT DE LA PILE
            final MovementCard movementCard = (MovementCard) gameStage.getMovementCardStack().getElement(0, 0);
            actions = ActionFactory.generatePutInContainer(model, movementCard, container.getName(), (int) coord2D.getX(), (int) coord2D.getY());
        } else if (action == 'D') {
            if(length != 2) return false;

            // SI L'INDEX DE L'ACTION RENVOIE -1 ALORS CE N'EST PAS UN NOMBRE QUI SUIT LA LETTRE 'D'
            // note : on vérifie si l'index fait -2 (et pas -1) car on fait -1 sur la variable pour avoir le vrai index
            //        par exemple l'index de la carte 3 est 2 (donc 3-1)
            final int indexCard = getIntFromString(line.substring(1))-1;
            if(indexCard == -2) return false;

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

            // SI LA CARTE MOUVEMENT SELECTIONNÉ N'EXISTE PAS
            if(movementCardSpread.isEmptyAt(indexCard, 0)) {
                System.out.println("Sélectionner une carte que vous possédez.");
                return false;
            }

            // RÉCUPÈRE LES ÉLÉMENTS ESSENTIELS
            final KoRBoard board = gameStage.getBoard();
            final MovementCard movementCard = (MovementCard) movementCardSpread.getElement(indexCard, 0);
            final Pawn pawn = (Pawn) pawnPot.getElement(0, 0);
            final GameElement king = gameStage.getKingPawn();

            // RÉCUPÈRE LA POSITION DU PION DU ROI QU'ON ADDITIONNE AU VECTEUR DE LA CARTE DÉPLACEMENT
            final Coord2D pos = ContainerElements.getElementPosition(king, board)
                    .add(movementCard.getDirectionVector());

            final int col = (int) pos.getX();
            final int row = (int) pos.getY();

            // SI ON NE PEUT PAS ATTEINDRE LA CASE AVEC LA CARTE
            if(!board.canReachCell(row, col)) {
                System.out.println("Vous ne pouvez pas jouer cette carte!");
                Logger.info("Sortie de tableau");
                return false;
            }


            // BOUGE LE PION DU ROI SUR LE PLATEAU
            actions = ActionFactory.generateMoveWithinContainer(model, king, row, col);

            // METTRE LE PION DU JOUEUR À LA MEME POSITION QUE LE ROI SUR LE PLATEAU
            actions.addAll(ActionFactory.generatePutInContainer(model, pawn, board.getName(), row, col));

            // ENLEVER LA CARTE DÉPLACEMENT DU JOUEUR
            // Note: Joue l'événement removeFromContainer pour lire l'event dans le Model
            //       et joue ensuite l'event removeFromStage pour enlever la carte de l'affichage
            actions.addAll(ActionFactory.generateRemoveFromContainer(model, movementCard));
            actions.addAll(ActionFactory.generateRemoveFromStage(model, movementCard));

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