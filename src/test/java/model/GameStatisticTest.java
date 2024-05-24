package model;

import boardifier.model.Model;
import model.data.AIData;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import utils.ContainerElements;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStatisticTest {

    @Test
    void testGameStatisticWinRate() {
        final GameStatistic gameStatistic = new GameStatistic(AIData.CAMARADE, AIData.GUIDE, 4);

        // Setup Model and StageModel
        final Model model = Mockito.mock(Model.class);
        final KoRStageModel gameStage = Mockito.mock(KoRStageModel.class);
        Mockito.when(model.getGameStage()).thenReturn(gameStage);

        // Winner player blue, blue, red, tie
        Mockito.when(model.getIdWinner()).thenReturn(0, 0, 1, -1);

        Mockito.when(gameStage.getTotalPawnOnBoard(Mockito.any())).thenReturn(0);
        Mockito.when(gameStage.getTotalPlayerPointSimple(Mockito.any())).thenReturn(0);
        Mockito.when(gameStage.getTotalPawnZone(Mockito.any())).thenReturn(0);
        Mockito.when(gameStage.getZoneAverage(Mockito.any())).thenReturn(0.0);

        try (MockedStatic<ContainerElements> utilities = Mockito.mockStatic(ContainerElements.class)) {
            utilities.when(() -> ContainerElements.countElements(Mockito.any())).thenReturn(4);

            gameStatistic.addStatistic(model);
            gameStatistic.addStatistic(model);
            gameStatistic.addStatistic(model);
            gameStatistic.addStatistic(model);
        }

        gameStatistic.calculateStatistic();

        assertEquals(1, gameStatistic.getGameBlocked());
        assertEquals(25, gameStatistic.getGameBlockedRate());
        assertEquals(50, gameStatistic.getBlueStatistic().getWinRate());
        assertEquals(25, gameStatistic.getRedStatistic().getWinRate());
    }

    @Test
    void testGameStatisticTotalPawnOnBoard() {
        final GameStatistic gameStatistic = new GameStatistic(AIData.CAMARADE, AIData.GUIDE, 2);

        // Setup Model and StageModel
        final Model model = Mockito.mock(Model.class);
        final KoRStageModel gameStage = Mockito.mock(KoRStageModel.class);
        Mockito.when(model.getGameStage()).thenReturn(gameStage);

        // Winner player blue, blue, red, tie
        Mockito.when(model.getIdWinner()).thenReturn(0);

        Mockito.when(gameStage.getTotalPawnOnBoard(Mockito.any())).thenReturn(4, 3, 4, 3);
        Mockito.when(gameStage.getTotalPlayerPointSimple(Mockito.any())).thenReturn(0, 0);
        Mockito.when(gameStage.getTotalPawnZone(Mockito.any())).thenReturn(0, 0);
        Mockito.when(gameStage.getZoneAverage(Mockito.any())).thenReturn(0.0, 0.0);

        try (MockedStatic<ContainerElements> utilities = Mockito.mockStatic(ContainerElements.class)) {
            utilities.when(() -> ContainerElements.countElements(Mockito.any())).thenReturn(4);

            gameStatistic.addStatistic(model);
            gameStatistic.addStatistic(model);
        }

        gameStatistic.calculateStatistic();

        assertEquals(8, gameStatistic.getBlueStatistic().getTotalPawnPlay());
        assertEquals(4, gameStatistic.getBlueStatistic().getPawnPlayRate());
        assertEquals(6, gameStatistic.getRedStatistic().getTotalPawnPlay());
        assertEquals(3, gameStatistic.getRedStatistic().getPawnPlayRate());
    }

}
