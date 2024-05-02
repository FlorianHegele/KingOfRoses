package model;

import boardifier.model.*;
import model.container.KoRBoard;
import model.container.PawnPot;
import model.container.card.MovementCardStack;
import model.container.card.HeroCardSpread;
import model.container.card.MovementCardSpread;
import model.element.Pawn;
import model.element.card.HeroCard;
import model.element.card.MovementCard;

import java.util.Deque;
import java.util.Random;

/**
 * KoRStageModel defines the model for the single stage in "The KoR". Indeed,
 * there are no levels in this game: a party starts and when it's done, the game is also done.
 *
 * KoRStageModel must define all that is needed to manage a party : state variables and game elements.
 * In the present case, there are only 2 state variables that represent the number of pawns to play by each player.
 * It is used to detect the end of the party.
 * For game elements, it depends on what is chosen as a final UI design. For that demo, there are 12 elements used
 * to represent the state : the main board, 2 pots, 8 pawns, and a text for current player.
 *
 * WARNING ! KoRStageModel DOES NOT create itself the game elements because it would prevent the possibility to mock
 * game element classes for unit testing purposes. This is why KoRStageModel just defines the game elements and the methods
 * to set this elements.
 * The instanciation of the elements is done by the KoRStageFactory, which uses the provided setters.
 *
 * KoRStageModel must also contain methods to check/modify the game state when given events occur. This is the role of
 * setupCallbacks() method that defines a callback function that must be called when a pawn is put in a container.
 * This is done by calling onPutInContainer() method, with the callback function as a parameter. After that call, boardifier
 * will be able to call the callback function automatically when a pawn is put in a container.
 * NB1: callback functions MUST BE defined with a lambda expression (i.e. an arrow function).
 * NB2:  there are other methods to defines callbacks for other events (see onXXX methods in GameStageModel)
 * In "The KoR", everytime a pawn is put in the main board, we have to check if the party is ended and in this case, who is the winner.
 * This is the role of computePartyResult(), which is called by the callback function if there is no more pawn to play.
 *
 */
public class KoRStageModel extends GameStageModel {

    // define stage state variables
    private int movementCardStackToPlay;

    private int blueHeroCardToPlay;
    private int redHeroCardToPlay;

    private int bluePawnsToPlay;
    private int redPawnsToPlay;


    // define stage game elements
    private KoRBoard board;

    private HeroCardSpread blueHeroCardSpread;
    private HeroCardSpread redHeroCardSpread;
    private HeroCard[] blueHeroCards;
    private HeroCard[] redHeroCards;

    private MovementCardStack movementCardStack;
    private Deque<MovementCard> movementCardDeck;

    private MovementCardSpread blueMovementCardsSpread;
    private MovementCardSpread redMovementCardsSpread;
    private MovementCard[] blueMovementCards;
    private MovementCard[] redMovementCards;

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

    // Uncomment next line if the example with a main container is used. see end of KoRStageFactory and KoRStageView
    //private ContainerElement mainContainer;

    public KoRStageModel(String name, Model model) {
        super(name, model);
        movementCardStackToPlay = 24;

        blueHeroCardToPlay = 4;
        redHeroCardToPlay = 4;

        bluePawnsToPlay = 26;
        redPawnsToPlay = 26;

        setupCallbacks();
    }

    public KoRBoard getBoard() {
        return board;
    }
    public void setBoard(KoRBoard board) {
        this.board = board;
        addContainer(board);
    }

    public HeroCardSpread getBlueHeroCardSpread() {
        return blueHeroCardSpread;
    }
    public void setBlueHeroCardSpread(HeroCardSpread blueHeroCardSpread) {
        this.blueHeroCardSpread = blueHeroCardSpread;
        addContainer(blueHeroCardSpread);
    }

    public HeroCardSpread getRedHeroCardSpread() {
        return redHeroCardSpread;
    }
    public void setRedHeroCardSpread(HeroCardSpread redHeroCardSpread) {
        this.redHeroCardSpread = redHeroCardSpread;
        addContainer(redHeroCardSpread);
    }

    public HeroCard[] getBlueHeroCards() {
        return blueHeroCards;
    }
    public void setBlueHeroCards(HeroCard[] blueHeroCards) {
        this.blueHeroCards = blueHeroCards;
        for (HeroCard blueHeroCard : blueHeroCards) {
            addElement(blueHeroCard);
        }
    }

    public HeroCard[] getRedHeroCards() {
        return redHeroCards;
    }
    public void setRedHeroCards(HeroCard[] redHeroCards) {
        this.redHeroCards = redHeroCards;
        for (HeroCard redHeroCard : redHeroCards) {
            addElement(redHeroCard);
        }
    }

    public MovementCardStack getMovementCardStack() {
        return movementCardStack;
    }
    public void setMovementCardStack(MovementCardStack movementCardStack) {
        this.movementCardStack = movementCardStack;
        addElement(movementCardStack);
    }

    public Deque<MovementCard> getMovementCardDeck() {
        return movementCardDeck;
    }
    public void setMovementCardDeck(Deque<MovementCard> movementCardDeck) {
        this.movementCardDeck = movementCardDeck;
        for (MovementCard movementCard : movementCardDeck) {
            addElement(movementCard);
        }
    }

    public MovementCardSpread getBlueMovementCardsSpread() {
        return blueMovementCardsSpread;
    }
    public void setBlueMovementCardsSpread(MovementCardSpread blueMovementCardsSpread) {
        this.blueMovementCardsSpread = blueMovementCardsSpread;
        addContainer(blueMovementCardsSpread);
    }
    
    public MovementCardSpread getRedMovementCardsSpread() {
        return redMovementCardsSpread;
    }
    public void setRedMovementCardsSpread(MovementCardSpread redMovementCardsSpread) {
        this.redMovementCardsSpread = redMovementCardsSpread;
        addContainer(redMovementCardsSpread);
    }
    
    public MovementCard[] getBlueMovementCards() {
        return blueMovementCards;
    }
    public void setBlueMovementCards(MovementCard[] blueMovementCards) {
        this.blueMovementCards = blueMovementCards;
        for (MovementCard blueMovementCard : blueMovementCards) {
            addElement(blueMovementCard);
        }
    }

    public MovementCard[] getRedMovementCards() {
        return redMovementCards;
    }
    public void setRedMovementCards(MovementCard[] redMovementCards) {
        this.redMovementCards = redMovementCards;
        for (MovementCard redMovementCard : redMovementCards) {
            addElement(redMovementCard);
        }
    }

    public PawnPot getBluePot() {
        return bluePot;
    }
    public void setBluePot(PawnPot bluePot) {
        this.bluePot = bluePot;
        addContainer(bluePot);
    }

    public PawnPot getRedPot() {
        return redPot;
    }
    public void setRedPot(PawnPot redPot) {
        this.redPot = redPot;
        addContainer(redPot);
    }
    
    public Pawn[] getBluePawns() {
        return bluePawns;
    }
    public void setBluePawns(Pawn[] bluePawns) {
        this.bluePawns = bluePawns;
        for (Pawn bluePawn : bluePawns) {
            addElement(bluePawn);
        }
    }

    public Pawn[] getRedPawns() {
        return redPawns;
    }
    public void setRedPawns(Pawn[] redPawns) {
        this.redPawns = redPawns;
        for (Pawn redPawn : redPawns) {
            addElement(redPawn);
        }
    }

    public Pawn getKingPawn() {
        return kingPawn;
    }
    public void setKingPawn(Pawn kingPawn) {
        this.kingPawn = kingPawn;
        addElement(kingPawn);
    }

    public TextElement getPlayerName() {
        return playerName;
    }
    public void setPlayerName(TextElement playerName) {
        this.playerName = playerName;
        addElement(playerName);
    }

    public TextElement getActionDescription1() {
        return actionDescription1;
    }
    public void setActionDescription1(TextElement actionDescription1) {
        this.actionDescription1 = actionDescription1;
        addElement(actionDescription1);
    }

    public TextElement getActionDescription2() {
        return actionDescription2;
    }
    public void setActionDescription2(TextElement actionDescription2) {
        this.actionDescription2 = actionDescription2;
        addElement(actionDescription2);
    }

    public TextElement getActionDescription3() {
        return actionDescription3;
    }
    public void setActionDescription3(TextElement actionDescription3) {
        this.actionDescription3 = actionDescription3;
        addElement(actionDescription3);
    }

    public TextElement getActionDescription4() {
        return actionDescription4;
    }
    public void setActionDescription4(TextElement actionDescription4) {
        this.actionDescription4 = actionDescription4;
        addElement(actionDescription4);
    }

    public TextElement getMovementCardStackText() {
        return movementCardStackText;
    }
    public void setMovementCardStackText(TextElement movementCardStackText) {
        this.movementCardStackText = movementCardStackText;
        addElement(movementCardStackText);
    }

    public TextElement getBluePawnText() {
        return bluePawnText;
    }
    public void setBluePawnText(TextElement bluePawnText) {
        this.bluePawnText = bluePawnText;
        addElement(bluePawnText);
    }

    public TextElement getRedPawnText() {
        return redPawnText;
    }
    public void setRedPawnText(TextElement redPawnText) {
        this.redPawnText = redPawnText;
        addElement(redPawnText);
    }

    public TextElement getBlueHeroCardText() {
        return blueHeroCardText;
    }
    public void setBlueHeroCardText(TextElement blueHeroCardText) {
        this.blueHeroCardText = blueHeroCardText;
        addElement(blueHeroCardText);
    }

    public TextElement getRedHeroCardText() {
        return redHeroCardText;
    }
    public void setRedHeroCardText(TextElement redHeroCardText) {
        this.redHeroCardText = redHeroCardText;
        addElement(redHeroCardText);
    }

    // TODO : REWRITE THE ENTIER CODE OF THIS FUNCTION
    private void setupCallbacks() {
        onPutInContainer((element, gridDest, rowDest, colDest) -> {
            // just check when pawns are put in 9x9 board
            if (gridDest != board) return;
            Pawn p = (Pawn) element;
            if (p.getStatus() == Pawn.Status.BLUE_PAWN) {
                bluePawnsToPlay--;
            } else if (p.getStatus() == Pawn.Status.RED_PAWN) {
                redPawnsToPlay--;
            }
            if ((bluePawnsToPlay == 0) && (redPawnsToPlay == 0)) {
                computePartyResult();
            }
        });
    }

    // TODO : REWRITE THE ENTIER CODE OF THIS FUNCTION
    private void computePartyResult() {
        int idWinner = new Random().nextInt(2);

        System.out.println("end of game");

        // set the winner
        model.setIdWinner(idWinner);
        // stop de the game
        model.stopStage();
    }

    public int getMovementCardStackToPlay() {
        return movementCardStackToPlay;
    }

    public void setMovementCardStackToPlay(int movementCardStackToPlay) {
        this.movementCardStackToPlay = movementCardStackToPlay;
    }

    public int getBlueHeroCardToPlay() {
        return blueHeroCardToPlay;
    }

    public int getBluePawnsToPlay() {
        return bluePawnsToPlay;
    }

    public int getRedHeroCardToPlay() {
        return redHeroCardToPlay;
    }

    public int getRedPawnsToPlay() {
        return redPawnsToPlay;
    }

    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new KoRStageFactory(this);
    }
}
