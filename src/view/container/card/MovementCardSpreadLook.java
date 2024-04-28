package view.container.card;

import boardifier.control.Logger;
import boardifier.model.ContainerElement;
import boardifier.view.GridLook;

// TODO: Adapt to the new game


public class MovementCardSpreadLook extends GridLook {

    public MovementCardSpreadLook(ContainerElement containerElement) {
        super(3, 2, containerElement, 2, 2);
        setVerticalAlignment(ALIGN_MIDDLE);
        setHorizontalAlignment(ALIGN_CENTER);
    }

}