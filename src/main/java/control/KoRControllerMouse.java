package control;

import boardifier.control.*;
import boardifier.model.Coord2D;
import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.animation.AnimationTypes;
import boardifier.view.GridLook;
import boardifier.view.View;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.data.ElementType;
import model.data.GameState;
import model.container.KoRBoard;
import model.container.PawnPot;
import model.KoRStageModel;
import model.data.PlayerData;
import model.element.card.HeroCard;
import model.element.card.MovementCard;

import java.util.List;

/**
 * A basic mouse controller that just grabs the mouse clicks and prints out some informations.
 * It gets the elements of the scene that are at the clicked position and prints them.
 */
public class KoRControllerMouse extends ControllerMouse implements EventHandler<MouseEvent> {

    public KoRControllerMouse(Model model, View view, Controller control) {
        super(model, view, control);
    }

    // TODO : REWRITE THIS FUNCTION
    @Override
    public void handle(MouseEvent event) {
        // if mouse event capture is disabled in the model, just return
        if (!model.isCaptureMouseEvent()) return;

        // get the clic x,y in the whole scene (this includes the menu bar if it exists)
        Coord2D clic = new Coord2D(event.getSceneX(), event.getSceneY());
        // get elements at that position
        List<GameElement> list = control.elementsAt(clic);
        // for debug, uncomment next instructions to display x,y and elements at that postion

        Logger.debug("click in "+event.getSceneX()+","+event.getSceneY());
        for(GameElement element : list) {
            Logger.debug(element.toString());
        }

        PlayerData currentPlayer = PlayerData.getCurrentPlayerData(model);
        KoRStageModel stageModel = (KoRStageModel) model.getGameStage();
        GameState currentState = stageModel.getGameState();

        Logger.info("current state : " + currentState.name());

        // CAN SELECT A MOVEMENT CARD FROM THE STACK OR HIS HAND
        if (currentState == GameState.SELECT_NONE) {
            for (GameElement element : list) {
                if (ElementType.MOVEMENT_CARD.isElement(element)) {
                    MovementCard movementCard = (MovementCard) element;

                    // SELECT A MOVEMENT CARD FROM THE STACK
                    if(movementCard.getOwner() == MovementCard.Owner.STACK) {
                        Logger.info("Movement card found");
                        return;
                    }

                    // SELECT A MOVEMENT CARD FROM HIS HAND
                    if(movementCard.getOwner().isSpecificPlayer(currentPlayer)) {
                        stageModel.setState(GameState.SELECT_MOVEMENT_CARD);
                        element.toggleSelected();
                        return;
                    }
                }
            }

        // CAN SELECT THE DEST OR A HERO CARD OR UNSELECT HIS CARD OR CHANGE HIS MOVEMENT CARD
        } else if (stageModel.getGameState() == GameState.SELECT_MOVEMENT_CARD) {
            // first check if the click is on the current selected pawn. In this case, unselect it
            for (GameElement element : list) {
                if (element.isSelected()) {
                    stageModel.setState(GameState.SELECT_NONE);
                    element.toggleSelected();
                    return;
                }

                // SELECT ANOTHER MOVEMENT CARD
                if (ElementType.MOVEMENT_CARD.isElement(element)) {
                    for(GameElement selected : stageModel.getElements()) {
                        if(selected.isSelected()) {
                            selected.toggleSelected();
                            break;
                        }
                    }
                    element.toggleSelected();
                    return;

                // SELECT A HERO CARD
                } else if (ElementType.HERO_CARD.isElement(element)) {
                    HeroCard heroCard = (HeroCard) element;
                    // SELECT A HERO CARD FROM HIS HAND
                    if(heroCard.getStatus().isOwnedBy(currentPlayer)) {
                        stageModel.setState(GameState.SELECT_MOVEMENT_CARD_HERO);
                        element.toggleSelected();
                        return;
                    }
                }
            }

        // CAN SELECT THE DEST OR UNSELECT THE HERO CARD OR UNSELECT MOVEMENT CARD (AND HERO CARD INDIRECTLY) OR CHANGE HIS MOVEMENT CARD
        } else if (stageModel.getGameState() == GameState.SELECT_MOVEMENT_CARD_HERO) {
            for (GameElement element : list) {
                // UNSELECT
                if (element.isSelected()) {
                    // UNSELECT HERO CARD
                    if (ElementType.HERO_CARD.isElement(element)) {
                        stageModel.setState(GameState.SELECT_MOVEMENT_CARD);
                        element.toggleSelected();

                    // UNSELECT MOVEMENT CARD AND HERO CARD
                    } else if (ElementType.MOVEMENT_CARD.isElement(element)) {
                        stageModel.setState(GameState.SELECT_NONE);
                        stageModel.getSelected().forEach(GameElement::toggleSelected);
                    }
                    return;
                }

                // SELECT AN OTHER MOVEMENT CARD
                if (ElementType.MOVEMENT_CARD.isElement(element)) {
                    element.toggleSelected();

                    for(GameElement selected : stageModel.getSelected()) {
                        if(ElementType.MOVEMENT_CARD.isElement(selected)) {
                            selected.toggleSelected();
                            break;
                        }
                    }
                    return;
                }
            }
        }
    }
}

