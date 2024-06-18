package boardifier.model.action;

import boardifier.model.Model;
import boardifier.model.animation.AnimationTypes;
import model.element.Pawn;


public class FlipPawn extends GameAction {
    
    // construct an action with an animation
    public FlipPawn(Model model, Pawn pawn) {
        super(model, pawn, AnimationTypes.NONE);
    }

    @Override
    public Pawn getElement() {
        return (Pawn) super.getElement();
    }

    public void execute() {
        getElement().flipStatus();
        onEndCallback.execute();
    }

    protected void createAnimation() {}

}
