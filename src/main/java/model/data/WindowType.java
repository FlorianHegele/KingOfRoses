package model.data;

import view.window.ConfigView;
import view.window.RuleView;
import view.window.WindowView;

public enum WindowType {

    NONE(-1),
    CONFIG(0, ConfigView.class), RULES(1, RuleView.class);

    private final int type;
    private Class<? extends WindowView> windowClass;

    WindowType(int type, Class<? extends WindowView> windowClass) {
        this.type = type;
        this.windowClass = windowClass;
    }

    WindowType(int type) {
        this(type, null);
    }

    public int getType() {
        return type;
    }

    public WindowView generateWindow() throws InstantiationException, IllegalAccessException {
        return windowClass.newInstance();
    }

}
