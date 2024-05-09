package view.container.card;

import boardifier.model.ContainerElement;
import boardifier.view.GridLook;


public class MovementCardSpreadLook extends GridLook {

    public MovementCardSpreadLook(ContainerElement containerElement) {
        super(3, 2, containerElement, 2, 1);
        setVerticalAlignment(ALIGN_TOP);
    }

}