package model;

import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;
import boardifier.model.TextElement;
import model.container.KoRBoard;
import model.container.PawnPot;
import model.container.card.CardStack;
import model.container.card.MovementCardSpread;
import model.element.Pawn;
import model.element.card.HeroCard;
import model.element.card.MovementCard;

import java.util.*;

public class KoRStageFactory extends StageElementsFactory {
    private KoRStageModel stageModel;

    public KoRStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (KoRStageModel) gameStageModel;
    }

    @Override
    public void setup() {

        // create the text that displays the player name and put it in 0,0 in the virtual space
        TextElement text = new TextElement(stageModel.getCurrentPlayerName(), stageModel);
        text.setLocation(20,0);
        stageModel.setPlayerName(text);


        /*
         CREATE ELEMENT CONTAINERS
         */

        // RED ELEMENT (without pawn)
        //create the red hero cards stack in 1,1 in the virtual space
        CardStack redHeroCardsStack = new CardStack(12, 2, stageModel);
        stageModel.setRedHeroCardsStack(redHeroCardsStack);

        // TODO : FIX VISUAL
        //create the red movements cards spread in 2,1 in the virtual space
        MovementCardSpread redMovementCardsSpread = new MovementCardSpread(30,5, stageModel);
        stageModel.setRedMovementCardsSpread(redMovementCardsSpread);


        // MOVEMENT CARD STACK AND BOARD
        // create the movement cards stack in 0, 2
        CardStack movementCardStack = new CardStack(2, 15, stageModel);
        stageModel.setMovementCardStack(movementCardStack);

        // create the board, in 1,2 in the virtual space
        KoRBoard board = new KoRBoard(10, 5, stageModel);
        stageModel.setBoard(board);


        // PAWN POT
        //create the blue pot in 25,2 in the virtual space
        PawnPot bluePot = new PawnPot(55,17, stageModel);
        stageModel.setBluePot(bluePot);

        //create the red pot in 19,2 in the virtual space
        PawnPot redPot = new PawnPot(55,12, stageModel);
        stageModel.setRedPot(redPot);


        // BLUE ELEMENT (without pawn)
        //create the blue hero cards stack in 1,3 in the virtual space
        CardStack blueHeroCardsStack = new CardStack(45, 25, stageModel);
        stageModel.setBlueHeroCardsStack(blueHeroCardsStack);

        // TODO : FIX VISUAL
        //create the blue movements cards spread in 2,3 in the virtual space
        MovementCardSpread blueMovementCardsSpread = new MovementCardSpread(30,20, stageModel);
        stageModel.setBlueMovementCardsSpread(blueMovementCardsSpread);


        /*
         CREATE ELEMENTS
         */

        // HERO CARD
        ///create red hero cards
        HeroCard[] redHeroCards = new HeroCard[4];
        for(int i=0; i<4; i++) {
            redHeroCards[i] = new HeroCard(HeroCard.Status.RED_CARD, stageModel);
        }
        stageModel.setRedHeroCards(redHeroCards);

        ///create blue hero cards
        HeroCard[] blueHeroCards = new HeroCard[4];
        for(int i=0; i<4; i++) {
            blueHeroCards[i] = new HeroCard(HeroCard.Status.BLUE_CARD, stageModel);
        }
        stageModel.setBlueHeroCards(blueHeroCards);

        // put hero card in stack
        for (int i=0;i<4;i++) {
            redHeroCardsStack.addElement(redHeroCards[i], i,0);
            blueHeroCardsStack.addElement(blueHeroCards[i], i,0);
        }


        // PAWN
        //create red pawns
        Pawn[] redPawns = new Pawn[52];
        for(int i=0; i<52; i++) {
            redPawns[i] = new Pawn(Pawn.Status.RED_PAWN, stageModel);
        }
        stageModel.setRedPawns(redPawns);

        //create blue pawns
        Pawn[] bluePawns = new Pawn[52];
        for(int i=0; i<52; i++) {
            bluePawns[i] = new Pawn(Pawn.Status.BLUE_PAWN, stageModel);
        }
        stageModel.setBluePawns(bluePawns);

        // put pawn in pot
        for (int i=0;i<52;i++) {
            redPot.addElement(redPawns[i], i,0);
            bluePot.addElement(bluePawns[i], i,0);
        }

        //create king pawn
        Pawn kingPawn = new Pawn(Pawn.Status.KING_PAWN, stageModel);
        stageModel.setKingPawn(kingPawn);
        //put it in the middle of the board
        board.addElement(kingPawn, 4, 4);



        // MOVEMENT CARD
        //create temp movement cards deck (to shuffle it)
        List<MovementCard> tempMovementCardDeck = new ArrayList<>();
        MovementCard.Direction[] directions = MovementCard.Direction.values();
        for(int i=0; i<24; i++) {
            final MovementCard.Direction direction = directions[i%8];
            final int step = i/8 + 1;
            tempMovementCardDeck.add(new MovementCard(step, direction, stageModel));
        }
        //shuffle stack
        Collections.shuffle(tempMovementCardDeck);

        //create the real movement cards deck
        Deque<MovementCard> movementCardDeck = new ArrayDeque<>(tempMovementCardDeck);
        stageModel.setMovementCardDeck(movementCardDeck);

        //create red movement cards
        MovementCard[] redMovementCards = new MovementCard[5];
        for(int i=0; i<5; i++) {
            //get card from the stack
            redMovementCards[i] = movementCardDeck.pop();
        }
        stageModel.setRedMovementCards(redMovementCards);

        //create blue movement cards
        MovementCard[] blueMovementCards = new MovementCard[5];
        for(int i=0; i<5; i++) {
            //get card from the stack
            blueMovementCards[i] = movementCardDeck.pop();
        }
        stageModel.setBlueMovementCards(blueMovementCards);
    }

}
