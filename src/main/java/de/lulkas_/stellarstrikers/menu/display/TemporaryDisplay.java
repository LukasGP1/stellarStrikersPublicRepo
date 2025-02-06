package de.lulkas_.stellarstrikers.menu.display;

import java.util.List;

public class TemporaryDisplay extends Display {
    private int ticks;
    public boolean visible = false;

    public TemporaryDisplay(int gameX, int gameY, int gameWidth, int gameHeight) {
        super(gameX, gameY, gameWidth, gameHeight);
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

    public List<?> getMiscData() {
        return List.of(0, visible ? 1 : 0);
    }

    public List<?> getPosData() {
        return List.of(0f, gameX / 1000f, gameY / 1000f, gameWidth / 1000f, gameHeight / 1000f);
    }
}
