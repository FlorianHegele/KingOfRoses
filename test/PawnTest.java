import boardifier.model.Model;
import model.KoRStageModel;
import model.element.Pawn;
import org.junit.jupiter.api.Test;

import static boardifier.view.ConsoleColor.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PawnTest {

    /*
        Teste la création d'un pion
     */
    @Test
    public void testPawnInitialization(){
        // Création d'un pion bleu
        Pawn pawn = new Pawn(Pawn.Status.BLUE_PAWN,
                // Création d'un plateau de jeu
                new KoRStageModel("test", new Model()));

        // Vérification que le pion crée est le bon
        assertEquals(Pawn.Status.BLUE_PAWN, pawn.getStatus());
        // Vérification que le type du pion est bien 50
        assertEquals(50, pawn.getType());
    }

    /*
        Teste le changement de statut d'un pion
     */
    @Test
    public void testFlipStatus() {
        // Création d'un pion bleu
        Pawn pawn = new Pawn(Pawn.Status.BLUE_PAWN,
                // Création d'un plateau de jeu
                new KoRStageModel("test", new Model()));
        // Changement de statut du pion
        pawn.flipStatus();

        // Vérification du changement de statut
        assertEquals(Pawn.Status.RED_PAWN, pawn.getStatus());
    }

    @Test
    public void testGetBackgroundColor(){
        // Création d'un plateau
        KoRStageModel stage = new KoRStageModel("test", new Model());
        // Création d'un pion bleu, rouge et jaune
        Pawn pawnB = new Pawn(Pawn.Status.BLUE_PAWN, stage);
        Pawn pawnR = new Pawn(Pawn.Status.RED_PAWN, stage);
        Pawn pawnY = new Pawn(Pawn.Status.KING_PAWN, stage);

        // Teste la récupération de la couleur de fond d'un pion
        assertEquals(BLUE_BACKGROUND, pawnB.getStatus().getBackgroundColor());
        assertEquals(RED_BACKGROUND, pawnR.getStatus().getBackgroundColor());
        assertEquals(YELLOW_BACKGROUND, pawnY.getStatus().getBackgroundColor());
    }

    @Test
    public void testGetOpposite(){
        // Création d'un plateau
        KoRStageModel stage = new KoRStageModel("test", new Model());
        // Création d'un pion bleu et rouge
        Pawn pawnB = new Pawn(Pawn.Status.BLUE_PAWN, stage);
        Pawn pawnR = new Pawn(Pawn.Status.RED_PAWN, stage);
        
        // Teste la récupération du statut opposé d'un pion
        assertEquals(Pawn.Status.RED_PAWN, pawnB.getStatus().getOpposite());
        assertEquals(Pawn.Status.BLUE_PAWN, pawnR.getStatus().getOpposite());
    }

    @Test
    public void testGetID(){
        // Création d'un plateau
        KoRStageModel stage = new KoRStageModel("test", new Model());
        // Création d'un pion bleu, rouge et jaune
        Pawn pawnB = new Pawn(Pawn.Status.BLUE_PAWN, stage);
        Pawn pawnR = new Pawn(Pawn.Status.RED_PAWN, stage);
        Pawn pawnY = new Pawn(Pawn.Status.KING_PAWN, stage);

        // Teste la récupération de l'ID d'un pion
        // L'ID est l'entier associé à un statut
        // 0 pour RED_PAWN, 1 pour BLUE_PAWN et 2 pour KING_PAWN
        assertEquals(1, pawnB.getStatus().getID());
        assertEquals(0, pawnR.getStatus().getID());
        assertEquals(2, pawnY.getStatus().getID());
    }


}
