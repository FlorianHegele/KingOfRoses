package control;

import boardifier.control.ActionFactory;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.animation.AnimationTypes;
import model.container.KoRBoard;
import model.container.PawnPot;
import model.KoRStageModel;
import model.element.Pawn;

import java.awt.*;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class KoRDecider extends Decider {

    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());

    public KoRDecider(Model model, Controller control) {
        super(model, control);
    }

    // TODO : REWRITE THE ENTIER CODE
    @Override
    public ActionList decide() {
        return null;
    }
}
