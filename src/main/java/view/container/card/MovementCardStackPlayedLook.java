package view.container.card;

import boardifier.model.ContainerElement;
import boardifier.view.GridLook;


public class MovementCardStackPlayedLook extends GridLook {

    public MovementCardStackPlayedLook(ContainerElement containerElement) {
        super(3, 2, containerElement, 2, 1);
        setVerticalAlignment(ALIGN_TOP);
    }

}