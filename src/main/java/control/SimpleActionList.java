package control;

import boardifier.control.ActionFactory;
import boardifier.model.ContainerElement;
import boardifier.model.Coord2D;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.action.FlipPawn;
import model.KoRStageModel;
import model.element.Pawn;
import model.element.card.HeroCard;
import model.element.card.MovementCard;
import utils.ContainerElements;

public class SimpleActionList {

    private final Model model;
    private final KoRStageModel gameStage;

    public SimpleActionList(Model model) {
        this.model = model;
        this.gameStage = (KoRStageModel) model.getGameStage();
    }

    public ActionList useHeroCard(HeroCard heroCard, MovementCard movementCard, Pawn pawn, Coord2D newKingPos) {
        final ActionList actionList = new ActionList();

        // ADD FLIP PAWN ACTION
        actionList.addSingleAction(new FlipPawn(model, pawn));

        // ADD MOVE KING AND REMOVE MOVEMENT CARD ACTIONS
        useMovementCardOnKing(actionList, movementCard, newKingPos);

        // ADD REMOVE HERO CARD ACTIONS
        actionList.addAll(ActionFactory.generateRemoveFromContainer(model, heroCard));
        actionList.addAll(ActionFactory.generateRemoveFromStage(model, heroCard));

        return actionList;
    }

    public ActionList useMovementCard(MovementCard movementCard, Pawn pawn, Coord2D newKingPos) {
        final ActionList actionList = new ActionList();

        final int col = (int) newKingPos.getX();
        final int row = (int) newKingPos.getY();

        // ADD MOVE PAWN ACTION
        actionList.addAll(ActionFactory.generatePutInContainer(model, pawn, gameStage.getBoard().getName(), row, col));

        // ADD MOVE KING AND REMOVE MOVEMENT CARD ACTIONS
        useMovementCardOnKing(actionList, movementCard, newKingPos);

        return actionList;
    }

    public ActionList pickUpMovementCard(ContainerElement container) {
        return pickUpMovementCard(container, ContainerElements.getEmptyPosition(container));
    }

    public ActionList pickUpMovementCard(ContainerElement container, Coord2D position) {
        final ActionList actionList = new ActionList();

        final int col = (int) position.getX();
        final int row = (int) position.getY();

        // RÉCUPÈRE LA 1ÈRE CARTE MOUVEMENT DE LA PILE
        final MovementCard movementCard = (MovementCard) gameStage.getMovementCardStack().getElement(0, 0);

        // DÉPLACE LA CARTE DE LA PILE DANS LA POSITION INDIQUÉE DU JOUEUR
        actionList.addAll(ActionFactory.generatePutInContainer(model, movementCard, container.getName(), row, col));

        return actionList;
    }

    private void useMovementCardOnKing(ActionList actionList, MovementCard movementCard, Coord2D position) {
        final int col = (int) position.getX();
        final int row = (int) position.getY();

        // BOUGE LE PION DU ROI SUR LE PLATEAU
        actionList.addAll(ActionFactory.generateMoveWithinContainer(model, gameStage.getKingPawn(), row, col));

        // ENLEVER LA CARTE DÉPLACEMENT DU JOUEUR
        // Note: Joue l'événement removeFromContainer pour lire l'event dans le Model
        //       et joue ensuite l'event removeFromStage pour enlever la carte de l'affichage

        actionList.addAll(ActionFactory.generateRemoveFromContainer(model, movementCard));
        actionList.addAll(ActionFactory.generateRemoveFromStage(model, movementCard));
    }

}
