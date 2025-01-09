package de.lulkas_.stellarstrikers.menu.display;

import java.awt.*;
import java.util.function.Supplier;

public class IntDisplay extends Display {
    private final String name1, name2;
    private final Supplier<Integer> toDraw;

    public IntDisplay(String name1, int x, int y, int size, Color color, String name2, Supplier<Integer> toDraw) {
        super(color, size, x, y, "");
        this.name1 = name1;
        this.name2 = name2;
        this.toDraw = toDraw;
    }

    public Graphics draw(Graphics g) {
        this.text = name1 + toDraw.get() + name2;
        return super.draw(g);
    }
}
