package control;

import boardifier.control.ActionFactory;
import boardifier.model.ContainerElement;
import boardifier.model.Coord2D;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.action.FlipPawn;
import model.KoRStageModel;
import model.PlayerData;
import model.container.PawnPot;
import model.element.Pawn;
import model.element.card.HeroCard;
import model.element.card.MovementCard;
import utils.ContainerElements;

/**
 * This class facilitates the creation of action lists for the game.
 * It provides methods to use hero cards, movement cards, and manage actions in the game.
 */
public class SimpleActionList {

    private final Model model;
    private final KoRStageModel gameStage;

    /**
     * Constructs a SimpleActionList with the specified model.
     *
     * @param model the game model.
     */
    public SimpleActionList(Model model) {
        this.model = model;
        this.gameStage = (KoRStageModel) model.getGameStage();
    }

    /**
     * Creates an action list to use a hero card.
     * This includes flipping a pawn, moving the king, and removing the hero card from the stage.
     *
     * @param heroCard the hero card to be used.
     * @param movementCard the movement card associated with the action.
     * @param pawn the pawn to be flipped.
     * @param newKingPos the new position of the king.
     * @return the action list containing all the actions.
     */
    public ActionList useHeroCard(HeroCard heroCard, MovementCard movementCard, Pawn pawn, Coord2D newKingPos) {
        final ActionList actionList = new ActionList();

        // Add flip pawn action
        actionList.addSingleAction(new FlipPawn(model, pawn));

        // Add move king and remove movement card actions
        useMovementCardOnKing(actionList, movementCard, newKingPos);

        // Add remove hero card actions
        actionList.addAll(ActionFactory.generateRemoveFromContainer(model, heroCard));
        actionList.addAll(ActionFactory.generateRemoveFromStage(model, heroCard));

        return actionList;
    }

    /**
     * Creates an action list to use a movement card.
     * This includes moving one of the current player's or the opponent's pawns
     * (if the pawn comes from the opponent, a pawn flip action will be added to te the list)
     * moving the king, and removing the movement card.
     *
     * @param movementCard the movement card to be used.
     * @param newKingPos the new position of the king.
     * @param playerData the player data associated with the action.
     * @return the action list containing all the actions.
     */
    public ActionList useMovementCard(MovementCard movementCard, Coord2D newKingPos, PlayerData playerData) {
        final ActionList actionList = new ActionList();

        final int col = (int) newKingPos.getX();
        final int row = (int) newKingPos.getY();

        final Pawn pawn = (Pawn) gameStage.getGeneralPot(playerData).getElement(0, 0);
        if(!pawn.getStatus().isOwnedBy(playerData)) actionList.addSingleAction(new FlipPawn(model, pawn));

        // Add move pawn action
        actionList.addAll(ActionFactory.generatePutInContainer(model, pawn, gameStage.getBoard().getName(), row, col));

        // Add move king and remove movement card actions
        useMovementCardOnKing(actionList, movementCard, newKingPos);

        return actionList;
    }

    /**
     * Creates an action list to pick up a movement card.
     * The card is placed in the first empty position of the specified container.
     *
     * @param container the container to place the picked-up card.
     * @return the action list containing all the actions.
     */
    public ActionList pickUpMovementCard(ContainerElement container) {
        return pickUpMovementCard(container, ContainerElements.getEmptyPosition(container));
    }

    /**
     * Creates an action list to pick up a movement card and place it in the specified position.
     *
     * @param container the container to place the picked-up card.
     * @param position the position in the container where the card will be placed.
     * @return the action list containing all the actions.
     */
    public ActionList pickUpMovementCard(ContainerElement container, Coord2D position) {
        final ActionList actionList = new ActionList();

        final int col = (int) position.getX();
        final int row = (int) position.getY();

        // Get the first movement card from the stack
        final MovementCard movementCard = (MovementCard) gameStage.getMovementCardStack().getElement(0, 0);

        // Move the card from the stack to the specified position in the player's hand
        actionList.addAll(ActionFactory.generatePutInContainer(model, movementCard, container.getName(), row, col));

        return actionList;
    }

    /**
     * Adds actions to move the king using a movement card and places the card in the played stack.
     *
     * @param actionList the action list to which actions will be added.
     * @param movementCard the movement card to be used.
     * @param position the new position of the king.
     */
    private void useMovementCardOnKing(ActionList actionList, MovementCard movementCard, Coord2D position) {
        final int col = (int) position.getX();
        final int row = (int) position.getY();

        // Move the king pawn on the board
        actionList.addAll(ActionFactory.generateMoveWithinContainer(model, gameStage.getKingPawn(), row, col));

        // Remove the movement card from the player hand and place it in the played stack
        actionList.addAll(ActionFactory.generatePutInContainer(model, movementCard, gameStage.getMovementCardStackPlayed().getName(), 0, 0));
    }

}
