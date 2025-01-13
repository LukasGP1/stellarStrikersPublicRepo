package de.lulkas_.stellarstrikers.menu.display;

import java.awt.*;

public class TemporaryDisplay extends Display {
    private int ticks;
    public boolean visible = false;

    public TemporaryDisplay(Color color, int fontSize, int gameX, int gameY, String text, int gameWidth, int gameHeight) {
        super(color, fontSize, gameX, gameY, text, gameWidth, gameHeight);
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
