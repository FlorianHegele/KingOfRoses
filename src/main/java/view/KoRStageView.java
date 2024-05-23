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
import view.container.card.MovementCardStackPlayedLook;
import view.element.PawnLook;
import view.element.card.HeroCardLook;
import view.element.card.MovementCardLook;

/**
 * The KoRStageView class is responsible for creating the visual representation of the game elements
 * produced by the KoRStageFactory. It generates the desired UI layout based on specific constraints.
 * The UI layout includes a main board, blue and red pots, and various other game elements.
 */
public class KoRStageView extends GameStageView {

    /**
     * Constructs a new KoRStageView with the specified name and associated game stage model.
     *
     * @param name           The name of the KoRStageView.
     * @param gameStageModel The game stage model associated with this view.
     */
    public KoRStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    /**
     * Creates the visual representations (looks) for all game elements using the provided model.
     * The method creates looks for the main board, text, pots, pawns, hero cards, and movement cards.
     */
    @Override
    public void createLooks() {
        KoRStageModel model = (KoRStageModel) gameStageModel;

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
        addLook(new ClassicBoardLook(2, 4, model.getBoard(), 1, 1, false));

        // create look for the hero cards
        addLook(new HeroCardStackLook(model.getRedHeroCardStack()));
        addLook(new HeroCardStackLook(model.getBlueHeroCardStack()));

        // create look for the movement deck cards
        addLook(new MovementCardStackLook(model.getMovementCardStack()));
        addLook(new MovementCardStackPlayedLook(model.getMovementCardStackPlayed()));

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
        for (int i = 0; i < 4; i++) {
            addLook(new HeroCardLook(model.getRedHeroCards()[i]));
            addLook(new HeroCardLook(model.getBlueHeroCards()[i]));
        }

        // create look for the pawns
        for (int i = 0; i < 26; i++) {
            addLook(new PawnLook(model.getRedPawns()[i]));
            addLook(new PawnLook(model.getBluePawns()[i]));
        }
        addLook(new PawnLook(model.getKingPawn()));

        // create look for the movement card
        for (MovementCard movementCard : model.getMovementCards()) {
            addLook(new MovementCardLook(movementCard));
        }

        Logger.debug("finished creating game stage looks", this);
    }
}
