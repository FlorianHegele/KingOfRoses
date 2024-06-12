package control;

import boardifier.control.*;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.view.GridLook;
import boardifier.view.View;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.data.ElementType;
import model.data.GameState;
import model.container.KoRBoard;
import model.KoRStageModel;
import model.data.PlayerData;
import model.element.Pawn;
import model.element.card.HeroCard;
import model.element.card.MovementCard;
import utils.ContainerElements;

import java.util.List;

/**
 * A basic mouse controller that just grabs the mouse clicks and prints out some informations.
 * It gets the elements of the scene that are at the clicked position and prints them.
 */
public class KoRControllerMouse extends ControllerMouse implements EventHandler<MouseEvent> {

    public KoRControllerMouse(Model model, View view, Controller control) {
        super(model, view, control);
    }

    @Override
    public void handle(MouseEvent event) {
        // if mouse event capture is disabled in the model, just return
        if (!model.isCaptureMouseEvent()) return;

        // get the clic x,y in the whole scene (this includes the menu bar if it exists)
        Coord2D clic = new Coord2D(event.getSceneX(), event.getSceneY());
        // get elements at that position
        List<GameElement> list = control.elementsAt(clic);
        // for debug, uncomment next instructions to display x,y and elements at that postion

        Logger.debug("click in "+event.getSceneX()+","+event.getSceneY());
        for(GameElement element : list) {
            Logger.debug(element.toString());
        }

        PlayerData currentPlayer = PlayerData.getCurrentPlayerData(model);
        KoRStageModel stageModel = (KoRStageModel) model.getGameStage();
        GameState currentState = stageModel.getGameState();

        Logger.info("current state : " + currentState.name());

        ActionList actionList = null;
        final SimpleActionList simpleActionList = new SimpleActionList(model, control, currentPlayer);

        // CAN SELECT A MOVEMENT CARD FROM THE STACK OR HIS HAND
        if (currentState == GameState.SELECT_NONE) {
            for (GameElement element : list) {
                if (ElementType.MOVEMENT_CARD.isElement(element)) {
                    MovementCard movementCard = (MovementCard) element;

                    // SELECT A MOVEMENT CARD FROM THE STACK
                    if(movementCard.getOwner() == MovementCard.Owner.STACK) {
                        actionList = pickupFromStack(stageModel, simpleActionList, currentPlayer);
                    }

                    // SELECT A MOVEMENT CARD FROM HIS HAND
                    else if(movementCard.getOwner().isSpecificPlayer(currentPlayer)) {
                        stageModel.setState(GameState.SELECT_MOVEMENT_CARD);
                        element.toggleSelected();
                        return;
                    }
                }
            }

        // CAN SELECT THE DEST OR A HERO CARD OR UNSELECT HIS CARD OR CHANGE HIS MOVEMENT CARD
        } else if (stageModel.getGameState() == GameState.SELECT_MOVEMENT_CARD) {
            // first check if the click is on the current selected pawn. In this case, unselect it
            for (GameElement element : list) {
                if (element.isSelected()) {
                    stageModel.setState(GameState.SELECT_NONE);
                    element.toggleSelected();
                    return;
                }

                // SELECT ANOTHER MOVEMENT CARD
                if (ElementType.MOVEMENT_CARD.isElement(element)) {
                    for(GameElement selected : stageModel.getElements()) {
                        if(selected.isSelected()) {
                            selected.toggleSelected();
                            break;
                        }
                    }
                    element.toggleSelected();
                    return;

                // SELECT A HERO CARD
                } else if (ElementType.HERO_CARD.isElement(element)) {
                    HeroCard heroCard = (HeroCard) element;
                    // SELECT A HERO CARD FROM HIS HAND
                    if(heroCard.getStatus().isOwnedBy(currentPlayer)) {
                        stageModel.setState(GameState.SELECT_MOVEMENT_CARD_HERO);
                        element.toggleSelected();
                        return;
                    }
                }
            }

            // CHECK IF CLICK ON THE BOARD
            if(cliqueOnBoard(list, stageModel)) actionList = playMovementCard(clic, stageModel, simpleActionList);

        // CAN SELECT THE DEST OR UNSELECT THE HERO CARD OR UNSELECT MOVEMENT CARD (AND HERO CARD INDIRECTLY) OR CHANGE HIS MOVEMENT CARD
        } else if (stageModel.getGameState() == GameState.SELECT_MOVEMENT_CARD_HERO) {
            for (GameElement element : list) {
                // UNSELECT
                if (element.isSelected()) {
                    // UNSELECT HERO CARD
                    if (ElementType.HERO_CARD.isElement(element)) {
                        stageModel.setState(GameState.SELECT_MOVEMENT_CARD);
                        element.toggleSelected();

                    // UNSELECT MOVEMENT CARD AND HERO CARD
                    } else if (ElementType.MOVEMENT_CARD.isElement(element)) {
                        stageModel.setState(GameState.SELECT_NONE);
                        stageModel.getSelected().forEach(GameElement::toggleSelected);
                    }
                    return;
                }

                // SELECT AN OTHER MOVEMENT CARD
                if (ElementType.MOVEMENT_CARD.isElement(element)) {
                    element.toggleSelected();

                    for(GameElement selected : stageModel.getSelected()) {
                        if(ElementType.MOVEMENT_CARD.isElement(selected)) {
                            selected.toggleSelected();
                            break;
                        }
                    }
                    return;
                }
            }

            // CHECK IF CLICK ON THE BOARD
            if(cliqueOnBoard(list, stageModel)) actionList = playHeroCard(clic, stageModel, simpleActionList);
        }

        // EXECUTE ACTIONS
        if(actionList == null) return;

        stageModel.setState(GameState.SELECT_NONE);
        stageModel.unselectAll();

        actionList.setDoEndOfTurn(true);
        final ActionPlayer actionPlayer = new ActionPlayer(model, control, actionList);
        actionPlayer.start();
    }

    private ActionList pickupFromStack(KoRStageModel gameStage, SimpleActionList simpleActionList, PlayerData playerData) {
        final ContainerElement container = (playerData == PlayerData.PLAYER_BLUE)
                ? gameStage.getBlueMovementCardsSpread()
                : gameStage.getRedMovementCardsSpread();

        // Get the first empty position in the player's movement card grid
        final Coord2D coord2D = ContainerElements.getEmptyPosition(container);

        // If the hand of the player is full, do nothing
        if (coord2D == null) return null;

        return simpleActionList.pickUpMovementCard(container, coord2D);
    }

    private ActionList playMovementCard(Coord2D clic, KoRStageModel gameStage, SimpleActionList simpleActionList) {
        // Get the necessary elements
        final KoRBoard board = gameStage.getBoard();
        final MovementCard movementCard = (MovementCard) gameStage.getSelected().get(0);

        GridLook lookBoard = (GridLook) control.getElementLook(board);
        final int[] ipos = lookBoard.getCellFromSceneLocation(clic);
        if(ipos == null) return null;

        final Coord2D pos = new Coord2D(ipos);
        final int col = (int) pos.getX();
        final int row = (int) pos.getY();

        Logger.info("col : " + col + ", row : " + row);
        // Validate the move
        if (!board.canReachCell(row, col)) return null;

        return simpleActionList.useMovementCard(movementCard, pos);
    }

    private ActionList playHeroCard(Coord2D clic, KoRStageModel gameStage, SimpleActionList simpleActionList) {
        // Get the necessary elements
        final KoRBoard board = gameStage.getBoard();
        final MovementCard movementCard = ContainerElements.getSelectedElement(gameStage, ElementType.MOVEMENT_CARD);

        GridLook lookBoard = (GridLook) control.getElementLook(board);
        final int[] ipos = lookBoard.getCellFromSceneLocation(clic);
        if(ipos == null) return null;

        final Coord2D pos = new Coord2D(ipos);
        final int col = (int) pos.getX();
        final int row = (int) pos.getY();

        Logger.info("col : " + col + ", row : " + row);
        // Validate the move
        if (!board.canReachCell(row, col)) return null;

        final HeroCard heroCard = ContainerElements.getSelectedElement(gameStage, ElementType.HERO_CARD);
        final Pawn pawn = (Pawn) board.getElement(row, col);

        return simpleActionList.useHeroCard(heroCard, movementCard, pawn, pos);
    }

    private boolean cliqueOnBoard(List<GameElement> list, KoRStageModel stageModel) {
        for (GameElement element : list) {
            if (element == stageModel.getBoard()) {
                return true;
            }
        }
        return false;
    }
}

