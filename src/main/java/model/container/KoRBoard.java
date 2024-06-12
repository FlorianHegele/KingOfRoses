package model.container;

import boardifier.control.Logger;
import boardifier.model.Coord2D;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.ContainerElement;
import model.KoRStageModel;
import model.data.GameState;
import model.data.PlayerData;
import model.element.Pawn;
import model.element.card.HeroCard;
import model.element.card.MovementCard;
import utils.ContainerElements;

import java.awt.*;
import java.util.List;

/**
 * The KoR main board represents the element where pawns are placed when played.
 * This class provides a 9x9 grid to accommodate pawns.
 */
public class KoRBoard extends ContainerElement {

    /**
     * Constructs a new KoRBoard with the specified coordinates and associated game stage model.
     *
     * @param x              The x-coordinate of the board.
     * @param y              The y-coordinate of the board.
     * @param gameStageModel The game stage model associated with this board.
     */
    public KoRBoard(int x, int y, GameStageModel gameStageModel) {
        // Call the super-constructor to create a 9x9 grid, named "KoR", at the specified coordinates
        super("KoRboard", x, y, 9, 9, gameStageModel);
        resetReachableCells(false);
    }

    public void setValidCells() {
        resetReachableCells(false);
        Point valid = computeValidCell();
        if (valid != null) {
            reachableCells[valid.y][valid.x] = true;
        }
        addChangeFaceEvent();
    }

    private Point computeValidCell() {
        final KoRStageModel gameStage = (KoRStageModel) this.gameStageModel;
        final PlayerData playerData = PlayerData.getCurrentPlayerData(getModel());
        final GameState gameState = gameStage.getGameState();

        Logger.debug("computeValidCell " + gameState.name());

        final Coord2D kingPos = ContainerElements.getElementPosition(gameStage.getKingPawn(), this);

        MovementCard movementCard = null;
        HeroCard heroCard = null;
        for(GameElement gameElement : gameStage.getSelected()) {
            if(gameElement instanceof HeroCard h) {
                heroCard = h;
            } else if (gameElement instanceof MovementCard m) {
                movementCard = m;
            }
        }

        if (gameState == GameState.SELECT_MOVEMENT_CARD) {
            if(movementCard == null) throw new IllegalArgumentException("Movement card cannot be null");

            final Coord2D newPos = kingPos.add(movementCard.getDirectionVector());
            final int col = (int) newPos.getX();
            final int row = (int) newPos.getY();
            // IF THE CELL CAN BE REACH AND THERE ARE NO PAWNS
            if(ContainerElements.isOutside(this, row, col) && isEmptyAt(row, col))
                return new Point(col, row);
        }

        if (gameState == GameState.SELECT_MOVEMENT_CARD_HERO) {
            if(movementCard == null) throw new IllegalArgumentException("Movement card cannot be null");
            if(heroCard == null) throw new IllegalArgumentException("Hero card cannot be null");

            final Coord2D newPos = kingPos.add(movementCard.getDirectionVector());
            final int col = (int) newPos.getX();
            final int row = (int) newPos.getY();
            // IF THE CELL CAN BE REACH AND THERE IS A PAWN OF THE OPPOSANT
            if(ContainerElements.isOutside(this, row, col) && !isEmptyAt(row, col)
                    && ((Pawn) getElement(row, col)).getStatus().isOwnedBy(playerData.getNextPlayerData()))
                return new Point(col, row);
        }

        return null;
    }

}