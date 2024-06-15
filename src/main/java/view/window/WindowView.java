package view.window;

import javafx.scene.layout.Pane;

public abstract class WindowView<T extends Pane> {

    protected T pane;
    protected int width;
    protected int height;

    protected WindowView(T pane) {
        this(pane, -1, -1);
    }

    protected WindowView(T pane, int width, int height) {
        this.pane = pane;
        this.width = width;
        this.height = height;
    }

    public abstract void createDefaultPane();

    public T getPane() {
        return pane;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean sizeToScene() {
        return width == -1 || height == -1;
    }
}
