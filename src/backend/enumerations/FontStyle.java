package backend.enumerations;

import java.awt.*;

public enum FontStyle {
    PLAIN(Font.PLAIN),
    BOLD(Font.BOLD),
    ITALIC(Font.ITALIC);

    private final int value;

    FontStyle(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
