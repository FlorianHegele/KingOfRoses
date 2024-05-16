package view.container.card;

import boardifier.model.ContainerElement;
import boardifier.view.GridLook;


public class MovementCardSpreadLook extends GridLook {

    public MovementCardSpreadLook(ContainerElement containerElement) {
        super(3, 4, containerElement, 2, 1);
        setVerticalAlignment(ALIGN_TOP);
        setHorizontalAlignment(ALIGN_CENTER);
    }

}