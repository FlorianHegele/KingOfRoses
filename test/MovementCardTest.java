import boardifier.model.GameStageModel;
import boardifier.model.Model;
import model.KoRStageModel;
import model.element.card.MovementCard;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MovementCardTest {

    private GameStageModel gameStageModel;
    private MovementCard northCard;
    private MovementCard eastCard;
    private MovementCard southCard;
    private MovementCard westCard;
    private MovementCard northeastCard;
    private MovementCard southeastCard;
    private MovementCard southwestCard;
    private MovementCard northwestCard;


    //Initialisation du test
    public void setUp() {
        gameStageModel = new KoRStageModel("test", new Model());
        northCard = new MovementCard(2, MovementCard.Direction.NORTH, gameStageModel);
        eastCard = new MovementCard(1, MovementCard.Direction.EAST, gameStageModel);
        southCard = new MovementCard(3, MovementCard.Direction.SOUTH, gameStageModel);
        westCard = new MovementCard(2, MovementCard.Direction.WEST, gameStageModel);
        northeastCard = new MovementCard(1, MovementCard.Direction.NORTHEAST, gameStageModel);
        southeastCard = new MovementCard(1, MovementCard.Direction.SOUTHEAST, gameStageModel);
        southwestCard = new MovementCard(2, MovementCard.Direction.SOUTHWEST, gameStageModel);
        northwestCard = new MovementCard(3, MovementCard.Direction.NORTHWEST, gameStageModel);

    }

    @Test
    //Teste qu'une carte de mouvement est bien initialisée
    public void testMovementCardInit(){
        setUp();
        assertEquals(51, northCard.getType());
        assertEquals(51, eastCard.getType());
        assertEquals(51, southCard.getType());
        assertEquals(51, westCard.getType());
        assertEquals(51, northeastCard.getType());
        assertEquals(51, southeastCard.getType());
        assertEquals(51, southwestCard.getType());
        assertEquals(51, northwestCard.getType());


    }

    @Test
    //Teste de la méthode permettant de savoir si une carte est dans la pile
    public void testIsInStack() {
        setUp();
        // Vérifie que la carte est dans la pile
        assertTrue(northCard.isInStack());
        assertTrue(eastCard.isInStack());
        assertTrue(southCard.isInStack());
        assertTrue(westCard.isInStack());
        assertTrue(northeastCard.isInStack());
        assertTrue(southeastCard.isInStack());
        assertTrue(southwestCard.isInStack());
        assertTrue(northwestCard.isInStack());

        // Retire la carte de la pile
        northCard.setInStack(false);
        eastCard.setInStack(false);
        southCard.setInStack(false);
        westCard.setInStack(false);
        northeastCard.setInStack(false);
        southeastCard.setInStack(false);
        southwestCard.setInStack(false);
        northwestCard.setInStack(false);
        // Vérifie que la carte n'est plus dans la pile
        assertFalse(northCard.isInStack());
        assertFalse(eastCard.isInStack());
        assertFalse(southCard.isInStack());
        assertFalse(westCard.isInStack());
        assertFalse(northeastCard.isInStack());
        assertFalse(southeastCard.isInStack());
        assertFalse(southwestCard.isInStack());
        assertFalse(northwestCard.isInStack());

    }



    @Test
    //Teste de la méthode permettant de récupérer le nombre de pas d'une carte
    public void testGetStep() {
        setUp();
        assertEquals(2, northCard.getStep());
        assertEquals(1, eastCard.getStep());
        assertEquals(3, southCard.getStep());
        assertEquals(2, westCard.getStep());
        assertEquals(1, northeastCard.getStep());
        assertEquals(1, southeastCard.getStep());
        assertEquals(2, southwestCard.getStep());
        assertEquals(3, northwestCard.getStep());

    }


    @Test
    //Teste de la méthode permettant de récupérer le symbole de la direction
    public void testGetStepSymbole() {
        setUp();
        assertEquals("\u2191", MovementCard.Direction.NORTH.getSymbole());
        assertEquals("\u2192", MovementCard.Direction.EAST.getSymbole());
        assertEquals("\u2193", MovementCard.Direction.SOUTH.getSymbole());
        assertEquals("\u2190", MovementCard.Direction.WEST.getSymbole());
        assertEquals("\u2197", MovementCard.Direction.NORTHEAST.getSymbole());
        assertEquals("\u2198", MovementCard.Direction.SOUTHEAST.getSymbole());
        assertEquals("\u2199", MovementCard.Direction.SOUTHWEST.getSymbole());
        assertEquals("\u2196", MovementCard.Direction.NORTHWEST.getSymbole());

    }


    @Test
    //Teste de la méthode permettant de récupérer la direction de la carte
    public void testGetDirection() {
        setUp();
        assertEquals(MovementCard.Direction.NORTH, northCard.getDirection());
        assertEquals(MovementCard.Direction.EAST, eastCard.getDirection());
        assertEquals(MovementCard.Direction.SOUTH, southCard.getDirection());
        assertEquals(MovementCard.Direction.WEST, westCard.getDirection());
        assertEquals(MovementCard.Direction.NORTHEAST, northeastCard.getDirection());
        assertEquals(MovementCard.Direction.SOUTHEAST, southeastCard.getDirection());
        assertEquals(MovementCard.Direction.SOUTHWEST, southwestCard.getDirection());
        assertEquals(MovementCard.Direction.NORTHWEST, northwestCard.getDirection());

    }

    @Test
    //Teste de la méthode permettant de récupérer la direction opposée
    public void testGetOppositeDirection() {
        setUp();
        assertEquals(MovementCard.Direction.SOUTH, northCard.getDirection().getOpposite());
        assertEquals(MovementCard.Direction.WEST, eastCard.getDirection().getOpposite());
        assertEquals(MovementCard.Direction.NORTH, southCard.getDirection().getOpposite());
        assertEquals(MovementCard.Direction.EAST, westCard.getDirection().getOpposite());
        assertEquals(MovementCard.Direction.SOUTHWEST, northeastCard.getDirection().getOpposite());
        assertEquals(MovementCard.Direction.NORTHWEST, southeastCard.getDirection().getOpposite());
        assertEquals(MovementCard.Direction.NORTHEAST, southwestCard.getDirection().getOpposite());
        assertEquals(MovementCard.Direction.SOUTHEAST, northwestCard.getDirection().getOpposite());
    }


    @Test
    //Teste de la méthode permettant de récupérer la représentation du nombre de pas
    public void testGetStepRepresentation() {
        setUp();
        assertEquals('\u2161', northCard.getStepRepresentation());
        assertEquals('\u2160', eastCard.getStepRepresentation());
        assertEquals('\u2162', southCard.getStepRepresentation());
        assertEquals('\u2161', westCard.getStepRepresentation());
        assertEquals('\u2160', northeastCard.getStepRepresentation());
        assertEquals('\u2160', southeastCard.getStepRepresentation());
        assertEquals('\u2161', southwestCard.getStepRepresentation());
        assertEquals('\u2162', northwestCard.getStepRepresentation());
    }


}
