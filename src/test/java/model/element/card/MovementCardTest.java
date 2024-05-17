package model.element.card;

import boardifier.model.GameStageModel;
import model.KoRStageModel;
import org.junit.jupiter.api.Test;

public class MovementCardTest {

    @Test
    public void testMovementCard() {
        GameStageModel gameStageModel = new KoRStageModel("", null);
        MovementCard movementCard = new MovementCard(1, MovementCard.Direction.NORTH, gameStageModel);
    }

}
