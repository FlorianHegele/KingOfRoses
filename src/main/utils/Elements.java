package utils;

import boardifier.model.TextElement;

public class Elements {

    private Elements() {
        throw new IllegalStateException("Utility class");
    }

    public static void updateText(TextElement textElement, int value) {
        final String text = (value == 0) ? "" : String.valueOf(value);
        textElement.setText(text);
    }

}
