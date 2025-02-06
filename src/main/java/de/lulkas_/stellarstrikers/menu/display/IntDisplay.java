package de.lulkas_.stellarstrikers.menu.display;

import java.util.function.Supplier;
import java.util.List;

public class IntDisplay extends Display {
    private final Supplier<Integer> toDraw;

    public IntDisplay(int x, int y, Supplier<Integer> toDraw, int gameWidth, int gameHeight) {
        super(x, y, gameWidth, gameHeight);
        this.toDraw = toDraw;
    }

    public List<?> getMiscData() {
        return List.of(0, toDraw.get());
    }

    public List<?> getPosData() {
        return List.of(0f, gameX / 1000f, gameY / 1000f, gameWidth / 1000f, gameHeight / 1000f);
    }
}
