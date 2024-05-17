package model.element;

import boardifier.model.Model;
import model.KoRStageModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PawnTest {

    @Test
    public void testGetOpposite() {
        // Création d'un plateau
        KoRStageModel stage = new KoRStageModel("test", new Model());
        // Création d'un pion bleu et rouge
        Pawn pawnB = new Pawn(Pawn.Status.BLUE_PAWN, stage);
        Pawn pawnR = new Pawn(Pawn.Status.RED_PAWN, stage);
        Pawn pawnK = new Pawn(Pawn.Status.KING_PAWN, stage);

        // Teste la récupération du statut opposé d'un pion
        assertEquals(Pawn.Status.RED_PAWN, pawnB.getStatus().getOpposite());
        assertEquals(Pawn.Status.BLUE_PAWN, pawnR.getStatus().getOpposite());
        assertThrows(IllegalCallerException.class, () -> pawnK.getStatus().getOpposite());

    }

}
