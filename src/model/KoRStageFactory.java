package model;

import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;
import boardifier.model.TextElement;
import model.container.KoRBoard;
import model.container.PawnPot;
import model.container.card.HeroCardSpread;
import model.container.card.MovementCardSpread;
import model.container.card.MovementCardStack;
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
        text.setLocation(0,1);
        stageModel.setPlayerName(text);

        TextElement movementCardStackText = new TextElement(String.valueOf(stageModel.getMovementCardStackToPlay()), stageModel);
        movementCardStackText.setLocation(28,1);
        stageModel.setMovementCardStackText(movementCardStackText);

        TextElement textBluePawn = new TextElement(String.valueOf(stageModel.getBluePawnsToPlay()), stageModel);
        textBluePawn.setLocation(12,28);
        stageModel.setBluePawnText(textBluePawn);

        TextElement textRedPawn = new TextElement(String.valueOf(stageModel.getRedPawnsToPlay()), stageModel);
        textRedPawn.setLocation(44,28);
        stageModel.setRedPawnText(textRedPawn);

        TextElement textBlueCardHero = new TextElement(String.valueOf(stageModel.getBlueHeroCardToPlay()), stageModel);
        textBlueCardHero.setLocation(3,5);
        stageModel.setBlueHeroCardText(textBlueCardHero);

        TextElement textRedCardHero = new TextElement(String.valueOf(stageModel.getRedHeroCardToPlay()), stageModel);
        textRedCardHero.setLocation(54,5);
        stageModel.setRedHeroCardText(textRedCardHero);

        /*
         CREATE ELEMENT CONTAINERS
         */

        // BOARD
        // create the board, in 1,2 in the virtual space
        KoRBoard board = new KoRBoard(10, 5, stageModel);
        stageModel.setBoard(board);


        // MOVEMENT CARD STACK
        // create the movement cards stack in 0, 2
        MovementCardStack movementCardStack = new MovementCardStack(28, 2, stageModel);
        stageModel.setMovementCardStack(movementCardStack);


        // RED ELEMENT
        //create the red hero cards stack in 1,1 in the virtual space
        HeroCardSpread redHeroCardSpread = new HeroCardSpread(53, 6, stageModel);
        stageModel.setRedHeroCardSpread(redHeroCardSpread);

        //create the red movements cards spread in 2,1 in the virtual space
        MovementCardSpread redMovementCardsSpread = new MovementCardSpread(53,9, stageModel);
        stageModel.setRedMovementCardsSpread(redMovementCardsSpread);

        //create the red pot in 19,2 in the virtual space
        PawnPot redPot = new PawnPot(44,25, stageModel);
        stageModel.setRedPot(redPot);


        // BLUE ELEMENT
        //create the blue hero cards stack in 1,3 in the virtual space
        HeroCardSpread blueHeroCardSpread = new HeroCardSpread(2, 6, stageModel);
        stageModel.setBlueHeroCardSpread(blueHeroCardSpread);

        //create the blue movements cards spread in 2,3 in the virtual space
        MovementCardSpread blueMovementCardsSpread = new MovementCardSpread(2,9, stageModel);
        stageModel.setBlueMovementCardsSpread(blueMovementCardsSpread);

        //create the blue pot in 25,2 in the virtual space
        PawnPot bluePot = new PawnPot(12,25, stageModel);
        stageModel.setBluePot(bluePot);


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
            redHeroCardSpread.addElement(redHeroCards[i], i,0);
            blueHeroCardSpread.addElement(blueHeroCards[i], i,0);
        }


        // PAWN
        //create red pawns
        Pawn[] redPawns = new Pawn[26];
        for(int i=0; i<26; i++) {
            redPawns[i] = new Pawn(Pawn.Status.RED_PAWN, stageModel);
        }
        stageModel.setRedPawns(redPawns);

        //create blue pawns
        Pawn[] bluePawns = new Pawn[26];
        for(int i=0; i<26; i++) {
            bluePawns[i] = new Pawn(Pawn.Status.BLUE_PAWN, stageModel);
        }
        stageModel.setBluePawns(bluePawns);

        // put pawn in pot
        for (int i=0;i<26;i++) {
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

        //create red movement cards
        MovementCard[] redMovementCards = new MovementCard[5];
        for(int i=0; i<5; i++) {
            //get card from the stack
            final MovementCard movementCard = movementCardDeck.pop();
            movementCard.setInStack(false);
            redMovementCards[i] = movementCard;
            redMovementCardsSpread.addElement(movementCard, i,0);
        }
        stageModel.setRedMovementCards(redMovementCards);

        //create blue movement cards
        MovementCard[] blueMovementCards = new MovementCard[5];
        for(int i=0; i<5; i++) {
            //get card from the stack
            final MovementCard movementCard = movementCardDeck.pop();
            movementCard.setInStack(false);
            blueMovementCards[i] = movementCard;
            blueMovementCardsSpread.addElement(movementCard, i,0);
        }
        stageModel.setBlueMovementCards(blueMovementCards);

        int i =0;
        for(MovementCard movementCard : movementCardDeck) {
            movementCardStack.addElement(movementCard, i, 0);
            i++;
        }
        stageModel.setMovementCardDeck(movementCardDeck);
    }

}
