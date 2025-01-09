package de.lulkas_.stellarstrikers.menu.display;

import java.awt.*;
import java.util.function.Supplier;

public class ThreeIntDisplay extends Display {
    private final String name1, name2, name3, name4;
    private final Supplier<Integer> toDraw1, toDraw2, toDraw3;

    public ThreeIntDisplay(Color color, int size, int x, int y, String name1, Supplier<Integer> toDraw1, String name2, Supplier<Integer> toDraw2, String name3, Supplier<Integer> toDraw3, String name4) {
        super(color, size, x, y, "");
        this.name1 = name1;
        this.name2 = name2;
        this.name3 = name3;
        this.name4 = name4;
        this.toDraw1 = toDraw1;
        this.toDraw2 = toDraw2;
        this.toDraw3 = toDraw3;
    }

    public Graphics draw(Graphics g) {
        this.text = name1 + toDraw1.get() + name2 + toDraw2.get() + name3 + toDraw3.get() + name4;
        return super.draw(g);
    }
}
