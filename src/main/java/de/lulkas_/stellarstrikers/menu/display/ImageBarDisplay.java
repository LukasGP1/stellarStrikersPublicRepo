package de.lulkas_.stellarstrikers.menu.display;

import java.util.List;
import java.util.function.Supplier;

public class ImageBarDisplay extends Display {
    private final Supplier<Integer> getCount;

    public ImageBarDisplay(int x, int y, Supplier<Integer> getCount, int gameWidth, int gameHeight) {
        super(x, y, gameWidth, gameHeight);
        this.getCount = getCount;
    }

    public java.util.List<?> getMiscData() {
        return java.util.List.of(0, getCount.get());
    }

    public java.util.List<?> getPosData() {
        return List.of(0f, gameX / 1000f, gameY / 1000f, gameWidth / 1000f, gameHeight / 1000f);
    }
}
