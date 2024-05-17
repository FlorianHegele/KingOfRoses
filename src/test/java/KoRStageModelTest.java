import boardifier.control.StageFactory;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.Model;
import boardifier.view.View;
import control.ConsoleController;
import control.KoRController;
import model.GameConfigurationModel;
import model.KoRStageModel;
import model.PlayerData;
import model.container.KoRBoard;
import model.container.PawnPot;
import model.container.card.HeroCardStack;
import model.container.card.MovementCardSpread;
import model.container.card.MovementCardStack;
import model.container.card.MovementCardStackPlayed;
import model.element.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import boardifier.model.GameException;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


class KoRStageModelTest {
    private KoRBoard board2;
    private KoRStageModel stage1;
    private KoRStageModel stage2;
    private HeroCardStack stack1;
    private HeroCardStack stack2;
    private GameStageModel gameStageModel;



    @BeforeEach
    public void setUp() {
        Model model = new Model();

        // Création du plateau de jeu et des piles de cartes
        gameStageModel = new KoRStageModel("test", model);
        board2 = new KoRBoard(0, 0, gameStageModel);
        stage1 = new KoRStageModel("test", model);
        stage2 = new KoRStageModel("test", model);
        stack1 = new HeroCardStack(1, 2, gameStageModel);
        stack2 = new HeroCardStack(0, 3, gameStageModel);

        // Configuration des mocks pour KoRStageModel
        koRStageModel.setBoard(mock(KoRBoard.class));
        koRStageModel.setBlueHeroCardStack(mock(HeroCardStack.class));
        koRStageModel.setRedHeroCardStack(mock(HeroCardStack.class));
        koRStageModel.setMovementCardStack(mock(MovementCardStack.class));
        koRStageModel.setMovementCardStackPlayed(mock(MovementCardStackPlayed.class));
        koRStageModel.setBlueMovementCardsSpread(mock(MovementCardSpread.class));
        koRStageModel.setRedMovementCardsSpread(mock(MovementCardSpread.class));
        koRStageModel.setBluePot(mock(PawnPot.class));
        koRStageModel.setRedPot(mock(PawnPot.class));


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
        final ConsoleController consoleController = new ConsoleController();
        final GameConfigurationModel gameConfigurationModel = new GameConfigurationModel(model);
        final KoRController control = new KoRController(model, korView, consoleController, gameConfigurationModel);
        control.setFirstStageName("kor");
        try {
            control.startGame();
        } catch (GameException e) {
            System.out.println("Cannot start the game. Abort");
        }
    }
    private KoRStageModel koRStageModel;

    @Test
    void testComputePartyResult() {
        // Mock des pions pour les joueurs rouge et bleu
        Pawn redPawn = mock(Pawn.class);
        when(redPawn.getStatus()).thenReturn(Pawn.Status.RED_PAWN);
        Pawn bluePawn = mock(Pawn.class);
        when(bluePawn.getStatus()).thenReturn(Pawn.Status.BLUE_PAWN);

        // Simulation des pions placés sur le plateau
        List<GameElement> elementsList = new ArrayList<>();
        elementsList.add(redPawn);
        elementsList.add(redPawn);
        elementsList.add(bluePawn);
        elementsList.add(bluePawn);

        doReturn(elementsList).when(koRStageModel).getElements();
        when(koRStageModel.getPlayedPawn(anyInt(), anyInt())).thenReturn(redPawn, redPawn, bluePawn, bluePawn);

        // Test pour un résultat d'égalité
        koRStageModel.computePartyResult();
        assertEquals(-1, koRStageModel.getModel().getIdWinner());

        // Test pour un joueur rouge gagnant
        koRStageModel.computePartyResult();
        when(koRStageModel.getPlayerPoint(PlayerData.PLAYER_RED)).thenReturn(5);
        when(koRStageModel.getPlayerPoint(PlayerData.PLAYER_BLUE)).thenReturn(0);
        assertEquals(0, koRStageModel.getModel().getIdWinner());

        // Test pour un joueur bleu gagnant
        when(koRStageModel.getPlayerPoint(PlayerData.PLAYER_RED)).thenReturn(3);
        when(koRStageModel.getPlayerPoint(PlayerData.PLAYER_BLUE)).thenReturn(5);
        koRStageModel.computePartyResult();
        assertEquals(1, koRStageModel.getModel().getIdWinner());
    }

}












