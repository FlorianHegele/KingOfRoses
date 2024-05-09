package view;

import boardifier.control.Logger;
import boardifier.model.GameStageModel;
import boardifier.view.ClassicBoardLook;
import boardifier.view.GameStageView;

import boardifier.view.TextLook;
import model.KoRStageModel;
import model.element.card.MovementCard;
import view.container.PawnPotLook;
import view.container.card.HeroCardStackLook;
import view.container.card.MovementCardSpreadLook;
import view.container.card.MovementCardStackLook;
import view.element.PawnLook;
import view.element.card.HeroCardLook;
import view.element.card.MovementCardLook;

/**
 * KoRStageView has to create all the looks for all game elements created by the KoRStageFactory.
 * The desired UI is the following:
 * player            ╔═╗    ┏━━━┓
 *    A   B   C      ║1║    ┃ 1 ┃
 *  ╔═══╦═══╦═══╗    ╠═╣    ┣━━━┫
 * 1║   ║   ║   ║    ║2║    ┃ 2 ┃
 *  ╠═══╬═══╬═══╣    ╠═╣    ┣━━━┫
 * 2║   ║   ║   ║    ║3║    ┃ 3 ┃
 *  ╠═══╬═══╬═══╣    ╠═╣    ┣━━━┫
 * 3║   ║   ║   ║    ║4║    ┃ 4 ┃
 *  ╚═══╩═══╩═══╝    ╚═╝    ┗━━━┛
 *
 * The UI constraints are :
 *   - the main board has double-segments border, coordinates, and cells of size 2x4
 *   - the black pot has double-segments border, will cells that resize to match what is within (or not)
 *   - the red pot has simple-segment border, and cells have a fixed size of 2x4
 *
 *   main board can be instanciated directly as a ClassicBoardLook.
 *   black pot could be instanciated directly as a TableLook, but in this demo a BlackPotLook subclass is created (in case of we want to modifiy the look in some way)
 *   for red pot, a subclass RedPotLook of GridLook is used, in order to override the method that render the borders.
 */

public class KoRStageView extends GameStageView {
    public KoRStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    @Override
    public void createLooks() {
        KoRStageModel model = (KoRStageModel)gameStageModel;

        /*
        TO FULFILL:
            using the model of the board, pots and pawns
            - create & add the look of the main board using an instance of ClassicBoardLook, with cells of size 4x2
            - create & add the look of the two pots using instances of PawnPotLook with cells of size 4x2
            - crate & add the look of the 8 pawns
         */

        // create look for the text element
        addLook(new TextLook(model.getPlayerName()));
        addLook(new TextLook(model.getActionDescription1()));
        addLook(new TextLook(model.getActionDescription2()));
        addLook(new TextLook(model.getActionDescription3()));
        addLook(new TextLook(model.getActionDescription4()));
        addLook(new TextLook(model.getMovementCardStackText()));
        addLook(new TextLook(model.getBlueHeroCardText()));
        addLook(new TextLook(model.getRedHeroCardText()));
        addLook(new TextLook(model.getRedPawnText()));
        addLook(new TextLook(model.getBluePawnText()));


        /*
         CREATE ELEMENT CONTAINERS LOOK
         */

        // create look for the main board
        addLook(new ClassicBoardLook(2, 4, model.getBoard(), 1, 1, true));

        // create look for the hero cards
        addLook(new HeroCardStackLook(model.getRedHeroCardStack()));
        addLook(new HeroCardStackLook(model.getBlueHeroCardStack()));

        // create look for the movement deck cards
        addLook(new MovementCardStackLook(model.getMovementCardStack()));

        // create look for the movement spread cards
        addLook(new MovementCardSpreadLook(model.getRedMovementCardsSpread()));
        addLook(new MovementCardSpreadLook(model.getBlueMovementCardsSpread()));

        // create look for the pawn pots
        addLook(new PawnPotLook(model.getRedPot()));
        addLook(new PawnPotLook(model.getBluePot()));


        /*
         CREATE ELEMENT LOOK
         */

        // create look for the hero cards
        for(int i=0; i<4; i++) {
            addLook(new HeroCardLook(model.getRedHeroCards()[i]));
            addLook(new HeroCardLook(model.getBlueHeroCards()[i]));
        }

        // create look for the pawns
        for(int i=0; i<26; i++) {
            addLook(new PawnLook(model.getRedPawns()[i]));
            addLook(new PawnLook(model.getBluePawns()[i]));
        }
        addLook(new PawnLook(model.getKingPawn()));

        // create look for the movement card
        for(MovementCard movementCard : model.getMovementCards()) {
            addLook(new MovementCardLook(movementCard));
        }

        Logger.debug("finished creating game stage looks", this);
    }
}
