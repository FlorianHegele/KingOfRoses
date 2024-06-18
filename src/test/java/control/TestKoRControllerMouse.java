package control;

import boardifier.model.Coord2D;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import javafx.scene.layout.Pane;
import model.KoRStageModel;
import model.data.GameState;
import model.element.card.MovementCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import utils.ContainerElements;
import view.KoRView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestKoRControllerMouse {

    private KoRStageModel stageModel;
    private Model model;
    private KoRController controller;
    private KoRView view;
    private KoRControllerMouse mouseController;

    @BeforeEach
    void createStageModel() {
        // Create the model
        model = new Model();
        model.addHumanPlayer("player1");
        model.addHumanPlayer("player2");

        stageModel = new KoRStageModel("", model);
        model.setGameStage(stageModel);

        stageModel.getDefaultElementFactory().setup();

        view = Mockito.mock(KoRView.class);
        controller = Mockito.mock(KoRController.class);

        Pane pane = new Pane();
        Mockito.when(view.getRootPane()).thenReturn(pane);

        mouseController = new KoRControllerMouse(model, view, controller);
    }

    @Test
    void testEmptySelection() {
        final Coord2D clic = new Coord2D(0, 0);

        mouseController.handleClickPos(clic);

        assertEquals(0, stageModel.getSelected().size());
    }

    @Test
    void testEmptyAfterMovementCardSelection() {
        Mockito.when(controller.elementsAt(Mockito.any())).thenReturn(List.of(stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0)), List.of());

        final Coord2D clic = new Coord2D(0, 0);

        mouseController.handleClickPos(clic);
        mouseController.handleClickPos(clic);

        assertEquals(1, stageModel.getSelected().size());
    }

    @Test
    void testMovementCardSelection() {
        Mockito.when(controller.elementsAt(Mockito.any())).thenReturn(List.of(stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0)));

        final Coord2D clic = new Coord2D(0, 0);
        mouseController.handleClickPos(clic);

        assertEquals(1, stageModel.getSelected().size());
    }

    @Test
    void testHeroCardSelection() {
        Mockito.when(controller.elementsAt(Mockito.any())).thenReturn(List.of(stageModel.getBlueHeroCards()[0]));

        final Coord2D clic = new Coord2D(0, 0);
        mouseController.handleClickPos(clic);

        assertEquals(0, stageModel.getSelected().size());
    }

    @Test
    void testHeroCardAfterMovementCardSelection() {
        Mockito.when(controller.elementsAt(Mockito.any())).thenReturn(
                List.of(stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0)),
                List.of(stageModel.getBlueHeroCards()[0])
        );

        final Coord2D clic = new Coord2D(0, 0);
        mouseController.handleClickPos(clic);
        mouseController.handleClickPos(clic);

        assertEquals(2, stageModel.getSelected().size());
    }

    @Test
    void testUnselectHeroCardSelection() {
        Mockito.when(controller.elementsAt(Mockito.any())).thenReturn(
                List.of(stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0)),
                List.of(stageModel.getBlueHeroCards()[0]),
                List.of(stageModel.getBlueHeroCards()[0])
        );

        final Coord2D clic = new Coord2D(0, 0);
        mouseController.handleClickPos(clic);
        mouseController.handleClickPos(clic);
        mouseController.handleClickPos(clic);

        assertEquals(1, stageModel.getSelected().size());
    }

    @Test
    void testUnselectMovementCard() {
        Mockito.when(controller.elementsAt(Mockito.any())).thenReturn(List.of(stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0)));

        final Coord2D clic = new Coord2D(0, 0);
        mouseController.handleClickPos(clic);
        mouseController.handleClickPos(clic);

        assertEquals(0, stageModel.getSelected().size());
    }

    @Test
    void testSwitchMovementCard() {
        Mockito.when(controller.elementsAt(Mockito.any())).thenReturn(
                List.of(stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0)),
                List.of(stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(1))
        );

        final Coord2D clic = new Coord2D(0, 0);
        mouseController.handleClickPos(clic);
        mouseController.handleClickPos(clic);

        assertEquals(1, stageModel.getSelected().size());
        assertEquals(stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(1), stageModel.getSelected().get(0));
    }

    @Test
    void testUnselectMovementCardWithHeroCardSelected() {
        Mockito.when(controller.elementsAt(Mockito.any())).thenReturn(
                List.of(stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0)),
                List.of(stageModel.getBlueHeroCards()[0]),
                List.of(stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0))
        );

        final Coord2D clic = new Coord2D(0, 0);
        mouseController.handleClickPos(clic);
        mouseController.handleClickPos(clic);
        mouseController.handleClickPos(clic);

        assertEquals(0, stageModel.getSelected().size());
    }

    @Test
    void testSelectOtherMovementCardWithHeroCardSelected() {
        Mockito.when(controller.elementsAt(Mockito.any())).thenReturn(
                List.of(stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0)),
                List.of(stageModel.getBlueHeroCards()[0]),
                List.of(stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(1))
        );

        final Coord2D clic = new Coord2D(0, 0);
        mouseController.handleClickPos(clic);
        mouseController.handleClickPos(clic);
        mouseController.handleClickPos(clic);

        assertEquals(2, stageModel.getSelected().size());
        assertFalse(stageModel.getSelected().contains(stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(0)));
        assertTrue(stageModel.getSelected().contains(stageModel.getMovementCards(MovementCard.Owner.PLAYER_BLUE).get(1)));
    }

    @Test
    void testPickupMovementCardWithFullHand() {
        final MovementCard movementCard = stageModel.getMovementCards(MovementCard.Owner.STACK).get(0);

        Mockito.when(controller.elementsAt(Mockito.any())).thenReturn(List.of(movementCard));

        final Coord2D clic = new Coord2D(0, 0);
        final ActionList actionList = mouseController.handleClickPos(clic);

        assertEquals(0, stageModel.getSelected().size());
        assertNull(actionList);
    }

    @Test
    void testPickupMovementCardWithNotFullHand() {
        final MovementCard movementCard = stageModel.getMovementCards(MovementCard.Owner.STACK).get(0);

        Mockito.when(controller.elementsAt(Mockito.any())).thenReturn(List.of(movementCard));

        final Coord2D clic = new Coord2D(0, 0);
        final ActionList actionList;
        try(MockedStatic<ContainerElements> containerElements = Mockito.mockStatic(ContainerElements.class)) {
            containerElements.when(() -> ContainerElements.getEmptyPosition(Mockito.any())).thenReturn(new Coord2D(0, 0));
            actionList = mouseController.handleClickPos(clic);
        }

        assertNotNull(actionList);
        assertEquals(0, stageModel.getSelected().size());
    }

    @Test
    void testMovementCardSelectionAdverse() {
        Mockito.when(controller.elementsAt(Mockito.any())).thenReturn(List.of(stageModel.getMovementCards(MovementCard.Owner.PLAYER_RED).get(0)));

        final Coord2D clic = new Coord2D(0, 0);
        mouseController.handleClickPos(clic);

        assertEquals(0, stageModel.getSelected().size());
    }

    @Test
    void testHeroCardSelectionAdverse() {
        Mockito.when(controller.elementsAt(Mockito.any())).thenReturn(List.of(stageModel.getRedHeroCards()[0]));

        final Coord2D clic = new Coord2D(0, 0);
        mouseController.handleClickPos(clic);

        assertEquals(0, stageModel.getSelected().size());
    }
}
