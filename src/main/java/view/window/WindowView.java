package view.window;

import javafx.scene.layout.Pane;

/**
 * Abstract class representing a view for a window.
 *
 * @param <T> the type of Pane used in this WindowView.
 */
public abstract class WindowView<T extends Pane> {

    protected T pane;
    protected int width;
    protected int height;

    /**
     * Constructs a WindowView with the specified pane and default width and height.
     *
     * @param pane the pane to be used for the window.
     */
    protected WindowView(T pane) {
        this(pane, -1, -1);
    }

    /**
     * Constructs a WindowView with the specified pane, width, and height.
     *
     * @param pane   the pane to be used for the window.
     * @param width  the width of the window.
     * @param height the height of the window.
     */
    protected WindowView(T pane, int width, int height) {
        this.pane = pane;
        this.width = width;
        this.height = height;
    }

    /**
     * Setup the default pane for the window.
     */
    public abstract void createDefaultPane();

    /**
     * Gets the pane used in the window.
     *
     * @return the pane.
     */
    public T getPane() {
        return pane;
    }

    /**
     * Gets the height of the window.
     *
     * @return the height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the width of the window.
     *
     * @return the width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Determines if the size of the window should be adjusted to fit the scene.
     *
     * @return true if the size should be adjusted, false otherwise.
     */
    public boolean sizeToScene() {
        return width == -1 || height == -1;
    }
}
