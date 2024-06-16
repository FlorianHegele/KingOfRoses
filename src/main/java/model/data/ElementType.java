package model.data;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;

public enum ElementType {

    PAWN("pawn", 50),
    MOVEMENT_CARD("direction_card", 51),
    HERO_CARD("hero_card", 52);

    private final String name;
    private final int value;

    ElementType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int register() {
        ElementTypes.register(name, value);
        return ElementTypes.getType(name);
    }

    public boolean isElement(GameElement element) {
        return element.getType() == ElementTypes.getType(name);
    }

}