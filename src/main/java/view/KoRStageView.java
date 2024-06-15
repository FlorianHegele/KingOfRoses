package view;

import boardifier.control.Logger;
import boardifier.model.GameStageModel;
import boardifier.view.GameStageView;
import boardifier.view.TextLook;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.KoRStageModel;
import model.element.card.MovementCard;
import view.container.card.HeroCardStackLook;
import view.container.KoRBoardLook;
import view.container.card.MovementCardSpreadLook;
import view.container.card.MovementCardStackLook;
import view.container.PawnPotLook;
import view.container.card.MovementCardStackPlayedLook;
import view.element.PawnLook;
import view.element.card.HeroCardLook;
import view.element.card.MovementCardLook;

public class KoRStageView extends GameStageView {

    public KoRStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    @Override
    public void createLooks() {
        KoRStageModel model = (KoRStageModel) gameStageModel;

        // create look for the text element
        addLook(new TextLook(24, Color.BLACK, model.getPlayerName()));
        addLook(new TextLook(24, Color.BLACK, model.getMovementCardStackText()));
        addLook(new TextLook(24, Color.BLACK, model.getBlueHeroCardText()));
        addLook(new TextLook(24, Color.BLACK, model.getRedHeroCardText()));
        addLook(new TextLook(24, Color.BLACK, model.getRedPawnText()));
        addLook(new TextLook(24, Color.BLACK, model.getBluePawnText()));

         /*
          CREATE ELEMENT CONTAINERS LOOK
         */

        // create look for the main board
        addLook(new KoRBoardLook(200, model.getBoard()));

        // create look for the hero cards
        addLook(new HeroCardStackLook(100, 80, model.getRedHeroCardStack()));
        addLook(new HeroCardStackLook(100, 80, model.getBlueHeroCardStack()));

        // create look for the movement deck cards
        addLook(new MovementCardStackLook(120, 100, model.getMovementCardStack()));
        addLook(new MovementCardStackPlayedLook(120, 100, model.getMovementCardStackPlayed()));

        // create look for the movement spread cards
        addLook(new MovementCardSpreadLook(100, 400, model.getRedMovementCardsSpread()));
        addLook(new MovementCardSpreadLook(100, 400, model.getBlueMovementCardsSpread()));

        // create look for the pawn pots
        addLook(new PawnPotLook(80, 80, model.getBluePot()));
        addLook(new PawnPotLook(80, 80, model.getRedPot()));


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
            addLook(new PawnLook(25, model.getRedPawns()[i]));
            addLook(new PawnLook(25, model.getBluePawns()[i]));
        }
        addLook(new PawnLook(25, model.getKingPawn()));

        // create look for the movement card
        for (MovementCard movementCard : model.getMovementCards()) {
            addLook(new MovementCardLook(movementCard));
        }

        Logger.debug("finished creating game stage looks", this);
    }
}
