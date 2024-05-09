import boardifier.model.Model;
import boardifier.view.ConsoleColor;
import model.KoRStageModel;
import model.element.card.HeroCard;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeroCardTest {
    // Teste l'initialisation d'une carte de héros
    @Test
    public void testHeroCardInitialization() {
        HeroCard card = new HeroCard(HeroCard.Status.BLUE_CARD, new KoRStageModel("test", new Model()));
        HeroCard card2 = new HeroCard(HeroCard.Status.RED_CARD, new KoRStageModel("test", new Model()));

        // Vérification que la carte créée est la bonne
        assertEquals(HeroCard.Status.BLUE_CARD, card.getStatus());
        assertEquals(HeroCard.Status.RED_CARD, card2.getStatus());
        // Vérification que le type de la carte est bien 52
        // 52 est l'entier associé au mot hero_card
        assertEquals(52, card.getType());
        assertEquals(52, card2.getType());
    }

    // Teste la récupération de la couleur de fond d'une carte de héros
    @Test
    public void testGetBackgroundColor() {
        // Création d'un plateau
        KoRStageModel stage = new KoRStageModel("test", new Model());
        // Création d'une carte de héros bleue et rouge
        HeroCard cardB = new HeroCard(HeroCard.Status.BLUE_CARD, stage);
        HeroCard cardR = new HeroCard(HeroCard.Status.RED_CARD, stage);

        // Teste la récupération de la couleur de fond d'une carte de héros
        assertEquals(ConsoleColor.BLUE_BACKGROUND, cardB.getStatus().getBackgroundColor());
        assertEquals(ConsoleColor.RED_BACKGROUND, cardR.getStatus().getBackgroundColor());
    }

}
