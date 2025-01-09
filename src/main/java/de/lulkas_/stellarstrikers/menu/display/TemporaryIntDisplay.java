package de.lulkas_.stellarstrikers.menu.display;

import java.awt.*;
import java.util.function.Supplier;

public class TemporaryIntDisplay extends IntDisplay {
    private int ticks;
    public boolean visible = false;

    public TemporaryIntDisplay(String name1, int x, int y, int size, Color color, String name2, Supplier<Integer> toDraw) {
        super(name1, x, y, size, color, name2, toDraw);
    }

    public void appear(int ticks) {
        visible = true;
        this.ticks = ticks;
    }

    @Override
    public void tick() {
        super.tick();

        if(visible) {
            ticks--;
            if(ticks <= 0) {
                visible = false;
            }
        }
    }

    @Override
    public Graphics draw(Graphics g) {
        if(visible) {
            return super.draw(g);
        } else {
            return g;
        }
    }
}
