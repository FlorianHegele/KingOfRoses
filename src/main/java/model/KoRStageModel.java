package model;

import boardifier.control.ActionPlayer;
import boardifier.model.*;
import control.SimpleActionList;
import model.container.KoRBoard;
import model.container.PawnPot;
import model.container.card.HeroCardStack;
import model.container.card.MovementCardSpread;
import model.container.card.MovementCardStack;
import model.container.card.MovementCardStackPlayed;
import model.data.PlayerData;
import model.element.Pawn;
import model.element.card.HeroCard;
import model.element.card.MovementCard;
import utils.ContainerElements;
import utils.Elements;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * KoRStageModel defines the model for the single stage in "The KoR". Indeed,
 * there are no levels in this game: a party starts and when it's done, the game is also done.
 * <p>
 * KoRStageModel must define all that is needed to manage a party: state variables and game elements.
 * In the present case, there are only 2 state variables that represent the number of pawns to play by each player.
 * It is used to detect the end of the party.
 * For game elements, it depends on what is chosen as a final UI design. For that demo, there are 12 elements used
 * to represent the state: the main board, 2 pots, 8 pawns, and a text for current player.
 * <p>
 * WARNING! KoRStageModel DOES NOT create itself the game elements because it would prevent the possibility to mock
 * game element classes for unit testing purposes. This is why KoRStageModel just defines the game elements and the methods
 * to set these elements.
 * The instantiation of the elements is done by the KoRStageFactory, which uses the provided setters.
 * <p>
 * KoRStageModel must also contain methods to check/modify the game state when given events occur. This is the role of
 * setupCallbacks() method that defines a callback function that must be called when a pawn is put in a container.
 * This is done by calling onPutInContainer() method, with the callback function as a parameter. After that call, boardifier
 * will be able to call the callback function automatically when a pawn is put in a container.
 * NB1: Callback functions MUST BE defined with a lambda expression (i.e. an arrow function).
 * NB2: There are other methods to define callbacks for other events ({@link #setupCallbacks()} methods)
 * In "The KoR", every time a pawn is put in the main board, we have to check if the party is ended and in this case, who is the winner.
 * This is the role of {@link #computePartyResult(boolean)} ()}, which is called by the callback function if there is no more pawn to play.
 */
public class KoRStageModel extends GameStageModel {

    // XY-axis movement for pawns
    private static final int[] dx = {1, -1, 0, 0};
    private static final int[] dy = {0, 0, 1, -1};

    // define stage game elements
    private KoRBoard board;

    private HeroCardStack blueHeroCardStack;
    private HeroCardStack redHeroCardStack;
    private HeroCard[] blueHeroCards;
    private HeroCard[] redHeroCards;

    private MovementCardStack movementCardStack;
    private MovementCardStackPlayed movementCardStackPlayed;
    private MovementCardSpread blueMovementCardsSpread;
    private MovementCardSpread redMovementCardsSpread;
    private MovementCard[] movementCards;

    private PawnPot bluePot;
    private PawnPot redPot;
    private Pawn[] bluePawns;
    private Pawn[] redPawns;

    private Pawn kingPawn;

    // TEXT
    private TextElement playerName;
    private TextElement actionDescription1;
    private TextElement actionDescription2;
    private TextElement actionDescription3;
    private TextElement actionDescription4;
    private TextElement movementCardStackText;
    private TextElement bluePawnText;
    private TextElement redPawnText;
    private TextElement blueHeroCardText;
    private TextElement redHeroCardText;

    /**
     * Constructs a new KoRStageModel with the specified name and model.
     *
     * @param name  the name of the stage.
     * @param model the game model.
     */
    public KoRStageModel(String name, Model model) {
        super(name, model);
        // Sets up callbacks for game events
        setupCallbacks();
    }

    /**
     * Get the main board of the game.
     *
     * @return the game board.
     */
    public KoRBoard getBoard() {
        return board;
    }

    /**
     * Set the main board of the game.
     *
     * @param board the game board.
     */
    public void setBoard(KoRBoard board) {
        this.board = board;
        addContainer(board);
    }

    /**
     * Get the container of blue hero cards.
     *
     * @return the container of blue hero cards.
     */
    public HeroCardStack getBlueHeroCardStack() {
        return blueHeroCardStack;
    }

    /**
     * Set the container of blue hero cards.
     *
     * @param blueHeroCardStack the container of blue hero cards.
     */
    public void setBlueHeroCardStack(HeroCardStack blueHeroCardStack) {
        this.blueHeroCardStack = blueHeroCardStack;
        addContainer(blueHeroCardStack);
    }

    /**
     * Get the container of red hero cards.
     *
     * @return the container of red hero cards.
     */
    public HeroCardStack getRedHeroCardStack() {
        return redHeroCardStack;
    }

    /**
     * Set the container of red hero cards.
     *
     * @param redHeroCardStack the container of red hero cards.
     */
    public void setRedHeroCardStack(HeroCardStack redHeroCardStack) {
        this.redHeroCardStack = redHeroCardStack;
        addContainer(redHeroCardStack);
    }

    /**
     * Get blue hero cards.
     *
     * @return an array of blue hero cards.
     */
    public HeroCard[] getBlueHeroCards() {
        return blueHeroCards;
    }

    /**
     * Set the blue hero cards array
     *
     * @param blueHeroCards an array of the blue hero cards.
     */
    public void setBlueHeroCards(HeroCard[] blueHeroCards) {
        this.blueHeroCards = blueHeroCards;
        for (HeroCard blueHeroCard : blueHeroCards) {
            addElement(blueHeroCard);
        }
    }

    /**
     * Get red hero cards.
     *
     * @return an array of red hero cards.
     */
    public HeroCard[] getRedHeroCards() {
        return redHeroCards;
    }

    /**
     * Set the red hero cards array
     *
     * @param redHeroCards an array the red hero cards.
     */
    public void setRedHeroCards(HeroCard[] redHeroCards) {
        this.redHeroCards = redHeroCards;
        for (HeroCard redHeroCard : redHeroCards) {
            addElement(redHeroCard);
        }
    }

    /**
     * Get the container for undiscovered movement cards.
     *
     * @return the container for undiscovered movement cards.
     */
    public MovementCardStack getMovementCardStack() {
        return movementCardStack;
    }

    /**
     * Set the container for undiscovered movement cards.
     *
     * @param movementCardStack the container for undiscovered movement cards.
     */
    public void setMovementCardStack(MovementCardStack movementCardStack) {
        this.movementCardStack = movementCardStack;
        addContainer(movementCardStack);
    }

    /**
     * Get the container of the stack of movement cards played.
     *
     * @return the container of the stack of movement cards played.
     */
    public MovementCardStackPlayed getMovementCardStackPlayed() {
        return movementCardStackPlayed;
    }

    /**
     * Set the container of movement card played
     *
     * @param movementCardStackPlayed the container of movement cards played
     */
    public void setMovementCardStackPlayed(MovementCardStackPlayed movementCardStackPlayed) {
        this.movementCardStackPlayed = movementCardStackPlayed;
        addContainer(movementCardStackPlayed);
    }

    /**
     * Get a list of movement cards for the selected owner.
     *
     * @param owner the owner of the stack.
     * @return the stack of movement cards played.
     */
    public List<MovementCard> getMovementCards(MovementCard.Owner owner) {
        final List<MovementCard> movementCardList = new ArrayList<>();
        for (MovementCard movementCard : movementCards) {
            if (movementCard.getOwner() == owner) movementCardList.add(movementCard);
        }
        return movementCardList;
    }

    /**
     * Get an array of all movement cards.
     *
     * @return array of all movement cards.
     */
    public MovementCard[] getMovementCards() {
        return movementCards;
    }

    /**
     * Set an array containing all the movement cards.
     *
     * @param movementCards array of all movement cards.
     */
    public void setMovementCards(MovementCard[] movementCards) {
        this.movementCards = movementCards;
        for (MovementCard movementCard : movementCards) {
            addElement(movementCard);
        }
    }

    /**
     * Get the container of blue hero cards.
     *
     * @return the container of blue hero cards.
     */
    public MovementCardSpread getBlueMovementCardsSpread() {
        return blueMovementCardsSpread;
    }

    /**
     * Set the container of blue hero cards.
     *
     * @param blueMovementCardsSpread the container of blue hero cards.
     */
    public void setBlueMovementCardsSpread(MovementCardSpread blueMovementCardsSpread) {
        this.blueMovementCardsSpread = blueMovementCardsSpread;
        addContainer(blueMovementCardsSpread);
    }

    /**
     * Get the container of red hero cards.
     *
     * @return the container of red hero cards.
     */
    public MovementCardSpread getRedMovementCardsSpread() {
        return redMovementCardsSpread;
    }

    /**
     * Set the container of red hero cards.
     *
     * @param redMovementCardsSpread the container of red hero cards.
     */
    public void setRedMovementCardsSpread(MovementCardSpread redMovementCardsSpread) {
        this.redMovementCardsSpread = redMovementCardsSpread;
        addContainer(redMovementCardsSpread);
    }

    /**
     * Get the container of blue pawns.
     *
     * @return the container of blue pawns.
     */
    public PawnPot getBluePot() {
        return bluePot;
    }

    /**
     * Set the blue pawn container.
     *
     * @param bluePot the blue pawn container.
     */
    public void setBluePot(PawnPot bluePot) {
        this.bluePot = bluePot;
        addContainer(bluePot);
    }

    /**
     * Get the container of red pawns.
     *
     * @return the container of red pawns.
     */
    public PawnPot getRedPot() {
        return redPot;
    }

    /**
     * Set the red pawn container.
     *
     * @param redPot the red pawn container.
     */
    public void setRedPot(PawnPot redPot) {
        this.redPot = redPot;
        addContainer(redPot);
    }

    /**
     * Get the blue pawns.
     *
     * @return the blue pawns.
     */
    public Pawn[] getBluePawns() {
        return bluePawns;
    }

    /**
     * Set the blue pawns.
     *
     * @param bluePawns array of blue pawns.
     */
    public void setBluePawns(Pawn[] bluePawns) {
        this.bluePawns = bluePawns;
        for (Pawn bluePawn : bluePawns) {
            addElement(bluePawn);
        }
    }

    /**
     * Get the red pawns.
     *
     * @return the red pawns.
     */
    public Pawn[] getRedPawns() {
        return redPawns;
    }

    /**
     * Set the red pawns.
     *
     * @param redPawns array of red pawns.
     */
    public void setRedPawns(Pawn[] redPawns) {
        this.redPawns = redPawns;
        for (Pawn redPawn : redPawns) {
            addElement(redPawn);
        }
    }

    /**
     * Get the king pawn.
     *
     * @return the king pawns.
     */
    public Pawn getKingPawn() {
        return kingPawn;
    }

    /**
     * Set the king pawn.
     *
     * @param kingPawn the king pawn.
     */
    public void setKingPawn(Pawn kingPawn) {
        this.kingPawn = kingPawn;
        addElement(kingPawn);
    }

    /**
     * Get the player name text.
     *
     * @return the player name text.
     */
    public TextElement getPlayerName() {
        return playerName;
    }

    /**
     * Set the player name text.
     *
     * @param playerName the player name text.
     */
    public void setPlayerName(TextElement playerName) {
        this.playerName = playerName;
        addElement(playerName);
    }

    /**
     * Get the action description 1 text.
     *
     * @return the action description 1 text.
     */
    public TextElement getActionDescription1() {
        return actionDescription1;
    }

    /**
     * Set the action description 1 text.
     *
     * @param actionDescription1 the action description 1 text.
     */
    public void setActionDescription1(TextElement actionDescription1) {
        this.actionDescription1 = actionDescription1;
        addElement(actionDescription1);
    }

    /**
     * Get the action description 2 text.
     *
     * @return the action description 2 text.
     */
    public TextElement getActionDescription2() {
        return actionDescription2;
    }

    /**
     * Set the action description 2 text.
     *
     * @param actionDescription2 the action description 2 text.
     */
    public void setActionDescription2(TextElement actionDescription2) {
        this.actionDescription2 = actionDescription2;
        addElement(actionDescription2);
    }

    /**
     * Get the action description 3 text.
     *
     * @return the action description 3 text.
     */
    public TextElement getActionDescription3() {
        return actionDescription3;
    }

    /**
     * Set the action description 3 text.
     *
     * @param actionDescription3 the action description 3 text.
     */
    public void setActionDescription3(TextElement actionDescription3) {
        this.actionDescription3 = actionDescription3;
        addElement(actionDescription3);
    }

    /**
     * Get the action description 4 text.
     *
     * @return the action description 4 text.
     */
    public TextElement getActionDescription4() {
        return actionDescription4;
    }

    /**
     * Set the action description 4 text.
     *
     * @param actionDescription4 the action description 4 text.
     */
    public void setActionDescription4(TextElement actionDescription4) {
        this.actionDescription4 = actionDescription4;
        addElement(actionDescription4);
    }

    /**
     * Get the movement card container text.
     *
     * @return the movement card container text.
     */
    public TextElement getMovementCardStackText() {
        return movementCardStackText;
    }

    /**
     * Set the movement card container text.
     *
     * @param movementCardStackText the movement card container text. Represent a number
     */
    public void setMovementCardStackText(TextElement movementCardStackText) {
        this.movementCardStackText = movementCardStackText;
        addElement(movementCardStackText);
    }

    /**
     * Get the blue pawns container text.
     *
     * @return the blue pawns container text.
     */
    public TextElement getBluePawnText() {
        return bluePawnText;
    }

    /**
     * Set the blue pawns container text.
     *
     * @param bluePawnText the blue pawns container text. Represent a number
     */
    public void setBluePawnText(TextElement bluePawnText) {
        this.bluePawnText = bluePawnText;
        addElement(bluePawnText);
    }

    /**
     * Get the red pawns container text.
     *
     * @return the red pawns container text.
     */
    public TextElement getRedPawnText() {
        return redPawnText;
    }

    /**
     * Set the red pawns container text.
     *
     * @param redPawnText the red pawns container text. Represent a number
     */
    public void setRedPawnText(TextElement redPawnText) {
        this.redPawnText = redPawnText;
        addElement(redPawnText);
    }

    /**
     * Get the blue hero card container text.
     *
     * @return the blue hero card container text.
     */
    public TextElement getBlueHeroCardText() {
        return blueHeroCardText;
    }

    /**
     * Set the red hero card container text.
     *
     * @param blueHeroCardText the blue hero card container text. Represent a number
     */
    public void setBlueHeroCardText(TextElement blueHeroCardText) {
        this.blueHeroCardText = blueHeroCardText;
        addElement(blueHeroCardText);
    }

    /**
     * Get the red hero card container text.
     *
     * @return the red hero card container text.
     */
    public TextElement getRedHeroCardText() {
        return redHeroCardText;
    }

    /**
     * Set the red hero card container text.
     *
     * @param redHeroCardText the red hero card container text. Represent a number
     */
    public void setRedHeroCardText(TextElement redHeroCardText) {
        this.redHeroCardText = redHeroCardText;
        addElement(redHeroCardText);
    }


    /**
     * Sets up the callback functions for various game events. These callbacks handle
     * actions such as playing a hero card, drawing a movement card, and updating counters.
     * The methods onRemoveFromContainer and onPutInContainer define the behavior for
     * elements being removed from or placed into containers.
     */
    private void setupCallbacks() {
        onRemoveFromContainer((element, containerFrom, rowDest, colDest) -> {
            // ACTION: Play a hero card
            if (element instanceof HeroCard heroCard) {
                // Update the hero card counter
                final TextElement textElement = (heroCard.getStatus() == HeroCard.Status.BLUE_CARD)
                        ? blueHeroCardText
                        : redHeroCardText;
                Elements.updateText(textElement, ContainerElements.countElements(containerFrom));
            }
        });

        onPutInContainer((element, containerDest, rowDest, colDest) -> {
            // ACTION: Draw a movement card from the stack
            if (containerDest instanceof MovementCardSpread) {
                // Change the owner of the card
                final MovementCard.Owner owner = (containerDest == blueMovementCardsSpread)
                        ? MovementCard.Owner.PLAYER_BLUE : MovementCard.Owner.PLAYER_RED;
                final MovementCard movementCard = (MovementCard) element;
                movementCard.setOwner(owner);

                // If the stack is empty, replenish it
                if (getMovementCards(MovementCard.Owner.STACK).isEmpty())
                    new ActionPlayer(model, null, new SimpleActionList(model).redoMovementCardStack()).start();

                // Update the stack counter
                movementCardStackText.setText(String.valueOf(ContainerElements.countElements(movementCardStack)));
            }

            // ACTION: Add a card to the played stack
            else if (containerDest == movementCardStackPlayed) {
                ((MovementCard) element).setOwner(MovementCard.Owner.OUT);
            }

            // ACTION: Add a card to the stack
            else if (containerDest == movementCardStack) {
                final MovementCard movementCard = (MovementCard) element;
                movementCard.setOwner(MovementCard.Owner.STACK);
                movementCardStackText.setText(String.valueOf(ContainerElements.countElements(movementCardStack)));
            }

            // ACTION: Place a pawn on the board
            else if (containerDest == board) {
                Pawn pawn = (Pawn) element;

                // Skip updating counters for the king pawn
                if (pawn.getStatus() == Pawn.Status.KING_PAWN) return;

                // Update the pawn counter
                redPawnText.setText(String.valueOf(ContainerElements.countElements(redPot)));
                bluePawnText.setText(String.valueOf(ContainerElements.countElements(bluePot)));
            }
        });
    }

    /**
     * Determines if a player can make a move.
     *
     * @param playerData The player for whom the check is being made.
     * @return True if the player can make a move, false otherwise.
     */
    public boolean playerCanPlay(PlayerData playerData) {
        final PawnPot pawnPot = getGeneralPot(playerData);

        // If the player has no pawns, they cannot do anything
        if (pawnPot == null) return false;

        final MovementCardSpread movementCardSpread;
        final HeroCardStack heroCardStack;

        // Assign the correct card stacks and spreads based on the player
        if (playerData == PlayerData.PLAYER_BLUE) {
            movementCardSpread = blueMovementCardsSpread;
            heroCardStack = blueHeroCardStack;
        } else {
            movementCardSpread = redMovementCardsSpread;
            heroCardStack = redHeroCardStack;
        }

        // If the player can draw a movement card
        final int countMovementCards = ContainerElements.countElements(movementCardSpread);
        if (countMovementCards < 5) return true;

        // Can the player play a card? If so, which ones?
        final boolean hasHeroCard = ContainerElements.countElements(heroCardStack) > 0;
        final Coord2D kingPos = ContainerElements.getElementPosition(kingPawn, board);
        final int cardRow = 0;
        for (int cardCol = 0; cardCol < countMovementCards; cardCol++) {
            if (movementCardSpread.isEmptyAt(cardRow, cardCol)) continue;

            // Get each movement card of the player
            final MovementCard movementCard = (MovementCard) movementCardSpread.getElement(cardRow, cardCol);
            // Get the potential position of the king with the movement card played
            final Coord2D potentialPos = movementCard.getDirectionVector().add(kingPos);
            final int col = (int) potentialPos.getX();
            final int row = (int) potentialPos.getY();

            // If the potential position cannot be reached, move to the next card
            if (!board.canReachCell(row, col)) continue;

            // Return true if the player can play a movement card
            // or if they can play it with a hero card
            if (board.isEmptyAt(row, col) || (hasHeroCard && !((Pawn) board.getElement(row, col)).getStatus().isOwnedBy(playerData))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the game is stuck, meaning no player can make a move.
     *
     * @return True if the game is stuck, false otherwise.
     */
    public boolean gameIsStuck() {
        return !playerCanPlay(PlayerData.PLAYER_BLUE) && !playerCanPlay(PlayerData.PLAYER_RED);
    }

    /**
     * Computes the result of the game.
     * Determines the winner based on zone points and total pawns placed on the board.
     */
    public int computePartyResult(boolean renderGame) {
        final int idWinner;
        final int redZoneCounter = getTotalPlayerPoint(PlayerData.PLAYER_RED, false);
        final int blueZoneCounter = getTotalPlayerPoint(PlayerData.PLAYER_BLUE, false);

        board.resetReachableCells(true);

        final int redPawnPlaced = getTotalPawnOnBoard(Pawn.Status.RED_PAWN);
        final int bluePawnPlaced = getTotalPawnOnBoard(Pawn.Status.BLUE_PAWN);

        if (redZoneCounter == blueZoneCounter) {
            if (redPawnPlaced == bluePawnPlaced) {
                // Perfect tie
                idWinner = -1;
            } else {
                // Determine the winner based on the player with the most pawns of their color placed on the board
                final PlayerData winner = (redPawnPlaced > bluePawnPlaced) ? PlayerData.PLAYER_RED : PlayerData.PLAYER_BLUE;
                idWinner = winner.getId();
            }
        } else {
            // Determine the winner based on the player with the most zone points
            final PlayerData winner = (redZoneCounter > blueZoneCounter) ? PlayerData.PLAYER_RED : PlayerData.PLAYER_BLUE;
            idWinner = winner.getId();
        }

        if (renderGame) {
            System.out.println("Points rouge : " + redZoneCounter + ", pions total " + redPawnPlaced);
            System.out.println("Points bleu : " + blueZoneCounter + ", pions total " + bluePawnPlaced);
        }
        // Set the winner
        model.setIdWinner(idWinner);
        // Stop de the game
        model.stopStage();
        return idWinner;
    }

    /**
     * Gets the total points of the specified player by considering all cells on the board.
     * Resets reachable cells after calculation.
     *
     * @param playerData The player for whom to calculate the total points.
     * @return The total points of the player.
     */
    public int getTotalPlayerPointSimple(PlayerData playerData) {
        final int playerPoint = getTotalPlayerPoint(playerData, true);
        board.resetReachableCells(true);
        return playerPoint;
    }

    /**
     * Gets the zone points of the specified player for the cell at the specified row and column.
     * Resets reachable cells after calculation.
     *
     * @param playerData The player for whom to calculate the zone points.
     * @param row        The row of the cell.
     * @param col        The column of the cell.
     * @return The zone points of the player for the specified cell.
     */
    public int getPlayerZonePawnSimple(PlayerData playerData, int row, int col) {
        final int playerPoint = getPlayerZonePawn(playerData, row, col, true);
        board.resetReachableCells(true);
        return playerPoint;
    }

    /**
     * Calculates the total points of the specified player by considering all cells on the board.
     *
     * @param playerData          The player for whom to calculate the total points.
     * @param acceptEmptyBaseCell Flag indicating whether to accept empty base cells or not.
     * @return The total points of the player.
     */
    private int getTotalPlayerPoint(PlayerData playerData, boolean acceptEmptyBaseCell) {
        int totalCounter = 0;
        for (int row = 0; row < board.getNbRows(); row++) {
            for (int col = 0; col < board.getNbCols(); col++) {
                final int total = getPlayerZonePawn(playerData, row, col, acceptEmptyBaseCell);

                // Add the square of the neighbor count to the final counter
                totalCounter += total * total;
            }
        }
        return totalCounter;
    }

    /**
     * Calculates the total number of zones owned by a player.
     * This method iterates over the entire game board, checking each cell to determine
     * the number of pawns in each zone owned by the specified player. It counts the total
     * number of zones where the player has at least one pawn.
     *
     * @param playerData The player for whom the total number of zones is being calculated.
     * @return The total number of zones owned by the specified player.
     */
    public int getTotalPawnZone(PlayerData playerData) {
        int totalCounter = 0;
        for (int row = 0; row < board.getNbRows(); row++) {
            for (int col = 0; col < board.getNbCols(); col++) {
                final int total = getPlayerZonePawn(playerData, row, col, false);

                totalCounter += (total > 0) ? 1 : 0;
            }
        }
        board.resetReachableCells(true);
        return totalCounter;
    }


    /**
     * Calculates the average number of pawns a player has per zone.
     * This method iterates over the entire game board, checking each cell to determine
     * the number of pawns in each zone owned by the specified player. It then calculates
     * the average number of pawns per zone.
     *
     * @param playerData The player for whom the average is being calculated.
     * @return The average number of pawns per zone for the specified player.
     * If the player has no zones, the method returns 0.
     */
    public double getZoneAverage(PlayerData playerData) {
        int totalCounter = 0;
        int zoneNumber = 0;
        for (int row = 0; row < board.getNbRows(); row++) {
            for (int col = 0; col < board.getNbCols(); col++) {
                final int total = getPlayerZonePawn(playerData, row, col, false);

                if (total > 0) {
                    totalCounter += total;
                    zoneNumber += 1;
                }
            }
        }
        board.resetReachableCells(true);

        // If there is no zone, then return 0
        if (zoneNumber == 0) return 0;

        return (double) totalCounter / zoneNumber;
    }

    /**
     * Calculates the zone points for the specified player at the given row and column on the board.
     *
     * @param playerData          The player for whom to calculate the zone points.
     * @param row                 The row of the cell.
     * @param col                 The column of the cell.
     * @param acceptEmptyBaseCell Flag indicating whether to accept empty base cells as a starting point.
     * @return The zone points for the player at the specified cell.
     */
    private int getPlayerZonePawn(PlayerData playerData, int row, int col, boolean acceptEmptyBaseCell) {
        final Pawn.Status status = Pawn.Status.getPawnStatus(playerData);
        final Deque<PawnNode> pawnNodes = new LinkedList<>();

        final Pawn pawn = getPlayedPawn(row, col);

        // SI IL N'Y A PAS DE PION ET QU'ON VEUT PAS ACCEPTER LES CASES VIDES COMME POINT
        // DE DÉPART, ALORS RENDRE LA CASE INATTEIGNABLE ET PASSER À LA CASE SUIVANTE
        if (!acceptEmptyBaseCell && pawn == null) {
            board.setCellReachable(row, col, false);
            return 0;
        }

        // If there is no pawn and empty base cells are not accepted as starting points,
        // make the cell unreachable and move to the next cell
        if (!board.canReachCell(row, col) || (pawn != null && pawn.getStatus() != status)) return 0;

        // Make the pawn unreachable
        board.setCellReachable(row, col, false);

        // AJOUTE UNE REFERENCE DU PION DANS UNE LISTE ET INITIALISE LE COMPTEUR DE VOISIN
        pawnNodes.add(new PawnNode(status, row, col));

        int counter = (pawn == null) ? -1 : 0;
        while (!pawnNodes.isEmpty()) {
            // While the list is not empty, add references of reachable neighboring pawns of the same color
            // as the first reference in the list and remove it from the list
            // Also, increment the neighbor count by 1

            counter++;
            pawnNodes.addAll(getNeighbors(pawnNodes.poll()));
        }

        return counter;
    }

    /**
     * Retrieves the neighboring pawn nodes of the specified pawn node.
     *
     * @param pawnNode The pawn node for which to retrieve neighboring nodes.
     * @return The list of neighboring pawn nodes.
     */
    public List<PawnNode> getNeighbors(PawnNode pawnNode) {
        final List<PawnNode> neighbors = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            final int row = dy[i] + pawnNode.row;
            final int col = dx[i] + pawnNode.col;

            // If the next cell cannot be reached or has already been reached,
            // do nothing and move to the next cell
            if (!board.canReachCell(row, col)) continue;

            final Pawn pawn = getPlayedPawn(row, col);

            // If the cell is empty, make it unreachable (if still in the array)
            // and move to the next neighboring cell
            if (pawn == null) {
                board.setCellReachable(row, col, false);
                continue;
            }

            // If the pawn belongs to the opponent, move to the next neighboring cell
            if (pawn.getStatus() != pawnNode.status) continue;

            // Add the pawn to the list and make the cell unreachable
            neighbors.add(new PawnNode(pawnNode.status, row, col));
            board.setCellReachable(row, col, false);
        }

        return neighbors;
    }

    /**
     * Calculates the total number of pawns on the board with the specified status.
     *
     * @param status The status of pawns to count.
     * @return The total number of pawns with the specified status on the board.
     */
    public int getTotalPawnOnBoard(Pawn.Status status) {
        int totalPawn = 0;

        for (int row = 0; row < board.getNbRows(); row++) {
            for (int col = 0; col < board.getNbCols(); col++) {
                final Pawn pawn = getPlayedPawn(row, col);

                // If there is no pawn, move to the next cell
                if (pawn == null) continue;

                // Increment the counter if the pawn's status matches the specified status
                if (pawn.getStatus() == status) totalPawn++;
            }
        }

        return totalPawn;
    }

    /**
     * Retrieves the default stage elements factory for this stage model.
     *
     * @return The default stage elements factory.
     */
    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new KoRStageFactory(this);
    }

    /**
     * Retrieves the pawn played at the specified position on the board.
     *
     * @param row The row index.
     * @param col The column index.
     * @return The pawn played at the specified position, or null if no pawn is played.
     */
    public Pawn getPlayedPawn(int row, int col) {
        for (GameElement gameElement : board.getElements(row, col)) {
            final Pawn pawn = (Pawn) gameElement;
            if (pawn == kingPawn) continue;
            return pawn;
        }
        return null;
    }

    /**
     * Retrieves the pawn pot for the specified player data.
     *
     * @param playerData The player data for which to retrieve the pawn pot.
     * @return The pawn pot associated with the specified player data.
     * @throws IllegalArgumentException if the player is not red or blue.
     */
    public PawnPot getPawnPot(PlayerData playerData) {
        final PawnPot pawnPot;
        switch (playerData) {
            case PLAYER_BLUE -> pawnPot = bluePot;
            case PLAYER_RED -> pawnPot = redPot;
            default -> throw new IllegalArgumentException("PlayerData " + playerData.name() + " not supported");
        }
        return pawnPot;
    }

    /**
     * Retrieves the pawn pot that still has pawns for the specified player data.
     * If the initial pawn pot is empty, it checks the next player's pawn pot.
     *
     * @param playerData The player data for which to retrieve the pawn pot.
     * @return The pawn pot that still has pawns, or null if both are empty.
     * @throws IllegalArgumentException if the player is not red or blue.
     */
    public PawnPot getGeneralPot(PlayerData playerData) {
        PawnPot pawnPot = getPawnPot(playerData);
        if (pawnPot.isEmpty()) {
            pawnPot = getPawnPot(playerData.getNextPlayerData());
            if (pawnPot.isEmpty()) return null;
        }
        return pawnPot;
    }

    /**
     * Represents a node containing information about a pawn.
     */
    public record PawnNode(Pawn.Status status, int row, int col) {
    }
}
