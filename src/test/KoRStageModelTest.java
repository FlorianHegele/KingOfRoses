import boardifier.control.StageFactory;
import boardifier.model.GameStageModel;
import boardifier.model.Model;
import boardifier.view.View;
import control.KoRController;
import model.KoRStageModel;
import model.container.KoRBoard;
import model.container.card.HeroCardStack;
import model.element.card.MovementCard;
import org.junit.jupiter.api.Test;
import boardifier.model.GameException;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


class KoRStageModelTest {
    private KoRBoard board2;
    private KoRStageModel stage1;
    private KoRStageModel stage2;
    private HeroCardStack stack1;
    private HeroCardStack stack2;

    public void setUp() {
        GameStageModel gameStageModel = new KoRStageModel("test", new Model());
        KoRBoard board1 = new KoRBoard(0, 0, gameStageModel);
        stage1 = new KoRStageModel("test", new Model());
        stage2 = new KoRStageModel("test", new Model());
        stack1 = new HeroCardStack(1,2, gameStageModel);
        stack2 = new HeroCardStack(0,3, gameStageModel);

    }
    @Test
    public void testKoRStageModelInit() {
        setUp();
        assertEquals("test", stage1.getName());
        assertEquals("test", stage2.getName());
        assertEquals(1, stack1.getX());
        assertEquals(2, stack1.getY());
        assertEquals(0, stack2.getX());
        assertEquals(3, stack2.getY());

    }

    @Test
    public void testsetBlueHeroCardStack() {
        setUp();
        stage1.setBlueHeroCardStack(stack1);
        assertEquals(stack1, stage1.getBlueHeroCardStack());
    }



    public void launchGame() {
        Model model = new Model();
        model.addHumanPlayer("player1");
        model.addHumanPlayer("player2");
        StageFactory.registerModelAndView("kor", "model.KoRStageModel", "view.KoRStageView");
        View korView = new View(model);
        KoRController control = new KoRController(model, korView);
        control.setFirstStageName("kor");
        try {
            control.startGame();
        } catch (GameException e) {
            System.out.println("Cannot start the game. Abort");
        }

    }

    @Test
    public void testsetupCallbacks(){
        setUp();
        launchGame();




}}
