package model.element;

import boardifier.model.Model;
import model.KoRStageModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class PawnTest {

    @Test
    void testGetOpposite() {
        final Pawn.Status redPawnStatus = Pawn.Status.RED_PAWN;
        final Pawn.Status bluePawnStatus = Pawn.Status.BLUE_PAWN;
        final Pawn.Status kingPawnStatus = Pawn.Status.KING_PAWN;

        // Teste la récupération du statut opposé d'un pion
        assertEquals(redPawnStatus, bluePawnStatus.getOpposite());
        assertEquals(bluePawnStatus, redPawnStatus.getOpposite());
        assertThrows(IllegalCallerException.class, kingPawnStatus::getOpposite);
    }

}
