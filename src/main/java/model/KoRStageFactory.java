package model;

import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;
import boardifier.model.TextElement;
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
import utils.Arrays;

/**
 * A factory class for creating elements specific to the stage.
 */
public class KoRStageFactory extends StageElementsFactory {

    private final KoRStageModel stageModel;

    /**
     * Constructs a new KoRStageFactory with the specified game stage model.
     *
     * @param gameStageModel The game stage model associated with this factory.
     */
    public KoRStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (KoRStageModel) gameStageModel;
    }

    /**
     * Sets up the stage by creating text elements, element containers, and elements.
     */
    @Override
    public void setup() {

        /*
         CREATE TEXT ELEMENTS
         */

        // create the text that displays the player name and put it in 0,1 in the virtual space
        TextElement playerText = new TextElement(stageModel.getCurrentPlayerName(), stageModel);
        playerText.setLocation(10, 20);
        stageModel.setPlayerName(playerText);

        TextElement movementCardStackText = new TextElement("14", stageModel);
        movementCardStackText.setLocation(2, 7);
        stageModel.setMovementCardStackText(movementCardStackText);

        TextElement textBluePawn = new TextElement("26", stageModel);
        textBluePawn.setLocation(850, 675);
        stageModel.setBluePawnText(textBluePawn);

        TextElement textRedPawn = new TextElement("26", stageModel);
        textRedPawn.setLocation(850, 225);
        stageModel.setRedPawnText(textRedPawn);

        TextElement textBlueCardHero = new TextElement("4", stageModel);
        textBlueCardHero.setLocation(735, 945);
        stageModel.setBlueHeroCardText(textBlueCardHero);

        TextElement textRedCardHero = new TextElement("4", stageModel);
        textRedCardHero.setLocation(15, 1);
        stageModel.setRedHeroCardText(textRedCardHero);


        /*
         CREATE ELEMENT CONTAINERS
         */

        // BOARD
        // create the board, in 10, 5 in the virtual space
        KoRBoard board = new KoRBoard(175, 150, stageModel);
        stageModel.setBoard(board);


        // MOVEMENT CARD STACK
        // create the movement cards stack in 2, 8
        MovementCardStack movementCardStack = new MovementCardStack(20, 250, stageModel);
        stageModel.setMovementCardStack(movementCardStack);

        // create the movement cards stack in 2, 15
        MovementCardStackPlayed movementCardStackPlayed = new MovementCardStackPlayed(20, 400, stageModel);
        stageModel.setMovementCardStackPlayed(movementCardStackPlayed);


        // RED ELEMENT
        //create the red hero cards stack in 10,2 in the virtual space
        HeroCardStack redHeroCardStack = new HeroCardStack(175, 20, stageModel);
        stageModel.setRedHeroCardStack(redHeroCardStack);

        //create the red movements cards spread in 16,1 in the virtual space
        MovementCardSpread redMovementCardsSpread = new MovementCardSpread(PlayerData.PLAYER_RED, 300, 20, stageModel);
        stageModel.setRedMovementCardsSpread(redMovementCardsSpread);

        //create the red pot in 50,8 in the virtual space
        PawnPot redPot = new PawnPot(825, 250, stageModel);
        stageModel.setRedPot(redPot);


        // BLUE ELEMENT
        //create the blue hero cards stack in 35,24 in the virtual space
        HeroCardStack blueHeroCardStack = new HeroCardStack(700, 800, stageModel);
        stageModel.setBlueHeroCardStack(blueHeroCardStack);

        //create the blue movements cards spread in 10,24 in the virtual space
        MovementCardSpread blueMovementCardsSpread = new MovementCardSpread(PlayerData.PLAYER_BLUE, 175, 800, stageModel);
        stageModel.setBlueMovementCardsSpread(blueMovementCardsSpread);

        //create the blue pot in 50,18 in the virtual space
        PawnPot bluePot = new PawnPot(825, 550, stageModel);
        stageModel.setBluePot(bluePot);


        /*
         CREATE ELEMENTS
         */

        // HERO CARD
        ///create red hero cards
        HeroCard[] redHeroCards = new HeroCard[4];
        for (int i = 0; i < 4; i++) {
            redHeroCards[i] = new HeroCard(HeroCard.Status.RED_CARD, stageModel);
        }
        stageModel.setRedHeroCards(redHeroCards);

        // create blue hero cards
        HeroCard[] blueHeroCards = new HeroCard[4];
        for (int i = 0; i < 4; i++) {
            blueHeroCards[i] = new HeroCard(HeroCard.Status.BLUE_CARD, stageModel);
        }
        stageModel.setBlueHeroCards(blueHeroCards);

        // put hero card in stack
        for (int i = 0; i < 4; i++) {
            redHeroCardStack.addElement(redHeroCards[i], 0, 0);
            blueHeroCardStack.addElement(blueHeroCards[i], 0, 0);
        }


        // PAWN
        // create red pawns
        Pawn[] redPawns = new Pawn[26];
        for (int i = 0; i < 26; i++) {
            redPawns[i] = new Pawn(Pawn.Status.RED_PAWN, stageModel);
        }
        stageModel.setRedPawns(redPawns);

        // create blue pawns
        Pawn[] bluePawns = new Pawn[26];
        for (int i = 0; i < 26; i++) {
            bluePawns[i] = new Pawn(Pawn.Status.BLUE_PAWN, stageModel);
        }
        stageModel.setBluePawns(bluePawns);

        // put pawn in pot
        for (int i = 0; i < 26; i++) {
            redPot.addElement(redPawns[i], 0, 0);
            bluePot.addElement(bluePawns[i], 0, 0);
        }

        //create king pawn
        Pawn kingPawn = new Pawn(Pawn.Status.KING_PAWN, stageModel);
        stageModel.setKingPawn(kingPawn);
        //put it in the middle of the board
        board.addElement(kingPawn, 4, 4);


        // MOVEMENT CARD
        // create movement cards deck
        MovementCard[] movementCardDeck = new MovementCard[24];
        MovementCard.Direction[] directions = MovementCard.Direction.values();
        for (int i = 0; i < 24; i++) {
            final MovementCard.Direction direction = directions[i % 8];
            final int step = i / 8 + 1;
            movementCardDeck[i] = new MovementCard(step, direction, stageModel);
        }
        stageModel.setMovementCards(movementCardDeck);

        //shuffle deck
        Arrays.shuffleArray(movementCardDeck, GameConfigurationModel.RANDOM);

        //distributes the cards to the players
        for (int i = 0; i < 5; i++) {
            //get card from the stack
            final MovementCard redMovementCard = movementCardDeck[i];
            redMovementCardsSpread.addElement(redMovementCard, 0, i);

            final MovementCard blueMovementCard = movementCardDeck[i + 5];
            blueMovementCardsSpread.addElement(blueMovementCard, 0, i);
        }

        //put the rest of the cards in the pile
        for (MovementCard movementCard : movementCardDeck) {
            if (movementCard.getOwner() == MovementCard.Owner.STACK)
                movementCardStack.addElement(movementCard, 0, 0);
        }
    }

}
