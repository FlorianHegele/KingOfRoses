package model;

import boardifier.model.Model;
import model.data.AIData;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import utils.ContainerElements;

class GameStatisticTest {

    @Test
    void testGameStatisticTie() {
        final GameStatistic gameStatistic = new GameStatistic(AIData.CAMARADE, AIData.GUIDE, 2);

        // Setup Model and StageModel
        final Model model = Mockito.mock(Model.class);
        final KoRStageModel gameStage = Mockito.mock(KoRStageModel.class);
        Mockito.when(model.getGameStage()).thenReturn(gameStage);

        // Winner player blue, blue, red, tie
        Mockito.when(model.getIdWinner()).thenReturn(-1);

        try (MockedStatic<ContainerElements> utilities = Mockito.mockStatic(ContainerElements.class)) {
            utilities.when(() -> ContainerElements.countElements(Mockito.any())).thenReturn(0, 0);

            Mockito.when(gameStage.getTotalPawnOnBoard(Mockito.any())).thenReturn(0, 0);
            Mockito.when(gameStage.getTotalPlayerPointSimple(Mockito.any())).thenReturn(0, 0);
            Mockito.when(gameStage.getTotalPawnZone(Mockito.any())).thenReturn(0, 0);
            Mockito.when(gameStage.getZoneAverage(Mockito.any())).thenReturn(0.0, 0.0);

            gameStatistic.addStatistic(model);

            gameStatistic.calculateStatistic();
        }
    }

}
