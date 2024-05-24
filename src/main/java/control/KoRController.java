package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.control.Logger;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.view.View;
import model.GameConfigurationModel;
import model.KoRStageModel;
import model.container.KoRBoard;
import model.container.PawnPot;
import model.container.card.HeroCardStack;
import model.container.card.MovementCardSpread;
import model.data.AIData;
import model.data.PlayerData;
import model.element.Pawn;
import model.element.card.HeroCard;
import model.element.card.MovementCard;
import utils.ContainerElements;
import utils.Strings;

/**
 * This class controls the game flow, allowing the AI or player to do specific action with {@link ActionList}.
 * It extends the Controller class and manages the main game loop and player turns.
 */
public class KoRController extends Controller {

    private final ConsoleController console;
    private final GameConfigurationModel gameConfigurationModel;

    boolean firstPlayer;
    private ActionList playerActionList;
    boolean sendStop;

    /**
     * Constructs a KoRController with the specified model, view, consoleController, and the gameConfigurationModel.
     *
     * @param model                  the game model.
     * @param view                   the game view.
     * @param consoleController      the console controller for handling console input.
     * @param gameConfigurationModel the configuration model for the game.
     */
    public KoRController(Model model, View view, ConsoleController consoleController, GameConfigurationModel gameConfigurationModel) {
        super(model, view);
        this.gameConfigurationModel = gameConfigurationModel;
        this.console = consoleController;
        this.firstPlayer = true;
    }

    /**
     * Defines what to do within the single stage of the single party.
     * Continuously plays the game until it is ended or the game is stuck.
     */
    public void stageLoop() {
        final boolean gameIsRender = gameConfigurationModel.isRenderGame();
        final KoRStageModel gameStage = (KoRStageModel) model.getGameStage();

        // While the game is not end
        while (!model.isEndStage()) {
            update(gameIsRender);

            final PlayerData playerData = PlayerData.getCurrentPlayerData(model);

            playTurn(gameStage, playerData);

            if(sendStop || gameStage.gameIsStuck()) model.stopStage();
        }

        if(!sendStop) update(true);

        // Print winner and stats of the game
        if(gameStage.gameIsStuck())
            gameStage.computePartyResult(gameConfigurationModel.isRenderGame());

        if (gameIsRender) endGame();
    }

    /**
     * Plays a single turn for the specified player data.
     * Determines whether the current player is a computer or a human and acts accordingly.
     *
     * @param gameStage  the current game stage model.
     * @param playerData the player data for the current player.
     */
    private void playTurn(KoRStageModel gameStage, PlayerData playerData) {
        final boolean gameIsRender = gameConfigurationModel.isRenderGame();

        final ActionPlayer actionPlayer;

        // Retrieve the player who need to play
        final Player p = model.getCurrentPlayer();

        // If the player is a computer
        if (p.getType() == Player.COMPUTER) {
            // Then play the turn automatically
            final AIData ai = gameConfigurationModel.getPlayerDataAIDataMap().get(playerData);
            final Decider decider = ai.getDecider(playerData, model, this);

            actionPlayer = new ActionPlayer(model, this, decider.decide());
        } else {
            boolean ok = false;
            while (!ok) {
                if(gameConfigurationModel.isRenderGame()) System.out.print(p.getName() + " > ");
                // Analyze the human player's input
                final String line = console.getConsoleLine();

                // Check if the player stops the game (e.g., CTRL + D or "stop")
                if (line == null || line.equals("stop")) {
                    sendStop = true;
                    return;
                }

                ok = actionAnalyse(gameStage, playerData, line);
                // If the input is invalid, loop again for new input
                if (!ok && gameIsRender) System.out.println("incorrect instruction. retry !");
            }
            // Retrieve the player's actions and create an ActionPlayer
            actionPlayer = new ActionPlayer(model, this, playerActionList);
        }

        // Execute the specified actions
        actionPlayer.start();

        // If the next player can play, declare the end of the turn to pass to the next player
        if (gameStage.playerCanPlay(playerData.getNextPlayerData())) {
            endOfTurn();
        } else {
            if(gameIsRender) System.out.println("The next player can't play and skip his turn");
        }
    }

    /**
     * Ends the current player's turn and sets the next player.
     * Updates the display to show the new player's name.
     * <p>
     * Played by :
     * <p>
     * - {@link #playTurn(KoRStageModel, PlayerData)}, if next player can play
     * <p>
     * - {@link ActionPlayer#start()}, if the ActionList {@link ActionList#mustDoEndOfTurn()} return true
     */
    @Override
    public void endOfTurn() {
        model.setNextPlayer();
        // Get the new player to display their name
        Player p = model.getCurrentPlayer();
        KoRStageModel stageModel = (KoRStageModel) model.getGameStage();
        stageModel.getPlayerName().setText(p.getName());
    }

    /**
     * Analyzes the player's input and constructs the corresponding action list if the input is valid.
     *
     * @param gameStage  the current game stage model.
     * @param playerData the data of the current player.
     * @param line       the player's input line.
     * @return true if the action is valid and an action list is created, false otherwise.
     */
    private boolean actionAnalyse(KoRStageModel gameStage, PlayerData playerData, String line) {
        final boolean gameIsRender = gameConfigurationModel.isRenderGame();

        if (line.isEmpty() || line.length() > 2) return false;

        final char action = line.charAt(0);
        final int length = line.length();

        final SimpleActionList simpleActionList = new SimpleActionList(model, playerData);
        if (action == 'P') {
            if (length != 1) return false;

            final ContainerElement container = (playerData == PlayerData.PLAYER_BLUE)
                    ? gameStage.getBlueMovementCardsSpread()
                    : gameStage.getRedMovementCardsSpread();

            // Get the first empty position in the player's movement card grid
            final Coord2D coord2D = ContainerElements.getEmptyPosition(container);

            // If the hand of the player is full, do nothing
            if (coord2D == null) {
                if(gameIsRender) System.out.println("You already have more than 5 movement cards.");
                return false;
            }

            playerActionList = simpleActionList.pickUpMovementCard(container, coord2D);
        } else if (action == 'D') {
            if (length != 2) return false;

            // Parse the index from the input and adjust to zero-based index
            final int indexCard = Strings.parseInt(line.substring(1)) - 1;
            // If the indexCard is invalid, do nothing
            if (indexCard < 0 || indexCard > 4) {
                if(gameIsRender) System.out.println("Select a card that you own");
                return false;
            }

            final MovementCardSpread movementCardSpread = (playerData == PlayerData.PLAYER_BLUE)
                    ? gameStage.getBlueMovementCardsSpread()
                    : gameStage.getRedMovementCardsSpread();

            // Get the general pot
            final PawnPot pawnPot = gameStage.getGeneralPot(playerData);
            // If Blue and Red pot is empty, this means that the code cannot be reached because the game should be over
            if (pawnPot == null)
                throw new IllegalCallerException("Unreachable code, the code to determine whether a player can play must be re-read");


            if (movementCardSpread.isEmpty()) {
                if(gameIsRender) System.out.println("You don't have a movement card to play. Draw a card!");
                return false;
            }

            if (movementCardSpread.isEmptyAt(0, indexCard)) {
                if(gameIsRender) System.out.println("Select a card that you own");
                return false;
            }

            // Get the necessary elements
            final KoRBoard board = gameStage.getBoard();
            final MovementCard movementCard = (MovementCard) movementCardSpread.getElement(0, indexCard);
            final GameElement king = gameStage.getKingPawn();

            // Calculate the new position for the king based on the movement card's direction vector
            final Coord2D pos = ContainerElements.getElementPosition(king, board)
                    .add(movementCard.getDirectionVector());

            final int col = (int) pos.getX();
            final int row = (int) pos.getY();

            // Validate the move
            if (!board.canReachCell(row, col)) {
                if(gameIsRender) System.out.println("Your pawn  is going outside the board.");
                return false;
            }

            if(!board.isEmptyAt(row, col)) {
                if(gameIsRender) System.out.println("You need to used a hero card to play this movement card.");
                return false;
            }

            playerActionList = simpleActionList.useMovementCard(movementCard, pos);
        } else if (action == 'H') {
            if (length != 2) return false;

            // Parse the index from the input and adjust to zero-based index
            final int indexCard = Strings.parseInt(line.substring(1)) - 1;
            // If the indexCard is invalid, do nothing
            if (indexCard < 0 || indexCard > 4) {
                if(gameIsRender) System.out.println("Select a card that you own");
                return false;
            }

            final MovementCardSpread movementCardSpread;
            final HeroCardStack heroCardStack;
            if (playerData == PlayerData.PLAYER_BLUE) {
                movementCardSpread = gameStage.getBlueMovementCardsSpread();
                heroCardStack = gameStage.getBlueHeroCardStack();
            } else {
                movementCardSpread = gameStage.getRedMovementCardsSpread();
                heroCardStack = gameStage.getRedHeroCardStack();
            }

            if (heroCardStack.isEmpty()) {
                if(gameIsRender) System.out.println("You no longer have any hero cards to play!");
                return false;
            }

            if (movementCardSpread.isEmpty()) {
                if(gameIsRender) System.out.println("You no longer have any movement cards to play! Draw a card!");
                return false;
            }

            if (movementCardSpread.isEmptyAt(0, indexCard)) {
                if(gameIsRender) System.out.println("Select a card that you own");
                return false;
            }

            // Get the necessary elements
            final KoRBoard board = gameStage.getBoard();
            final HeroCard heroCard = (HeroCard) heroCardStack.getElement(0, 0);
            final MovementCard movementCard = (MovementCard) movementCardSpread.getElement(0, indexCard);
            final GameElement king = gameStage.getKingPawn();

            // Calculate the new position for the king based on the movement card's direction vector
            final Coord2D pos = ContainerElements.getElementPosition(king, board)
                    .add(movementCard.getDirectionVector());

            final int col = (int) pos.getX();
            final int row = (int) pos.getY();

            // Validate the move
            if (!board.canReachCell(row, col)) {
                if(gameIsRender) System.out.println("Your pawn is going outside the board.");
                return false;
            }

            if (board.isEmptyAt(row, col)){
                if(gameIsRender) System.out.println("A hero card cannot be used on an empty cell.");
                return false;
            }

            // Get the pawn to be flipped and check its ownership
            final Pawn pawn = (Pawn) board.getElement(row, col);
            if (pawn.getStatus().isOwnedBy(playerData)) {
                if(gameIsRender) System.out.println("You cannot use a hero card on your own pawn.");
                return false;
            }

            playerActionList = simpleActionList.useHeroCard(heroCard, movementCard, pawn, pos);
        } else {
            return false;
        }

        Logger.info(playerActionList.toString());
        return true;
    }

}