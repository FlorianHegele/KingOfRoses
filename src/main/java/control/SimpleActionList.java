package control;

import boardifier.control.ActionFactory;
import boardifier.control.Controller;
import boardifier.control.Logger;
import boardifier.model.ContainerElement;
import boardifier.model.Coord2D;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.action.FlipPawn;
import model.GameConfigurationModel;
import model.KoRStageModel;
import model.container.KoRBoard;
import model.container.PawnPot;
import model.container.card.HeroCardStack;
import model.container.card.MovementCardSpread;
import model.data.PlayerData;
import model.element.Pawn;
import model.element.card.HeroCard;
import model.element.card.MovementCard;
import utils.ContainerElements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class facilitates the creation of action lists for the game.
 * It provides methods to use hero cards, movement cards, and handle actions in the game.
 */

public class SimpleActionList {

    private final Model model;
    private final Controller control;
    private final KoRStageModel gameStage;

    private final PlayerData playerData;

    /**
     * Constructs a SimpleActionList with the specified model and player data.
     *
     * @param model      the game model.
     * @param playerData the player data used to generate the moves.
     */
    public SimpleActionList(Model model, Controller control, PlayerData playerData) {
        this.model = model;
        this.control = control;
        this.gameStage = (KoRStageModel) model.getGameStage();
        this.playerData = playerData;

        if (playerData == null) throw new IllegalArgumentException("The player data cannot be null !");
    }

    /**
     * Constructs a SimpleActionList with the specified model.
     * The player data is retrieved from the current player in the model.
     *
     * @param model the game model.
     */
    public SimpleActionList(Model model, Controller control) {
        this(model, control, PlayerData.getCurrentPlayerData(model));
    }

    /**
     * Creates an action list to use a hero card.
     * This includes flipping a pawn, moving the king, and removing the hero card from the stage.
     *
     * @param heroCard     the hero card to be used.
     * @param movementCard the movement card associated with the action.
     * @param pawn         the pawn to be flipped.
     * @param newKingPos   the new position of the king.
     * @return the action list containing all the actions.
     */
    public ActionList useHeroCard(HeroCard heroCard, MovementCard movementCard, Pawn pawn, Coord2D newKingPos) {
        Logger.trace("SimpleActionList::useHeroCard");
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
     * @param newKingPos   the new position of the king.
     * @return the action list containing all the actions.
     */
    public ActionList useMovementCard(MovementCard movementCard, Coord2D newKingPos) {
        Logger.trace("SimpleActionList::useMovementCard");

        final ActionList actionList = new ActionList();

        final int col = (int) newKingPos.getX();
        final int row = (int) newKingPos.getY();


        final Pawn pawn = (Pawn) gameStage.getGeneralPot(playerData).getElement(0, 0);
        if (!pawn.getStatus().isOwnedBy(playerData)) actionList.addSingleAction(new FlipPawn(model, pawn));

        // Add move pawn action
        actionList.addAll(ActionFactory.generatePutInContainer(control, model, pawn, gameStage.getBoard().getName(), row, col));

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
     * @param position  the position in the container where the card will be placed.
     * @return the action list containing all the actions.
     */
    public ActionList pickUpMovementCard(ContainerElement container, Coord2D position) {
        final ActionList actionList = new ActionList();

        final int col = (int) position.getX();
        final int row = (int) position.getY();

        // Get the first movement card from the stack
        final MovementCard movementCard = (MovementCard) gameStage.getMovementCardStack().getElement(0, 0);

        // Move the card from the stack to the specified position in the player's hand
        actionList.addAll(ActionFactory.generatePutInContainer(control, model, movementCard, container.getName(), row, col));

        return actionList;
    }

    /**
     * Adds actions to move the king using a movement card and places the card in the played stack.
     *
     * @param actionList   the action list to which actions will be added.
     * @param movementCard the movement card to be used.
     * @param position     the new position of the king.
     */
    private void useMovementCardOnKing(ActionList actionList, MovementCard movementCard, Coord2D position) {
        final int col = (int) position.getX();
        final int row = (int) position.getY();

        // Move the king pawn on the board
        actionList.addAll(ActionFactory.generateMoveWithinContainer(control, model, gameStage.getKingPawn(), row, col));

        // Remove the movement card from the player hand and place it in the played stack
        actionList.addAll(ActionFactory.generatePutInContainer(control, model, movementCard, gameStage.getMovementCardStackPlayed().getName(), 0, 0));
    }

    /**
     * Gets the possible movement card actions for the player.
     *
     * @return a list of ActionPoints representing the possible actions for the player to play a movement card.
     */
    public List<ActionPoints> getPossibleMovementCards() {
        final List<ActionPoints> actions = new ArrayList<>();

        final KoRBoard board = gameStage.getBoard();
        final PawnPot pawnPot = gameStage.getGeneralPot(playerData);
        final Pawn kingPawn = gameStage.getKingPawn();
        final MovementCardSpread movementCardSpread;
        final HeroCardStack heroCardStack;

        if (playerData == PlayerData.PLAYER_BLUE) {
            movementCardSpread = gameStage.getBlueMovementCardsSpread();
            heroCardStack = gameStage.getBlueHeroCardStack();
        } else {
            movementCardSpread = gameStage.getRedMovementCardsSpread();
            heroCardStack = gameStage.getRedHeroCardStack();
        }

        // If Blue and Red pot is empty, this means that the code cannot be reached because the game should be over
        if (pawnPot.isEmpty())
            throw new IllegalCallerException("Unreachable code, the code to determine whether a player can play must be re-read");

        final int countMovementCards = ContainerElements.countElements(movementCardSpread);
        final Coord2D kingPos = ContainerElements.getElementPosition(kingPawn, board);

        final int cardRow = 0;
        for (int cardCol = 0; cardCol < countMovementCards; cardCol++) {
            if (movementCardSpread.isEmptyAt(cardRow, cardCol)) continue;

            // RÉCUPÈRE CHAQUE CARTE DIRECTION DU JOUEUR
            final MovementCard movementCard = (MovementCard) movementCardSpread.getElement(cardRow, cardCol);
            // RÉCUPÈRE L'EMPLACEMENT POTENTIEL DU ROI AVEC LA CARTE DIRECTION JOUÉE
            final Coord2D potentialPos = movementCard.getDirectionVector().add(kingPos);
            final int col = (int) potentialPos.getX();
            final int row = (int) potentialPos.getY();

            // SI ON NE PEUT PAS ATTEINDRE LA POSITION POTENTIEL, ALORS ON PASSE À LA CARTE SUIVANTE
            if (!board.canReachCell(row, col)) continue;

            // SI L'EMPLACEMENT EST VIDE, ALORS RAJOUTER L'ACTION DU DÉPLACEMENT SIMPLE
            if (board.isEmptyAt(row, col)) {
                actions.add(new ActionPoints(useMovementCard(movementCard, potentialPos), gameStage.getPlayerZonePawnSimple(playerData, row, col)));
            }
        }
        return actions;
    }

    /**
     * Gets the possible hero move actions for the player.
     *
     * @return a list of ActionPoints representing the possible actions for the player to play a hero card.
     */
    public List<ActionPoints> getPossibleHeroMove() {
        final List<ActionPoints> actions = new ArrayList<>();

        final PlayerData opponent = playerData.getNextPlayerData();

        final KoRBoard board = gameStage.getBoard();
        final PawnPot pawnPot = gameStage.getGeneralPot(playerData);
        final Pawn kingPawn = gameStage.getKingPawn();
        final MovementCardSpread movementCardSpread;
        final HeroCardStack heroCardStack;

        if (playerData == PlayerData.PLAYER_BLUE) {
            movementCardSpread = gameStage.getBlueMovementCardsSpread();
            heroCardStack = gameStage.getBlueHeroCardStack();
        } else {
            movementCardSpread = gameStage.getRedMovementCardsSpread();
            heroCardStack = gameStage.getRedHeroCardStack();
        }

        // If Blue and Red pot is empty, this means that the code cannot be reached because the game should be over
        if (pawnPot.isEmpty())
            throw new IllegalCallerException("Unreachable code, the code to determine whether a player can play must be re-read");

        // If the player has no more hero cards, return an empty action list
        if (ContainerElements.countElements(heroCardStack) == 0) return actions;

        final int countMovementCards = ContainerElements.countElements(movementCardSpread);

        final Coord2D kingPos = ContainerElements.getElementPosition(kingPawn, board);
        final int cardRow = 0;
        for (int cardCol = 0; cardCol < countMovementCards; cardCol++) {
            // If there is no card in this slot, move on to the next slot
            if (movementCardSpread.isEmptyAt(cardRow, cardCol)) continue;

            // Get each player's direction card
            final MovementCard movementCard = (MovementCard) movementCardSpread.getElement(cardRow, cardCol);

            // Recovers the potential location of the king with the direction card played
            final Coord2D potentialPos = movementCard.getDirectionVector().add(kingPos);
            final int col = (int) potentialPos.getX();
            final int row = (int) potentialPos.getY();

            // If the potential position can't be reached or there is no pawn
            // then move on to the next card
            if (!board.canReachCell(row, col) || board.isEmptyAt(row, col)) continue;

            // If the pawn is not the player's pawn
            // then add the action from the movement and hero card
            if (!((Pawn) board.getElement(row, col)).getStatus().isOwnedBy(playerData)) {
                actions.add(new ActionPoints(useHeroCard((HeroCard) heroCardStack.getElement(0, 0), movementCard,
                        (Pawn) board.getElement(row, col), potentialPos), gameStage.getPlayerZonePawnSimple(opponent, row, col)));
            }

        }
        return actions;
    }

    /**
     * Gets the possible actions for the player to pick up a movement card.
     *
     * @return a list of ActionList representing the possible actions for the player to pick up a movement card.
     */
    public List<ActionList> getPossibleTakeCardAction() {
        final List<ActionList> actions = new ArrayList<>();
        if (playerData == null) return actions;

        final SimpleActionList simpleActionList = new SimpleActionList(model, control, playerData);

        final PawnPot pawnPot = gameStage.getGeneralPot(playerData);
        final MovementCardSpread movementCardSpread = (playerData == PlayerData.PLAYER_BLUE)
                ? gameStage.getBlueMovementCardsSpread()
                : gameStage.getRedMovementCardsSpread();

        // If Blue and Red pot is empty, this means that the code cannot be reached because the game should be over
        if (pawnPot.isEmpty())
            throw new IllegalCallerException("Unreachable code, the code to determine whether a player can play must be re-read");


        // If the player can take a card from the stack
        final int countMovementCards = ContainerElements.countElements(movementCardSpread);
        if (countMovementCards < 5) {
            // Add the action of picking
            actions.add(simpleActionList.pickUpMovementCard(movementCardSpread));
        }

        return actions;
    }

    /**
     * Generates all possible action lists for the current player.
     *
     * @return a list of all possible action lists.
     */
    public List<ActionList> getPossiblePlayerActions() {
        final KoRStageModel stage = (KoRStageModel) model.getGameStage();
        final List<ActionList> actions = new ArrayList<>();
        if (playerData == null) return actions;

        final SimpleActionList simpleActionList = new SimpleActionList(model, control, playerData);

        final KoRBoard board = stage.getBoard();
        final PawnPot pawnPot = stage.getGeneralPot(playerData);
        final MovementCardSpread movementCardSpread;
        final HeroCardStack heroCardStack;

        if (playerData == PlayerData.PLAYER_BLUE) {
            movementCardSpread = stage.getBlueMovementCardsSpread();
            heroCardStack = stage.getBlueHeroCardStack();
        } else {
            movementCardSpread = stage.getRedMovementCardsSpread();
            heroCardStack = stage.getRedHeroCardStack();
        }

        // If Blue and Red pot is empty, this means that the code cannot be reached because the game should be over
        if (pawnPot.isEmpty())
            throw new IllegalCallerException("Unreachable code, the code to determine whether a player can play must be re-read");

        // If the player can pick up a movement card.
        final int countMovementCards = ContainerElements.countElements(movementCardSpread);
        if (countMovementCards < 5) {
            // Add the action to pick up a movement card.
            actions.add(simpleActionList.pickUpMovementCard(movementCardSpread));
        }

        // If the player has no movement cards, only return the action to pick up a card.
        if (countMovementCards == 0) return actions;

        final boolean hasHeroCard = ContainerElements.countElements(heroCardStack) > 0;
        final Coord2D kingPos = ContainerElements.getElementPosition(stage.getKingPawn(), board);
        final int cardRow = 0;
        for (int cardCol = 0; cardCol < countMovementCards; cardCol++) {
            if (movementCardSpread.isEmptyAt(cardRow, cardCol)) continue;

            // Get each movement card the player has.
            final MovementCard movementCard = (MovementCard) movementCardSpread.getElement(cardRow, cardCol);
            // Calculate the potential position of the king if the movement card is played.
            final Coord2D potentialPos = movementCard.getDirectionVector().add(kingPos);
            final int col = (int) potentialPos.getX();
            final int row = (int) potentialPos.getY();

            // If the potential position is out of bounds, skip this card.
            if (!board.canReachCell(row, col)) continue;

            // If the position is empty, add the simple movement action.
            // Otherwise, if the player has a hero card and the pawn at the position is not theirs,
            // add the movement + hero action.
            if (board.isEmptyAt(row, col)) {
                actions.add(simpleActionList.useMovementCard(movementCard, potentialPos));
            } else if (hasHeroCard && !((Pawn) board.getElement(row, col)).getStatus().isOwnedBy(playerData)) {
                actions.add(simpleActionList.useHeroCard((HeroCard) heroCardStack.getElement(0, 0), movementCard,
                        (Pawn) board.getElement(row, col), potentialPos));
            }
        }
        return actions;
    }

    /**
     * Redoes the movement card stack by reshuffling the played movement
     * cards and putting them back into the stack.
     */
    public ActionList redoMovementCardStack() {
        // Retrieve the played movement cards
        final List<MovementCard> movementCardList = gameStage.getMovementCards(MovementCard.Owner.OUT);

        // Shuffle the played cards
        Collections.shuffle(movementCardList, GameConfigurationModel.RANDOM);

        final ActionList actionList = new ActionList();
        final String containerName = gameStage.getMovementCardStack().getName();

        // Put the played cards back into the stack
        for (MovementCard movementCard : movementCardList) {
            if (movementCard.isInverted()) movementCard.toggleInverted();
            actionList.addAll(ActionFactory.generatePutInContainer(control, model, movementCard, containerName, 0, 0));
        }

        return actionList;
    }

}