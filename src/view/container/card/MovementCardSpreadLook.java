package view.container.card;

import boardifier.control.Logger;
import boardifier.model.ContainerElement;
import boardifier.view.GridLook;

// FIXME : Bug décalage élément
// Element décalé de 1


public class MovementCardSpreadLook extends GridLook {

    public MovementCardSpreadLook(ContainerElement containerElement) {
        super(4, 2, containerElement, 2, 1);
        setVerticalAlignment(ALIGN_TOP);
    }

}