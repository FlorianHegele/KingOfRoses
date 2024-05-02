
import boardifier.model.Model;
import model.KoRStageModel;
import model.element.card.HeroCard;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeroCardTest {
    // Teste l'initialisation d'une carte de héros
    @Test
    public void testHeroCardInitialization() {
        HeroCard card = new HeroCard(HeroCard.Status.BLUE_CARD, new KoRStageModel("test", new Model()));
        assertEquals(HeroCard.Status.BLUE_CARD, card.getStatus());
    }

}
