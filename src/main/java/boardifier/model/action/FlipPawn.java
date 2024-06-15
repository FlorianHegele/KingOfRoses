package boardifier.model.action;

import boardifier.model.Model;
import boardifier.model.animation.AnimationTypes;
import model.element.Pawn;


public class FlipPawn extends GameAction {
    
    protected double factor; // a speed in pixel/ms or the whole duration, see LinearMoveAnimation

    // construct an action with an animation
    public FlipPawn(Model model, Pawn pawn, String animationName, double factor) {
        super(model, pawn, animationName);
        this.factor = factor;
    }

    public FlipPawn(Model model, Pawn pawn) {
        this(model, pawn, AnimationTypes.NONE, 0);
    }

    @Override
    public Pawn getElement() {
        return (Pawn) super.getElement();
    }

    public void execute() {
        getElement().flipStatus();
        onEndCallback.execute();
    }

    protected void createAnimation() {
    }
}
