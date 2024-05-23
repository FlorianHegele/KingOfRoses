package utils;

import boardifier.model.TextElement;

/**
 * The {@code Elements} class provides utility methods for working with graphical elements.
 */
public class Elements {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Elements() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Updates the text of a text element with the specified value.
     *
     * @param textElement the text element to update.
     * @param value       the new value to set as the text of the element.
     */
    public static void updateText(TextElement textElement, int value) {
        final String text = (value == 0) ? "" : String.valueOf(value);
        textElement.setText(text);
    }

}
